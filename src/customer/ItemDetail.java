package customer;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ItemDetail extends javax.swing.JFrame {

    String name;
    String price;
    String imagePath;
    int quantity = 1;
    
    private JFrame previousFrame;

    javax.swing.JLabel lblQty = new javax.swing.JLabel("1");
    javax.swing.JButton btnMinus = new javax.swing.JButton("-");
    javax.swing.JButton btnPlus = new javax.swing.JButton("+");
    
    

    public ItemDetail() {
        initComponents();
    }
    
   public ItemDetail(String pName, String pPrice, String pPath, JFrame parent) {
        this.previousFrame = parent; // Save the parent frame
        
        initComponents();
        initQuantityLogic();
        
        this.name = pName;
        this.price = pPrice;
        this.imagePath = pPath;

        Name.setText(name); 
        if (price != null) {
            Price.setText(price.contains("₱") ? price : "₱ " + price);
        }

        scaleAndSetImage(imagePath);
        loadVariantsFromDatabase(pName);
        loadProductStats(pName);
    }
    
    private void loadVariantsFromDatabase(String productName) {
        variantPanel.removeAll(); 
        variantPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10)); 

        try (Connection conn = config.db.mycon()) {
            String sql = "SELECT p_image_path FROM products WHERE p_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, productName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String pathFromDB = rs.getString("p_image_path");
                addThumbnailToPanel(pathFromDB);
            }
            
        } catch (Exception e) {
            System.err.println("Database Error loading variants: " + e.getMessage());
            addThumbnailToPanel(imagePath);
        }

        variantPanel.revalidate();
        variantPanel.repaint();
    }
    
    private void addThumbnailToPanel(String path) {
        File fileCheck = new File(path);
        if (!fileCheck.exists() && !path.equals(imagePath)) return;

        javax.swing.JLabel thumb = new javax.swing.JLabel();
        thumb.setPreferredSize(new Dimension(60, 60));
        thumb.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(230, 230, 230)));
        thumb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            thumb.setIcon(new ImageIcon(img));

            thumb.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    scaleAndSetImage(path);
                    imagePath = path;      
                }
            });
            variantPanel.add(thumb);
        } catch (Exception e) {
            System.err.println("Thumb error: " + e.getMessage());
        }
    }

    private void scaleAndSetImage(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            
            // Part C: Smooth Scaling
            // We use the actual size of the JLabel Image
            int width = Image.getWidth() > 0 ? Image.getWidth() : 318;
            int height = Image.getHeight() > 0 ? Image.getHeight() : 308;

            Image img = icon.getImage();
            // SCALE_SMOOTH is the key to professional looking photos
            Image scaledImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            
            Image.setIcon(new ImageIcon(scaledImg));
            Image.setText(""); // Remove "Image" text if it exists
        } catch (Exception e) {
            System.err.println("Scaling Error: " + e.getMessage());
        }
    }
    
    private void initQuantityLogic() {
        // 1. Set Hand Cursor for the labels to act like buttons
        plus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // 2. Logic for Plus
        plus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quantity++;
                qty.setText(String.valueOf(quantity));
            }
        });

        // 3. Logic for Minus
        minus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (quantity > 1) {
                    quantity--;
                    qty.setText(String.valueOf(quantity));
                }
            }
        });
    }
    
    private void loadProductStats(String productName) {
        try (Connection conn = config.db.mycon()) {
            // Query both the product details and the count of 'Completed' orders for 'Sold'
            String sql = "SELECT p_rating, (SELECT COUNT(*) FROM orders WHERE p_name = ? AND status = 'Completed') as total_sold " +
                         "FROM products WHERE p_name = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, productName);
            pst.setString(2, productName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String ratingValue = rs.getString("p_rating");
                int soldCount = rs.getInt("total_sold");

                // If rating is NULL in DB, default to 5.0
                String displayRating = (ratingValue == null || ratingValue.isEmpty()) ? "5.0" : ratingValue.replace(" Stars", ".0");

                rate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/star.png"))); 
                rate.setText(displayRating + " (" + soldCount + ")");
                ttlsold.setText(soldCount + " Sold");
            }

            // After loading stats, load the actual text reviews into the gray panel
            loadUserReviews(productName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadUserReviews(String productName) {
        reviewPanel.removeAll();
        // Using BoxLayout to stack reviews vertically
        reviewPanel.setLayout(new javax.swing.BoxLayout(reviewPanel, javax.swing.BoxLayout.Y_AXIS));

        try (Connection conn = config.db.mycon()) {
            // Now that u_id exists in products, we can link it to the users table
            String sql = "SELECT u.u_fname, u.u_lname, p.p_rating, p.p_feedback " +
                         "FROM products p " +
                         "JOIN users u ON u.u_id = p.u_id " + 
                         "WHERE p.p_name = ? AND p.p_feedback IS NOT NULL";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, productName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("u_fname");
                String lastName = rs.getString("u_lname");
                String fullName = (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
                String feedback = rs.getString("p_feedback");
                String rating = rs.getString("p_rating");

                // Individual review container
                JPanel row = new JPanel();
                row.setLayout(new java.awt.GridLayout(3, 1, 0, 5)); 
                row.setBackground(Color.WHITE);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // Prevents reviews from stretching too tall
                row.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                // 1. User Name
                JLabel nameLbl = new JLabel(fullName.trim().isEmpty() ? "Anonymous" : fullName);
                nameLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

                // 2. Feedback Text (with HTML for auto-wrapping)
                JLabel commentLbl = new JLabel("<html><body style='width: 200px'>" + feedback + "</body></html>");
                commentLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

                // 3. Star Rating
                JLabel starLbl = new JLabel(rating);
                try {
                    starLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/star.png")));
                } catch (Exception e) {
                    // Icon path fallback
                }

                row.add(nameLbl);
                row.add(commentLbl);
                row.add(starLbl);

                reviewPanel.add(row);
            }

            if (reviewPanel.getComponentCount() == 0) {
                JLabel empty = new JLabel("No reviews available for this item.");
                empty.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
                reviewPanel.add(empty);
            }

        } catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        reviewPanel.revalidate();
        reviewPanel.repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Image = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Price = new javax.swing.JLabel();
        ttlsold = new javax.swing.JLabel();
        rate = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        btnBuyNow = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        variantPanel = new javax.swing.JPanel();
        btnAddToCart = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnQty = new javax.swing.JPanel();
        plus = new javax.swing.JLabel();
        minus = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reviewPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bg.setMinimumSize(new java.awt.Dimension(800, 500));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Image, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Image, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 320, 310));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(60, 139, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thrifty");
        bg.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 90, 40));

        jLabel1.setBackground(new java.awt.Color(242, 245, 248));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo-40.png"))); // NOI18N
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Name.setText("Product Name");
        bg.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Reviews");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, -1, -1));

        Price.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Price.setForeground(new java.awt.Color(60, 139, 153));
        Price.setText("Price");
        bg.add(Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, -1, -1));

        ttlsold.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ttlsold.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ttlsold.setText("0 Sold");
        bg.add(ttlsold, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 50, -1));

        rate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rate.setText("Star 5 ( 0 )");
        bg.add(rate, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 60, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("l");
        bg.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 30, -1));

        back.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/arrow-small-left.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        bg.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        btnBuyNow.setBackground(new java.awt.Color(60, 139, 153));
        btnBuyNow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuyNow.setForeground(new java.awt.Color(255, 255, 255));
        btnBuyNow.setText("Buy now");
        btnBuyNow.setBorder(null);
        btnBuyNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyNowActionPerformed(evt);
            }
        });
        bg.add(btnBuyNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 120, 30));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        variantPanel.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(variantPanel);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 320, 80));

        btnAddToCart.setBackground(new java.awt.Color(75, 160, 175));
        btnAddToCart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddToCart.setForeground(new java.awt.Color(255, 255, 255));
        btnAddToCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/shopping-cart-add.png"))); // NOI18N
        btnAddToCart.setBorder(null);
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });
        bg.add(btnAddToCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 60, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Quantity");
        bg.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        btnQty.setBackground(new java.awt.Color(250, 250, 250));
        btnQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQty.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        plus.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        plus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        plus.setText("+");
        plus.setMinimumSize(new java.awt.Dimension(40, 30));
        btnQty.add(plus, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 50, 30));

        minus.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        minus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minus.setText(" -");
        btnQty.add(minus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 30));

        qty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        qty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qty.setText("1");
        btnQty.add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 40, 30));

        bg.add(btnQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 140, 30));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        reviewPanel.setLayout(new javax.swing.BoxLayout(reviewPanel, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane3.setViewportView(reviewPanel);

        bg.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 390, 170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
    if (previousFrame != null) {
            previousFrame.setVisible(true); // Show Cart or Shop
        }
        this.dispose();// Close details
    }//GEN-LAST:event_backMouseClicked

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        try (Connection conn = config.db.mycon()) {
            String sql = "INSERT INTO cart (p_name, p_price, p_image, p_qty) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, name);
            String cleanPrice = price.replace("₱", "").trim();
            pst.setString(2, cleanPrice);
            pst.setString(3, imagePath);
            pst.setInt(4, quantity); 

            int result = pst.executeUpdate();
            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Added to cart!");
                // Instead of opening a NEW Cart, just go back to the previous frame
                if (previousFrame != null) {
                    previousFrame.setVisible(true);
                    // If the previous frame was the Cart, you might need to call a refresh method here
                }
                this.dispose();
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void btnBuyNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyNowActionPerformed
        String productName = Name.getText(); 
        String productPrice = Price.getText().replace("₱ ", "").trim();
        int quantity = Integer.parseInt(qty.getText().trim()); 
        double total = Double.parseDouble(productPrice) * quantity;

        try (Connection conn = config.db.mycon()) {
            // Step 1: Insert into Orders
            String sqlOrder = "INSERT INTO orders (p_name, p_price, p_qty, p_total, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstOrder = conn.prepareStatement(sqlOrder);
            pstOrder.setString(1, productName);
            pstOrder.setString(2, productPrice);
            pstOrder.setInt(3, quantity);
            pstOrder.setDouble(4, total);
            pstOrder.setString(5, "Pending");

            int updated = pstOrder.executeUpdate();

            if (updated > 0) {
                // Step 2: Delete from Cart (This "hides" it from the cart table)
                String sqlDelete = "DELETE FROM cart WHERE p_name = ?";
                PreparedStatement pstDelete = conn.prepareStatement(sqlDelete);
                pstDelete.setString(1, productName);
                pstDelete.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(this, "Order Placed Successfully!");

                // Step 3: Refresh and Navigate
                Orders orderPage = new Orders();
                orderPage.setVisible(true);
                orderPage.pack();
                orderPage.setLocationRelativeTo(null);

                this.dispose(); 
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnBuyNowActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ItemDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Image;
    private javax.swing.JLabel Name;
    private javax.swing.JLabel Price;
    private javax.swing.JLabel back;
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnBuyNow;
    private javax.swing.JPanel btnQty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel minus;
    private javax.swing.JLabel plus;
    private javax.swing.JLabel qty;
    private javax.swing.JLabel rate;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JLabel ttlsold;
    private javax.swing.JPanel variantPanel;
    // End of variables declaration//GEN-END:variables
}


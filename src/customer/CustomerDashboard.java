package customer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class CustomerDashboard extends javax.swing.JFrame {

    public CustomerDashboard() {
        initComponents();

        productContainer.setLayout(new java.awt.GridLayout(0, 4, 8, 8));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        displayAllProducts();     
    }
    
    public ImageIcon scaleImage(String path) {
        try {
            // Safety check: if path is null or empty, return a default icon or null
            if (path == null || path.isEmpty()) return null;
            
            ImageIcon myImage = new ImageIcon(path);
            Image img = myImage.getImage();
            Image newImg = img.getScaledInstance(120, 110, Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (Exception e) {
            return null; // Prevents crash if image file is missing
        }
    }
    
    public void displayAllProducts() {
        productContainer.removeAll(); 

        try (Connection conn = config.db.mycon(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT *, (SELECT COUNT(*) "
                     + "FROM orders "
                     + "WHERE orders.p_name = products.p_name "
                     + "AND status = 'Completed') "
                     + "as actual_sold FROM products")) {
            
            if (conn == null) return;

            while (rs.next()) {
                String name = rs.getString("p_name");
                double price = rs.getDouble("p_price");
                String path = rs.getString("p_image_path");
                String reviews = rs.getString("p_reviews");

                JPanel newCard = createProductCard(name, price, path, reviews);
                productContainer.add(newCard);
            }

            // 4. Force the UI to recalculate the height based on the new products
            productContainer.revalidate();
            productContainer.repaint();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    public JPanel createProductCard(String name, double price, String imgPath, String reviews) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(140, 210)); 
        card.setBackground(Color.WHITE);
        card.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 1. Image
        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblImg.setIcon(scaleImage(imgPath)); 
        card.add(lblImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 110));

        // 2. Name
        JLabel lblName = new JLabel(name);
        lblName.setFont(new java.awt.Font("Segoe UI", 1, 12));
        card.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 120, -1));

        // 3. Price
        JLabel lblPrice = new JLabel("₱ " + price);
        lblPrice.setForeground(new Color(60, 139, 153));
        lblPrice.setFont(new java.awt.Font("Segoe UI", 1, 11));
        card.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, -1, -1));

        // 4. Reviews (Example: "45 reviews")
        JLabel lblRev = new JLabel(reviews + " reviews");
        lblRev.setFont(new java.awt.Font("Segoe UI", 0, 10));
        lblRev.setForeground(Color.GRAY);
        card.add(lblRev, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        // 5. Add Button
        JButton btnAdd = new JButton("+");
        btnAdd.setBackground(new Color(60, 139, 153));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> {
            // Pass data to Detail page
            ItemDetail detail = new ItemDetail(name, "₱ " + price, imgPath, CustomerDashboard.this);
            detail.setVisible(true);
            CustomerDashboard.this.setVisible(false);
        });
        card.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 175, 35, 25));

        return card;
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        shop = new javax.swing.JLabel();
        mycart = new javax.swing.JLabel();
        order = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(242, 245, 248));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(60, 139, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(242, 245, 248));
        jPanel2.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel1.setBackground(new java.awt.Color(242, 245, 248));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thrifty");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 11, 71, 40));

        logout.setBackground(new java.awt.Color(242, 245, 248));
        logout.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/sign-out-alt.png"))); // NOI18N
        logout.setText("  Logout");
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        jPanel1.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 160, -1));

        shop.setBackground(new java.awt.Color(242, 245, 248));
        shop.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        shop.setForeground(new java.awt.Color(255, 255, 255));
        shop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/store-alt (1).png"))); // NOI18N
        shop.setText("  Shop");
        shop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shopMouseClicked(evt);
            }
        });
        jPanel1.add(shop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 130, -1));

        mycart.setBackground(new java.awt.Color(242, 245, 248));
        mycart.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        mycart.setForeground(new java.awt.Color(255, 255, 255));
        mycart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mycart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/shopping-cart.png"))); // NOI18N
        mycart.setText("  My Cart");
        mycart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mycartMouseClicked(evt);
            }
        });
        jPanel1.add(mycart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 150, -1));

        order.setBackground(new java.awt.Color(242, 245, 248));
        order.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        order.setForeground(new java.awt.Color(255, 255, 255));
        order.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/task-checklist.png"))); // NOI18N
        order.setText("  Orders");
        order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderMouseClicked(evt);
            }
        });
        jPanel1.add(order, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 150, -1));

        profile.setBackground(new java.awt.Color(242, 245, 248));
        profile.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        profile.setForeground(new java.awt.Color(255, 255, 255));
        profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/user.png"))); // NOI18N
        profile.setText("  Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        jPanel1.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 150, -1));

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jTextField1.setText("Search...");
        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        bg.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 360, 30));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tops", "Bottoms", "Accessories" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        bg.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 90, 30));

        jLabel11.setBackground(new java.awt.Color(242, 245, 248));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/bell-notification-social-media.png"))); // NOI18N
        bg.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 30, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Discover our latest finds!");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 220, -1));

        jScrollPane1.setBackground(new java.awt.Color(242, 245, 248));
        jScrollPane1.setBorder(null);

        productContainer.setBackground(new java.awt.Color(242, 245, 248));
        productContainer.setLayout(new java.awt.GridLayout(0, 4));
        jScrollPane1.setViewportView(productContainer);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 600, 420));

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
    
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            thrifty.Signin loginFrame = new thrifty.Signin(); 
            loginFrame.setVisible(true);
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null); // Center it

            // 3. Close the current Dashboard
            this.dispose(); 
        }
    }//GEN-LAST:event_logoutMouseClicked

    private void mycartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mycartMouseClicked
        Cart mycart = new Cart(); 
        mycart.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_mycartMouseClicked

    private void shopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shopMouseClicked
        CustomerDashboard dashboard = new CustomerDashboard(); 
        dashboard.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_shopMouseClicked

    private void orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderMouseClicked
        Orders list = new Orders(); 
        list.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderMouseClicked

    private void profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseClicked
        Profile user = new Profile(); 
        user.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profileMouseClicked

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
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel mycart;
    private javax.swing.JLabel order;
    private javax.swing.JPanel productContainer;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel shop;
    // End of variables declaration//GEN-END:variables
}

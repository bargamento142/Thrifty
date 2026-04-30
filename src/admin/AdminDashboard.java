package admin;

import config.db; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager; 
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AdminDashboard extends javax.swing.JFrame {

    public AdminDashboard() {
        initComponents();
    }
    
    private void addProductToDatabase() {
        String name = ProductName.getText();
        String priceStr = ProductPrice.getText();
        String sourcePath = ImagePath.getText().trim();
        String reviews = "0"; 

        if (name.isEmpty() || priceStr.isEmpty() || sourcePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        // 1. Define the destination folder and target file
        File sourceFile = new File(sourcePath);
        String fileName = sourceFile.getName(); // Extracts "red-hoodie.png"
        String destinationPath = "C:\\Users\\Administrator\\Documents\\NetBeansProjects\\Thrifty\\src\\image\\" + fileName;
        File targetFile = new File(destinationPath);

        try {
            // 2. Physically copy the file to your project folder
            // This ensures the image exists within the project for other PCs to see
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 3. Insert into Database using the NEW destination path
            String sql = "INSERT INTO products(p_name, p_price, p_image_path, p_reviews) VALUES(?,?,?,?)";

            try (Connection conn = db.mycon(); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if (conn == null) return;

                pstmt.setString(1, name);
                pstmt.setDouble(2, Double.parseDouble(priceStr)); 
                pstmt.setString(3, destinationPath); // Saving the internal project path
                pstmt.setString(4, reviews);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Product Added");

                // Clear fields
                ProductName.setText("");
                ProductPrice.setText("");
                ImagePath.setText("");
                lblPreview.setIcon(null);

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the price.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage());
            System.out.println("Insert/Copy Error: " + e.getMessage());
        }
    }
    
    public ImageIcon scaleImage(String imagePath) {
        ImageIcon myImage = new ImageIcon(imagePath);

        // Get the current dimensions of your preview label
        int width = lblPreview.getWidth();
        int height = lblPreview.getHeight();

        // Ensure width and height are valid (greater than 0)
        if (width <= 0) width = 240; 
        if (height <= 0) height = 230;

        // Scale the image smoothly
        Image img = myImage.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(newImg);
    }
    
    private void browseImage() {
        JFileChooser chooser = new JFileChooser();

        // Set an approved extension filter for images only
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        chooser.setFileFilter(filter);

        // Open the dialog
        int result = chooser.showOpenDialog(this);

        // If the user clicked "Open" instead of "Cancel"
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            // 1. Set the path into the text field (which the database uses)
            ImagePath.setText(path);


            ImageIcon scaledIcon = scaleImage(path);
            lblPreview.setText(""); 
            lblPreview.setIcon(scaledIcon);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dashboard = new javax.swing.JLabel();
        product = new javax.swing.JLabel();
        orderslist = new javax.swing.JLabel();
        users = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        ttlProduct = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        ttlOrders = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        ttleRevenue = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        ProductPrice = new javax.swing.JTextField();
        ImagePath = new javax.swing.JTextField();
        ProductName = new javax.swing.JTextField();
        addProduct = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblPreview = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(242, 245, 248));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(60, 139, 153));
        jPanel1.setMinimumSize(new java.awt.Dimension(220, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(220, 500));
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

        jLabel3.setBackground(new java.awt.Color(242, 245, 248));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/sign-out-alt.png"))); // NOI18N
        jLabel3.setText("  Logout");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 150, -1));

        dashboard.setBackground(new java.awt.Color(242, 245, 248));
        dashboard.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        dashboard.setForeground(new java.awt.Color(255, 255, 255));
        dashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/house-chimney (1).png"))); // NOI18N
        dashboard.setText("  Dashboard");
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });
        jPanel1.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, -1));

        product.setBackground(new java.awt.Color(242, 245, 248));
        product.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        product.setForeground(new java.awt.Color(255, 255, 255));
        product.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/box-open-full.png"))); // NOI18N
        product.setText("  Manage Products");
        product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productMouseClicked(evt);
            }
        });
        jPanel1.add(product, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 210, -1));

        orderslist.setBackground(new java.awt.Color(242, 245, 248));
        orderslist.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        orderslist.setForeground(new java.awt.Color(255, 255, 255));
        orderslist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderslist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/task-checklist.png"))); // NOI18N
        orderslist.setText("  Orders List");
        orderslist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderslistMouseClicked(evt);
            }
        });
        jPanel1.add(orderslist, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, -1));

        users.setBackground(new java.awt.Color(242, 245, 248));
        users.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        users.setForeground(new java.awt.Color(255, 255, 255));
        users.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        users.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/users-alt.png"))); // NOI18N
        users.setText("  User Management");
        users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersMouseClicked(evt);
            }
        });
        jPanel1.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 220, -1));

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jPanel4.setBackground(new java.awt.Color(60, 139, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setPreferredSize(new java.awt.Dimension(160, 105));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total Products");

        ttlProduct.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ttlProduct.setForeground(new java.awt.Color(255, 255, 255));
        ttlProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ttlProduct.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ttlProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(ttlProduct)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 160, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(160, 105));

        ttlOrders.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ttlOrders.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ttlOrders.setText("Total Orders");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ttlOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ttlOrders)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, -1));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(160, 105));

        ttleRevenue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ttleRevenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ttleRevenue.setText("Total Revenue");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ttleRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ttleRevenue)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        btnBrowse.setBackground(new java.awt.Color(60, 139, 153));
        btnBrowse.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/plus-small.png"))); // NOI18N
        btnBrowse.setText("Insert Photo");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });
        bg.add(btnBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 130, -1));

        ProductPrice.setBackground(new java.awt.Color(242, 245, 248));
        ProductPrice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ProductPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductPriceActionPerformed(evt);
            }
        });
        bg.add(ProductPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 270, -1));

        ImagePath.setBackground(new java.awt.Color(242, 245, 248));
        ImagePath.setText(" ");
        ImagePath.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Photo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ImagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImagePathActionPerformed(evt);
            }
        });
        bg.add(ImagePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 270, -1));

        ProductName.setBackground(new java.awt.Color(242, 245, 248));
        ProductName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductNameActionPerformed(evt);
            }
        });
        bg.add(ProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 270, -1));

        addProduct.setBackground(new java.awt.Color(60, 139, 153));
        addProduct.setForeground(new java.awt.Color(255, 255, 255));
        addProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/plus-small.png"))); // NOI18N
        addProduct.setText("Add Product");
        addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductActionPerformed(evt);
            }
        });
        bg.add(addProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );

        bg.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 240, 230));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Dashboard");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 100, -1));

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

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        browseImage();
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void ProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductPriceActionPerformed

    private void ImagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImagePathActionPerformed
        try {
            String path = ImagePath.getText().trim();
            if (!path.isEmpty()) {
                lblPreview.setIcon(scaleImage(path));
            }
        } catch (Exception e) {
            // Silent catch if the path isn't a valid image yet
        }
    }//GEN-LAST:event_ImagePathActionPerformed

    private void ProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductNameActionPerformed

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed
        addProductToDatabase();
    }//GEN-LAST:event_addProductActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
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
    }//GEN-LAST:event_jLabel3MouseClicked

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        AdminDashboard dashboard = new AdminDashboard(); 
        dashboard.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_dashboardMouseClicked

    private void productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productMouseClicked
        Products manage = new Products(); 
        manage.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_productMouseClicked

    private void orderslistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderslistMouseClicked
        OrdersList list = new OrdersList(); 
        list.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_orderslistMouseClicked

    private void usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersMouseClicked
        Users manage = new Users(); 
        manage.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_usersMouseClicked

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
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ImagePath;
    private javax.swing.JTextField ProductName;
    private javax.swing.JTextField ProductPrice;
    private javax.swing.JButton addProduct;
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JLabel dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel orderslist;
    private javax.swing.JLabel product;
    private javax.swing.JLabel ttlOrders;
    private javax.swing.JLabel ttlProduct;
    private javax.swing.JLabel ttleRevenue;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables
}

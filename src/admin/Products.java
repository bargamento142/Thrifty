package admin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Products extends javax.swing.JFrame {

    private String selectedImagePath = "";
    
    public Products() {
        initComponents();
        initTableStructure();
        loadTableData();
    }
    
    
    private void initTableStructure() {
        // Define the column headers
        String[] columns = {"ID", "Product Name", "Price", "Image Path"};

        // Create a model that prevents users from editing the cells directly
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This makes the table read-only
            }
        };

        ProductsTable.setModel(model);
    }
    
    public void loadTableData() {
        try (Connection conn = config.db.mycon()) {
            String sql = "SELECT * FROM products";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) ProductsTable.getModel();
            model.setRowCount(0); 

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("p_id"),          // Column 0
                    rs.getString("p_name"),     // Column 1
                    rs.getDouble("p_price"),    // Column 2
                    rs.getString("p_image_path") // Column 3
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void ProductsTableMouseClicked(java.awt.event.MouseEvent evt) {                                          
        int row = ProductsTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) ProductsTable.getModel();

        // Set text fields
        ProductName.setText(model.getValueAt(row, 1).toString());
        ProductPrice.setText(model.getValueAt(row, 2).toString());

        // Get path from the table and show the image
        try {
            String pathFromDb = model.getValueAt(row, 3).toString(); 
            selectedImagePath = pathFromDb; // Update global variable

            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(pathFromDb);
            java.awt.Image img = icon.getImage().getScaledInstance(lblPreview.getWidth(), 
                                 lblPreview.getHeight(), java.awt.Image.SCALE_SMOOTH);
            lblPreview.setIcon(new javax.swing.ImageIcon(img));
        } catch (Exception e) {
            lblPreview.setIcon(null);
        }
    }
    
    private void clearFields() {
        ProductName.setText("");
        ProductPrice.setText("");
        ProductStocks.setText(""); // Added if you decide to use it later
        selectedImagePath = "";
        lblPreview.setIcon(null); // Clears the image preview
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dashboard = new javax.swing.JLabel();
        product = new javax.swing.JLabel();
        orderslist = new javax.swing.JLabel();
        users = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductsTable = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lblPreview = new javax.swing.JLabel();
        ProductStocks = new javax.swing.JTextField();
        ProductName = new javax.swing.JTextField();
        addProduct = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        ProductPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(242, 245, 248));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(60, 139, 153));
        jPanel2.setMinimumSize(new java.awt.Dimension(220, 500));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 500));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(242, 245, 248));
        jPanel3.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel1.setBackground(new java.awt.Color(242, 245, 248));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thrifty");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 11, 71, 40));

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
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 150, -1));

        dashboard.setBackground(new java.awt.Color(242, 245, 248));
        dashboard.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        dashboard.setForeground(new java.awt.Color(255, 255, 255));
        dashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/house-chimney.png"))); // NOI18N
        dashboard.setText("  Dashboard");
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });
        jPanel2.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, -1));

        product.setBackground(new java.awt.Color(242, 245, 248));
        product.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        product.setForeground(new java.awt.Color(255, 255, 255));
        product.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/box-open-full (1).png"))); // NOI18N
        product.setText("  Manage Products");
        product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productMouseClicked(evt);
            }
        });
        jPanel2.add(product, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 210, -1));

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
        jPanel2.add(orderslist, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, -1));

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
        jPanel2.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 220, -1));

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Manage Products");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 160, -1));

        ProductsTable.setBackground(new java.awt.Color(242, 245, 248));
        ProductsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ProductsTable.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(ProductsTable);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 289, 561, 200));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tops", "Bottoms", "Accessories" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        bg.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 90, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
        );

        bg.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 140, 140));

        ProductStocks.setBackground(new java.awt.Color(242, 245, 248));
        ProductStocks.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stocks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ProductStocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductStocksActionPerformed(evt);
            }
        });
        bg.add(ProductStocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 270, -1));

        ProductName.setBackground(new java.awt.Color(242, 245, 248));
        ProductName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductNameActionPerformed(evt);
            }
        });
        bg.add(ProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 270, -1));

        addProduct.setBackground(new java.awt.Color(60, 139, 153));
        addProduct.setForeground(new java.awt.Color(255, 255, 255));
        addProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/plus-small.png"))); // NOI18N
        addProduct.setText("Add Product");
        addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductActionPerformed(evt);
            }
        });
        bg.add(addProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, -1, -1));

        btnBrowse.setBackground(new java.awt.Color(60, 139, 153));
        btnBrowse.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/plus-small.png"))); // NOI18N
        btnBrowse.setText("Insert Photo");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });
        bg.add(btnBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 140, 30));

        ProductPrice.setBackground(new java.awt.Color(242, 245, 248));
        ProductPrice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        ProductPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductPriceActionPerformed(evt);
            }
        });
        bg.add(ProductPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 270, -1));

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void ProductStocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductStocksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductStocksActionPerformed

    private void ProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductNameActionPerformed

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed
        String name = ProductName.getText();
        String price = ProductPrice.getText();

        if (name.isEmpty() || price.isEmpty() || selectedImagePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and browse an image!");
            return;
        }

        try (Connection conn = config.db.mycon()) {
            String sql = "INSERT INTO products (p_name, p_price, p_image_path) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, price);
            pst.setString(3, selectedImagePath);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product successfully saved!");

            // Refresh the table and clear the inputs
            loadTableData(); 
            clearFields(); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }//GEN-LAST:event_addProductActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();

        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath(); // Save path for the database

            // Display the image in the label (lblPreview)
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(selectedImagePath);
            // Resize image to fit the label
            java.awt.Image img = icon.getImage().getScaledInstance(lblPreview.getWidth(), 
                                 lblPreview.getHeight(), java.awt.Image.SCALE_SMOOTH);
            lblPreview.setIcon(new javax.swing.ImageIcon(img));
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void ProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductPriceActionPerformed

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
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ProductName;
    private javax.swing.JTextField ProductPrice;
    private javax.swing.JTextField ProductStocks;
    private javax.swing.JTable ProductsTable;
    private javax.swing.JButton addProduct;
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JLabel dashboard;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel orderslist;
    private javax.swing.JLabel product;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables
}

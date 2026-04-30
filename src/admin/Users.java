package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Users extends javax.swing.JFrame {

    public Users() {
    initComponents();
    initUserTable(); 
    loadUsers();    
}

    private void initUserTable() {
        String[] columns = {"ID", "First Name", "Last Name", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        UsersTable.setModel(model);
    }

    public void loadUsers() {
        try (Connection conn = config.db.mycon()) {
            String sql = "SELECT u_id, u_fname, u_lname, u_email FROM users"; 
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) UsersTable.getModel();
            model.setRowCount(0); 

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("u_id"),
                    rs.getString("u_fname"),
                    rs.getString("u_lname"),
                    rs.getString("u_email")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void clearFields() {
        fname.setText("");
        lname.setText("");
        email.setText("");
        pass.setText("");
        cpass.setText("");
        UsersTable.clearSelection();
    }
    
    public void deleteUser() {
    int row = UsersTable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a user to delete.");
        return;
    }
    
    String id = UsersTable.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Warning", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = config.db.mycon()) {
                String sql = "DELETE FROM users WHERE u_id=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                pst.executeUpdate();
                loadUsers();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
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
        UsersTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        fname = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cpass = new javax.swing.JPasswordField();
        btnUpdate = new javax.swing.JToggleButton();

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
        product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/box-open-full.png"))); // NOI18N
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
        users.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/icons/users-alt (1).png"))); // NOI18N
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
        jLabel13.setText("Users Management");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 170, -1));

        UsersTable.setBackground(new java.awt.Color(242, 245, 248));
        UsersTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        UsersTable.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        UsersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(UsersTable);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 289, 561, 200));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("First Name");
        bg.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Last Name");
        bg.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, -1, -1));

        lname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnameActionPerformed(evt);
            }
        });
        bg.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 230, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Email");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 50, -1));

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        bg.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 230, 30));

        fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameActionPerformed(evt);
            }
        });
        bg.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 230, 30));
        bg.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 230, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Password");
        bg.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 70, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Confirm Password");
        bg.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 100, -1));
        bg.add(cpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 230, 30));

        btnUpdate.setBackground(new java.awt.Color(60, 139, 153));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sign Up");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateMouseExited(evt);
            }
        });
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        bg.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, 230, 40));

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

    private void lnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lnameActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameActionPerformed

    private void btnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseEntered
        btnUpdate.setBackground(new java.awt.Color(75, 160, 175));
    }//GEN-LAST:event_btnUpdateMouseEntered

    private void btnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseExited
        btnUpdate.setBackground(new java.awt.Color(60, 139, 153));
    }//GEN-LAST:event_btnUpdateMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = UsersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table to update!");
            return;
        }

        String id = UsersTable.getValueAt(row, 0).toString();
        String password = new String(pass.getPassword());
        String confirmPass = new String(cpass.getPassword());

        // Basic Validation
        if (fname.getText().isEmpty() || lname.getText().isEmpty() || email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields (except password) are required!");
            return;
        }

        try (Connection conn = config.db.mycon()) {
            String sql;
            PreparedStatement pst;

            // Check if we are updating the password or just profile info
            if (password.isEmpty()) {
                sql = "UPDATE users SET u_fname=?, u_lname=?, u_email=? WHERE u_id=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, fname.getText());
                pst.setString(2, lname.getText());
                pst.setString(3, email.getText());
                pst.setString(4, id);
            } else {
                if (!password.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match!");
                    return;
                }
                sql = "UPDATE users SET u_fname=?, u_lname=?, u_email=?, u_pass=? WHERE u_id=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, fname.getText());
                pst.setString(2, lname.getText());
                pst.setString(3, email.getText());
                pst.setString(4, password);
                pst.setString(5, id);
            }

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User details updated successfully!");
            loadUsers(); // Refresh the table
            clearFields(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void UsersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsersTableMouseClicked
        int row = UsersTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) UsersTable.getModel();

        // Fill fields
        fname.setText(model.getValueAt(row, 1).toString());
        lname.setText(model.getValueAt(row, 2).toString());
        email.setText(model.getValueAt(row, 3).toString());

        // Reset passwords for security (admin must re-type to change)
        pass.setText("");
        cpass.setText("");
    }//GEN-LAST:event_UsersTableMouseClicked

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
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Users().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable UsersTable;
    private javax.swing.JPanel bg;
    private javax.swing.JToggleButton btnUpdate;
    private javax.swing.JPasswordField cpass;
    private javax.swing.JLabel dashboard;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lname;
    private javax.swing.JLabel orderslist;
    private javax.swing.JPasswordField pass;
    private javax.swing.JLabel product;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables
}

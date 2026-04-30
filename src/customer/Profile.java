package customer;

import config.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Profile extends javax.swing.JFrame {

 
    private int loggedInUserId;
            
    public Profile() {
        initComponents();
    }
    
    public Profile(int userId) {
        this.loggedInUserId = userId;
        initComponents();
        loadUserData(); 
    }
    
    private void loadUserData() {
        try (Connection conn = db.mycon()) {
            String sql = "SELECT u_fname, u_lname, u_email, u_pass FROM users WHERE u_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, loggedInUserId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Fixed variable names to match your 'Variables declaration' section
                fname.setText(rs.getString("u_fname"));
                lname.setText(rs.getString("u_lname"));
                email.setText(rs.getString("u_email"));
                // We usually don't show the hashed password to the user for security
                pass.setText(""); 
                cpass.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        shop = new javax.swing.JLabel();
        mycart = new javax.swing.JLabel();
        order = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        cpass = new javax.swing.JPasswordField();
        btnUpdate = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(242, 245, 248));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jPanel4.setBackground(new java.awt.Color(60, 139, 153));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 500));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thrifty");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 11, 71, 40));

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
        jPanel4.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 160, -1));

        shop.setBackground(new java.awt.Color(242, 245, 248));
        shop.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        shop.setForeground(new java.awt.Color(255, 255, 255));
        shop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/store-alt.png"))); // NOI18N
        shop.setText("  Shop");
        shop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shopMouseClicked(evt);
            }
        });
        jPanel4.add(shop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 130, -1));

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
        jPanel4.add(mycart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 150, -1));

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
        jPanel4.add(order, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 150, -1));

        profile.setBackground(new java.awt.Color(242, 245, 248));
        profile.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        profile.setForeground(new java.awt.Color(255, 255, 255));
        profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/user (1).png"))); // NOI18N
        profile.setText("  Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        jPanel4.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 150, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameActionPerformed(evt);
            }
        });
        jPanel1.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 270, 30));

        lname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnameActionPerformed(evt);
            }
        });
        jPanel1.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 270, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Last Name");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("First Name");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Email");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 50, -1));

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 270, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Password");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 70, -1));
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 270, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Confirm Password");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 100, -1));
        jPanel1.add(cpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 270, 30));

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
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 270, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void shopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shopMouseClicked
        CustomerDashboard dashboard = new CustomerDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_shopMouseClicked

    private void mycartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mycartMouseClicked
        Cart mycart = new Cart();
        mycart.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mycartMouseClicked

    private void orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderMouseClicked
        Orders list = new Orders();
        list.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderMouseClicked

    private void profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseClicked
        Profile userProfile = new Profile(this.loggedInUserId); 
        userProfile.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profileMouseClicked

    private void fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameActionPerformed

    private void lnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lnameActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void btnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseEntered
        btnUpdate.setBackground(new java.awt.Color(75, 160, 175));
    }//GEN-LAST:event_btnUpdateMouseEntered

    private void btnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseExited
        btnUpdate.setBackground(new java.awt.Color(60, 139, 153));
    }//GEN-LAST:event_btnUpdateMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String firstName = fname.getText();
        String lastName = lname.getText();
        String userEmail = email.getText();
        String password = new String(pass.getPassword());
        String confirmPassword = new String(cpass.getPassword());

        // 1. Basic Validation
        if (firstName.isEmpty() || userEmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        // 2. Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        try (Connection conn = config.db.mycon()) {
            // 3. Hash the password before saving
            String securePassword = hashPassword(password);

            String sql = "UPDATE users SET u_fname = ?, u_lname = ?, u_email = ?, u_pass = ? WHERE u_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, userEmail);
            pst.setString(4, securePassword); // Save the Hashed version
            pst.setInt(5, loggedInUserId);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Profile and Password Updated Successfully!");
                // Optional: Clear password fields for security
                pass.setText("");
                cpass.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Failed: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnUpdate;
    private javax.swing.JPasswordField cpass;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField lname;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel mycart;
    private javax.swing.JLabel order;
    private javax.swing.JPasswordField pass;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel shop;
    // End of variables declaration//GEN-END:variables
}

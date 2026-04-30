package thrifty;

import admin.AdminDashboard;
import customer.CustomerDashboard;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Signin extends javax.swing.JFrame {

    
    public Signin() {
        initComponents();
    }
    
    private String hashPassword(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        signin = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        signup = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(242, 245, 248));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signin.setBackground(new java.awt.Color(60, 139, 153));
        signin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        signin.setForeground(new java.awt.Color(255, 255, 255));
        signin.setText("Sign In");
        signin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signinMouseExited(evt);
            }
        });
        signin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinActionPerformed(evt);
            }
        });
        bg.add(signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, 240, 40));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

        jPanel1.setBackground(new java.awt.Color(60, 139, 153));

        signup.setBackground(new java.awt.Color(60, 139, 153));
        signup.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        signup.setForeground(new java.awt.Color(255, 255, 255));
        signup.setText("Sign Up");
        signup.setBorder(null);
        signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupMouseExited(evt);
            }
        });
        signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hello, Trendsetter!");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" \"Sign in to explore the latest arrivals and");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("your personalized thrift picks.\"");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(155, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(30, 30, 30)
                .addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 500));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo-60.png"))); // NOI18N
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 60, 50));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        bg.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 240, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Email");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));
        bg.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 240, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Password");
        bg.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, -1, -1));

        jPanel2.setBackground(new java.awt.Color(242, 245, 248));

        jCheckBox1.setBackground(new java.awt.Color(242, 245, 248));
        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jCheckBox1.setText("Remember me");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Forgot Password");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, 240, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(60, 139, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sign In");
        bg.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 390, -1));

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

    private void signinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinMouseEntered
        signin.setBackground(new java.awt.Color(75, 160, 175));
    }//GEN-LAST:event_signinMouseEntered

    private void signinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinMouseExited
        signin.setBackground(new java.awt.Color(60, 139, 153));
    }//GEN-LAST:event_signinMouseExited

    private void signinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signinActionPerformed
        String userEmail = jTextField1.getText();
        String userPass = String.valueOf(jPasswordField1.getPassword());

        // 1. Basic validation
        if (userEmail.isEmpty() || userPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Email and Password.");
            return;
        }

        // Use try-with-resources to ensure connection closes even if an error occurs
        try (Connection con = config.db.mycon()) {

            // 2. Safety check: Exit if connection failed (prevents NullPointerException)
            if (con == null) return; 

            // 3. Hash the user input (matching your 12-character substring format)
            String hashedInput = hashPassword(userPass).substring(0, 12);

            String sql = "SELECT * FROM users WHERE u_email = ? AND u_pass = ?";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, userEmail);
                pst.setString(2, hashedInput);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("u_fname"); 
                        String role = rs.getString("u_role");

                        JOptionPane.showMessageDialog(this, "Welcome back, " + name + "!");

                        // 4. Dashboard Redirection
                        try {
                            if (role.equalsIgnoreCase("Admin")) {
                                new AdminDashboard().setVisible(true);
                            } else {
                                new CustomerDashboard().setVisible(true);
                            }
                            this.dispose(); // Close login window
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Dashboard Error: Check if your images exist in /image/ folder.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Email or Password.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }//GEN-LAST:event_signinActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void signupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMouseEntered
        signup.setForeground(new java.awt.Color(220, 220, 220)); 
        signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_signupMouseEntered

    private void signupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMouseExited
        signup.setForeground(java.awt.Color.WHITE);
    }//GEN-LAST:event_signupMouseExited

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupActionPerformed
        Signup signupFrame = new Signup();
        signupFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_signupActionPerformed

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
            java.util.logging.Logger.getLogger(Signin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Signin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Signin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Signin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Signin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton signin;
    private javax.swing.JToggleButton signup;
    // End of variables declaration//GEN-END:variables
}

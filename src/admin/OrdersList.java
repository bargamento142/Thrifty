package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class OrdersList extends javax.swing.JFrame {

    
    public OrdersList() {
        initComponents();
        loadOrdersTable(); // Load data on startup

        // Add Mouse Listener for Right-Click functionality
        OrdersListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (javax.swing.SwingUtilities.isRightMouseButton(evt)) {
                    int row = OrdersListTable.rowAtPoint(evt.getPoint());
                    OrdersListTable.setRowSelectionInterval(row, row);
                    showAdminPopupMenu(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
    }
    
    private void loadOrdersTable() {
        String[] columns = {"ID", "Product", "Price", "Quantity", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        OrdersListTable.setModel(model);

        try (Connection conn = config.db.mycon()) {
            // Querying your SQLite table structure shown in your image
            String sql = "SELECT id, p_name, p_price, p_qty, p_total, status FROM orders WHERE status != 'Archived' ORDER BY id DESC";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"), // This must match the index used in showAdminPopupMenu
                    rs.getString("p_name"),
                    "₱ " + rs.getString("p_price"),
                    rs.getInt("p_qty"),
                    "₱ " + rs.getString("p_total"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateOrderStatus(int orderId, String newStatus) {
        try (Connection conn = config.db.mycon()) {
            String sql = "UPDATE orders SET status = ? WHERE id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newStatus);
            pst.setInt(2, orderId);

            if (pst.executeUpdate() > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Order " + newStatus);
                loadOrdersTable(); // Refresh table to show changes
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void showAdminPopupMenu(java.awt.Component invoker, int x, int y) {
        javax.swing.JPopupMenu adminMenu = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem acceptItem = new javax.swing.JMenuItem("Accept Order");
        javax.swing.JMenuItem cancelItem = new javax.swing.JMenuItem("Cancel Order");

        // Get the ID from the selected row (Column 0)
        int selectedRow = OrdersListTable.getSelectedRow();
        int orderId = (int) OrdersListTable.getValueAt(selectedRow, 0);

        acceptItem.addActionListener(e -> updateOrderStatus(orderId, "Accepted"));
        cancelItem.addActionListener(e -> updateOrderStatus(orderId, "Cancelled"));

        adminMenu.add(acceptItem);
        adminMenu.add(cancelItem);
        adminMenu.show(invoker, x, y);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        OrdersListTable = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();

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
        orderslist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/task-checklist (1).png"))); // NOI18N
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

        OrdersListTable.setBackground(new java.awt.Color(242, 245, 248));
        OrdersListTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OrdersListTable.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(OrdersListTable);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 54, 561, 435));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Orders List");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 100, -1));

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
            java.util.logging.Logger.getLogger(OrdersList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdersList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdersList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdersList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrdersList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable OrdersListTable;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel orderslist;
    private javax.swing.JLabel product;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables
}

package customer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Orders extends javax.swing.JFrame {

    public Orders() {
        initComponents();
        loadOrdersTable();

        // Enable Right-Click on the table
        OrdersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (javax.swing.SwingUtilities.isRightMouseButton(evt)) {
                    int row = OrdersTable.rowAtPoint(evt.getPoint());
                    if (row != -1) {
                        OrdersTable.setRowSelectionInterval(row, row);
                        showCustomerPopupMenu(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
            }
        });
    }

    private void loadOrdersTable() {
        String[] columns = {"Product", "Price", "Qty", "Total", "Status", "ID"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        OrdersTable.setModel(model);

        try (Connection conn = config.db.mycon()) {
            // Ensure necessary columns exist in the products table to prevent the p_rating error
            Statement st = conn.createStatement();
            try { st.execute("ALTER TABLE products ADD COLUMN p_rating TEXT"); } catch (Exception e) { /* Column exists */ }
            try { st.execute("ALTER TABLE products ADD COLUMN p_feedback TEXT"); } catch (Exception e) { /* Column exists */ }

            String sql = "SELECT p_name, p_price, p_qty, p_total, status, id FROM orders ORDER BY id DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("p_name"),
                    "₱ " + rs.getString("p_price"),
                    rs.getInt("p_qty"),
                    "₱ " + rs.getString("p_total"),
                    rs.getString("status") == null ? "Pending" : rs.getString("status"),
                    rs.getInt("id")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }

        styleOrdersTable();
        OrdersTable.getColumnModel().getColumn(5).setMinWidth(0);
        OrdersTable.getColumnModel().getColumn(5).setMaxWidth(0);
        OrdersTable.getColumnModel().getColumn(5).setPreferredWidth(0);
    }

    private void styleOrdersTable() {
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);

                if (column == 4 && value != null) {
                    String status = value.toString();
                    if (status.equalsIgnoreCase("Accepted") || status.equalsIgnoreCase("Shipped")) {
                        setForeground(new Color(0, 153, 0));
                    } else if (status.equalsIgnoreCase("Cancelled")) {
                        setForeground(Color.RED);
                    } else if (status.equalsIgnoreCase("Completed")) {
                        setForeground(new Color(0, 102, 204));
                    } else {
                        setForeground(new Color(204, 102, 0));
                    }
                    setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(Color.BLACK);
                    setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        };

        for (int i = 0; i < OrdersTable.getColumnCount(); i++) {
            OrdersTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
        }
        OrdersTable.setRowHeight(35);
        OrdersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void showCustomerPopupMenu(java.awt.Component invoker, int x, int y) {
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
        int row = OrdersTable.getSelectedRow();
        if (row == -1) return;

        String currentStatus = OrdersTable.getValueAt(row, 4).toString();
        int orderId = (int) OrdersTable.getValueAt(row, 5);
        String pName = OrdersTable.getValueAt(row, 0).toString();

        if (currentStatus.equalsIgnoreCase("Pending")) {
            javax.swing.JMenuItem cancelItem = new javax.swing.JMenuItem("Cancel Order");
            cancelItem.addActionListener(e -> handleCancelOrder(orderId));
            menu.add(cancelItem);
        }

        if (currentStatus.equalsIgnoreCase("Shipped") || currentStatus.equalsIgnoreCase("Accepted")) {
            javax.swing.JMenuItem receiveItem = new javax.swing.JMenuItem("Order Received");
            receiveItem.addActionListener(e -> handleOrderReceived(orderId, pName));
            menu.add(receiveItem);
        }

        if (menu.getComponentCount() > 0) {
            menu.show(invoker, x, y);
        }
    }

    private void handleCancelOrder(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this order?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = config.db.mycon()) {
                String sql = "UPDATE orders SET status = 'Cancelled' WHERE id = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                loadOrdersTable();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void handleOrderReceived(int orderId, String pName) {
        try (Connection conn = config.db.mycon()) {
            // 1. Update the order status to Completed
            String sqlUpdate = "UPDATE orders SET status = 'Completed' WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sqlUpdate);
            pst.setInt(1, orderId);

            if (pst.executeUpdate() > 0) {
                // 2. Prompt for Rating
                String[] options = {"5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star"};
                String rating = (String) JOptionPane.showInputDialog(this, 
                        "How would you rate " + pName + "?", "Rate Product", 
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (rating != null) {
                    // 3. Optional Feedback
                    String feedback = JOptionPane.showInputDialog(this, 
                            "(Optional) Leave a comment about the product:", "Feedback", 
                            JOptionPane.PLAIN_MESSAGE);

                    // 4. Get the SPECIFIC Product ID for this order
                    int actualProductID = getProductIDByName(pName); 

                    // 5. Save using the unique p_id
                    saveRatingToDatabase(actualProductID, rating, (feedback == null) ? "" : feedback);

                    JOptionPane.showMessageDialog(this, "Order Completed! Thank you for your feedback.");
                }
                loadOrdersTable();
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    private void saveRatingToDatabase(int productId, String ratingValue, String feedback) {
        try (Connection conn = config.db.mycon()) {
            // TARGETING BY p_id instead of name prevents duplicate updates
            String sql = "UPDATE products SET p_rating = ?, p_feedback = ? WHERE p_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, ratingValue);
            pst.setString(2, feedback);
            pst.setInt(3, productId); // Using p_id here
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper to find the p_id. 
    // Ideally, you should save p_id into your 'orders' table when the user buys the item.
    private int getProductIDByName(String pName) {
        int id = 0;
        try (Connection conn = config.db.mycon()) {
            // This finds the first product matching the name. 
            // If names are identical, this is a temporary workaround.
            String sql = "SELECT p_id FROM products WHERE p_name = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("p_id");
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        shop = new javax.swing.JLabel();
        mycart = new javax.swing.JLabel();
        order = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrdersTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(242, 245, 248));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/task-checklist (1).png"))); // NOI18N
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
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/user.png"))); // NOI18N
        profile.setText("  Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        jPanel4.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 150, -1));

        bg.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Orders");
        bg.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 70, -1));

        OrdersTable.setBackground(new java.awt.Color(242, 245, 248));
        OrdersTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OrdersTable.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(OrdersTable);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 54, 561, 435));
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

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
            java.util.logging.Logger.getLogger(Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Orders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable OrdersTable;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel mycart;
    private javax.swing.JLabel order;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel shop;
    // End of variables declaration//GEN-END:variables
}

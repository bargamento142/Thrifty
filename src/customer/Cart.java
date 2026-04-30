package customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Cart extends javax.swing.JFrame {

    public Cart() {
        initComponents();
        configureTableStyle();
        loadCartTable();
    }

    private void configureTableStyle() {
        CartTable.setShowGrid(false);
        CartTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        CartTable.setRowHeight(100); // Height to accommodate image padding
        
        JTableHeader header = CartTable.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        CartTable.setSelectionBackground(new java.awt.Color(230, 240, 240));
    }

    private void loadCartTable() {
        String[] columns = {"Image", "Product", "Price", "Quantity", "Actions", "Path", "Cart ID"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };
        CartTable.setModel(model);

        try (Connection conn = config.db.mycon()) {
            // Using cart_id here
            String sql = "SELECT cart_id, p_name, p_price, p_image, p_qty FROM cart WHERE status = 'Active'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int cId = rs.getInt("cart_id");
                String name = rs.getString("p_name");
                String price = rs.getString("p_price");
                String imgPath = rs.getString("p_image");
                int qty = rs.getInt("p_qty");

                ImageIcon icon = null;
                try {
                    icon = new ImageIcon(new ImageIcon(imgPath).getImage()
                        .getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                } catch (Exception e) { }

                model.addRow(new Object[]{icon, name, "₱ " + price, qty, "", imgPath, cId});
            }
            ttlcart.setText("Shopping Cart ( " + model.getRowCount() + " )");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupTableRenderersAndEditors();

        // Hide the Cart ID column
        CartTable.getColumnModel().getColumn(6).setMinWidth(0);
        CartTable.getColumnModel().getColumn(6).setMaxWidth(0);
        CartTable.getColumnModel().getColumn(6).setPreferredWidth(0);
    }
    
    private void setupTableRenderersAndEditors() {
        // 1. Image Renderer
        CartTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
                return label;
            }
        });

        // 2. Action Column (Icon Button)
        TableColumn actionColumn = CartTable.getColumnModel().getColumn(4);
        actionColumn.setCellRenderer(new ActionButtonRenderer());
        actionColumn.setCellEditor(new ActionButtonEditor(new JCheckBox()));

        // 3. Center Text Renderers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);

                // Force the text to stay black even when selected
                setForeground(Color.BLACK); 

                // Ensure the background still changes to your selection color
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

                return this;
            }
        };

        // Apply to Product name, Price, and Quantity columns
        CartTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); 
        CartTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
        CartTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        CartTable.getColumnModel().getColumn(5).setMinWidth(0);
        CartTable.getColumnModel().getColumn(5).setMaxWidth(0);
        CartTable.getColumnModel().getColumn(5).setPreferredWidth(0);
    }

    // --- CUSTOM CLASSES FOR THE ACTION BUTTON ---

    class ActionButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton button = new JButton();
        public ActionButtonRenderer() {
            setLayout(new GridBagLayout());
            button.setPreferredSize(new Dimension(30, 30));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/customer/icons/menu-dots-vertical.png"));
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                button.setText("⋮");
            }
            add(button);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    class ActionButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel(new GridBagLayout());
        private final JButton button = new JButton();

        public ActionButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button.setPreferredSize(new Dimension(30, 30));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/customer/icons/menu-dots-vertical.png"));
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                button.setText("⋮");
            }
            
            button.addActionListener(e -> {
                fireEditingStopped(); // Close editor immediately
                int row = CartTable.getSelectedRow();
                Rectangle rect = CartTable.getCellRect(row, 4, true);
                showPopupMenu(CartTable, rect.x + rect.width/2, rect.y + rect.height);
            });
            panel.add(button);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }
    }

    private void showPopupMenu(Component invoker, int x, int y) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem buyItem = new JMenuItem("Buy Now"); 
        JMenuItem viewItem = new JMenuItem("View Product");
        JMenuItem removeItem = new JMenuItem("Remove from Cart");

        // --- BUY NOW ACTION ---
        buyItem.addActionListener(e -> {
            int row = CartTable.getSelectedRow();
            if (row != -1) {
                int cId = (int) CartTable.getValueAt(row, 6);
                String name = CartTable.getValueAt(row, 1).toString();
                String rawPrice = CartTable.getValueAt(row, 2).toString().replace("₱ ", "").trim();
                double price = Double.parseDouble(rawPrice);
                int qty = Integer.parseInt(CartTable.getValueAt(row, 3).toString());
                double total = price * qty;

                int confirm = JOptionPane.showConfirmDialog(this, "Buy " + name + " now?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = config.db.mycon()) {
                        // 1. Record the Order
                        String sqlOrder = "INSERT INTO orders (p_name, p_price, p_qty, p_total, status) VALUES (?, ?, ?, ?, 'Pending')";
                        PreparedStatement pstOrder = conn.prepareStatement(sqlOrder);
                        pstOrder.setString(1, name);
                        pstOrder.setDouble(2, price);
                        pstOrder.setInt(3, qty);
                        pstOrder.setDouble(4, total);
                        pstOrder.executeUpdate();

                        // 2. Mark this specific cart entry as Ordered
                        String sqlUpdate = "UPDATE cart SET status = 'Ordered' WHERE cart_id = ?";
                        PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                        pstUpdate.setInt(1, cId);
                        pstUpdate.executeUpdate();

                        loadCartTable();
                        JOptionPane.showMessageDialog(this, "Order placed successfully!");
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        });

        // --- VIEW PRODUCT ACTION ---
        viewItem.addActionListener(e -> {
            int row = CartTable.getSelectedRow();
            if (row != -1) {
                String name = CartTable.getValueAt(row, 1).toString();
                String price = CartTable.getValueAt(row, 2).toString();
                String path = CartTable.getValueAt(row, 5).toString(); 
                new ItemDetail(name, price, path, this).setVisible(true);
                this.dispose();
            }
        });

        // --- REMOVE ACTION ---
        removeItem.addActionListener(e -> {
            int row = CartTable.getSelectedRow();
            if (row != -1) {
                int cId = (int) CartTable.getValueAt(row, 6);
                int confirm = JOptionPane.showConfirmDialog(this, "Remove item from cart?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = config.db.mycon()) {
                        String sql = "UPDATE cart SET status = 'Archived' WHERE cart_id = ?";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setInt(1, cId);
                        if (pst.executeUpdate() > 0) {
                            loadCartTable();
                            JOptionPane.showMessageDialog(null, "Item removed.");
                        }
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        });

        menu.add(buyItem);
        menu.add(viewItem);
        menu.addSeparator();
        menu.add(removeItem);
        menu.show(invoker, x, y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        ttlcart = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        CartTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        shop = new javax.swing.JLabel();
        mycart = new javax.swing.JLabel();
        order = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(242, 245, 248));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Shopping Cart");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 130, -1));

        ttlcart.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ttlcart.setText("( 0 )");
        jPanel1.add(ttlcart, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 40, -1));

        CartTable.setBackground(new java.awt.Color(242, 245, 248));
        CartTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CartTable.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(CartTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 54, 561, 435));

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
        mycart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/shopping-cart (1).png"))); // NOI18N
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
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/icons/user.png"))); // NOI18N
        profile.setText("  Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        jPanel4.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 150, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 10));

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
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable CartTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel mycart;
    private javax.swing.JLabel order;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel shop;
    private javax.swing.JLabel ttlcart;
    // End of variables declaration//GEN-END:variables
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Gui {
    JLabel message;
    DefaultTableModel shopTableModel;
    ArrayList<String> idOfItems = new ArrayList<>();

    Gui(Player player) {
        JFrame jFrame = new JFrame("Chop Wood Game");


        jFrame.setFocusable(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));


        jFrame.setSize(600, 600);
        jFrame.setLocationRelativeTo(null);


        JLabel playerInfo = new JLabel(updatePlayerInfo(player));
        playerInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton chopWood = new JButton("[1] Get wood");
        chopWood.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton sellWood = new JButton("[2] Sell wood");
        sellWood.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton shop = new JButton("[3] Shop");
        shop.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton equipment = new JButton("Show equipment");
        equipment.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton coinThrow = new JButton("Coin Throw Game");
        coinThrow.setAlignmentX(Component.CENTER_ALIGNMENT);

        chopWood.addActionListener(e -> {
            int woodGot = player.makeWood();
            playerInfo.setText(updatePlayerInfo(player));
            message.setText("You got " + woodGot + " wood.");
        });


        sellWood.addActionListener(e -> {
            int soldWood = player.sellWood();
            if (soldWood != -1) {
                playerInfo.setText(updatePlayerInfo(player));
                message.setText("Sold " + soldWood + " wood. Added money: " + (soldWood / 10));
            } else {
                message.setText("Cannot sell wood.");
            }
        });

        shop.addActionListener(e -> {
            //TODO: Make action listener for SHOP
            JFrame shopFrame = new JFrame("Shop");
            shopFrame.setLayout(new BoxLayout(shopFrame.getContentPane(), BoxLayout.Y_AXIS));
            shopFrame.setFocusable(true);

            shopTableModel = createShopTableModel(player);

            JTable bee = new JTable(shopTableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JScrollPane scrollPane = new JScrollPane(bee);
            JButton buy = new JButton("Buy");
            bee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            buy.addActionListener(e1 -> {
                //TODO: add a action listener to BUY button
                if (bee.getSelectedRow() != -1) {
                    String itemId = idOfItems.get(bee.getSelectedRow());
                    //JOptionPane.showConfirmDialog(shopFrame, itemId);
                    String itemBuy = "Buy " + Items.getItem(itemId).getNameItem() + "?";
                    if (JOptionPane.showConfirmDialog(shopFrame, itemBuy, "Shop", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Shop.buyItem(itemId, player);
                        playerInfo.setText(updatePlayerInfo(player));
                        shopTableModel = createShopTableModel(player);
                        shopFrame.dispose();
                        shop.doClick();
                    }

                } else JOptionPane.showMessageDialog(shopFrame, "Choose item first!");
            });

            bee.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        buy.doClick();
                    }
                }
            });

            shopFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) && bee.getSelectedRow() == -1 && bee.getRowCount()>0) {
                        bee.setRowSelectionInterval(0, 0);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && bee.getSelectedRow() < bee.getRowCount() - 1) {
                        bee.setRowSelectionInterval(bee.getSelectedRow() + 1, bee.getSelectedRow() + 1);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP && bee.getSelectedRow() > 0) {
                        bee.setRowSelectionInterval(bee.getSelectedRow() - 1, bee.getSelectedRow() - 1);
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        buy.doClick();
                    }
                }
            });


            shopFrame.add(scrollPane);
            buy.setAlignmentX(Component.CENTER_ALIGNMENT);
            shopFrame.add(buy);


            shopFrame.setSize(300, 300);
            shopFrame.setLocationRelativeTo(jFrame);

            shopFrame.setVisible(true);
        });

        equipment.addActionListener(e -> {
            //TODO: Make action listener for EQUIPMENT
        });

        coinThrow.addActionListener(e -> {
            //TODO: Make action listener for COIN THROW
        });

        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(playerInfo);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(chopWood);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(sellWood);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(shop);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(equipment);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));
        jFrame.add(coinThrow);
        jFrame.add(Box.createRigidArea(new Dimension(600, 10)));

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    chopWood.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    sellWood.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    shop.doClick();
                }
            }
        });

        message = new JLabel("");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        jFrame.add(message);
        jFrame.setVisible(true);
    }

    private String updatePlayerInfo(Player player) {
        return "Level: " + player.getLevel() +
                ", Wood: " + player.getWoodAmount() +
                ", Money: " + player.getMoney() +
                ", Power: " + player.getPower() +
                ", Exp: " + player.getExperience();
    }

    private DefaultTableModel createShopTableModel(Player player) {
        String[] column = {"Name", "Power bonus", "Cost"};
        shopTableModel = new DefaultTableModel(null, column);
        idOfItems.clear();
        for (int i = 0; i < Items.itemsSize(); i++) {
            String itemId = Items.getItem(Items.getItemId(i)).getNameId();
            String name = Items.getItem(Items.getItemId(i)).getNameItem();
            int powerBonus = Items.getItem(Items.getItemId(i)).getPower();
            int cost = Items.getItem(Items.getItemId(i)).getPrice();
            if (!player.findItem(itemId)) {
                shopTableModel.addRow(new String[]{name, String.valueOf(powerBonus), String.valueOf(cost)});
                idOfItems.add(itemId);
            }
        }
        return shopTableModel;
    }


}

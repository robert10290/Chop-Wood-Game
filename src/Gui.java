import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class Gui {
    JLabel message;
    DefaultTableModel tableModel;
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
        JButton equipment = new JButton("[4] Show equipment");
        equipment.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton coinThrow = new JButton("[5] Coin Throw Game");
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

            tableModel = createShopTableModel(player);

            JTable table = new JTable(tableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JScrollPane scrollPane = new JScrollPane(table);
            JButton buy = new JButton("Buy");
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            buy.addActionListener(e1 -> {
                //TODO: add a action listener to BUY button
                if (table.getSelectedRow() != -1) {
                    String itemId = idOfItems.get(table.getSelectedRow());
                    //JOptionPane.showConfirmDialog(shopFrame, itemId);
                    String itemBuy = "Buy " + Items.getItem(itemId).getNameItem() + "?";
                    if (JOptionPane.showConfirmDialog(shopFrame, itemBuy, "Shop", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Shop.buyItem(itemId, player);
                        playerInfo.setText(updatePlayerInfo(player));
                        tableModel = createShopTableModel(player);
                        shopFrame.dispose();
                        shop.doClick();
                    }

                } else JOptionPane.showMessageDialog(shopFrame, "Choose item first!");
            });

            table.addKeyListener(new KeyAdapter() {
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
                    if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) && table.getSelectedRow() == -1 && table.getRowCount() > 0) {
                        table.setRowSelectionInterval(0, 0);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && table.getSelectedRow() < table.getRowCount() - 1) {
                        table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP && table.getSelectedRow() > 0) {
                        table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        buy.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        shopFrame.dispose();
                    }
                }
            });


            shopFrame.add(scrollPane);
            buy.setAlignmentX(Component.CENTER_ALIGNMENT);
            shopFrame.add(buy);


            shopFrame.setSize(300, 300);
            shopFrame.setLocationRelativeTo(jFrame);

            shopFrame.setVisible(true);

            shopFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jFrame.requestFocus();
                }
            });
        });

        equipment.addActionListener(e -> {
            //TODO: Make action listener for EQUIPMENT
            if (player.getEquipment().isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "Equipment empty");
                jFrame.requestFocus();
                return;
            }
            JFrame equipmentFrame = new JFrame("Equipment");
            equipmentFrame.setLayout(new BoxLayout(equipmentFrame.getContentPane(), BoxLayout.Y_AXIS));
            equipmentFrame.setFocusable(true);
            tableModel = createEquipmentTableModel(player);

            JTable bee = new JTable(tableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JScrollPane scrollPane = new JScrollPane(bee);
            bee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //scrollPane.add(bee);
            equipmentFrame.add(scrollPane);
            equipmentFrame.setSize(300, 300);
            equipmentFrame.setLocationRelativeTo(jFrame);
            equipmentFrame.setVisible(true);

            equipmentFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jFrame.requestFocus();
                }
            });


        });


        coinThrow.addActionListener(e -> {
            //TODO: Make action listener for COIN THROW
            try {
                Object[] options = {"Heads", "Tails", "Quit"};
                String ret = JOptionPane.showInputDialog(
                        jFrame,
                        "Enter the amount of money you want to bet",
                        "Casino",
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null).toString();
                int moneyBet = Integer.parseInt(ret);
                int choice;
                if(moneyBet>0 && moneyBet<=player.getMoney()) {
                    choice = JOptionPane.showOptionDialog(
                            jFrame,
                            "Heads or Tails?",
                            "Choose",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);
                    int resultOfFlip = Casino.coinflip();
                    if(resultOfFlip == choice && choice!=2) {
                        JOptionPane.showMessageDialog(jFrame, "You've won money.");
                        player.addMoney(moneyBet);
                        playerInfo.setText(updatePlayerInfo(player));
                    }
                    else if(choice!=2){
                        JOptionPane.showMessageDialog(jFrame, "You've lost money.");
                        player.addMoney(-moneyBet);
                        playerInfo.setText(updatePlayerInfo(player));
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(jFrame, "Not valid value");
            } catch (NullPointerException ignored) {

            }
            jFrame.requestFocus();



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
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    equipment.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_5) {
                    coinThrow.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
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
        tableModel = new DefaultTableModel(null, column);
        idOfItems.clear();
        for (int i = 0; i < Items.itemsSize(); i++) {
            String itemId = Items.getItem(Items.getItemId(i)).getNameId();
            String name = Items.getItem(Items.getItemId(i)).getNameItem();
            int powerBonus = Items.getItem(Items.getItemId(i)).getPower();
            int cost = Items.getItem(Items.getItemId(i)).getPrice();
            if (!player.findItem(itemId)) {
                tableModel.addRow(new String[]{name, String.valueOf(powerBonus), String.valueOf(cost)});
                idOfItems.add(itemId);
            }
        }
        return tableModel;
    }

    private DefaultTableModel createEquipmentTableModel(Player player) {
        String[] column = {"Name", "Power bonus"};
        tableModel = new DefaultTableModel(null, column);
        ArrayList<Item> eq = (ArrayList<Item>) player.getEquipment();
        idOfItems.clear();
        for (Item item : eq) {
            String itemId = item.getNameId();
            String name = item.getNameItem();
            int powerBonus = item.getPower();
            tableModel.addRow(new String[]{name, String.valueOf(powerBonus)});
            idOfItems.add(itemId);
        }
        return tableModel;
    }


}

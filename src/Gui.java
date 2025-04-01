import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    JLabel message;

    Gui(Player player) {
        JFrame jfrm = new JFrame("Game");


        //jfrm.setLayout(new FlowLayout());
        jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jfrm.setLayout(new BoxLayout(jfrm.getContentPane(), BoxLayout.Y_AXIS));


        jfrm.setSize(600,600);
        jfrm.setLocationRelativeTo(null);


        JLabel playerInfo = new JLabel(updatePlayerInfo(player));
        playerInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton chopWood = new JButton("Get wood");
        chopWood.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton sellWood = new JButton("Sell wood");
        sellWood.setAlignmentX(Component.CENTER_ALIGNMENT);

        chopWood.addActionListener(e -> {
            int woodGot=player.makeWood();
            playerInfo.setText(updatePlayerInfo(player));
            message.setText("You got "+woodGot+" wood.");
        });

        sellWood.addActionListener(e -> {
            int soldWood = player.sellWood();
            if(soldWood!=-1) {
                playerInfo.setText(updatePlayerInfo(player));
                message.setText("Sold "+soldWood+" wood. Added money: "+(soldWood/10));
            }
            else {
                message.setText("Cannot sell wood.");
            }
        });

        jfrm.add(Box.createRigidArea(new Dimension(600, 10)));
        jfrm.add(playerInfo);
        jfrm.add(Box.createRigidArea(new Dimension(600, 10)));
        jfrm.add(chopWood);
        jfrm.add(Box.createRigidArea(new Dimension(600, 10)));
        jfrm.add(sellWood);
        jfrm.add(Box.createRigidArea(new Dimension(600, 10)));

        message=new JLabel("");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        jfrm.add(message);
        //jfrm.pack();
        jfrm.setVisible(true);
    }

    private String updatePlayerInfo(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Level: "+player.getLevel());
        stringBuilder.append(", Wood: "+player.getWoodAmount());
        stringBuilder.append(", Money: "+player.getMoney());
        stringBuilder.append(", Power: "+player.getPower());
        stringBuilder.append(", Exp: "+player.getExperience());

        return stringBuilder.toString();
    }

    //private JButton createJButton(String text,)
}

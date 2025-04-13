import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Init.start(); //initialize objects

        String name = "namae";
        Player player = new Player(name);

        SwingUtilities.invokeLater(() -> new Gui(player));
    }
}
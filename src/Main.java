import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Init.start(); //initialize objects
        System.out.println("Hello in da Game!");
        System.out.println("Name of a player:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Player player = new Player(name);
        if(!player.getName().isEmpty()) System.out.println("Hello "+player.getName());
        String action="";
        while(!action.equalsIgnoreCase("quit")) {

            System.out.print("Level: "+player.getLevel());
            System.out.print(", Wood: "+player.getWoodAmount());
            System.out.print(", Money: "+player.getMoney());
            System.out.print(", Power: "+player.getPower());
            System.out.println(", Exp: "+player.getExperience());
            System.out.println("Select action:");
            System.out.println("1. Get wood");
            System.out.println("2. Sell wood");
            System.out.println("3. Shop");
            System.out.println("4. Show equipment");
            System.out.println("5. Coin Throw Game");
            System.out.println("quit: exit game");
            //action=String.valueOf(System.in.read());
            action=scanner.nextLine();
            switch (action) {
                case "1":
                    int wood = player.makeWood();
                    System.out.println("You got "+wood+" wood.");
                    break;
                case "2":
                    player.sellWood();
                    break;
                case "3":
                    Shop.open(player);
                    break;
                case "4":
                    player.showEquipment();
                    break;
                case "5":
                    player.coinFlipGame(player);
                    break;
            }
        }
        scanner.close();

    }
}
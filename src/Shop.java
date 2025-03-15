import java.util.Scanner;

public class Shop {
    public static void open(Player player) {
        Scanner scanner = new Scanner(System.in);

        String action="";

        while(!action.equalsIgnoreCase("b")) {
            System.out.println("Hello in Shop! Things to buy:");
            if(player.findItem(Item.basicAxe().getNameId())) System.out.println("1. Basic axe [+5 to Power][10 money]");
            System.out.println("b. Back");
            action=scanner.nextLine();
            switch (action) {
                case "1":
                    if(player.getMoney()>=10 && !player.findItem(Item.basicAxe().getNameId())) {
                        player.addMoney(-10);
                        Item basicAxe = Item.basicAxe();
                        player.addItemToEquipment(basicAxe);
                        player.addPower(basicAxe.getPower());
                        System.out.println("You bought Basic Axe. You've got +5 to Power");
                        break;
                    }
                    else System.out.println("Cannot buy");
                    break;
            }


        }


    }
}

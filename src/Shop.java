import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    public static void open(Player player) {
        Scanner scanner = new Scanner(System.in);
        String action = "";
        while (!action.equalsIgnoreCase("b")) {
            System.out.println("Hello in Shop! Things to buy:");
            /*if(!player.findItem("basic_axe")) System.out.println("1. Basic axe [+5 to Power][10 money]");
            if(!player.findItem("normal_axe")) System.out.println("2. Normal axe [+20 to Power][20 money]");
            System.out.println("b. Back");*/

            int iter = 1;
            List<String> itemNameIdList = new ArrayList<>();
            for (int i = 0; i < Items.itemsSize(); i++) {
                String itemId = Items.getItemId(i);
                Item item = Items.getItem(Items.getItemId(i));
                if (!player.findItem(itemId)) {
                    System.out.println((iter) + ". " + item.getNameItem() + " [+" +
                            item.getPower() + " to Power][" + item.getPrice() + " money]");
                    itemNameIdList.add(item.getNameId());
                    iter++;
                }
            }


            action = scanner.nextLine();
            if (action.equalsIgnoreCase("b")) return;

            try {
                Integer.parseInt(action);
            } catch (NumberFormatException ignored) {
            }

            switch (action) {
                case "1":
                    /*if(player.getMoney()>=10 && !player.findItem(Item.basicAxe().getNameId())) {
                        player.addMoney(-10);
                        Item basicAxe = Item.basicAxe();
                        player.addItemToEquipment(basicAxe);
                        player.addPower(basicAxe.getPower());
                        System.out.println("You bought Basic Axe. You've got +5 to Power");
                        break;
                    }
                    else System.out.println("Cannot buy");
                    break;*/
                    buyItem("basic_axe", player, 10);
                    break;
                case "2":
                    buyItem("normal_axe", player, 20);
                    break;
            }


        }


    }

    public static void buyItem(String itemId, Player player, int price) {
        if (player.getMoney() >= price && !player.findItem(itemId)) {
            player.addMoney(-price);
            Item item = Item.createItem(itemId);
            player.addItemToEquipment(item);
            player.addPower(item.getPower());
            System.out.println("You bought " + item.getNameItem() + ". You've got +" + item.getPower() + " to Power");
        } else System.out.println("Cannot buy");


    }
}

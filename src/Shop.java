import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    public static void open(Player player) {
        Scanner scanner = new Scanner(System.in);
        String action = "";
        while (!action.equalsIgnoreCase("b")) {
            System.out.println("Hello in Shop! Things to buy:");

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
                int chosenAction = Integer.parseInt(action);
                buyItem(itemNameIdList.get(chosenAction - 1), player);
            } catch (Exception ignored) {
            }
        }


    }

    public static void buyItem(String itemId, Player player) {
        int price = Items.getItem(itemId).getPrice();
        if (player.getMoney() >= price && !player.findItem(itemId)) {
            player.addMoney(-price);
            Item item = Item.createItem(itemId);
            player.addItemToEquipment(item);
            player.addPower(item.getPower());
            System.out.println("You bought " + item.getNameItem() + ". You've got +" + item.getPower() + " to Power");
        } else System.out.println("Cannot buy");


    }
}

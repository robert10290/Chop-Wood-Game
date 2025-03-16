public class Item {
    private String nameItem;
    private String nameId;
    private int power;
    private int price;

    public int getPrice() {
        return price;
    }

    public Item(String nameItem, String nameId, int power, int price) {
        this.nameItem = nameItem;
        this.nameId = nameId;
        this.power = power;
        this.price = price;
    }

    public static Item createItem(String itemId) {
        String nameItem = Items.getItem(itemId).nameItem;
        String nameId = Items.getItem(itemId).nameId;
        int power = Items.getItem(itemId).power;
        int price = Items.getItem(itemId).price;
        return new Item(nameItem, nameId, power, price);
        //if(itemId.equalsIgnoreCase("basic_axe")) this("Basic Axe", "basic_axe", 5);
    }

    public String getNameItem() {
        return nameItem;
    }

    public String getNameId() {
        return nameId;
    }

    public int getPower() {
        return power;
    }
}

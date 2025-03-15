public class Item {
    private String nameItem;
    private String nameId;
    private int power;

    public Item(String nameItem, String nameId, int power) {
        this.nameItem = nameItem;
        this.nameId = nameId;
        this.power = power;
    }

    public static Item basicAxe() {
        return new Item("Basic Axe", "basic_axe",5);
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

import java.util.*;

public class Player {
    private String name;
    private int woodAmount;
    private int power;
    private int experience;
    private int level;
    private int money;
    private final List<Item> equipment;

    Player(String name) {
        setName(name);
        setWoodAmount(0);
        setPower(1);
        setExperience(0);
        updateLevel();
        setMoney(3000);
        equipment = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    //level update based on experience amount
    public int updateLevel() {
        int i = 1;
        while (true) {
            if (experience < 1000 * i) {
                level = i;
                return level;
            }
            i++;
        }
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void addExperience(int exp) {
        experience += exp;
        if (level < updateLevel()) System.out.println("Promoted to level " + level);
    }

    public int getPower() {
        return power;
    }

    public int getExperience() {
        return experience;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int woodAmount) {
        this.woodAmount = woodAmount;
    }

    public void addWoodAmount(int woodAmount) {
        this.woodAmount += woodAmount;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void addPower(int power) {
        this.power += power;
    }

    public int makeWood() {
        Random random = new Random();
        int wood = (int) ((random.nextInt(2)) + (0.5 * random.nextInt(level + power)));
        if (wood > 0) {
            int gainedExp = (int) (Math.random() * 20 + wood);
            System.out.println("Gained " + gainedExp + " exp points.");
            addExperience(gainedExp);
            addWoodAmount(wood);
        }
        return wood;
    }

    public int sellWood() {
        if ((getWoodAmount() / 10) > 0) {
            int soldAmountOfWood = (getWoodAmount() / 10) * 10;
            int addedMoney = (getWoodAmount() / 10);
            money += addedMoney;
            woodAmount = getWoodAmount() % 10;
            System.out.println("Sold " + soldAmountOfWood + " wood. Added money: " + addedMoney);
            return soldAmountOfWood;
        } else {
            System.out.println("Cannot sell wood.");
            return -1;
        }

    }

    public void addItemToEquipment(Item item) {
        equipment.add(item);
    }

    public List<Item> getEquipment() {
        return equipment;
    }

    public boolean findItem(String nameId) {
        if (equipment.isEmpty()) return false;
        Iterator<Item> itemIterator = equipment.iterator();
        do {
            if (itemIterator.next().getNameId().equalsIgnoreCase(nameId)) return true;
        }
        while (itemIterator.hasNext());
        return false;

    }
}

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private int woodAmount;
    private int power;
    private int experience;
    private int level;
    private int money;
    private List<Item> equipment;

    Player(String name) {
        setName(name);
        setWoodAmount(0);
        setPower(1);
        setExperience(0);
        updateLevel();
        setMoney(10);
        equipment = new ArrayList<>();
    }

    public void showEquipment(){
        if(equipment.isEmpty()) System.out.println("Equipment: [empty]");
        else {
            Iterator<Item> itemIterator = equipment.iterator();
            System.out.print("Equipment: [ ");
            do {
                System.out.print(itemIterator.next().getNameItem());
                if(itemIterator.hasNext()) System.out.print(", ");
            }
            while (itemIterator.hasNext());
            System.out.println(" ]");
        }

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
        int i=1;
        while(true) {
            if(experience<1000*i){
                level=i;
                return level;
            }
            i++;
        }
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    public void addExperience(int exp) {
        experience+=exp;
        if(level<updateLevel()) System.out.println("Promoted to level "+level);
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

    public String getName() {
        return name;
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
        this.woodAmount+=woodAmount;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void addPower(int power) {
        this.power += power;
    }

    public int makeWood() {
        Random random = new Random();
        //int wood = (int)(Math.*(level+1)*getPower());
        int wood = (int)((random.nextInt(2))+(0.5* random.nextInt(level+power)));
        if(wood>0) {
            int gainedExp = (int)(Math.random()*20+wood);
            System.out.println("Gained " +gainedExp+" exp points.");
            addExperience(gainedExp);
            addWoodAmount(wood);
        }
        return wood;
    }

    public void sellWood() {
        if((getWoodAmount()/10)>0) {
            int soldAmountOfWood = (getWoodAmount()/10)*10;
            int addedMoney = (getWoodAmount()/10);
            money += addedMoney;
            woodAmount = getWoodAmount()%10;
            System.out.println("Sold "+soldAmountOfWood+" wood. Added money: "+addedMoney);
        }
        else System.out.println("Cannot sell wood.");
    }

    public void addItemToEquipment(Item item) {
        equipment.add(item);
    }

    public boolean findItem(String nameId) {
        if(equipment.isEmpty()) return false;
        Iterator<Item> itemIterator = equipment.iterator();
        do {
            return itemIterator.next().getNameId().equalsIgnoreCase(nameId);
        }
        while (itemIterator.hasNext());

    }
}

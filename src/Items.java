import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

abstract class Items {
    private static final HashMap<String, Item> items = new HashMap<>();

    public static void init() {
        if (items.isEmpty()) {
            items.put("basic_axe", new Item("Basic Axe", "basic_axe", 5, 10));
            items.put("normal_axe", new Item("Normal Axe", "normal_axe", 20, 20));
            items.put("stronger_axe", new Item("Stronger Axe", "stronger_axe", 50, 100));
            items.put("diabolic_axe", new Item("Diabolic Axe", "diabolic_axe", 500, 1000));
        }
    }

    public static Item getItem(String itemId) {
        return items.get(itemId);
    }

    public static int itemsSize() {
        return items.size();
    }

    public static String getItemId(int index) {
        AtomicInteger i= new AtomicInteger();
        AtomicReference<String> itemId = new AtomicReference<>();
        items.forEach((nameId, item) -> {
            if(index==i.intValue()) itemId.set(nameId);
            i.incrementAndGet();
        });
        return itemId.get();
    }


}

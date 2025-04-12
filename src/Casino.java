import java.util.Random;

public class Casino {
    public static int coinflip() {
        Random random = new Random();
        return random.nextInt(2);
    }
}

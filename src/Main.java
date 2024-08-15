import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        String[] let = new String[100];
        for (int i = 0; i < let.length; i++) {
            let[i] = generateRoute("RLRFR", 100);
        }

        List<Thread> letters = new ArrayList<>();

        for (String route : let) {
            Thread thread = new Thread(() -> {
                Integer count = 0;
                for (int i = 0; i < route.length(); i++) {
                    if (route.charAt(i) == 'R') {
                        count++;
                    }
                }
                System.out.println(route.substring(0, 100) + " -> " + count);

                synchronized (count) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            });
            letters.add(thread);
            thread.start();
        }

        for (Thread thread : letters) {
            thread.join();
        }

        int maxKey = 0;
        int maxValue = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (maxKey == 0 || sizeToFreq.get(key) > sizeToFreq.get(maxKey)) {
                maxKey = key;
                maxValue = sizeToFreq.get(maxKey);
            }
        }

        for (Integer key : sizeToFreq.keySet()) {
            if (maxKey == 0 || sizeToFreq.get(key) > sizeToFreq.get(maxKey)) {
                maxKey = key;
                maxValue = sizeToFreq.get(maxKey);
            }
        }

        System.out.printf("Самое частое количество повторений - %d (встретилось %d раз)\n", maxKey, maxValue);
        sizeToFreq.remove(maxKey, maxValue);
        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.printf("%d (%d раз)\n", key, value);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
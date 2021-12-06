import java.util.HashSet;
import java.util.LinkedList;

public class URLPool {
    private int maxDepth;

    // Ещё не посещённые URl
    private LinkedList<URLDepthPair> pendingURLs;
    // Обработанные URL
    private LinkedList<URLDepthPair> processedURLs;
    // Посещённые URL
    private HashSet<String> seenURLs;
    // Количество ожидающих потоков
    private int waitCount = 0;

    public URLPool(int max) {
        pendingURLs = new LinkedList<>();
        processedURLs = new LinkedList<>();
        seenURLs = new HashSet<>();
        this.maxDepth = max;
    }

    public int getWaitCount() {
        return waitCount;
    }

    // Добавление новой пары
    public synchronized void add(URLDepthPair pair) {
        String newURL = pair.getURL().toString();

        // Вырезает "/" из URL
        String trimURL = (newURL.endsWith("/")) ? newURL.substring(0, newURL.length() - 1) : newURL;
        if (seenURLs.contains(trimURL))
            return;
        seenURLs.add(trimURL);
        if (pair.getDepth() < maxDepth) {
            pendingURLs.add(pair);
            notify();
        }
        processedURLs.add(pair);
    }

    public synchronized URLDepthPair getFirstPair() {
        // Приостанавливаем поток, пока не появится новый URL
        while (pendingURLs.size() == 0) {
            waitCount++;
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Отработано исключение InterruptedException - " + e.getMessage());
            }
            waitCount--;
        }
        return pendingURLs.removeFirst();
    }

    /**
     * Вывести весь пул обработанных.
     */
    public void printURLs() {
        System.out.println("\nКол-во уникальных URLs: " + processedURLs.size());
        while (!processedURLs.isEmpty()) {
            System.out.println(processedURLs.removeFirst());
        }
    }
}

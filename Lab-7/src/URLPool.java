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

    public URLPool(int max) {
        pendingURLs = new LinkedList<>();
        processedURLs = new LinkedList<>();
        seenURLs = new HashSet<>();
        this.maxDepth = max;
    }

    // Добавление новой пары
    public void add(URLDepthPair pair) {
        String newURL = pair.getURL().toString();

        // Вырезает "/" из URL
        String trimURL = (newURL.endsWith("/")) ? newURL.substring(0, newURL.length() - 1) : newURL;
        if (seenURLs.contains(trimURL))
            return;
        seenURLs.add(trimURL);
        if (pair.getDepth() < maxDepth)
            pendingURLs.add(pair);
        processedURLs.add(pair);
    }

    public LinkedList<URLDepthPair> getPendingURLs() {
        return pendingURLs;
    }

    public URLDepthPair getFirstPair() {
        return pendingURLs.removeFirst();
    }

    public void printURLs() {
        System.out.println("\nКол-во уникальных URLs: " + processedURLs.size());
        while (!processedURLs.isEmpty()) {
            System.out.println(processedURLs.removeFirst());
        }
    }
}
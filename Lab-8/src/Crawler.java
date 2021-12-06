import java.net.*;

public class Crawler {
    private final URLPool pool;
    public static int threads = 4;

    public Crawler(String root, int maxDepth) throws MalformedURLException {
        pool = new URLPool(maxDepth);
        URL rootURL = new URL(root);
        pool.add(new URLDepthPair(rootURL, 0));
    }

    public void setThreads(int threads) {
        Crawler.threads = threads;
    }
    public void crawl() {

        for (int i = 0; i < threads; i++) {
            CrawlerTask crawlerTask = new CrawlerTask(pool);
            Thread thread = new Thread(crawlerTask);
            thread.start();
        }

        while (pool.getWaitCount() != threads) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                System.out.println("Отработано исключение InterruptedException - " +
                        e.getMessage());
            }
        }
        pool.printURLs();
    }

}
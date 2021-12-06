import java.net.MalformedURLException;


public class Main {
    public static void main(String[] args) {

        try {
            String url = args[0];
            int depth = Integer.parseInt(args[1]);
            int threads = Integer.parseInt(args[2]);
            Crawler crawler = new Crawler(url, depth);
            crawler.setThreads(threads);
            crawler.crawl();
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            // Проверка на количество аргументов
            System.err.println("There are not enough arguments");
        }
        catch (NumberFormatException | NullPointerException exception) {
            System.err.print("usage: java Crawler <URL>, <depth>");
        }
        catch (MalformedURLException exception) {
            System.err.printf("URl %s не работает", args[0]);
        }
        System.exit(0);
    }
}
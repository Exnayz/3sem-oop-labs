import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    public static final Pattern LINK_PATTERN = Pattern.compile("href\\s*=\\s*\"([^$^\"]*)\"");
    public static int maxPatience = 5000;
    private URLPool pool;

    public Crawler(String root, int maxDepth) throws MalformedURLException {
        pool = new URLPool(maxDepth);
        URL rootURL = new URL(root);
        pool.add(new URLDepthPair(rootURL, 0));
    }

    public void crawl() {
        URLDepthPair nextPair;
        while (pool.getPendingURLs().size() != 0) {
            nextPair = pool.getFirstPair();
            try {
                processURL(nextPair);
            }
            catch (IOException e) {
                System.err.println("Ошибка: " + nextPair.getURL() + " - " + e.getMessage());
            }
        }
        pool.printURLs();
    }

    // Создает сокет для отправки HTTP-запроса на данную страницу.
    public Socket sendRequest(URLDepthPair nextPair) throws IOException {
        Socket socket = new Socket(nextPair.getHost(), 80);
        socket.setSoTimeout(maxPatience);
        OutputStream os = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(os, true);
        writer.println("GET " + nextPair.getDocPath() + " HTTP/1.1\n" + "Host: " + nextPair.getHost() + "\nConnection: close\n");
        return socket;
    }

    //Обработка URL-адреса и добавление всех найденных ссылок в пул
    public void processURL(URLDepthPair url) throws IOException {
        Socket socket;
        try {
            socket = sendRequest(url);
        }
        catch (UnknownHostException e) {
            System.err.println("Хост \"" + url.getHost() + "\" не определен.");
            return;
        }
        catch (SocketException e) {
            System.err.println("Ошибка: " + url.getURL() + " - " + e.getMessage());
            return;
        }
        catch (IOException e) {
            System.err.println("Не удалось получить страницу " + url.getURL() + " - " + e.getMessage());
            return;
        }

        InputStream input = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = reader.readLine()) != null) {
            Matcher LinkFinder = LINK_PATTERN.matcher(line);
            while (LinkFinder.find()) {
                String newURL = LinkFinder.group(1);

                URL newSite;
                try {
                    if (URLDepthPair.isRightURL(newURL)) {
                        newSite = new URL(newURL);
                        pool.add(new URLDepthPair(newSite, url.getDepth() + 1));
                    }
                } catch (MalformedURLException e) {
                    System.err.println("Ошибка URL - " + e.getMessage());
                }
            }
        }
        reader.close();

        try {
            socket.close();
        }
        catch (IOException e) {
            System.err.println("Не удалось закрыть соединение с " + url.getHost() + " - " + e.getMessage());
        }
    }
}
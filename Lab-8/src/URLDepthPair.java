import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDepthPair {
    public static final Pattern URL_PATTERN = Pattern.compile("^((https:|http:)//[\\w-]+)(\\.[\\w-]+)+(.*)?");
    private final URL url;
    private final int depth;

    public URLDepthPair(URL url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public URL getURL() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public String getHost() {
        return url.getHost();
    }

    public String getDocPath() {
        if (url.getPath().equals(""))
            return "/";
        else
            return url.getPath();
    }

    public static boolean isRightURL(String url) {
        Matcher URLChecker = URL_PATTERN.matcher(url);
        return URLChecker.find();
    }

    @Override
    public String toString() {
        return "URL = " + url + " depth = " + depth;
    }
}

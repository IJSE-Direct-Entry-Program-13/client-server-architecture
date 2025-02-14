import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Demo {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        URL url = new URL("https://google.lk/search?q=ijse#test");
        System.out.println(url);
        System.out.println(url.toURI());

        URI uri = new URI("https://google.lk/search?q=ijse#test");
        System.out.println(uri);
        System.out.println(uri.toURL());
    }
}

package MainFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class PagesParser {

    private boolean ready = false;
    private int n = 0;
    private URI uri;
    private String links;

    PagesParser(String string) throws URISyntaxException, IOException {
        uri = new URI(string);
        links = getLinksDown(uri, uri.toURL().toString()).toString();
    }


    public String getPagesURLs() {
        return links;
    }

    public boolean isReady() {
        return ready;
    }

    public StringBuilder getLinksDown (URI uri, String site) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        //Получаем страницу
        Document doc = Jsoup.connect(uri.toURL().toString()).get();
        //Получаем все ссылки с страницы
        Elements elements = doc.select("[href]");
        for (Element element : elements) {
            //Проверяем является ли базовый uri элемента таким же как у отпарсенной страницы, не равен ли абсолютный юрл абсолютному юрл самой страницы (т.к. если не проводить проверку
            //то программа закольцуется, почти на каждой странице присутствует обратный линк на главную страницу, и не равен ли юрл предыдущей ссылке, чтобы не уйти на страницу вверх
            if (element.baseUri().equals(doc.baseUri())) {
                if (!element.absUrl("href").equals(uri.toURL().toString()) && !site.equals(element.absUrl("href"))) {
                    if (n != 0) {
                        stringBuilder.append(String.format("%" + n + "S" + element.absUrl("href") + "\n", " "));
                    } else {
                        stringBuilder.append(element.absUrl("href") + "\n");
                    }
                    try {
                        URI newURI = new URI(element.absUrl("href"));
                        n++;
                        getLinksDown(newURI, site);
                    } catch (Exception e) {
//                    System.out.println("Не получилось создать ссылку");
                    } finally {
                        n--;
                    }
                }
            }

        }
        ready = true;
        return stringBuilder;
    }
}

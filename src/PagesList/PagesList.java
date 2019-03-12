package PagesList;

import java.net.URL;
import java.util.HashSet;

public class PagesList {
    private URL url;
    private HashSet<URL> pagesList;

    public PagesList (URL url) {
        this.url = url;
        pagesList = new HashSet<>();
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public HashSet<URL> getPagesList() {
        return pagesList;
    }

    public void setPagesList(HashSet<URL> pagesList) {
        this.pagesList = pagesList;
    }

    public void addPageToList(URL url) {
        pagesList.add(url);
    }
}

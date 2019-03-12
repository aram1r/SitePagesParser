package PagesList;

import java.net.URL;

public class Page {
    URL url;

    public Page (URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

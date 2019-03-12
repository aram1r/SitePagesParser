package MainFrame;

import java.io.IOException;
import java.net.URISyntaxException;

public class ParseThread extends Thread {
    String site;
    PagesParser pagesParser;
    boolean ready;
    String links;

    ParseThread(String string) throws IOException, URISyntaxException {
        ready = false;
        site = string;
        pagesParser = new PagesParser(site);
    }

    @Override
    public void run()  {
        try {
            links=pagesParser.getPagesURLs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady () {
        ready = pagesParser.isReady();
        return ready;
    }

    public String getLinks () {
        if (isReady()) {
            return pagesParser.getPagesURLs();
        } else {
            return null;
        }
    }
}

package ca.seneca.map524.smartsecretary.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ca.seneca.map524.smartsecretary.model.News;

/**
 * Created by Wonho on 11/6/2016.
 */
public class NewsXmlParser {

    private final static String TAG = "NewsXmlParser";

    /**
     * parse news XML
     * @param in
     * @return
     */
    public List<News> getNews(InputStreamReader in) {

        List<News> newsList = new ArrayList<News>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(in);

            int event = xpp.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {
                    // news item
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        // parse <item>, then add the item to list
                        newsList.add(parseItem(xpp));
                    }
                }

                event = xpp.next();

                // get only 4 news
                if (newsList.size() > 3) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }

    /**
     * parse <item> includes <title> and <description>
     * @param xpp
     * @return
     * @throws Exception
     */
    public News parseItem(XmlPullParser xpp) throws Exception {

        String headline = null;
        String imgSrc = null;
        String article = null;

        int event = xpp.next();
        while (event != XmlPullParser.END_TAG || !xpp.getName().equalsIgnoreCase("item")) {
            if (event == XmlPullParser.START_TAG) {
                if (xpp.getName().equalsIgnoreCase("title")) {
                    event = xpp.next();
                    if (event == XmlPullParser.TEXT) {
                        headline = xpp.getText();
                    }
                } else if (xpp.getName().equalsIgnoreCase("description")) {
                    event = xpp.next();
                    if (event == XmlPullParser.TEXT) {
                        String description = xpp.getText();

                        // <img src='http://i.cbc.ca/.../csis-info-sharing.jpg' alt='...' height='259' /> <p>...</p>
                        // get an image source url
                        String[] array = description.split("'");
                        imgSrc = array[1];

                        // get an article
                        array = description.split("<p>");
                        article = array[1].substring(0, array[1].indexOf("</p>"));
                    }
                }
            }

            event = xpp.next();
        }

        // set the item to News
        News news = new News();
        news.setHeadline(headline);
        news.setImgSrc(imgSrc);
        news.setArticle(article);

        return news;
    }
}

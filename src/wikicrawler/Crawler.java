/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;

/**
 *
 * @author rezvov
 */
public class Crawler {
    
    static private String contentId = "#mw-content-text";
    
    Document doc;
    String host;
    URL url;
    
    public Crawler(URL url) {
        this.url = url;
        try {
            this.host = url.getHost();
            Connection con = Jsoup.connect(url.toExternalForm());
            this.doc = con.get();
        }
        catch ( Exception e ) {
            this.doc = new Document("");
        }
    }
    
    public String getContent() {
        Elements el = this.doc.select(Crawler.contentId);
        return el.html();
    }
    
    public String getTitle() {
        return this.doc.select("#firstHeading span").text();
    }
    
    public ArrayList<Link> getLinks() {
        Elements els = this.doc.select(Crawler.contentId + " a");
        ArrayList<Link> ls = new ArrayList();
        for ( Element el : els ) {
            if ( !el.hasClass("internal") && !el.hasClass("new")
                    && !el.parent().hasClass("editsection") 
                    && !el.attr("title").isEmpty() ) {
                try {
                    URL u = new URL("http://" + this.host + el.attr("href"));
                    Link e = new Link(u, 
                            el.attr("title"), 
                            el.text(), 
                            el.parent().text());
                    ls.add(e);
                }
                catch (Exception e) {
                }
            }
        }
        return ls;
    }
}

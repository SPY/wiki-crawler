/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

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
    
    public class Edge {
        public String to;
        public String title;
        public String text;
        public String content;
        
        public Edge(String to, String title, String text, String content) {
            this.to = to;
            this.title = title;
            this.text = text;
            this.content = content;
        }
        
        public String toString() {
            return this.title + " (" + this.text + "): " + this.to + " in text: " + this.content;
        }
    }
    
    Document doc;
    
    public Crawler(String url) {
        try {
            Connection con = Jsoup.connect(url);
            this.doc = con.get();
        }
        catch ( Exception e ) {
            this.doc = new Document("");
        }
    }
    
    public String getContent() {
        Elements el = this.doc.select("#mw-content-text");
        return el.html();
    }
    
    public ArrayList<Edge> getLinks() {
        Elements els = this.doc.select("#mw-content-text a");
        ArrayList<Edge> ls = new ArrayList();
        Iterator<Element> it = els.iterator();
        while(it.hasNext()) {
            Element el = it.next();
            Edge e = new Edge(el.attr("href"), el.attr("title"), el.text(), el.parent().text());
            if ( !el.hasClass("internal") 
                    && !el.parent().hasClass("editsection") 
                    && !e.title.isEmpty() ) {
                ls.add(e);
            }
        }
        return ls;
    }
}

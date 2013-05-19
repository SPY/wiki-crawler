/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import java.util.*;
import java.net.URL;
/**
 *
 * @author rezvov
 */
public class Notion {
    
    public URL url;
    public String title;
    public HashMap<URL, ArrayList<Link>> links;
    public String content;
    
    private Notion(URL url, String title, ArrayList<Link> links) {
        this.url = url;
        this.title = title;
        this.fillLinks(links);
    }
    
    public static Notion parsePage(URL url) {
        Crawler c = new Crawler(url);
        Notion n = new Notion(url, c.getTitle(), c.getLinks());
        n.content = c.getContent();
        return n;
    }
    
    private void fillLinks(ArrayList<Link> links) {
        this.links = new HashMap();
        for( Link l : links ) {
            if ( this.links.containsKey(l.url)) {
                this.links.get(l.url).add(l);
            }
            else {
                ArrayList<Link> ls = new ArrayList();
                ls.add(l);
                this.links.put(l.url, ls);
            }
        }
    }
}

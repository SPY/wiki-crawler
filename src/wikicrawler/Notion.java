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
    public ArrayList<Link> links;
    public HashMap<URL, Integer> counts;
    
    public Notion(URL url, String title, ArrayList<Link> links) {
        this.url = url;
        this.title = title;
        this.links = links;
    }
    
    public static Notion parsePage(URL url) {
        Crawler c = new Crawler(url);
        Notion n = new Notion(url, c.getTitle(), c.getLinks());
        n.calcCounts();
        return n;
    }
    
    private void calcCounts() {
        this.counts = new HashMap();
        Iterator<Link> it = links.iterator();
        while(it.hasNext()) {
            URL u = it.next().url;
            if ( this.counts.containsKey(u) ) {
                Integer old = this.counts.get(u);
                this.counts.put(u, ++old);
            }
            else {
                this.counts.put(u, 1);
            }
        }
    }
}

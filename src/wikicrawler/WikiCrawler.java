/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import wikicrawler.Crawler;
import java.util.*;
/**
 *
 * @author rezvov
 */
public class WikiCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Crawler c = new Crawler("http://ru.wikipedia.org/wiki/BPMN");
        ArrayList<Crawler.Edge> links = c.getLinks();
        Iterator<Crawler.Edge> it = links.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

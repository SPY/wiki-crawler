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
public class WikiCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Notion n = Notion.parsePage(new URL("http://ru.wikipedia.org/wiki/BPMN"));
            System.out.println(n.title);
            for ( Link l : n.links ) {
                System.out.println(l.title);
            }
        } 
        catch( Exception e ) {
            System.out.println(e);
        }
    }
}

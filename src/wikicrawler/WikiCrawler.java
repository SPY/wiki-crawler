/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import java.util.*;
import java.net.*;
import java.io.IOException;
/**
 *
 * @author rezvov
 */
public class WikiCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if ( args.length <= 0 ) {
            System.out.println("Enter crawler enter-point");
        }
        try {
            download(new URL(args[0]));
        } 
        catch( MalformedURLException e ) {
            System.out.println("Invalid url");
        }
        catch( Exception e ) {
            System.out.println(e);
        }
    }
    
    public static void download(URL u) throws IOException {
        Notion n = Notion.parsePage(u);
        if ( n.title.isEmpty() ) {
            return;
        }
        NotionStore.store(n);
        for ( URL lu : n.links.keySet() ) {
            if ( !NotionStore.exist(lu) ) {
                download(lu);                    
            }
        }
    }
}

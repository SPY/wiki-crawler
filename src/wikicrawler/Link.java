/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import java.net.URL;

/**
 *
 * @author rezvov
 */
public class Link {
    public URL url;
    public String title;
    public String text;
    public String content;

    public Link(URL url, String title, String text, String content) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.content = content;
    }
}

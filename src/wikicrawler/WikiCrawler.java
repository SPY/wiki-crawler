/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import java.util.*;
import java.net.*;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.IdentityTableReducer;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.mapred.lib.IdentityReducer;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

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
            if (false) {
                NotionStore.init();
                //download(new URL(args[0]));
                download(new URL("http://ru.wikipedia.org/wiki/BPMN"));    
            } else {
                testMap();
            }
            
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
    
    private static void testMap() throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Job job = new Job(conf, "Test map jab");
        
        
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("links"));
        scan.addFamily(Bytes.toBytes("notion"));
        //scan.addColumn(Bytes.toBytes("no"), Bytes.toBytes("title"));
        
        TableMapReduceUtil.initTableMapperJob(
            "notions", scan, LinksMapper.class,
            ImmutableBytesWritable.class, Result.class, job
        );
        /*TableMapReduceUtil.initTableReducerJob("/test-links-output",
            IdentityTableReducer.class, job);*/
        //job.setOutputFormatClass(NullOutputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        //job.setReducerClass(MyReducer.class);
        job.setNumReduceTasks(0);
        
        FileOutputFormat.setOutputPath(job, new Path("/tmp/output"));
        job.setJarByClass(WikiCrawler.class);
        job.setOutputKeyClass(Text.class);
        //job.setWorkingDirectory(new Path("/"));
        
        job.waitForCompletion(true);
    }
}

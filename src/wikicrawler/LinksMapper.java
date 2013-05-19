/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

/**
 *
 * @author spy
 */

import java.net.URL;

import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;


public class LinksMapper 
    extends TableMapper<Text, LongWritable> {
   
    @Override
    protected void map(ImmutableBytesWritable rowkey,
        Result result,
        Context context)
    {
        byte[] title = result.getColumnLatest(Bytes.toBytes("notion"), Bytes.toBytes("title")).getValue();
        NavigableMap<byte[], byte[]> links = result.getFamilyMap(Bytes.toBytes("links"));
        if (title ==  null) {
            Text text = new Text("context-null-test");
            LongWritable long_writable = new LongWritable(0);
            try {
                context.write(text, long_writable);
            } catch ( Exception e) {}
            return;
        }
        String msg = Bytes.toString(title);
        //if (msg.isEmpty()) return;
        //System.out.println("Mapping value: '" + msg + "'");
        for (Map.Entry<byte[], byte[]> entry : links.entrySet()) {
            msg += ", url: '" + Bytes.toString(entry.getKey()) + "'";
            msg += ", number: " + Bytes.toInt(entry.getValue());
        }
        Text text = new Text(msg);
        LongWritable long_writable = new LongWritable(1);
        try {
            context.write(text, long_writable);
        } catch ( Exception e) {}
    }
    
}

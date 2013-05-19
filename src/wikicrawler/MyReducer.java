/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicrawler;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 *
 * @author spy
 */
public class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    
    public void reduce(Text key, Iterable<LongWritable> values,
            Context context) throws IOException, InterruptedException {
        long i = 0;
        for (LongWritable val : values)
            i += val.get();
        context.write(key, new LongWritable(i));
    }
}

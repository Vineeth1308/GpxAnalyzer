package com.gpx;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.json.simple.parser.ParseException;

public class GpxReducer extends Reducer<Text, LongWritable, Text, LongWritable>{

	@Override
	protected void reduce(Text arg0, Iterable<LongWritable> arg1,
			Reducer<Text, LongWritable, Text, LongWritable>.Context arg2) throws IOException, InterruptedException {
		
		String record = arg0.toString();
		String input[] = record.split(" ");
		String address="";
		try {
			address = ConvertToAddress.getAddress(input[1], input[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int c=0;
		@SuppressWarnings("rawtypes")
		Iterator iterator = arg1.iterator();
		while(iterator.hasNext()) {
			c += Integer.parseInt(iterator.next().toString());
		}
		arg2.write(new Text(address), new LongWritable(c));
	}

}

package com.gpx;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GpxMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		try {
			 
            InputStream is = new ByteArrayInputStream(value.toString().getBytes());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
 
            doc.getDocumentElement().normalize();
 
            NodeList nList = doc.getElementsByTagName("wpt");
 
            for (int temp = 0; temp < nList.getLength(); temp++) {
 
                Node nNode = nList.item(temp);
 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
                    Element eElement = (Element) nNode;
                    String lon = eElement.getAttribute("lon").toString();
                    String lat = eElement.getAttribute("lat").toString();
                    context.write(new Text(lon + " " + lat), new LongWritable(1));
 
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}


}

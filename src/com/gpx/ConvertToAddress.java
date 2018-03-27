package com.gpx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConvertToAddress {
	@SuppressWarnings({ "unused", "finally" })
	public static String getAddress(String lat, String lng)
            throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
         
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + lat + "," + lng + "&sensor=true&key=AIzaSyBE9K6p2AHBFVTsRDkTyc9HIk0Lbmo-YyE");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String formattedAddress = "";
 
        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            
            JSONParser parser = new JSONParser();
            JSONObject rsp = (JSONObject) parser.parse(result);
 
            if (rsp.containsKey("results")) {
            	System.out.println("2");
                JSONArray matches = (JSONArray) rsp.get("results");
                JSONObject data = (JSONObject) matches.get(0); 
                formattedAddress = (String) data.get("formatted_address");
                System.out.println("af"+formattedAddress);
            }
 
            return formattedAddress;
        } finally {
            urlConnection.disconnect();
            return formattedAddress;
        }
    }
}
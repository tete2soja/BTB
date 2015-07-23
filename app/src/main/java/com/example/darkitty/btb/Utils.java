package com.example.darkitty.btb;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nlegall on 12/06/2015.
 */
public class Utils {

    public static Map<String, Integer> images;

    static {
        Map<String, Integer> images2 = new HashMap<String, Integer>();
        images2.put("1", R.drawable.ligne_1);
        images2.put("2", R.drawable.ligne_2);
        images2.put("3", R.drawable.ligne_3);
        images2.put("4", R.drawable.ligne_4);
        images2.put("5", R.drawable.ligne_5);
        images2.put("6", R.drawable.ligne_6);
        images2.put("7", R.drawable.ligne_7);
        images2.put("8", R.drawable.ligne_8);
        images2.put("9", R.drawable.ligne_9);
        images2.put("10", R.drawable.ligne_10);
        images2.put("13", R.drawable.ligne_13);
        images2.put("14", R.drawable.ligne_14);
        images2.put("A", R.drawable.ligne_a);
        images2.put("AERO", R.drawable.ligne_aero);
        images2.put("ARS", R.drawable.ligne_base_navale);
        images2.put("44", R.drawable.ligne_44);
        images = Collections.unmodifiableMap(images2);
    }

    public static JSONArray getJSON(String url)
    {
        JSONArray jr = null;
        try
        {
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            // Setup the get request
            HttpGet httpGetRequest = new HttpGet(url);

            // Execute the request in the client
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            // Grab the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();

            // Instantiate a JSON object from the request response
            jr = new JSONArray(json);
        }
        catch (Exception e) {}
        return jr;
    }
}

package com.example.darkitty.btb;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by nlegall on 12/06/2015.
 */
public class Utils {
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

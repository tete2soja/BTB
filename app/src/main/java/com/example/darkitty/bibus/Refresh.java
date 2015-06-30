package com.example.darkitty.bibus;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by nlegall on 25/06/2015.
 */
public class Refresh {

    public void refreshLines() {
        try {
            // Create a new HTTP Client
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            // Setup the get request
            HttpGet httpGetRequest = new HttpGet("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getRoutes?format=json");

            // Execute the request in the client
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            // Grab the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            File sdcard = new File("/data/data/com.darkitty.bibus/");
            File file = new File(sdcard, "lines.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(reader.readLine());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e){
            e.getMessage();
        }
    }


}

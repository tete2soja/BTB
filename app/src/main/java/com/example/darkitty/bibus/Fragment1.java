package com.example.darkitty.bibus;

/**
 * Created by nlegall on 17/06/2015.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment1 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment1 newInstance(int sectionNumber) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        try {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            try{
                // Create a new HTTP Client
                DefaultHttpClient defaultClient = new DefaultHttpClient();
                // Setup the get request
                HttpGet httpGetRequest = new HttpGet("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getPerturbations?format=json");

                // Execute the request in the client
                HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
                // Grab the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();

                // Instantiate a JSON object from the request response
                JSONArray jr = new JSONArray(json);

                for(int i = 0; i < jr.length(); i++) {
                    JSONObject object = (JSONObject) jr.getJSONObject(i);
                    Map<String, String> dat = new HashMap<String, String>(2);
                    dat.put("date", object.getString("Description"));
                    dat.put("title", object.getString("Title"));
                    data.add(dat);
                }

            } catch(Exception e){
                // In your production code handle any errors and catch the individual exceptions
                e.printStackTrace();
            }

            ListView listView = (ListView) rootView.findViewById(R.id.listPerturbations);
            SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(), data, android.R.layout.simple_list_item_2, new String[] {"title", "date"}, new int[] {android.R.id.text1, android.R.id.text2});
            listView.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
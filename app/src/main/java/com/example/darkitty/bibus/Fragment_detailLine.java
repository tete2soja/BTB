package com.example.darkitty.bibus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
 * Created by nlegall on 26/06/2015.
 */
public class Fragment_detailLine  extends Fragment {
    static String idLine;
    static String destination;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment_detailLine newInstance(int sectionNumber) {
        Fragment_detailLine fragment = new Fragment_detailLine();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_detailLine() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_line, container, false);

        Intent in = getActivity().getIntent();
        // getting attached intent data
        String product = in.getStringExtra("LineNumber");
        this.idLine = product;
        // displaying selected product name
        getActivity().setTitle("Ligne n° " + this.idLine);

        try{
            // Create a new HTTP Client
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            // Setup the get request
            HttpGet httpGetRequest = new HttpGet("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getDestinations?format=json&route_id=" + product);

            // Execute the request in the client
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            // Grab the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();


            List<String> list = new ArrayList<String>();
            // Instantiate a JSON object from the request response
            JSONArray jr = new JSONArray(json);

            for(int i = 0; i < jr.length(); i++) {
                JSONObject object = (JSONObject) jr.getJSONObject(i);
                String ar = object.getString("Trip_headsign");
                list.add(object.getString("Trip_headsign"));
            }

            //

            Spinner terminus = (Spinner) rootView.findViewById(R.id.spinnerStopDetail);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
            terminus.setAdapter(dataAdapter);


            List<String> data = new ArrayList<String>();
                // Create a new HTTP Client
                DefaultHttpClient defaultClient2 = new DefaultHttpClient();
                // Setup the get request
                HttpGet httpGetRequest2 = new HttpGet("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStops_route?format=json&route_id="+product+"&trip_headsign="+terminus.getSelectedItem().toString());

                // Execute the request in the client
                HttpResponse httpResponse2 = defaultClient2.execute(httpGetRequest2);
                // Grab the response
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(httpResponse2.getEntity().getContent(), "UTF-8"));
                String json2 = reader2.readLine();

                // Instantiate a JSON object from the request response
                JSONArray jr2 = new JSONArray(json2);

                for(int i = 0; i < jr2.length(); i++) {
                    JSONObject object2 = (JSONObject) jr2.getJSONObject(i);
                    data.add(object2.getString("Stop_name"));
                }

                ListView listView2 = (ListView) rootView.findViewById(R.id.listViewStop);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                listView2.setAdapter(adapter);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Spinner terminus = (Spinner) getView().findViewById(R.id.spinnerStopDetail);
                        TextView tmp = ((TextView) view);
                        String product = tmp.getText().toString();
                        Intent i = new Intent(getActivity().getApplicationContext(), DetailStop.class);
                        Bundle extras = new Bundle();
                        extras.putString("lineNumber", Fragment_detailLine.idLine);
                        extras.putString("destination", terminus.getSelectedItem().toString());
                        extras.putString("departure", product);
                        i.putExtras(extras);
                        startActivity(i);
                    }
                });

        } catch(Exception e){
            // In your production code handle any errors and catch the individual exceptions
            e.printStackTrace();
        }

        return rootView;
    }
}

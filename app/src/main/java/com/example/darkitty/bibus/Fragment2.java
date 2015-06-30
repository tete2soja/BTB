package com.example.darkitty.bibus;

/**
 * Created by nlegall on 17/06/2015.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    HashMap<String, Integer> images = new HashMap<>();
    private SwipeRefreshLayout swipeLayout;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment2 newInstance(int sectionNumber) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment2() {
        images.put("1", R.drawable.ligne_1);
        images.put("2", R.drawable.ligne_2);
        images.put("3", R.drawable.ligne_3);
        images.put("4", R.drawable.ligne_4);
        images.put("5", R.drawable.ligne_5);
        images.put("6", R.drawable.ligne_6);
        images.put("7", R.drawable.ligne_7);
        images.put("8", R.drawable.ligne_8);
        images.put("9", R.drawable.ligne_9);
        images.put("10", R.drawable.ligne_10);
        images.put("13", R.drawable.ligne_13);
        images.put("14", R.drawable.ligne_14);
        images.put("A", R.drawable.ligne_a);
        images.put("AERO", R.drawable.ligne_aero);
        images.put("ARS", R.drawable.ligne_base_navale);
        images.put("44", R.drawable.ligne_44);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.container);
        swipeLayout.setOnRefreshListener(this);

        try {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            List<RowItem> rowItems = rowItems = new ArrayList<RowItem>();
            try{
                File sdcard = new File("/data/data/com.darkitty.bibus/");
                File file = new File(sdcard, "lines.json");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String json = reader.readLine();

                // Instantiate a JSON object from the request response
                JSONArray jr = new JSONArray(json);

                for(int i = 0; i < jr.length(); i++) {
                    JSONObject object = (JSONObject) jr.getJSONObject(i);
                    Map<String, String> dat = new HashMap<String, String>(2);
                    dat.put("date", object.getString("Route_id"));
                    dat.put("title", object.getString("Route_long_name"));
                    RowItem item = null;
                    if (images.get(object.getString("Route_id")) != null){
                        item = new RowItem(images.get(object.getString("Route_id")), object.getString("Route_id"), object.getString("Route_long_name"));
                    }
                    else {
                        item = new RowItem(0, object.getString("Route_id"), object.getString("Route_long_name"));
                    }
                    rowItems.add(item);
                    data.add(dat);
                }

            } catch(Exception e){
                // In your production code handle any errors and catch the individual exceptions
                e.printStackTrace();
            }

            ListView listView = (ListView) rootView.findViewById(R.id.listLines);
            CustomListViewAdapter adapter = new CustomListViewAdapter(rootView.getContext(), R.layout.list_item, rowItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tmp = ((TextView) view.findViewById(R.id.title));
                    String product = tmp.getText().toString();
                    Intent i = new Intent(getActivity().getApplicationContext(), DetailLine_tab.class);
                    i.putExtra("LineNumber", product);
                    startActivity(i);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }

    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }
}
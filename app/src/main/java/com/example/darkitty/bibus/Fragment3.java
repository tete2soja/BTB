package com.example.darkitty.bibus;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.darkitty.bibus.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment3 newInstance(int sectionNumber) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

        try {

            File sdcard = new File("/data/data/com.darkitty.bibus/");
            File file = new File(sdcard, "bookmarks.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> json = new ArrayList<String>();
            String line;

            File sdcard2 = new File("/data/data/com.darkitty.bibus/");
            File file2 = new File(sdcard, "lines.json");
            BufferedReader reader2 = new BufferedReader(new FileReader(file2));
            String json2 = reader2.readLine();

            // Instantiate a JSON object from the request response
            JSONArray jr = new JSONArray(json2);

            while((line = reader.readLine()) != null) {
                JSONObject object = (JSONObject) jr.getJSONObject(Integer.parseInt(line));
                json.add(object.getString("Route_long_name"));
            }
            ListView listView = (ListView) rootView.findViewById(R.id.listLinesB);
            ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,json);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

}

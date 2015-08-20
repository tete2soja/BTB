package fr.nlegall.btb;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_disturbances extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment_disturbances newInstance(int sectionNumber) {
        Fragment_disturbances fragment = new Fragment_disturbances();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_disturbances() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        try {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();;

            // Instantiate a JSON object from the request response
            JSONArray jr = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getPerturbations?format=json");

            for(int i = 0; i < jr.length(); i++) {
                JSONObject object = (JSONObject) jr.getJSONObject(i);
                Map<String, String> dat = new HashMap<String, String>(2);
                dat.put("date", object.getString("Description"));
                dat.put("title", object.getString("Title"));
                data.add(dat);
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
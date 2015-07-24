package com.example.darkitty.btb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_bookmark.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_bookmark#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_bookmark extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment_bookmark newInstance(int sectionNumber) {
        Fragment_bookmark fragment = new Fragment_bookmark();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_bookmark() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

        try {

            /*File sdcard = new File("/data/data/com.darkitty.bibus/");
            File file = new File(sdcard, "bookmarks.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));*/
            File file = new File(rootView.getContext().getFilesDir(), "bookmarks.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> json = new ArrayList<String>();
            String line;


            InputStream inputStream = rootView.getContext().getResources().openRawResource(R.raw.lines);
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream));
            String json2 = reader2.readLine();

            // Instantiate a JSON object from the request response
            JSONArray jr = new JSONArray(json2);
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            List<RowItem> rowItems = rowItems = new ArrayList<RowItem>();

            while((line = reader.readLine()) != null) {
                JSONObject object = (JSONObject) jr.getJSONObject(Integer.parseInt(line));
                Map<String, String> dat = new HashMap<String, String>(2);
                dat.put("date", object.getString("Route_id"));
                dat.put("title", object.getString("Route_long_name"));
                RowItem item = null;
                if (Utils.images.get(object.getString("Route_id")) != null){
                    item = new RowItem(Utils.images.get(object.getString("Route_id")), object.getString("Route_id"), object.getString("Route_long_name"));
                }
                else {
                    item = new RowItem(0, object.getString("Route_id"), object.getString("Route_long_name"));
                }
                rowItems.add(item);
                data.add(dat);
            }
            ListView listView = (ListView) rootView.findViewById(R.id.listLinesB);
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
            ListView listView = (ListView) rootView.findViewById(R.id.listLinesB);
            listView.setVisibility(View.INVISIBLE);
            TextView text = (TextView) rootView.findViewById(R.id.textB);
            text.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

        return rootView;
    }

}
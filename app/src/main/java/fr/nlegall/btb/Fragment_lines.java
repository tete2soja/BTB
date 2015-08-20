package fr.nlegall.btb;

/**
 * Created by nlegall on 17/06/2015.
 */


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_lines extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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
    public static Fragment_lines newInstance(int sectionNumber) {
        Fragment_lines fragment = new Fragment_lines();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_lines() {
        /* Nothing */
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

            InputStream inputStream = rootView.getContext().getResources().openRawResource(R.raw.lines);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String json = reader.readLine();

            // Instantiate a JSON object from the request response
            JSONArray jr = new JSONArray(json);

            for(int i = 0; i < jr.length(); i++) {
                JSONObject object = (JSONObject) jr.getJSONObject(i);
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
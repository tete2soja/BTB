package fr.nlegall.btb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkitty.btb.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nlegall on 26/06/2015.
 */
public class Fragment_detailLine  extends Fragment {
    static String idLine;
    static String destination;
    static ListView list;
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

        if(idLine.contains("A"))
            getActivity().setTitle("Ligne " + idLine);
        else
            getActivity().setTitle("Ligne n° " + idLine);

        ListView listView2 = (ListView) rootView.findViewById(R.id.listViewStop);
        this.list = listView2;

        try{
            List<String> list = new ArrayList<String>();
            // Instantiate a JSON object from the request response
            JSONArray jr = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getDestinations?format=json&route_id=" + product);

            for(int i = 0; i < jr.length(); i++) {
                JSONObject object = (JSONObject) jr.getJSONObject(i);
                String ar = object.getString("Trip_headsign");
                list.add(object.getString("Trip_headsign"));
            }
            if(product.equals("A")) {
                list.remove(0);
            }

            //

            Spinner terminus = (Spinner) rootView.findViewById(R.id.spinnerStopDetail);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
            terminus.setAdapter(dataAdapter);

            terminus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(), getResources().getString(R.string.destination) + " " +
                            parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                    JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStops_route?format=json&route_id="+idLine+"&trip_headsign="+parent.getItemAtPosition(position).toString().replace(" ", "%20"));
                    try {
                        List<String> data = new ArrayList<String>();
                        for(int i = 0; i < jr2.length(); i++) {
                            JSONObject object2 = (JSONObject) jr2.getJSONObject(i);
                            data.add(object2.getString("Stop_name"));
                        }
                        ListView listView2 = Fragment_detailLine.list;
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
                        listView2.setAdapter(adapter);
                    } catch(Exception e){
                        // In your production code handle any errors and catch the individual exceptions
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // Instantiate a JSON object from the request response
            JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStops_route?format=json&route_id="+product+"&trip_headsign="+terminus.getSelectedItem().toString().replace(" ", "%20"));

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

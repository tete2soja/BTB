package fr.nlegall.btb;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_search extends android.support.v4.app.Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment_search newInstance(int sectionNumber) {
        Fragment_search fragment = new Fragment_search();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_search() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        JSONArray stops = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStopsNames?format=");
        String[] stopName = new String[stops.length()];

        for (int i = 0; i < stops.length(); i++) {
            try {
                JSONObject currentStop = stops.getJSONObject(i);
                stopName[i] = currentStop.getString("Stop_name");
            } catch (Exception ex) {}
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item,stopName);
        AutoCompleteTextView actv= (AutoCompleteTextView)rootView.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

        return rootView;
    }

}

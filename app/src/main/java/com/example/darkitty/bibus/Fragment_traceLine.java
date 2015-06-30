package com.example.darkitty.bibus;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nlegall on 26/06/2015.
 */
public class Fragment_traceLine extends Fragment implements LocationListener {
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
    public static Fragment_traceLine newInstance(int sectionNumber) {
        Fragment_traceLine fragment = new Fragment_traceLine();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_traceLine() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trace_line, container, false);

        Intent in = getActivity().getIntent();
        String product = in.getStringExtra("LineNumber");

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();

        try {
            JSONArray tmp = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getDestinations?format=json&route_id=" + product);
            String terminus = tmp.getJSONObject(0).getString("Trip_headsign");

            JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStops_route?format=json&route_id="+product+"&trip_headsign="+terminus);

            for(int i = 1; i < jr2.length(); i++) {
                JSONObject object2 = (JSONObject) jr2.getJSONObject(i);
                waypoints.add(new GeoPoint(Double.valueOf(object2.getString("Stop_lat")), Double.valueOf(object2.getDouble("Stop_lon"))));
            }

            // Instantiate a JSON object from the request response
            JSONArray jr3 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getGeolocatedVehiclesPosition?format=json&route_id="+product+"&trip_headsign="+terminus);
            Resources res = getResources();
            for(int i = 1; i < jr3.length(); i++) {
                JSONObject object = (JSONObject) jr3.getJSONObject(i);
                OverlayItem overlayItem = new OverlayItem("0, 0", "0, 0", new GeoPoint(Double.valueOf(object.getString("Lat")), Double.valueOf(object.getDouble("Lon"))));
                overlayItem.setMarker(res.getDrawable(R.drawable.icone_bus));
                overlayItemArray.add(overlayItem);
            }
        } catch (Exception ex) {
            ex.getMessage();
        }

        MapView mMap = (MapView) rootView.findViewById(R.id.mapLine);
        IMapController mapController = mMap.getController();
        RoadManager roadManager = new OSRMRoadManager();

        Road road = roadManager.getRoad(waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road, rootView.getContext());
        mMap.getOverlays().add(roadOverlay);

        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getActivity(), overlayItemArray, null);
        mMap.getOverlays().add(anotherItemizedIconOverlay);

        mapController.setCenter(waypoints.get(1));
        mapController.setZoom(13);
        mMap.setMultiTouchControls(true);

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

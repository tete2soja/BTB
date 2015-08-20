package fr.nlegall.btb;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class Routing extends ActionBarActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        // getting attached intent data
        Bundle extras = in.getExtras();
        double lat = Double.parseDouble(extras.getString("lat"));
        double lon = Double.parseDouble(extras.getString("lon"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Criteria criteria = new Criteria();
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //GeoPoint gp = new GeoPoint(48.416312, -4.466546);
        GeoPoint gp = null;
        if(locationGPS != null) {
            gp = new GeoPoint(locationGPS.getLatitude(), locationGPS.getLongitude());
        }
        else if(locationNet != null){
            gp = new GeoPoint(locationNet.getLatitude(), locationNet.getLongitude());
        } else {
            return;
        }

        MapView mMap = (MapView) findViewById(R.id.mapRoute);
        IMapController mapController = mMap.getController();
        RoadManager roadManager = new OSRMRoadManager();

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(gp);
        GeoPoint endPoint = new GeoPoint(lat, lon);
        waypoints.add(endPoint);
        Road road = roadManager.getRoad(waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road, this);
        mMap.getOverlays().add(roadOverlay);
        //mMap.invalidate();

        /*NominatimPOIProvider poiProvider = new NominatimPOIProvider();
        ArrayList<POI> pois = poiProvider.getPOICloseTo(gp, "garage", 50, 0.1);
        FolderOverlay poiMarkers = new FolderOverlay(this);
        mMap.getOverlays().add(poiMarkers);*/

        mapController.setCenter(endPoint);
        mapController.setZoom(13);
        mMap.setMultiTouchControls(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_routing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

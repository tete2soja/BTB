package com.example.darkitty.bibus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DetailStop extends ActionBarActivity {
    private SwipeRefreshLayout swipeLayout;

    String idLigne;
    String stop;
    String destination;

    private MapView mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop);
        //swipeLayout = (SwipeRefreshLayout) findViewById(R.id.container2);
        //swipeLayout.setOnRefreshListener(this);

        Intent in = getIntent();
        // getting attached intent data
        Bundle extras = in.getExtras();
        idLigne = extras.getString("lineNumber").replace(" ", "%20");
        stop = extras.getString("departure").replace(" ", "%20");
        destination = extras.getString("destination".replace(" ", "%20"));

        try{

            JSONArray jr = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getStop?format=json&stop_name=" + this.stop);
            JSONObject object = (JSONObject) jr.getJSONObject(0);

            GeoPoint gp = new GeoPoint(Double.parseDouble(object.getString("Stop_lat")), Double.parseDouble(object.getString("Stop_lon")));
            mMap = (MapView) findViewById(R.id.map);
            mMap.setMultiTouchControls(true);
            mMap.setBuiltInZoomControls(true);
            IMapController mapController = mMap.getController();
            mapController.setZoom(20);
            mapController.setCenter(gp);
            Marker mrk = new Marker(mMap);
            mrk.setPosition(gp);
            mrk.setAlpha(0.75f);
            mrk.setSnippet(stop);
            mMap.getOverlays().add(mrk);

            TextView lat = (TextView) findViewById(R.id.lat);
            lat.setText(object.getString("Stop_lat"));
            TextView lon = (TextView) findViewById(R.id.lon);
            lon.setText(object.getString("Stop_lon"));


            List<String> data = new ArrayList<String>();

            // Instantiate a JSON object from the request response
            JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getRemainingTimes?format=json&route_id="+idLigne+"&trip_headsign="+destination+"&stop_name="+stop);

            TextView next = (TextView) findViewById(R.id.nextPassage);
            TextView next2 = (TextView) findViewById(R.id.reamingTime);

            JSONObject object2 = (JSONObject) jr2.getJSONObject(0);
            next.setText(object2.getString("Arrival_time"));
            next2.setText(object2.getString("Remaining_time"));

            Button btn = (Button) findViewById(R.id.btnSubmit);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView latView = (TextView) findViewById(R.id.lat);
                    TextView lonView = (TextView) findViewById(R.id.lon);
                    Intent i = new Intent(getApplicationContext(), Routing.class);
                    Bundle extras = new Bundle();
                    String lat = latView.getText().toString();
                    String lon = lonView.getText().toString();
                    extras.putString("lat", lat);
                    extras.putString("lon", lon);
                    i.putExtras(extras);
                    startActivity(i);
                }
            });

        } catch(Exception e){
            // In your production code handle any errors and catch the individual exceptions
            e.printStackTrace();
        }
        //this.onRefresh();
    }
}

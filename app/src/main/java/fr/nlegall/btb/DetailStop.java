package fr.nlegall.btb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;


public class DetailStop extends ActionBarActivity {
    private SwipeRefreshLayout swipeLayout;
    private static final int SETTINGS_RESULT = 1;

    public Handler mHandler;

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

        if(idLigne.contains("A"))
            setTitle("Ligne " + idLigne + " - " + this.stop);
        else
            setTitle("Ligne n° " + idLigne + " - " + this.stop);

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
            JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getRemainingTimes?format=json&route_id="+idLigne+"&trip_headsign="+destination.replace(" ", "%20")+"&stop_name="+stop.replace(" ", "%20"));

            TextView next = (TextView) findViewById(R.id.nextPassage);
            TextView next2 = (TextView) findViewById(R.id.reamingTime);

            JSONObject object2 = (JSONObject) jr2.getJSONObject(0);
            next.setText(object2.getString("Arrival_time"));
            next2.setText(object2.getString("Remaining_time"));

            JSONArray jr3 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getNextDepartures?format=json&route_id="+idLigne+"&trip_headsign="+destination.replace(" ", "%20")+"&stop_name="+stop.replace(" ", "%20"));

            String[] date = new String[jr3.length()-1];
            for(int i = 1; i < jr3.length(); i++) {
                date[i-1] = jr3.getJSONObject(i).getString("Arrival_time");
            }
            Spinner np = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, date);
            np.setAdapter(dataAdapter);


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

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,5000);
    }



    private final Runnable m_Runnable = new Runnable()
    {
        public void run()
        {
            try {
                JSONArray jr2 = Utils.getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getRemainingTimes?format=json&route_id="+idLigne+"&trip_headsign="+destination.replace(" ", "%20")+"&stop_name="+stop.replace(" ", "%20"));

                TextView next = (TextView) findViewById(R.id.nextPassage);
                TextView next2 = (TextView) findViewById(R.id.reamingTime);

                JSONObject object2 = (JSONObject) jr2.getJSONObject(0);
                next.setText(object2.getString("Arrival_time"));
                next2.setText(object2.getString("Remaining_time"));
            } catch (Exception ex) { ex.getMessage(); }

            mHandler.postDelayed(m_Runnable, 5000);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, SETTINGS_RESULT);
        }
        else if (id == R.id.action_refresh) {
            //f2.refreshLines();
        }

        return super.onOptionsItemSelected(item);
    }
}

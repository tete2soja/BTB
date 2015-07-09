package com.example.darkitty.bibus;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkitty.bibus.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bookmark extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        List<String> lines = new ArrayList<String>();
        try{
            File sdcard = new File("/data/data/com.darkitty.bibus/");
            File file = new File(sdcard, "lines.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String json = reader.readLine();

            // Instantiate a JSON object from the request response
            JSONArray jr = new JSONArray(json);

            for(int i = 0; i < jr.length(); i++) {
                JSONObject object = (JSONObject) jr.getJSONObject(i);
                lines.add("Line n° " + object.getString("Route_id"));
            }

        } catch(Exception e){
            // In your production code handle any errors and catch the individual exceptions
            e.printStackTrace();
        }

        ListView bookmarks = (ListView) findViewById(R.id.bookmarkList);

        // this-The current activity context.
        // Second param is the resource Id for list layout row item
        // Third param is input array
        BookmarkArrayadapter arrayAdapter = new BookmarkArrayadapter(this, lines);
        bookmarks.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        bookmarks.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, lines));
        /*bookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView lv = (ListView) arg0;
                TextView tv = (TextView) lv.getChildAt(arg2);
                String s = tv.getText().toString();
                Toast.makeText(Bookmark.this, "Clicked item is"+s, Toast.LENGTH_LONG).show();
            }} );*/

        Button save = (Button) findViewById(R.id.saveBokmarks);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lv = (ListView)findViewById(R.id.bookmarkList);
                SparseBooleanArray sp = lv.getCheckedItemPositions();
                StringBuffer str = new StringBuffer();
                for(int i=0;i<sp.size();i++){
                    if(sp.valueAt(i)==true){
                        String s = ((TextView) lv.getChildAt(i)).getText().toString();
                        str = str.append(" "+s);
                    }
                }
                Toast.makeText(Bookmark.this, "Selected items are "+str.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmark, menu);
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
}

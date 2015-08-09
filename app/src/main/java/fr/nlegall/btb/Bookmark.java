package fr.nlegall.btb;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.darkitty.btb.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Bookmark extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        List<String> lines = new ArrayList<String>();
        try{
            InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.lines);
            //File sdcard = new File("/data/data/com.darkitty.bibus/");
            //File file = new File(sdcard, "lines.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
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
                        //String s = ((TextView) lv.getChildAt(sp.keyAt(i))).getText().toString();
                        str = str.append(sp.keyAt(i)+"\n");
                }
                /*File sdcard = new File("/data/data/com.darkitty.bibus/");
                File file = new File(sdcard, "bookmarks.txt");*/
                File file = new File(getApplicationContext().getFilesDir(), "bookmarks.txt");
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(str.toString());
                    writer.close();
                    Toast.makeText(Bookmark.this, "Bookmarks saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Bookmark.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

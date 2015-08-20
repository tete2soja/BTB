package fr.nlegall.btb;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nlegall on 23/07/2015.
 */
public class Init extends Activity {
    public Init() {
        InputStream in = getResources().openRawResource(R.raw.lines);
        File file = new File(getApplicationContext().getFilesDir(), "lines.json");
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            FileOutputStream out = new FileOutputStream(file);
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

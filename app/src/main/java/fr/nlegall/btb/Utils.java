package fr.nlegall.btb;

import com.example.darkitty.btb.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nlegall on 12/06/2015.
 */
public class Utils {

    public static Map<String, Integer> images;

    static {
        Map<String, Integer> images2 = new HashMap<String, Integer>();
        images2.put("1", R.drawable.ligne_1);
        images2.put("2", R.drawable.ligne_2);
        images2.put("3", R.drawable.ligne_3);
        images2.put("4", R.drawable.ligne_4);
        images2.put("5", R.drawable.ligne_5);
        images2.put("6", R.drawable.ligne_6);
        images2.put("7", R.drawable.ligne_7);
        images2.put("8", R.drawable.ligne_8);
        images2.put("9", R.drawable.ligne_9);
        images2.put("10", R.drawable.ligne_10);
        images2.put("11", R.drawable.ligne_11);
        images2.put("12", R.drawable.ligne_12);
        images2.put("13", R.drawable.ligne_13);
        images2.put("14", R.drawable.ligne_14);
        images2.put("20", R.drawable.ligne_20);
        images2.put("21", R.drawable.ligne_21);
        images2.put("22", R.drawable.ligne_22);
        images2.put("23", R.drawable.ligne_23);
        images2.put("24", R.drawable.ligne_24);
        images2.put("25", R.drawable.ligne_25);
        images2.put("26", R.drawable.ligne_26);
        images2.put("27", R.drawable.ligne_27);
        images2.put("28", R.drawable.ligne_28);
        images2.put("50", R.drawable.ligne_50);
        images2.put("51", R.drawable.ligne_51);
        images2.put("52", R.drawable.ligne_52);
        images2.put("53", R.drawable.ligne_53);
        images2.put("54", R.drawable.ligne_54);
        images2.put("55", R.drawable.ligne_55);
        images2.put("56", R.drawable.ligne_56);
        images2.put("57", R.drawable.ligne_57);
        images2.put("58", R.drawable.ligne_58);
        images2.put("59", R.drawable.ligne_59);
        images2.put("60", R.drawable.ligne_60);
        images2.put("61", R.drawable.ligne_61);
        images2.put("40", R.drawable.ligne_40);
        images2.put("41", R.drawable.ligne_41);
        images2.put("42", R.drawable.ligne_42);
        images2.put("43", R.drawable.ligne_43);
        images2.put("44", R.drawable.ligne_44);
        images2.put("45", R.drawable.ligne_45);
        images2.put("46", R.drawable.ligne_46);
        images2.put("47", R.drawable.ligne_47);
        images2.put("98", R.drawable.ligne_98);
        images2.put("99", R.drawable.ligne_99);
        images2.put("100", R.drawable.ligne_100);
        images2.put("101", R.drawable.ligne_101);
        images2.put("510", R.drawable.ligne_510);
        images2.put("530", R.drawable.ligne_530);
        images2.put("610", R.drawable.ligne_610);

        images2.put("A", R.drawable.ligne_a);

        images2.put("AERO", R.drawable.ligne_aero);
        images2.put("ARS", R.drawable.ligne_ars);
        images2.put("NAV", R.drawable.ligne_nav);

        images = Collections.unmodifiableMap(images2);
    }

    public Utils() {
        try {
            getJSON("https://applications002.brest-metropole.fr/WIPOD01/Transport.svc/getDestinations?format=json&route_id=A");
        } catch (Exception ex) {}
    }

    public static JSONArray getJSON(String url)
    {
        JSONArray jr = null;
        try
        {
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            // Setup the get request
            HttpGet httpGetRequest = new HttpGet(url);

            // Execute the request in the client
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            // Grab the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();

            // Instantiate a JSON object from the request response
            jr = new JSONArray(json);
        }
        catch (Exception e) {
            e.getMessage();
        }
        return jr;
    }
}

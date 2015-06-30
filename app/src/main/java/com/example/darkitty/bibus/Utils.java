package com.example.darkitty.bibus;

import android.app.Activity;

/**
 * Created by nlegall on 12/06/2015.
 */
public class Utils {
    private static int cTheme = 0;
    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:

            case 0:

                activity.setTheme(R.style.AppTheme);
                cTheme = 1;
                break;

            case 1:

                activity.setTheme(R.style.AppThemeDark);
                cTheme = 0;
                break;

        }

    }

}

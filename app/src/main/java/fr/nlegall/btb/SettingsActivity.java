package fr.nlegall.btb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.example.darkitty.btb.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_general);

            Preference button = (Preference)getPreferenceManager().findPreference("bookmark");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        Intent i = new Intent(getApplicationContext(), Bookmark.class);
                        startActivity(i);
                        return true;
                    }
                });
            }

            Preference buttonAbout = (Preference)getPreferenceManager().findPreference("about");
            if (buttonAbout != null) {
                buttonAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        FragmentManager manager = getFragmentManager();
                        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
                        if (frag != null) {
                            manager.beginTransaction().remove(frag).commit();
                        }
                        AboutPopup alertDialogFragment = new AboutPopup();
                        alertDialogFragment.show(manager, "fragment_edit_name");
                        return true;
                    }
                });
            }
        }
    }

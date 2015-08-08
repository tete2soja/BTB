package fr.nlegall.btb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class AboutPopup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.stat_notify_error)
                        // set Dialog Title
                .setTitle("About")
                        // Set Dialog Message
            .setMessage("Author : Nicolas LE GALL\n\nSource code : https://github.com/Darkitty/btb\nLicence : MIT")

                        // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Pressed OK", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }
}
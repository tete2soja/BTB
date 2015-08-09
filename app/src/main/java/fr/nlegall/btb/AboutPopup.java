package fr.nlegall.btb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
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
                        .setMessage(Html.fromHtml("<u>Author :</u> Nicolas LE GALL<br/><a href=\"http://www.nlegall.fr\">http://www.nlegall.fr</a><br/><br/>" +
                                "<u>Source code :</u> https://github.com/Darkitty/btb<br/><br/>" +
                                "<u>Licence :</u> MIT"))

                        // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }
}
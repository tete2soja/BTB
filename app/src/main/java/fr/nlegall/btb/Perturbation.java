package fr.nlegall.btb;

/**
 * Created by nlegall on 16/06/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Perturbation extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.android_frag, container, false);
        ((TextView)android.findViewById(R.id.textView)).setText("Android");
        return android;
    }}

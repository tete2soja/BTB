package fr.nlegall.btb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nlegall on 09/07/2015.
 */
public class BookmarkArrayadapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> values;

    public BookmarkArrayadapter(Context context, List<String> resource) {
        super(context, R.layout.item_bookmark, resource);
        this.context = context;
        this.values = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_bookmark, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView5);
        CheckBox imageView = (CheckBox) rowView.findViewById(R.id.checkBox);
        textView.setText(values.get(position));

        // Change icon based on name
        String s = values.get(position);

        return rowView;
    }
}

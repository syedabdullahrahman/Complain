package com.example.nadia.complain;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ComplainList extends ArrayAdapter<ComplainBox> {
    private Activity context;
    private List<ComplainBox> complainBoxes;

    public ComplainList(Activity context, List<ComplainBox> complainBoxes) {
        super(context, R.layout.list_layout, complainBoxes);
        this.context = context;
        this.complainBoxes = complainBoxes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView compList = (TextView) listViewItem.findViewById(R.id.compList);
        TextView namet = (TextView) listViewItem.findViewById(R.id.namet);
        TextView phonet = (TextView) listViewItem.findViewById(R.id.phonet);

        ComplainBox comp = complainBoxes.get(position);

        compList.setText(comp.getCmpln());
        namet.setText(comp.getNameC());
        phonet.setText(comp.getPhoneC());

        return listViewItem;
    }
}

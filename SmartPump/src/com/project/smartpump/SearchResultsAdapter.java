package com.project.smartpump;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.project.classes.GasStation;
import com.project.classes.StationSearchResult;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<StationSearchResult> stationResults;

    public SearchResultsAdapter(ArrayList<StationSearchResult> results,
            Context context) {
        super();
        this.context = context;
        this.stationResults = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.search_result_item, null);
        }

        TextView name = (TextView) v.findViewById(R.id.stationName);
        // TextView address = (TextView) v.findViewById(R.id.stationAddress);
        TextView distance = (TextView) v.findViewById(R.id.distanceAway);
        TextView pumpPrice = (TextView) v.findViewById(R.id.pumpPrice);
        TextView adjustedCost = (TextView) v.findViewById(R.id.adjustedCost);

        StationSearchResult r = stationResults.get(position);
        GasStation s = r.getStation();
        
        name.setText(s.getStationName());
        // address.setText(s.getWebAddress());
        distance.setText(String.valueOf(s.getDistance()) + " mi");
        
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        double fuelPrice = s.getSelectedFuelPrice().getPrice();
        double adjustCost = r.getAdjustedCost();
        pumpPrice.setText(fuelPrice == 0.0 ? "Not Found" : currency.format(fuelPrice));
        adjustedCost.setText(adjustCost == 0.0 ? "Not Found" : currency.format(adjustCost));

        return v;
    }

    @Override
    public int getCount() {
        return stationResults.size();
    }

    @Override
    public Object getItem(int position) {
        return stationResults.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
}

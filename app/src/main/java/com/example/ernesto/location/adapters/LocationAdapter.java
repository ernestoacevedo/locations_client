package com.example.ernesto.location.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ernesto.location.R;
import com.example.ernesto.location.models.Location;

import java.util.ArrayList;

/**
 * Created by ernesto on 13-07-15.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<Location> locations;
    int itemLayout;

    public LocationAdapter(ArrayList<Location> locations,int itemLayout){
        Log.e("->", "LocationAdapter");
        this.itemLayout = itemLayout;
        this.locations = locations;
    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder viewHolder, int i) {
        Location location  = locations.get(i);
        Log.e("->", "setname");
        viewHolder.name.setText(location.getName());
        viewHolder.position.setText(location.getLat()+","+location.getLng());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView position;

        public ViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.location_name);
            position = (TextView) itemView.findViewById(R.id.location_position);
        }
    }
}

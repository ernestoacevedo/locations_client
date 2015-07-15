package com.example.ernesto.location.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ernesto.location.R;
import com.example.ernesto.location.models.LocationModel;

import java.util.ArrayList;

/**
 * Created by ernesto on 13-07-15.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<LocationModel> locations;
    int itemLayout;

    public LocationAdapter(ArrayList<LocationModel> locations,int itemLayout){
        Log.e("->", "LocationAdapter");
        this.itemLayout = itemLayout;
        this.locations = locations;
    }

    public void setLocations(ArrayList<LocationModel> locations){
        this.locations = locations;
        notifyDataSetChanged();
    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder viewHolder, int i) {
        LocationModel location  = locations.get(i);
        viewHolder.currentLocation = locations.get(i);
        viewHolder.name.setText(location.getName());
        String position = location.getLat()+","+location.getLng();
        viewHolder.position.setText(position);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView position;
        LocationModel currentLocation;

        public ViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.location_name);
            position = (TextView) itemView.findViewById(R.id.location_position);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.e("click", currentLocation.getLat());
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:0,0?q=" + currentLocation.getLat() + "," + currentLocation.getLng() + "&z=16 ("+currentLocation.getName()+")"));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

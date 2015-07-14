package com.example.ernesto.location.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ernesto.location.R;
import com.example.ernesto.location.adapters.LocationAdapter;
import com.example.ernesto.location.models.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationsFragment extends Fragment {

    ArrayList<Location> dataset = new ArrayList<Location>();
    LocationAdapter locationAdapter;

    public LocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = "http://52.25.110.191:3000/locations.json";

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Obteniendo datos");
        JsonArrayRequest req = new JsonArrayRequest(url,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.e("response: ",response.toString());
                dataset = parser(response);
                locationAdapter.setLocations(dataset);
                progressDialog.cancel();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("->",error.toString());
                progressDialog.cancel();
            }
        });

        queue.add(req);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        locationAdapter = new LocationAdapter(dataset,R.layout.row_location);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public ArrayList<Location> parser(JSONArray response){
        ArrayList<Location> locations_response = new ArrayList<Location>();
        for (int i = 0;i<response.length() ;i++){
            Location location = new Location();
            try {
                JSONObject jsonObject = (JSONObject)response.get(i);
                location.setName(jsonObject.getString("name"));
                location.setLat(jsonObject.getString("lat"));
                location.setLng(jsonObject.getString("lng"));
                location.setDescription(jsonObject.getString("description"));
                locations_response.add(location);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return locations_response;
    }

}

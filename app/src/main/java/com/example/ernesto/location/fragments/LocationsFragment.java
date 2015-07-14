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

    ArrayList<Location> dataset;// = new ArrayList<Location>();

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
                Log.e("->","uno");
                Log.e("response: ",response.toString());
                dataset = parser(response);
                progressDialog.cancel();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
            }
        });

        ArrayList<Location> locations_test = new ArrayList<Location>();
        Location location1 = new Location();
        location1.setName("Ubicación 1");
        location1.setLat("30");
        location1.setLng("70");
        locations_test.add(location1);
        Location location2 = new Location();
        location2.setName("Ubicación 2");
        location2.setLat("30");
        location2.setLng("70");
        locations_test.add(location2);
        Location location3 = new Location();
        location3.setName("Ubicación 3");
        location3.setLat("30");
        location3.setLng("70");
        locations_test.add(location3);

        queue.add(req);
        Log.e("->","dos");
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new LocationAdapter(locations_test, R.layout.row_location));
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

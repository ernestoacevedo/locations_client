package com.example.ernesto.location.fragments;


import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ernesto.location.R;
import com.example.ernesto.location.models.LocationModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment implements View.OnClickListener {

    LocationManager locationManager;
    EditText etNombre;
    EditText etDescripcion;


    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        Button btnEnviar = (Button) view.findViewById(R.id.btn_submit);
        btnEnviar.setOnClickListener(this);
        etNombre = (EditText)view.findViewById(R.id.form_name);
        etDescripcion = (EditText)view.findViewById(R.id.form_description);
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.e("->", "click");
        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        locationManager = (LocationManager) view.getContext().getSystemService(view.getContext().LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e("-> nombre:", nombre+"<-");
        Log.e("-> descripcion:", descripcion + "<-");
        Log.e("->", String.valueOf(location.getLatitude()));
        Log.e("->", String.valueOf(location.getLongitude()));
        LocationModel loc = new LocationModel();
        loc.setName(nombre);
        loc.setDescription(descripcion);
        loc.setLat(String.valueOf(location.getLatitude()));
        loc.setLng(String.valueOf(location.getLongitude()));
        postData(loc);
    }

    private void postData(final LocationModel locationModel) {

        final String URL = "http://52.25.110.191:3000/locations.json";
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Enviando datos");
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("name",locationModel.getName());
            data.put("description",locationModel.getDescription());
            data.put("lat",locationModel.getLat());
            data.put("lng",locationModel.getLng());
            jsonObj.put("location",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postReq = new JsonObjectRequest(URL,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.v("Response: ", response.toString());
                        progressDialog.cancel();
                        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT);
                        toast1.show();
                        etDescripcion.setText("");
                        etNombre.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.toString());
                        progressDialog.cancel();
                        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "Error al enviar datos", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
        });

        rq.add(postReq);

    }


}

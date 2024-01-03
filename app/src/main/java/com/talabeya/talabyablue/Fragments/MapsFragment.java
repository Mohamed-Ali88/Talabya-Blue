package com.talabeya.talabyablue.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.talabeya.talabyablue.Adapter.faceDataAdapter;
import com.talabeya.talabyablue.Domain.faceDataDomain;
import com.talabeya.talabyablue.Fragments.SwichFrgments.HomeViewModel;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private HomeViewModel homeViewModel;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    SharedPreferences userPref;
    private SupportMapFragment mapFragment;
    Location currentLocaiton;
    ArrayList<LatLng> locations = new ArrayList<>();
    private boolean isFirstTime = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        getIds();
        checkLocation();
        return view;
    }

    private void getIds() {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocaiton = location;
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(getView(), getString(R.string.permission_require), Snackbar.LENGTH_SHORT);
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.setOnMyLocationButtonClickListener(() -> {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Snackbar.make(getView(), getString(R.string.permission_require), Snackbar.LENGTH_SHORT);
                        return true;
                    }
                    fusedLocationProviderClient.getLastLocation()
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 20f));
                                }
                            });
                    return true;
                });
                setMultiplePin(mMap);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse c) {
                Toast.makeText(getContext(), "Permission " + c.getPermissionName() + " was denied!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();

        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.uber_maps_style));
            if (success) Log.e("EDMT_ERROR", "style parsing error");
        } catch (Resources.NotFoundException e) {
            Log.e("EDMT_ERROR", e.getMessage());

        }
        Snackbar.make(mapFragment.getView(), "you are online", Snackbar.LENGTH_LONG)
                .show();
        isFirstTime = false;


    }

    private void setMultiplePin(GoogleMap mMap) {
        int id = userPref.getInt("id", 0);
        StringRequest request = new StringRequest(Request.Method.GET,"______", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj1 = array.getJSONObject(i);
                    String location = jsonObj1.getString("----");
                    String[] parts = location.split("|");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    int value1 = jsonObj1.getInt(part1);
                    int value2 = jsonObj1.getInt(part2);
                    locations.add(new LatLng(value1,value2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

        LatLng pin1 = new LatLng(45, 63);
        LatLng pin2 = new LatLng(25, 36);
        LatLng pin3 = new LatLng(78, 22);
        LatLng pin4 = new LatLng(-13, 77);
        LatLng pin5 = new LatLng(-45, 44);
        LatLng pin6 = new LatLng(-15, 23);
        LatLng pin7 = new LatLng(-30, 89);
        LatLng pin8 = new LatLng(33, 56);
        locations.add(pin1);
        locations.add(pin2);
        locations.add(pin3);
        locations.add(pin4);
        locations.add(pin5);
        locations.add(pin6);
        locations.add(pin7);
        locations.add(pin8);
        for (int i = 0; i < locations.size(); i++) {
            if (i == 1 || i == 2) {
                mMap.addMarker(new MarkerOptions().position(locations.get(i)).title("blue").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_blue)));
            } else {
                mMap.addMarker(new MarkerOptions().position(locations.get(i)).title("red").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red)));
            }
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locations.get(i)));

        }
    }

    private void checkLocation() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setView(view);
            final AlertDialog test = dialog.create();
            Button open = (Button) view.findViewById(R.id.ok);
            open.setOnClickListener(new android.view.View.OnClickListener() {
                public void onClick(View v) {
                    getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    test.dismiss();
                    getLastLocation();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(MapsFragment.this).attach(MapsFragment.this).commit();
                }
            });
            test.setCancelable(false);
            test.show();

        } else {
            getLastLocation();
        }
    }


}
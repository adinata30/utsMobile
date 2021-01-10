package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class FindRestaurant extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private final AppCompatActivity CURRENT_ACTIVITY = this;
    private GoogleMap mMap;
    private ArrayList<Restaurant> restaurants;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean locationPermissionGranted ;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private TextView restaurant_name;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Button chooseRestaurantBtn ;
    public static LatLng coord;
    public static String rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        restaurant_name = findViewById(R.id.selected_restaurant_text);
        chooseRestaurantBtn = findViewById(R.id.go_to_restaurant_btn);
        chooseRestaurantBtn.setEnabled(false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        chooseRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rid = "";
                for(Restaurant rest: restaurants)
                {
                    if(rest.getName().equals(restaurant_name.getText().toString())){
                        rid = rest.getId();
                    }

                }

                Intent intent = new Intent(CURRENT_ACTIVITY,MainActivity.class);
                intent.putExtra("rid",rid);
                startActivity(intent);

            }
        });
    }

    private void getRestaurantList() {
        db.collection("restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot result = task.getResult();

                    restaurants = new ArrayList<>(result.toObjects(Restaurant.class));

                    for(Restaurant rest : restaurants)
                    {
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(rest.getLatitude(),rest.getLongitude())).title(rest.getName());

                        mMap.addMarker(marker);
                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        getRestaurantList();
        getDeviceLocation();
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera



    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            getLocationPermission();
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location lastKnownLocation = task.getResult();
                            coord = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

                            if (lastKnownLocation != null) {
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude())).title("Current Location");
                                mMap.addMarker(marker);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 15));
                            }
                        } else {
                            Log.d("FAILED", "Current location is null. Using defaults.");
                            Log.e("FAILED", "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!marker.getTitle().equals("Current Location"))
        {
            restaurant_name.setText(marker.getTitle());
            chooseRestaurantBtn.setEnabled(true);
        }


        return false;
    }
}
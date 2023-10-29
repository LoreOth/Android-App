package com.example.atencinciudadana;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.atencinciudadana.ui.ApiService;
import com.example.atencinciudadana.ui.LocationData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.atencinciudadana.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnTelefonos;
    private ImageButton btnBuscarDEA;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnTelefonos = findViewById(R.id.btnTelefonos);

        btnBuscarDEA = findViewById(R.id.btnBuscarDEA);

        btnBuscarDEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            fetchLocationsAndAddToMap(googleMap);
                        }
                    });
                }
            }
        });


        btnTelefonos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTelefonosEmergencia();
            }
        });

        // Inicializamos el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        // Activa la capa "Mi ubicación"
                        googleMap.setMyLocationEnabled(true);


                        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                            if (location != null) {
                                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
                    }
                }
            });
        }
    }

    private void mostrarTelefonosEmergencia() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {

                });
            }
        }
    }

    private void fetchLocationsAndAddToMap(final GoogleMap googleMap) {
        // Simulación de respuesta de la API
        List<LocationData> simulatedResponse = new ArrayList<>();

        // Punto 1: Obelisco de Buenos Aires
        LocationData location1 = new LocationData();
        location1.latitude = -34.603722;
        location1.longitude = -58.381592;
        simulatedResponse.add(location1);

        // Punto 2: Casa Rosada
        LocationData location2 = new LocationData();
        location2.latitude = -34.6083;
        location2.longitude = -58.3712;
        simulatedResponse.add(location2);

        // Añadimos los puntos al mapa
        for (LocationData locationData : simulatedResponse) {
            LatLng point = new LatLng(locationData.latitude, locationData.longitude);
            googleMap.addMarker(new MarkerOptions().position(point));
        }
    }


}

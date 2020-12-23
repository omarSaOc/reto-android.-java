package com.omar.retoandroid.ui.visit;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.omar.retoandroid.R;
import com.omar.retoandroid.Util.Utils;
import com.omar.retoandroid.data.VisitViewModel;
import com.omar.retoandroid.ui.home.HomeActivity;

public class VisitActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude, longitude;
    private String streetName, suburb, id;
    private boolean isVisited;
    private TextView tvStreetName, tvSuburb,
                    tvStreetNameVisited, tvSuburbVisited;
    private ImageView ivBack;
    private Button buttonNavigation, buttonVisit;
    private CardView cvAddress, cvAddressVisited;
    private VisitViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        setup();
        getViews();
        getEvents();
    }

    private void setup(){
        Utils.checkLocationPermission(this);
        viewModel = new ViewModelProvider(this).get(VisitViewModel.class);
        if (getIntent().getExtras() != null){
            latitude   = getIntent().getExtras().getDouble("LATITUDE", 0.0);
            longitude  = getIntent().getExtras().getDouble("LONGITUDE", 0.0);
            streetName = getIntent().getExtras().getString("STREET_NAME", "");
            suburb     = getIntent().getExtras().getString("SUBURB", "");
            isVisited  = getIntent().getExtras().getBoolean("VISITED", false);
            id         = getIntent().getExtras().getString("ID", "");
        }
    }

    private void getViews(){
        cvAddress = findViewById(R.id.cvAddress);
        cvAddressVisited = findViewById(R.id.cvAddressVisited);
        tvStreetName = findViewById(R.id.tvStreetName);
        tvSuburb = findViewById(R.id.tvSuburb);
        tvStreetNameVisited = findViewById(R.id.tvStreetNameVisited);
        tvSuburbVisited = findViewById(R.id.tvSuburbVisited);
        ivBack = findViewById(R.id.ivBack);
        buttonNavigation = findViewById(R.id.buttonNavigation);
        buttonVisit = findViewById(R.id.buttonVisit);

        if (isVisited){
            cvAddress.setVisibility(View.GONE);
            cvAddress.setEnabled(false);
            tvStreetNameVisited.setText(streetName);
            tvSuburbVisited.setText(suburb);
        }else{
            cvAddressVisited.setVisibility(View.GONE);
            cvAddressVisited.setEnabled(false);
            tvStreetName.setText(streetName);
            tvSuburb.setText(suburb);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getEvents(){
        ivBack.setOnClickListener(view -> {
            finish();
        });

        buttonVisit.setOnClickListener(view -> {
            visitPlace();
        });

        buttonNavigation.setOnClickListener(view -> {
            searchToGoogleMaps();
        });
    }

    private void visitPlace(){
        viewModel.updateVisit(id);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void searchToGoogleMaps(){
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor descriptor;
        if (isVisited){
            descriptor = Utils.bitmapDescriptorFromVector(this, R.drawable.ic_visited_marker);
        }else{
            descriptor = Utils.bitmapDescriptorFromVector(this, R.drawable.ic_marker);
        }
        mMap.addMarker(new MarkerOptions().position(latLng).icon(descriptor));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                .target(latLng)
                .zoom(18f)
                .build()
        ));
    }
}
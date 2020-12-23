package com.omar.retoandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.omar.retoandroid.R;
import com.omar.retoandroid.data.VisitViewModel;
import com.omar.retoandroid.data.room.Visit;
import com.omar.retoandroid.ui.visit.VisitActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.OnVisitListener {

    private RecyclerView rvVisits;
    private List<Visit> visits = new ArrayList<>();
    private HomeAdapter visitAdapter;
    private VisitViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        getViews();
        loadVisits();
    }

    private void setup(){
        viewModel = new ViewModelProvider(this).get(VisitViewModel.class);
    }

    private void getViews(){
        rvVisits = findViewById(R.id.rvVisits);
        rvVisits.setLayoutManager(new LinearLayoutManager(this));
        visitAdapter = new HomeAdapter(visits, this);
        rvVisits.setAdapter(visitAdapter);

    }

    private void loadVisits(){
        viewModel.getStoreVisits().observe(this, visits1 -> {
            visits = visits1;
            visitAdapter.setData(visits);
        });
    }

    @Override
    public void onVisitClick(int position)
    {
        Visit visit = visits.get(position);
        Intent intent = new Intent(this, VisitActivity.class);
        intent.putExtra("LATITUDE", visit.getLatitude());
        intent.putExtra("LONGITUDE", visit.getLongitude());
        intent.putExtra("STREET_NAME", visit.getStreetName());
        intent.putExtra("SUBURB", visit.getSuburb());
        intent.putExtra("VISITED", visit.isStatus());
        intent.putExtra("ID", visit.getId());
        startActivity(intent);
    }
}
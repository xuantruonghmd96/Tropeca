package com.example.android.tropeca.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.tropeca.ActivityUtils;
import com.example.android.tropeca.R;
import com.example.android.tropeca.adapter.PlacesAdapter;
import com.example.android.tropeca.data.PlaceRepo;
import com.example.android.tropeca.data.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceActivity extends AppCompatActivity {

    @BindView(R.id.lvPlacesAct)
    ListView lvPlaces;
    @BindView(R.id.txtPlacesAct_NoData)
    TextView txtNoData;

    private String categoryID;
    private PlaceRepo placeRepo;
    private List<Place> places = new ArrayList<>();
    private PlacesAdapter placesAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        placesAdapter = new PlacesAdapter(this, places);
        initProgressDialog();
        getPlaces();
        onPlaceClick();
    }

    private void getPlaces(){
        places = placeRepo.getPlaces(categoryID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                if (!places.isEmpty()){
                    txtNoData.setVisibility(View.GONE);
                }
                lvPlaces.setAdapter(placesAdapter);
                placesAdapter.updatePlaces(places);
            }
        }, 2000);
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(PlaceActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void onPlaceClick(){
        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = places.get(position);
                Intent detailActInternt = new Intent(PlaceActivity.this, DetailActivity.class);
                detailActInternt.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
                startActivity(detailActInternt);
            }
        });
    }

    @OnClick(R.id.fabPlacesAct_AddNewPlace)
    public void addNewPlace(View view){

    }

    @OnClick(R.id.btnPlacesAct_ShowAllOnMap)
    public void showAllOnMap(View view){

    }
}

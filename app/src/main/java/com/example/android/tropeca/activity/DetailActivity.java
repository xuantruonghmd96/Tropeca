package com.example.android.tropeca.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.tropeca.ActivityUtils;
import com.example.android.tropeca.R;
import com.example.android.tropeca.data.PlaceRepo;
import com.example.android.tropeca.data.model.Place;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgDetailAct_PlacePicture)
    ImageView imgPlacePicture;
    @BindView(R.id.edtAddEditAct_PlaceName)
    EditText edtPlaceName;
    @BindView(R.id.edtAddEditAct_PlaceAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtDetailAct_PlaceDescription)
    EditText edtPlaceDescription;

    private String placeID;
//    private String categoryID;
    private PlaceRepo placeRepo;
    private Place place;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
//        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        initProgressDialog();
        setPlace();
    }

    private void setPlace(){
        place = placeRepo.getPlace(placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                if (place.getPlaceImage() != null){
                    Bitmap placeBitmap = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0, place.getPlaceImage().length);
                    imgPlacePicture.setImageBitmap(placeBitmap);
                }
                edtPlaceName.setText(place.getPlaceName());
                edtPlaceAddress.setText(place.getPlaceAddress());
                edtPlaceDescription.setText(place.getPlaceDescription());
            }
        }, 2000);

    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @OnClick(R.id.iBtnDetailAct_Delete)
    public void deletePlace(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.warning_delete));
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setMessage(
                getResources().getString(R.string.warning_delete)
                + " '"
                + place.getPlaceName()
                + "' ?"
        );

        alertDialog.setPositiveButton(getResources().getString(R.string.text_positive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        placeRepo.delete(placeID);
                    }
                });
        alertDialog.setNegativeButton(getResources().getString(R.string.text_negative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.
                    }
                });
        alertDialog.show();
    }

    @OnClick(R.id.iBtnDetailAct_edit)
    public void editPlace(View view){
        Intent addEditActInternt = new Intent(DetailActivity.this, AddEditActivity.class);
        addEditActInternt.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
        addEditActInternt.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, place.getPlaceCategoryID());
        startActivity(addEditActInternt);
    }

    @OnClick(R.id.iBtnDetailAct_direct)
    public void directPlace(View view){

    }
}

package com.example.android.tropeca.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.tropeca.ActivityUtils;
import com.example.android.tropeca.R;
import com.example.android.tropeca.data.PlaceRepo;
import com.example.android.tropeca.data.model.Place;
import com.example.android.tropeca.map.ServiceAPI;
import com.example.android.tropeca.map.geocoding.GeocodingRoot;
import com.example.android.tropeca.map.geocoding.Location;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditActivity extends AppCompatActivity {

    @BindView(R.id.imgAddEditAct_PlacePicture)
    ImageView imgPlacePicture;
    @BindView(R.id.edtAddEditAct_PlaceName)
    EditText edtPlaceName;
    @BindView(R.id.edtAddEditAct_PlaceAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtAddEditAct_PlaceDescription)
    EditText edtPlaceDescription;

    private String placeID;
    private String categoryID;
    private PlaceRepo placeRepo;
    private Retrofit retrofit;
    private Location location;
    private boolean hasImage = false;
    private boolean allowSave = false;

    private ProgressDialog progressDialog;

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK){
            if (data == null) {
                // Them moi
                if (placeID == null) {
                    hasImage = false;
                    allowSave = false;
                } else {    // Cap nhat
                    hasImage = true;
                }
            }
//                Toast.makeText(getApplicationContext(), "Hãy nhấn vào hình ảnh phía trên để chụp hình hoặc chọn hình cho địa điểm này", Toast.LENGTH_SHORT).show();
            else{
                hasImage = true;
                allowSave = true;
                Bitmap placeImage = (Bitmap) data.getExtras().get("data");
                imgPlacePicture.setImageBitmap(placeImage);
            }
        }
    }

    private void init(){
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        initRetrofit();
        initProgressDialog();

        // Kiem tra edit
        if (placeID != null){
            hasImage = true;
            setPlace(placeID, categoryID);
        }
    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ActivityUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(AddEditActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_save));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setPlace(String placeID, String categoryID){
        Place place = placeRepo.getPlace(placeID);

        Bitmap placeImage = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0, place.getPlaceImage().length);
        imgPlacePicture.setImageBitmap(placeImage);
        edtPlaceName.setText(place.getPlaceName());
        edtPlaceAddress.setText(place.getPlaceAddress());
        edtPlaceDescription.setText(place.getPlaceDescription());

    }

    private void getLatLng(String address){
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<GeocodingRoot> call = serviceAPI.getLocation(address);
        call.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {
                GeocodingRoot geocodingRoot = response.body();
                double lat = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLat();
                double lng = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLng();

                location = new Location(lat, lng);
            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {

            }
        });
    }

    private byte[] converImageViewToByteArray(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private void redirectPlacesAct(){
        Intent placeActIntent = new Intent(AddEditActivity.this, PlaceActivity.class);
        placeActIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(placeActIntent);
        finish();
    }

    private void redirectDetailAct(){
        Intent detailActIntent = new Intent(AddEditActivity.this, DetailActivity.class);
        detailActIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, placeID);
        startActivity(detailActIntent);
        finish();
    }

    @OnClick(R.id.btnAddEditAct_Save)
    public void savePlace(View v){
        final String placeName = edtPlaceName.getText().toString();
        final String placeAddress = edtPlaceAddress.getText().toString();
        final String placeDescription = edtPlaceDescription.getText().toString();

        if (Place.validateInput(placeName, placeAddress, placeDescription, categoryID)){
            allowSave = true;
            getLatLng(placeName + ", " + placeAddress);
        } else{
//            Toast.
        }

        if (allowSave) {
            // Them moi
            progressDialog.show();
            if (hasImage && placeID == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Place place = new Place.Builder()
                                .setPlaceID(UUID.randomUUID().toString())
                                .setPlaceCategoryID(categoryID)
                                .setPlaceName(placeName)
                                .setPlaceAddress(placeAddress)
                                .setPlaceDescription(placeDescription)
                                .setPlaceImage(converImageViewToByteArray(imgPlacePicture))
                                .setPlaceLat(location.getLat())
                                .setPlaceLng(location.getLng())
                                .build();
                        placeRepo.insert(place);
                        progressDialog.dismiss();
                        redirectPlacesAct();
                    }
                }, 2000);
            }
            // Cap nhat
            if (placeID != null){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Place place = new Place.Builder()
                                .setPlaceID(placeID)
                                .setPlaceCategoryID(categoryID)
                                .setPlaceName(placeName)
                                .setPlaceAddress(placeAddress)
                                .setPlaceDescription(placeDescription)
                                .setPlaceImage(converImageViewToByteArray(imgPlacePicture))
                                .setPlaceLat(location.getLat())
                                .setPlaceLng(location.getLng())
                                .build();
                        placeRepo.update(place);
                        progressDialog.dismiss();
                        redirectDetailAct();
                    }
                }, 2000);
            }
        }
    }

    @OnClick(R.id.imgAddEditAct_PlacePicture)
    public void OpenCamera(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }
}

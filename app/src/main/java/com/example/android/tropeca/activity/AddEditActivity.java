package com.example.android.tropeca.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.tropeca.R;

import butterknife.BindView;

public class AddEditActivity extends AppCompatActivity {

    @BindView(R.id.imgAddEditAct_PlacePicture)
    ImageView imgPlacePicture;
    @BindView(R.id.edtAddEditAct_PlaceName)
    EditText edtPlaceName;
    @BindView(R.id.edtAddEditAct_PlaceAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtAddEditAct_PlaceDescription)
    EditText edtPlaceDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
    }
}

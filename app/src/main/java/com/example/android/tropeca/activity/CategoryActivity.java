package com.example.android.tropeca.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tropeca.ActivityUtils;
import com.example.android.tropeca.R;
import com.example.android.tropeca.data.PlaceRepo;
import com.example.android.tropeca.data.model.Category;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.txtCategoryAct_coffee)
    TextView txtCoffee;
    @BindView(R.id.txtCategoryAct_petPark)
    TextView txtPetPark;
    @BindView(R.id.txtCategoryAct_petShop)
    TextView txtPetShop;
    @BindView(R.id.txtCategoryAct_petYard)
    TextView txtPetYard;

    private PlaceRepo placeRepo;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        placeRepo = PlaceRepo.getInstance(this);
        categories = placeRepo.getCategories();
    }

    private void startPlaceAct(String catID) {
        Intent placeActIntent = new Intent(CategoryActivity.this, PlaceActivity.class);
        placeActIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, catID);
        startActivity(placeActIntent);
    }

    @OnClick(R.id.lytCategoryAct_coffee)
    public void clickOnCoffee(View v) {
        String catID = categories.get(0).getCategoryID();
        startPlaceAct(catID);
    }

    @OnClick(R.id.lytCategoryAct_petPark)
    public void clickOnPetPark(View v) {
        String catID = categories.get(1).getCategoryID();
        startPlaceAct(catID);
    }

    @OnClick(R.id.lytCategoryAct_petShop)
    public void clickOnPetShop(View v) {
        String catID = categories.get(2).getCategoryID();
        startPlaceAct(catID);
    }

    @OnClick(R.id.lytCategoryAct_petYard)
    public void clickOnPetYard(View v) {
        String catID = categories.get(3).getCategoryID();
        startPlaceAct(catID);
    }
}

package com.example.android.tropeca;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @BindView(R.id.layoutSplashAct)
    RelativeLayout layout;
    @BindView(R.id.imgSplashAct_Logo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        Animation transitionAnim = AnimationUtils.loadAnimation(this, R.anim.transition_icon);
        imgLogo.setAnimation(transitionAnim)    ;
        Animation alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_backkground);
        layout.setAnimation(alphaAnim);

        alphaAnim.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

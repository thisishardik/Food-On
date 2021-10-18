package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {

    Button connectAsChef;
    Button connectAsCust;
    Button connectAsDelivery;
    Intent intent;
    String type;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        constraintLayout = findViewById(R.id.constraintLayout);
        connectAsChef = findViewById(R.id.connectAsChef);
        connectAsCust = findViewById(R.id.connectAsCust);
        connectAsDelivery = findViewById(R.id.connectAsDelivery);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        constraintLayout.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        Intent intent = getIntent();
        type = intent.getStringExtra("Home");

        connectAsChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    startActivity(new Intent(ChooseOne.this, ChefLoginActivity.class));
                    finish();
                }
                if(type.equals("Phone")){
                    startActivity(new Intent(ChooseOne.this, ChefPhoneLoginActivity.class));
                    finish();
                }
                if(type.equals("SignUp")){
                    startActivity(new Intent(ChooseOne.this, ChefSignUpActivity.class));
                }
            }
        });

        connectAsCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    startActivity(new Intent(ChooseOne.this, CustLoginActivity.class));
                    finish();
                }
                if(type.equals("Phone")){
                    startActivity(new Intent(ChooseOne.this, CustPhoneLoginActivity.class));
                    finish();
                }
                if(type.equals("SignUp")){
                    startActivity(new Intent(ChooseOne.this, CustSignUpActivity.class));
                }
            }
        });

        connectAsDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    startActivity(new Intent(ChooseOne.this, DeliveryLoginActivity.class));
                    finish();
                }
                if(type.equals("Phone")){
                    startActivity(new Intent(ChooseOne.this, DeliveryPhoneLoginActivity.class));
                    finish();
                }
                if(type.equals("SignUp")){
                    startActivity(new Intent(ChooseOne.this, DeliverySignUpActivity.class));
                }
            }
        });

    }
}
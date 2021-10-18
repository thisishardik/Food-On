package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    Button signInWithEmail;
    Button signInWithPhone;
    Button signUp;
    ImageView bgImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bgImage = findViewById(R.id.bgImage);
        signInWithEmail = findViewById(R.id.signInWithEmail);
        signInWithPhone = findViewById(R.id.signInWithPhone);
        signUp  = findViewById(R.id.signUp);

        final Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        bgImage.setAnimation(zoomIn);
        bgImage.setAnimation(zoomOut);

        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgImage.startAnimation(zoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        signInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signEmailIntent = new Intent(MainMenu.this, ChooseOne.class);
                signEmailIntent.putExtra("Home", "Email");
                startActivity(signEmailIntent);
                finish();
            }
        });
        signInWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signPhoneIntent = new Intent(MainMenu.this, ChooseOne.class);
                signPhoneIntent.putExtra("Home", "Phone");
                startActivity(signPhoneIntent);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(MainMenu.this, ChooseOne.class);
                signUpIntent.putExtra("Home", "SignUp");
                startActivity(signUpIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
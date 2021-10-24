package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class ChefPhoneLoginActivity extends AppCompatActivity {
    EditText num;
    Button sendOtp, signInEmail;
    TextView signUpTextViewChefPhoneLogin;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_phone_login);
        num = (EditText) findViewById(R.id.phoneChefPhoneLogin);
        sendOtp = (Button) findViewById(R.id.otpChefPhoneLogin);
        cpp = (CountryCodePicker) findViewById(R.id.countryCodePickerChefPhoneLogin);
        signInEmail = (Button) findViewById(R.id.loginButtonChefPhoneLogin);
        signUpTextViewChefPhoneLogin = (TextView) findViewById(R.id.signUpTextViewChefPhoneLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = num.getText().toString().trim();
                String phoneNum = cpp.getSelectedCountryCodeWithPlus() + number;
                Intent b = new Intent(ChefPhoneLoginActivity.this, ChefSendOtp.class);
                b.putExtra("phoneNum", phoneNum);
                startActivity(b);
                finish();
            }
        });

        signUpTextViewChefPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefPhoneLoginActivity.this, ChefSignUpActivity.class));
                finish();
            }
        });

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefPhoneLoginActivity.this, ChefLoginActivity.class));
                finish();
            }
        });
    }
}
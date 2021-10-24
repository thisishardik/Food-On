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

public class DeliveryPhoneLoginActivity extends AppCompatActivity {
    EditText num;
    Button sendOtp, signInEmail;
    TextView signUpTextViewDeliveryPhoneLogin;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_phone_login);
        num = (EditText) findViewById(R.id.phoneDeliveryPhoneLogin);
        sendOtp = (Button) findViewById(R.id.otpDeliveryPhoneLogin);
        cpp = (CountryCodePicker) findViewById(R.id.countryCodePickerDeliveryPhoneLogin);
        signInEmail = (Button) findViewById(R.id.loginButtonDeliveryPhoneLogin);
        signUpTextViewDeliveryPhoneLogin = (TextView) findViewById(R.id.signUpTextViewDeliveryPhoneLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = num.getText().toString().trim();
                String phoneNum = cpp.getSelectedCountryCodeWithPlus() + number;
                Intent b = new Intent(DeliveryPhoneLoginActivity.this, DeliverySendOtp.class);
                b.putExtra("phoneNum", phoneNum);
                startActivity(b);
                finish();
            }
        });

        signUpTextViewDeliveryPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryPhoneLoginActivity.this, DeliverySignUpActivity.class));
                finish();
            }
        });

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryPhoneLoginActivity.this, DeliveryLoginActivity.class));
                finish();
            }
        });
    }
}
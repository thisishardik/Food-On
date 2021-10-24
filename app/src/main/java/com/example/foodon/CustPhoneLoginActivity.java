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

public class CustPhoneLoginActivity extends AppCompatActivity {
    EditText num;
    Button sendOtp, signInEmail;
    TextView signUpTextViewCustPhoneLogin;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_phone_login);
        num = (EditText) findViewById(R.id.phoneCustPhoneLogin);
        sendOtp = (Button) findViewById(R.id.otpCustPhoneLogin);
        cpp = (CountryCodePicker) findViewById(R.id.countryCodePickerCustPhoneLogin);
        signInEmail = (Button) findViewById(R.id.loginButtonCustPhoneLogin);
        signUpTextViewCustPhoneLogin = (TextView) findViewById(R.id.signUpTextViewCustPhoneLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = num.getText().toString().trim();
                String phoneNum = cpp.getSelectedCountryCodeWithPlus() + number;
                Intent b = new Intent(CustPhoneLoginActivity.this, CustSendOtpActivity.class);
                b.putExtra("phoneNum", phoneNum);
                startActivity(b);
                finish();
            }
        });

        signUpTextViewCustPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustPhoneLoginActivity.this, CustSignUpActivity.class));
                finish();
            }
        });

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustPhoneLoginActivity.this, CustLoginActivity.class));
                finish();
            }
        });
    }
}
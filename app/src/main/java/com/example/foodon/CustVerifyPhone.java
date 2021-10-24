package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CustVerifyPhone extends AppCompatActivity {

    String verificationId;
    FirebaseAuth firebaseAuth;
    Button verifyOTPCustLogin, resendOTPCustLogin;
    TextView txt;
    OtpEditText et_otp;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_verify_phone);

        phoneNumber = getIntent().getStringExtra("phoneNumber").trim();

        et_otp = findViewById(R.id.et_otp_Cust);
        txt = findViewById(R.id.txt);
        resendOTPCustLogin = findViewById(R.id.resendOTPCustLogin);
        verifyOTPCustLogin = findViewById(R.id.verifyOTPCustLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        resendOTPCustLogin.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verifyOTPCustLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_otp.getText().toString().trim();
                resendOTPCustLogin.setVisibility(View.INVISIBLE);
                if (code.isEmpty() && code.length() < 6) {
                    et_otp.setError("Enter code");
                    et_otp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                resendOTPCustLogin.setVisibility(View.VISIBLE);
                txt.setVisibility(View.INVISIBLE);
            }
        }.start();

        resendOTPCustLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTPCustLogin.setVisibility(View.INVISIBLE);
                resendOTP(phoneNumber);

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code in " + millisUntilFinished / 1000 + " seconds");
                    }

                    @Override
                    public void onFinish() {
                        resendOTPCustLogin.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                30,
                TimeUnit.SECONDS,
                this,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                et_otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(CustVerifyPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(CustVerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(CustVerifyPhone.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ReusableCodeForAll.showAlert(CustVerifyPhone.this, "Error", task.getException().getMessage());
                        }
                    }
                });
    }

    private void resendOTP(String phoneNum) {
        sendVerificationCode(phoneNum);
    }
}
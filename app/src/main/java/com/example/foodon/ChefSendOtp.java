package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChefSendOtp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth firebaseAuth;
    Button verifyOTPSendChef, resendOtpSendChef;
    TextView sendOtpChefTextView;
    EditText otpSendChef;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_send_otp);

        phoneNumber = getIntent().getStringExtra("phoneNum").trim();
        otpSendChef = (EditText) findViewById(R.id.otpSendChef);
        sendOtpChefTextView = (TextView) findViewById(R.id.sendOtpChefTextView);
        resendOtpSendChef = (Button) findViewById(R.id.resendOtpSendChef);
        verifyOTPSendChef = (Button) findViewById(R.id.verifyOTPSendChef);
        firebaseAuth = FirebaseAuth.getInstance();

        resendOtpSendChef.setVisibility(View.INVISIBLE);
        sendOtpChefTextView.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verifyOTPSendChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpSendChef.getText().toString().trim();
                resendOtpSendChef.setVisibility(View.INVISIBLE);
                if (code.isEmpty() && code.length() < 6) {
                    otpSendChef.setError("Enter code");
                    otpSendChef.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendOtpChefTextView.setVisibility(View.VISIBLE);
                sendOtpChefTextView.setText("Resend Code in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                resendOtpSendChef.setVisibility(View.VISIBLE);
                sendOtpChefTextView.setVisibility(View.INVISIBLE);
            }
        }.start();

        resendOtpSendChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpSendChef.setVisibility(View.INVISIBLE);
                resendOTP(phoneNumber);

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sendOtpChefTextView.setVisibility(View.VISIBLE);
                        sendOtpChefTextView.setText("Resend Code in" + millisUntilFinished / 1000 + "Seconds");
                    }

                    @Override
                    public void onFinish() {
                        resendOtpSendChef.setVisibility(View.VISIBLE);
                        sendOtpChefTextView.setVisibility(View.INVISIBLE);

                    }
                }.start();
            }
        });
    }

    private void resendOTP(String phonenum) {
        sendVerificationCode(phonenum);
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mcallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpSendChef.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ChefSendOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhone(credential);
    }

    private void signInWithPhone(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(ChefSendOtp.this, ChefFoodPanelActivity.class));
                            finish();

                        } else {
                            ReusableCodeForAll.showAlert(ChefSendOtp.this, "Error", task.getException().getMessage());
                        }

                    }
                });
    }
}

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

public class CustSendOtpActivity extends AppCompatActivity {

    String verificationId;
    FirebaseAuth firebaseAuth;
    Button verifyOTPSendCust, resendOtpSendCust;
    TextView sendOtpCustTextView;
    EditText otpSendCust;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_send_otp);

        phoneNumber = getIntent().getStringExtra("phoneNum").trim();
        otpSendCust = (EditText) findViewById(R.id.otpSendCust);
        sendOtpCustTextView = (TextView) findViewById(R.id.sendOtpCustTextView);
        resendOtpSendCust = (Button) findViewById(R.id.resendOtpSendCust);
        verifyOTPSendCust = (Button) findViewById(R.id.verifyOTPSendCust);
        firebaseAuth = FirebaseAuth.getInstance();

        resendOtpSendCust.setVisibility(View.INVISIBLE);
        sendOtpCustTextView.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verifyOTPSendCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpSendCust.getText().toString().trim();
                resendOtpSendCust.setVisibility(View.INVISIBLE);
                if (code.isEmpty() && code.length() < 6) {
                    otpSendCust.setError("Enter code");
                    otpSendCust.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendOtpCustTextView.setVisibility(View.VISIBLE);
                sendOtpCustTextView.setText("Resend Code in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                resendOtpSendCust.setVisibility(View.VISIBLE);
                sendOtpCustTextView.setVisibility(View.INVISIBLE);
            }
        }.start();

        resendOtpSendCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpSendCust.setVisibility(View.INVISIBLE);
                resendOTP(phoneNumber);

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sendOtpCustTextView.setVisibility(View.VISIBLE);
                        sendOtpCustTextView.setText("Resend Code in" + millisUntilFinished / 1000 + "Seconds");
                    }

                    @Override
                    public void onFinish() {
                        resendOtpSendCust.setVisibility(View.VISIBLE);
                        sendOtpCustTextView.setVisibility(View.INVISIBLE);
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
                otpSendCust.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(CustSendOtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                            startActivity(new Intent(CustSendOtpActivity.this, CustFoodPanelActivity.class));
                            finish();

                        } else {
                            ReusableCodeForAll.showAlert(CustSendOtpActivity.this, "Error", task.getException().getMessage());
                        }

                    }
                });
    }
}

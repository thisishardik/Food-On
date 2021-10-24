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

public class DeliverySendOtp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth firebaseAuth;
    Button verifyOTPSendDelivery, resendOtpSendDelivery;
    TextView sendOtpDeliveryTextView;
    EditText otpSendDelivery;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_send_otp);

        phoneNumber = getIntent().getStringExtra("phoneNum").trim();
        otpSendDelivery = (EditText) findViewById(R.id.otpSendDelivery);
        sendOtpDeliveryTextView = (TextView) findViewById(R.id.sendOtpDeliveryTextView);
        resendOtpSendDelivery = (Button) findViewById(R.id.resendOtpSendDelivery);
        verifyOTPSendDelivery = (Button) findViewById(R.id.verifyOTPSendDelivery);
        firebaseAuth = FirebaseAuth.getInstance();

        resendOtpSendDelivery.setVisibility(View.INVISIBLE);
        sendOtpDeliveryTextView.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verifyOTPSendDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpSendDelivery.getText().toString().trim();
                resendOtpSendDelivery.setVisibility(View.INVISIBLE);
                if (code.isEmpty() && code.length() < 6) {
                    otpSendDelivery.setError("Enter code");
                    otpSendDelivery.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendOtpDeliveryTextView.setVisibility(View.VISIBLE);
                sendOtpDeliveryTextView.setText("Resend Code in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                resendOtpSendDelivery.setVisibility(View.VISIBLE);
                sendOtpDeliveryTextView.setVisibility(View.INVISIBLE);
            }
        }.start();

        resendOtpSendDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpSendDelivery.setVisibility(View.INVISIBLE);
                resendOTP(phoneNumber);

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sendOtpDeliveryTextView.setVisibility(View.VISIBLE);
                        sendOtpDeliveryTextView.setText("Resend Code in" + millisUntilFinished / 1000 + "Seconds");
                    }

                    @Override
                    public void onFinish() {
                        resendOtpSendDelivery.setVisibility(View.VISIBLE);
                        sendOtpDeliveryTextView.setVisibility(View.INVISIBLE);

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
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpSendDelivery.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(DeliverySendOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                            startActivity(new Intent(DeliverySendOtp.this, DeliveryFoodPanelActivity.class));
                            finish();

                        } else {
                            ReusableCodeForAll.showAlert(DeliverySendOtp.this, "Error", task.getException().getMessage());
                        }

                    }
                });
    }
}

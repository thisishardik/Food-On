package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DeliveryLoginActivity extends AppCompatActivity {
    TextInputLayout emailDeliveryLogin, passwordDeliveryLogin;
    Button loginButtonDelivery, loginWithPhoneDeliveryLogin;
    TextView forgotPassword, signUp, textView6DeliveryLogin;
    FirebaseAuth firebaseAuth;
    String emailId, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login);
        try {
            emailDeliveryLogin = findViewById(R.id.emailDeliveryLogin);
            passwordDeliveryLogin = findViewById(R.id.passwordDeliveryLogin);
            loginButtonDelivery = (Button) findViewById(R.id.loginButtonDelivery);
            forgotPassword = (TextView) findViewById(R.id.forgotPassDeliveryLogin);
            loginWithPhoneDeliveryLogin = (Button) findViewById(R.id.loginWithPhoneDeliveryLogin);
            textView6DeliveryLogin = findViewById(R.id.textView6DeliveryLogin);
            firebaseAuth = FirebaseAuth.getInstance();

            loginButtonDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailId = emailDeliveryLogin.getEditText().getText().toString().trim();
                    pwd = passwordDeliveryLogin.getEditText().getText().toString().trim();
                    if (isValid()) {
                        final ProgressDialog mDialog = new ProgressDialog(DeliveryLoginActivity.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Logging in...");
                        mDialog.show();
                        firebaseAuth.signInWithEmailAndPassword(emailId, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        mDialog.dismiss();
                                        Toast.makeText(DeliveryLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(DeliveryLoginActivity.this, DeliveryFoodPanelActivity.class);
                                        startActivity(z);
                                        finish();
                                    } else {
                                        ReusableCodeForAll.showAlert(DeliveryLoginActivity.this, "", "Please verify your email");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.showAlert(DeliveryLoginActivity.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });

            textView6DeliveryLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Register = new Intent(DeliveryLoginActivity.this, DeliverySignUpActivity.class);
                    startActivity(Register);
                    finish();
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(DeliveryLoginActivity.this, DeliveryForgotPassword.class);
                    startActivity(a);
                    finish();
                }
            });

            loginWithPhoneDeliveryLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent q = new Intent(DeliveryLoginActivity.this, DeliveryLoginActivity.class);
                    startActivity(q);
                    finish();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        emailDeliveryLogin.setErrorEnabled(false);
        emailDeliveryLogin.setError("");
        passwordDeliveryLogin.setErrorEnabled(false);
        passwordDeliveryLogin.setError("");

        boolean isValidEmail = false, isValidPassword = false, isValid = false;
        if (TextUtils.isEmpty(emailId)) {
            emailDeliveryLogin.setErrorEnabled(true);
            emailDeliveryLogin.setError("Email is required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailDeliveryLogin.setErrorEnabled(true);
                emailDeliveryLogin.setError("Enter a valid Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            passwordDeliveryLogin.setErrorEnabled(true);
            passwordDeliveryLogin.setError("Password is required");
        } else {
            isValidPassword = true;
        }
        isValid = (isValidEmail && isValidPassword) ? true : false;
        return isValid;
    }
}
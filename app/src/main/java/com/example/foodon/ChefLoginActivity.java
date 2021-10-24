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

public class ChefLoginActivity extends AppCompatActivity {
    TextInputLayout emailChefLogin, passwordChefLogin;
    Button loginButtonChef, loginWithPhoneChefLogin;
    TextView forgotPassword, signUp, textView6ChefLogin;
    FirebaseAuth firebaseAuth;
    String emailId, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);
        try {
            emailChefLogin = findViewById(R.id.emailChefLogin);
            passwordChefLogin = findViewById(R.id.passwordChefLogin);
            loginButtonChef = (Button) findViewById(R.id.loginButtonChef);
            forgotPassword = (TextView) findViewById(R.id.forgotPassChefLogin);
            loginWithPhoneChefLogin = (Button) findViewById(R.id.loginWithPhoneChefLogin);
            textView6ChefLogin = findViewById(R.id.textView6ChefLogin);
            firebaseAuth = FirebaseAuth.getInstance();

            loginButtonChef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailId = emailChefLogin.getEditText().getText().toString().trim();
                    pwd = passwordChefLogin.getEditText().getText().toString().trim();
                    if (isValid()) {
                        final ProgressDialog mDialog = new ProgressDialog(ChefLoginActivity.this);
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
                                        Toast.makeText(ChefLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(ChefLoginActivity.this, ChefFoodPanelActivity.class);
                                        startActivity(z);
                                        finish();
                                    } else {
                                        ReusableCodeForAll.showAlert(ChefLoginActivity.this, "", "Please verify your email");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.showAlert(ChefLoginActivity.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });

            textView6ChefLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Register = new Intent(ChefLoginActivity.this, ChefSignUpActivity.class);
                    startActivity(Register);
                    finish();
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(ChefLoginActivity.this, ChefForgotPassword.class);
                    startActivity(a);
                    finish();
                }
            });

            loginWithPhoneChefLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent q = new Intent(ChefLoginActivity.this, ChefLoginActivity.class);
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
        emailChefLogin.setErrorEnabled(false);
        emailChefLogin.setError("");
        passwordChefLogin.setErrorEnabled(false);
        passwordChefLogin.setError("");

        boolean isValidEmail = false, isValidPassword = false, isValid = false;
        if (TextUtils.isEmpty(emailId)) {
            emailChefLogin.setErrorEnabled(true);
            emailChefLogin.setError("Email is required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailChefLogin.setErrorEnabled(true);
                emailChefLogin.setError("Enter a valid Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            passwordChefLogin.setErrorEnabled(true);
            passwordChefLogin.setError("Password is required");
        } else {
            isValidPassword = true;
        }
        isValid = (isValidEmail && isValidPassword) ? true : false;
        return isValid;
    }
}
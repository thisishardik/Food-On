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

public class CustLoginActivity extends AppCompatActivity {
    TextInputLayout emailCustLogin, passwordCustLogin;
    Button loginButtonCust, loginWithPhoneCustLogin;
    TextView forgotPassword, signUp, textView6CustLogin;
    FirebaseAuth firebaseAuth;
    String emailId, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);
        try {
            emailCustLogin = findViewById(R.id.emailCustLogin);
            passwordCustLogin = findViewById(R.id.passwordCustLogin);
            loginButtonCust = (Button) findViewById(R.id.loginButtonCust);
            forgotPassword = (TextView) findViewById(R.id.forgotPassCustLogin);
            loginWithPhoneCustLogin = (Button) findViewById(R.id.loginWithPhoneCustLogin);
            textView6CustLogin = findViewById(R.id.textView6CustLogin);
            firebaseAuth = FirebaseAuth.getInstance();

            loginButtonCust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailId = emailCustLogin.getEditText().getText().toString().trim();
                    pwd = passwordCustLogin.getEditText().getText().toString().trim();
                    if (isValid()) {
                        final ProgressDialog mDialog = new ProgressDialog(CustLoginActivity.this);
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
                                        Toast.makeText(CustLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(CustLoginActivity.this, CustFoodPanelActivity.class);
                                        startActivity(z);
                                        finish();
                                    } else {
                                        ReusableCodeForAll.showAlert(CustLoginActivity.this, "", "Please verify your email");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.showAlert(CustLoginActivity.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });

            textView6CustLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Register = new Intent(CustLoginActivity.this, CustSignUpActivity.class);
                    startActivity(Register);
                    finish();
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(CustLoginActivity.this, CustForgotPassword.class);
                    startActivity(a);
                    finish();
                }
            });

            loginWithPhoneCustLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent q = new Intent(CustLoginActivity.this, CustLoginActivity.class);
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
        emailCustLogin.setErrorEnabled(false);
        emailCustLogin.setError("");
        passwordCustLogin.setErrorEnabled(false);
        passwordCustLogin.setError("");

        boolean isValidEmail = false, isValidPassword = false, isValid = false;
        if (TextUtils.isEmpty(emailId)) {
            emailCustLogin.setErrorEnabled(true);
            emailCustLogin.setError("Email is required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailCustLogin.setErrorEnabled(true);
                emailCustLogin.setError("Enter a valid Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            passwordCustLogin.setErrorEnabled(true);
            passwordCustLogin.setError("Password is required");
        } else {
            isValidPassword = true;
        }
        isValid = (isValidEmail && isValidPassword) ? true : false;
        return isValid;
    }
}
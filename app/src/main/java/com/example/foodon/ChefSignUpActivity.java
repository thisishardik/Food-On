package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class ChefSignUpActivity extends AppCompatActivity {

    String[] Maharashtra = {"Mumbai", "Pune", "Nashik"};
    String[] Madhyapradesh = {"Bhopal", "Indore", "Ujjain"};

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextInputLayout firstNameChef, lastNameChef, emailChef, passwordChef, confirmPassChef, phoneChef, houseNoChef, areaChef, pinCodeChef;
    Spinner stateSpinnerChef, citySpinnerChef;
    Button signUpButtonChef, signInEmailChef, signInPhoneChef;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fName, lName, emailId, password, confPassword, mobile, house, area, pinCode, state, city;

    String role = "Chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_sign_up);

        firstNameChef = (TextInputLayout) findViewById(R.id.firstNameChef);
        lastNameChef = (TextInputLayout) findViewById(R.id.lastNameChef);
        emailChef = (TextInputLayout) findViewById(R.id.emailChef);
        passwordChef = (TextInputLayout) findViewById(R.id.passwordChef);
        confirmPassChef = (TextInputLayout) findViewById(R.id.confirmPassChef);
        phoneChef = (TextInputLayout) findViewById(R.id.phoneChef);
        houseNoChef = (TextInputLayout) findViewById(R.id.houseNoChef);
        areaChef = (TextInputLayout) findViewById(R.id.areaChef);
        stateSpinnerChef = (Spinner) findViewById(R.id.stateSpinnerChef);
        citySpinnerChef = (Spinner) findViewById(R.id.citySpinnerChef);
        pinCodeChef = (TextInputLayout) findViewById(R.id.pinCodeChef);
        signUpButtonChef = (Button) findViewById(R.id.signUpButtonChef);
        signInEmailChef = (Button) findViewById(R.id.signInButtonEmailChef);
        signInPhoneChef = (Button) findViewById(R.id.signInButtonPhoneChef);

        cpp = (CountryCodePicker) findViewById(R.id.countryCodeHolderChef);

        stateSpinnerChef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                state = value.toString().trim();
                if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerChef.setAdapter(arrayAdapter);
                } else if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerChef.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ChefSignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        citySpinnerChef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ChefSignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("Chef");
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(ChefSignUpActivity.this);

        signUpButtonChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = firstNameChef.getEditText().getText().toString().trim();
                lName = lastNameChef.getEditText().getText().toString().trim();
                emailId = emailChef.getEditText().getText().toString().trim();
                mobile = phoneChef.getEditText().getText().toString().trim();
                password = passwordChef.getEditText().getText().toString().trim();
                confPassword = confirmPassChef.getEditText().getText().toString().trim();
                area = areaChef.getEditText().getText().toString().trim();
                house = houseNoChef.getEditText().getText().toString().trim();
                pinCode = pinCodeChef.getEditText().getText().toString().trim();

                if (isValid()) {
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Registering user. Please wait.");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid);
                            final HashMap map1 = new HashMap<String, String>();
                            map1.put("Role", role);

                            databaseReference.setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    HashMap<String, String> map2 = new HashMap<String, String>();
                                    map2.put("first_name", fName);
                                    map2.put("last_name", lName);
                                    map2.put("email", emailId);
                                    map2.put("phone_number", mobile);
                                    map2.put("house_number", house);
                                    map2.put("area", area);
                                    map2.put("city", city);
                                    map2.put("state", state);
                                    map2.put("pin_code", pinCode);

                                    firebaseDatabase.getInstance().getReference("Chef")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        new AlertDialog.Builder(ChefSignUpActivity.this)
                                                                .setTitle("Registration successful")
                                                                .setMessage("You have successfully registered. Please verify your email address.")
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                        String phoneNumber = cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                        Intent intent = new Intent(ChefSignUpActivity.this, ChefVerifyPhone.class);
                                                                        intent.putExtra("phoneNumber", phoneNumber);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }).show();
                                                    } else {
                                                        progressDialog.dismiss();
                                                        ReusableCodeForAll.showAlert(ChefSignUpActivity.this, task.getException().toString(), task.getException().getMessage());
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

        signInEmailChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefSignUpActivity.this,ChefLoginActivity.class));
                finish();
            }
        });

        signInPhoneChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefSignUpActivity.this,ChefPhoneLoginActivity.class));
                finish();
            }
        });
    }

    public boolean isValid() {
        boolean areCredentialsValid = false;
        boolean isValidHouseNo = false;
        boolean isValidLName = false;
        boolean isValidName = false;
        boolean isValidEmail = false;
        boolean isValidPassword = false;
        boolean isValidConfPassword = false;
        boolean isValidMobileNum = false;
        boolean isValidArea = false;
        boolean isValidPinCode = false;

        emailChef.setErrorEnabled(false);
        emailChef.setError("");
        firstNameChef.setErrorEnabled(false);
        firstNameChef.setError("");
        lastNameChef.setErrorEnabled(false);
        lastNameChef.setError("");
        passwordChef.setErrorEnabled(false);
        passwordChef.setError("");
        phoneChef.setErrorEnabled(false);
        phoneChef.setError("");
        confirmPassChef.setErrorEnabled(false);
        confirmPassChef.setError("");
        areaChef.setErrorEnabled(false);
        areaChef.setError("");
        houseNoChef.setErrorEnabled(false);
        houseNoChef.setError("");
        pinCodeChef.setErrorEnabled(false);
        pinCodeChef.setError("");

        if (TextUtils.isEmpty(fName)) {
            firstNameChef.setErrorEnabled(true);
            firstNameChef.setError("Enter First Name");
        } else {
            isValidName = true;
        }
        if (TextUtils.isEmpty(lName)) {
            lastNameChef.setErrorEnabled(true);
            lastNameChef.setError("Enter Last Name");
        } else {
            isValidLName = true;
        }
        if (TextUtils.isEmpty(emailId)) {
            emailChef.setErrorEnabled(true);
            emailChef.setError("Email Is Required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailChef.setErrorEnabled(true);
                emailChef.setError("Enter a Valid Email Id");
            }
        }
        if (TextUtils.isEmpty(password)) {
            passwordChef.setErrorEnabled(true);
            passwordChef.setError("Enter Password");
        } else {
            if (password.length() < 8) {
                passwordChef.setErrorEnabled(true);
                passwordChef.setError("Password length cannot be less than 8");
            } else {
                isValidPassword = true;
            }
        }
        if (TextUtils.isEmpty(confPassword)) {
            confirmPassChef.setErrorEnabled(true);
            confirmPassChef.setError("Enter Password Again");
        } else {
            if (!password.equals(confPassword)) {
                confirmPassChef.setErrorEnabled(true);
                confirmPassChef.setError("Passwords don't match");
            } else {
                isValidConfPassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            phoneChef.setErrorEnabled(true);
            phoneChef.setError("Mobile Number is Required");
        } else {
            if (mobile.length() < 10) {
                phoneChef.setErrorEnabled(true);
                phoneChef.setError("Invalid Mobile Number");
            } else {
                isValidMobileNum = true;
            }
        }
        if (TextUtils.isEmpty(area)) {
            areaChef.setErrorEnabled(true);
            areaChef.setError("Area Is Required");
        } else {
            isValidArea = true;
        }
        if (TextUtils.isEmpty(pinCode)) {
            pinCodeChef.setErrorEnabled(true);
            pinCodeChef.setError("Please enter PinCode");
        } else {
            isValidPinCode = true;
        }
        if (TextUtils.isEmpty(house)) {
            houseNoChef.setErrorEnabled(true);
            houseNoChef.setError("Fields can't Be empty");
        } else {
            isValidHouseNo = true;
        }

        areCredentialsValid = (isValidArea && isValidConfPassword && isValidPassword && isValidPinCode && isValidEmail && isValidMobileNum && isValidName && isValidHouseNo && isValidLName) ? true : false;
        return areCredentialsValid;
    }
}
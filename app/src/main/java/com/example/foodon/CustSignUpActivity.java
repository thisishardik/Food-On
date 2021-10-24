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

public class CustSignUpActivity extends AppCompatActivity {
    String[] Maharashtra = {"Mumbai", "Pune", "Nashik"};
    String[] Madhyapradesh = {"Bhopal", "Indore", "Ujjain"};

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextInputLayout firstNameCust, lastNameCust, emailCust, passwordCust, confirmPassCust, phoneCust, localAddressCust, areaCust, pinCodeCust;
    Spinner stateSpinnerCust, citySpinnerCust;
    Button signUpButtonCust, signInEmailCust, signInPhoneCust;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fName, lName, emailId, password, confPassword, mobile, localAddress, area, pinCode, state, city;

    String role = "Customer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_sign_up);
        firstNameCust = (TextInputLayout) findViewById(R.id.firstNameCust);
        lastNameCust = (TextInputLayout) findViewById(R.id.lastNameCust);
        emailCust = (TextInputLayout) findViewById(R.id.emailCust);
        passwordCust = (TextInputLayout) findViewById(R.id.passwordCust);
        confirmPassCust = (TextInputLayout) findViewById(R.id.confirmPassCust);
        phoneCust = (TextInputLayout) findViewById(R.id.phoneCust);
        localAddressCust = (TextInputLayout) findViewById(R.id.localAddressCust);
        areaCust = (TextInputLayout) findViewById(R.id.areaCust);
        stateSpinnerCust = (Spinner) findViewById(R.id.stateSpinnerCust);
        citySpinnerCust = (Spinner) findViewById(R.id.citySpinnerCust);
        pinCodeCust = (TextInputLayout) findViewById(R.id.pinCodeCust);
        signUpButtonCust = (Button) findViewById(R.id.signUpButtonCust);
        signInEmailCust = (Button) findViewById(R.id.signInButtonEmailCust);
        signInPhoneCust = (Button) findViewById(R.id.signInButtonPhoneCust);

        cpp = (CountryCodePicker) findViewById(R.id.countryCodeHolderCust);

        stateSpinnerCust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                state = value.toString().trim();
                if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CustSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerCust.setAdapter(arrayAdapter);
                } else if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CustSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerCust.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CustSignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        citySpinnerCust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CustSignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("Customer");
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(CustSignUpActivity.this);

        signUpButtonCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = firstNameCust.getEditText().getText().toString().trim();
                lName = lastNameCust.getEditText().getText().toString().trim();
                emailId = emailCust.getEditText().getText().toString().trim();
                mobile = phoneCust.getEditText().getText().toString().trim();
                password = passwordCust.getEditText().getText().toString().trim();
                confPassword = confirmPassCust.getEditText().getText().toString().trim();
                area = areaCust.getEditText().getText().toString().trim();
                localAddress = localAddressCust.getEditText().getText().toString().trim();
                pinCode = pinCodeCust.getEditText().getText().toString().trim();

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
                                    map2.put("First Name", fName);
                                    map2.put("Last Name", lName);
                                    map2.put("Email", emailId);
                                    map2.put("Phone Number", mobile);
                                    map2.put("Local Address", localAddress);
                                    map2.put("Area", area);
                                    map2.put("City", city);
                                    map2.put("State", state);
                                    map2.put("Pin Code", pinCode);

                                    firebaseDatabase.getInstance().getReference("Customer")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        new AlertDialog.Builder(CustSignUpActivity.this)
                                                                .setTitle("Registration successful")
                                                                .setMessage("You have successfully registered. Please verify your email address.")
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                        String phoneNumber = cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                        Intent intent = new Intent(CustSignUpActivity.this, CustVerifyPhone.class);
                                                                        intent.putExtra("phoneNumber", phoneNumber);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }).show();
                                                    } else {
                                                        progressDialog.dismiss();
                                                        ReusableCodeForAll.showAlert(CustSignUpActivity.this, task.getException().toString(), task.getException().getMessage());
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

        signInEmailCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustSignUpActivity.this,CustLoginActivity.class));
                finish();
            }
        });

        signInPhoneCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustSignUpActivity.this,CustPhoneLoginActivity.class));
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

        emailCust.setErrorEnabled(false);
        emailCust.setError("");
        firstNameCust.setErrorEnabled(false);
        firstNameCust.setError("");
        lastNameCust.setErrorEnabled(false);
        lastNameCust.setError("");
        passwordCust.setErrorEnabled(false);
        passwordCust.setError("");
        phoneCust.setErrorEnabled(false);
        phoneCust.setError("");
        confirmPassCust.setErrorEnabled(false);
        confirmPassCust.setError("");
        areaCust.setErrorEnabled(false);
        areaCust.setError("");
        localAddressCust.setErrorEnabled(false);
        localAddressCust.setError("");
        pinCodeCust.setErrorEnabled(false);
        pinCodeCust.setError("");

        if (TextUtils.isEmpty(fName)) {
            firstNameCust.setErrorEnabled(true);
            firstNameCust.setError("Enter First Name");
        } else {
            isValidName = true;
        }
        if (TextUtils.isEmpty(lName)) {
            lastNameCust.setErrorEnabled(true);
            lastNameCust.setError("Enter Last Name");
        } else {
            isValidLName = true;
        }
        if (TextUtils.isEmpty(emailId)) {
            emailCust.setErrorEnabled(true);
            emailCust.setError("Email Is Required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailCust.setErrorEnabled(true);
                emailCust.setError("Enter a Valid Email Id");
            }
        }
        if (TextUtils.isEmpty(password)) {
            passwordCust.setErrorEnabled(true);
            passwordCust.setError("Enter Password");
        } else {
            if (password.length() < 8) {
                passwordCust.setErrorEnabled(true);
                passwordCust.setError("Password length cannot be less than 8");
            } else {
                isValidPassword = true;
            }
        }
        if (TextUtils.isEmpty(confPassword)) {
            confirmPassCust.setErrorEnabled(true);
            confirmPassCust.setError("Enter Password Again");
        } else {
            if (!password.equals(confPassword)) {
                confirmPassCust.setErrorEnabled(true);
                confirmPassCust.setError("Passwords don't match");
            } else {
                isValidConfPassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            phoneCust.setErrorEnabled(true);
            phoneCust.setError("Mobile Number is Required");
        } else {
            if (mobile.length() < 10) {
                phoneCust.setErrorEnabled(true);
                phoneCust.setError("Invalid Mobile Number");
            } else {
                isValidMobileNum = true;
            }
        }
        if (TextUtils.isEmpty(area)) {
            areaCust.setErrorEnabled(true);
            areaCust.setError("Area Is Required");
        } else {
            isValidArea = true;
        }
        if (TextUtils.isEmpty(pinCode)) {
            pinCodeCust.setErrorEnabled(true);
            pinCodeCust.setError("Please enter PinCode");
        } else {
            isValidPinCode = true;
        }
        if (TextUtils.isEmpty(localAddress)) {
            localAddressCust.setErrorEnabled(true);
            localAddressCust.setError("Fields can't Be empty");
        } else {
            isValidHouseNo = true;
        }

        areCredentialsValid = (isValidArea && isValidConfPassword && isValidPassword && isValidPinCode && isValidEmail && isValidMobileNum && isValidName && isValidHouseNo && isValidLName) ? true : false;
        return areCredentialsValid;
    }
}
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

public class DeliverySignUpActivity extends AppCompatActivity {

    String[] Maharashtra = {"Mumbai", "Pune", "Nashik"};
    String[] Madhyapradesh = {"Bhopal", "Indore", "Ujjain"};

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextInputLayout firstNameDelivery, lastNameDelivery, emailDelivery, passwordDelivery, confirmPassDelivery, phoneDelivery, houseNoDelivery, areaDelivery, pinCodeDelivery;
    Spinner stateSpinnerDelivery, citySpinnerDelivery;
    Button signUpButtonDelivery, signInEmailDelivery, signInPhoneDelivery;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fName, lName, emailId, password, confPassword, mobile, house, area, pinCode, state, city;

    String role = "Delivery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_sign_up);

        firstNameDelivery = (TextInputLayout) findViewById(R.id.firstNameDelivery);
        lastNameDelivery = (TextInputLayout) findViewById(R.id.lastNameDelivery);
        emailDelivery = (TextInputLayout) findViewById(R.id.emailDelivery);
        passwordDelivery = (TextInputLayout) findViewById(R.id.passwordDelivery);
        confirmPassDelivery = (TextInputLayout) findViewById(R.id.confirmPassDelivery);
        phoneDelivery = (TextInputLayout) findViewById(R.id.phoneDelivery);
        houseNoDelivery = (TextInputLayout) findViewById(R.id.houseNoDelivery);
        areaDelivery = (TextInputLayout) findViewById(R.id.areaDelivery);
        stateSpinnerDelivery = (Spinner) findViewById(R.id.stateSpinnerDelivery);
        citySpinnerDelivery = (Spinner) findViewById(R.id.citySpinnerDelivery);
        pinCodeDelivery = (TextInputLayout) findViewById(R.id.pinCodeDelivery);
        signUpButtonDelivery = (Button) findViewById(R.id.signUpButtonDelivery);
        signInEmailDelivery = (Button) findViewById(R.id.signInButtonEmailDelivery);
        signInPhoneDelivery = (Button) findViewById(R.id.signInButtonPhoneDelivery);

        cpp = (CountryCodePicker) findViewById(R.id.countryCodeHolderDelivery);

        stateSpinnerDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                state = value.toString().trim();
                if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DeliverySignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerDelivery.setAdapter(arrayAdapter);
                } else if (state.equals("Maharashtra")) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (String city : Maharashtra) {
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DeliverySignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerDelivery.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DeliverySignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        citySpinnerDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DeliverySignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("Delivery");
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(DeliverySignUpActivity.this);

        signUpButtonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = firstNameDelivery.getEditText().getText().toString().trim();
                lName = lastNameDelivery.getEditText().getText().toString().trim();
                emailId = emailDelivery.getEditText().getText().toString().trim();
                mobile = phoneDelivery.getEditText().getText().toString().trim();
                password = passwordDelivery.getEditText().getText().toString().trim();
                confPassword = confirmPassDelivery.getEditText().getText().toString().trim();
                area = areaDelivery.getEditText().getText().toString().trim();
                house = houseNoDelivery.getEditText().getText().toString().trim();
                pinCode = pinCodeDelivery.getEditText().getText().toString().trim();

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
                                    map2.put("House Number", house);
                                    map2.put("Area", area);
                                    map2.put("City", city);
                                    map2.put("State", state);
                                    map2.put("Pin Code", pinCode);

                                    firebaseDatabase.getInstance().getReference("Delivery")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        new AlertDialog.Builder(DeliverySignUpActivity.this)
                                                                .setTitle("Registration successful")
                                                                .setMessage("You have successfully registered. Please verify your email address.")
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                        String phoneNumber = cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                        Intent intent = new Intent(DeliverySignUpActivity.this, DeliveryVerifyPhone.class);
                                                                        intent.putExtra("phoneNumber", phoneNumber);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }).show();
                                                    } else {
                                                        progressDialog.dismiss();
                                                        ReusableCodeForAll.showAlert(DeliverySignUpActivity.this, task.getException().toString(), task.getException().getMessage());
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

        signInEmailDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliverySignUpActivity.this,DeliveryLoginActivity.class));
                finish();
            }
        });

        signInPhoneDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliverySignUpActivity.this,DeliveryPhoneLoginActivity.class));
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

        emailDelivery.setErrorEnabled(false);
        emailDelivery.setError("");
        firstNameDelivery.setErrorEnabled(false);
        firstNameDelivery.setError("");
        lastNameDelivery.setErrorEnabled(false);
        lastNameDelivery.setError("");
        passwordDelivery.setErrorEnabled(false);
        passwordDelivery.setError("");
        phoneDelivery.setErrorEnabled(false);
        phoneDelivery.setError("");
        confirmPassDelivery.setErrorEnabled(false);
        confirmPassDelivery.setError("");
        areaDelivery.setErrorEnabled(false);
        areaDelivery.setError("");
        houseNoDelivery.setErrorEnabled(false);
        houseNoDelivery.setError("");
        pinCodeDelivery.setErrorEnabled(false);
        pinCodeDelivery.setError("");

        if (TextUtils.isEmpty(fName)) {
            firstNameDelivery.setErrorEnabled(true);
            firstNameDelivery.setError("Enter First Name");
        } else {
            isValidName = true;
        }
        if (TextUtils.isEmpty(lName)) {
            lastNameDelivery.setErrorEnabled(true);
            lastNameDelivery.setError("Enter Last Name");
        } else {
            isValidLName = true;
        }
        if (TextUtils.isEmpty(emailId)) {
            emailDelivery.setErrorEnabled(true);
            emailDelivery.setError("Email Is Required");
        } else {
            if (emailId.matches(emailPattern)) {
                isValidEmail = true;
            } else {
                emailDelivery.setErrorEnabled(true);
                emailDelivery.setError("Enter a Valid Email Id");
            }
        }
        if (TextUtils.isEmpty(password)) {
            passwordDelivery.setErrorEnabled(true);
            passwordDelivery.setError("Enter Password");
        } else {
            if (password.length() < 8) {
                passwordDelivery.setErrorEnabled(true);
                passwordDelivery.setError("Password length cannot be less than 8");
            } else {
                isValidPassword = true;
            }
        }
        if (TextUtils.isEmpty(confPassword)) {
            confirmPassDelivery.setErrorEnabled(true);
            confirmPassDelivery.setError("Enter Password Again");
        } else {
            if (!password.equals(confPassword)) {
                confirmPassDelivery.setErrorEnabled(true);
                confirmPassDelivery.setError("Passwords don't match");
            } else {
                isValidConfPassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            phoneDelivery.setErrorEnabled(true);
            phoneDelivery.setError("Mobile Number is Required");
        } else {
            if (mobile.length() < 10) {
                phoneDelivery.setErrorEnabled(true);
                phoneDelivery.setError("Invalid Mobile Number");
            } else {
                isValidMobileNum = true;
            }
        }
        if (TextUtils.isEmpty(area)) {
            areaDelivery.setErrorEnabled(true);
            areaDelivery.setError("Area Is Required");
        } else {
            isValidArea = true;
        }
        if (TextUtils.isEmpty(pinCode)) {
            pinCodeDelivery.setErrorEnabled(true);
            pinCodeDelivery.setError("Please enter PinCode");
        } else {
            isValidPinCode = true;
        }
        if (TextUtils.isEmpty(house)) {
            houseNoDelivery.setErrorEnabled(true);
            houseNoDelivery.setError("Fields can't Be empty");
        } else {
            isValidHouseNo = true;
        }

        areCredentialsValid = (isValidArea && isValidConfPassword && isValidPassword && isValidPinCode && isValidEmail && isValidMobileNum && isValidName && isValidHouseNo && isValidLName) ? true : false;
        return areCredentialsValid;
    }
}
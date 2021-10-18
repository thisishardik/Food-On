package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

public class ChefSignUpActivity extends AppCompatActivity {

    String[] Maharashtra = {"Mumbai","Pune","Nashik"};
    String[] Madhyapradesh = {"Bhopal","Indore","Ujjain"};

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextInputLayout firstNameChef,lastNameChef,emailChef,passwordChef,confirmPassChef,phoneChef,houseNoChef,areaChef,pinCodeChef;
    Spinner stateSpinnerChef, citySpinnerChef;
    Button signUpButtonChef, signInEmailChef, signInPhoneChef;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, lname, emailId, password, confPassword, mobile, house, area, pinCode, state, city;

    String role="Chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_sign_up);

        firstNameChef = (TextInputLayout)findViewById(R.id.firstNameChef);
        lastNameChef = (TextInputLayout)findViewById(R.id.lastNameChef);
        emailChef = (TextInputLayout)findViewById(R.id.emailChef);
        passwordChef = (TextInputLayout)findViewById(R.id.passwordChef);
        confirmPassChef = (TextInputLayout)findViewById(R.id.confirmPassChef);
        phoneChef = (TextInputLayout)findViewById(R.id.phoneChef);
        houseNoChef = (TextInputLayout)findViewById(R.id.houseNoChef);
        areaChef = (TextInputLayout)findViewById(R.id.areaChef);
        stateSpinnerChef = (Spinner) findViewById(R.id.stateSpinnerChef);
        citySpinnerChef = (Spinner) findViewById(R.id.citySpinnerChef);
        pinCodeChef = (TextInputLayout)findViewById(R.id.pinCodeChef);

        signUpButtonChef = (Button)findViewById(R.id.signUpButtonChef);
        signInEmailChef = (Button)findViewById(R.id.signInButtonEmailChef);
        signInPhoneChef = (Button)findViewById(R.id.signInButtonPhoneChef);

        cpp = (CountryCodePicker)findViewById(R.id.countryCodeHolderChef);

        stateSpinnerChef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                state = value.toString().trim();
                if(state.equals("Maharashtra")){
                    ArrayList<String> list = new ArrayList<String>();
                    for(String city: Maharashtra){
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( ChefSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerChef.setAdapter(arrayAdapter);
                } else if(state.equals("Maharashtra")){
                    ArrayList<String> list = new ArrayList<String>();
                    for(String city: Maharashtra){
                        list.add(city);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( ChefSignUpActivity.this, android.R.layout.simple_spinner_item, list);
                    citySpinnerChef.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ChefSignUpActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = firebaseDatabase.getReference("Chef");
        firebaseAuth = FirebaseAuth.getInstance();

        signUpButtonChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = firstNameChef.getEditText().getText().toString().trim();
                lname = lastNameChef.getEditText().getText().toString().trim();
                emailId = emailChef.getEditText().getText().toString().trim();
                mobile = phoneChef.getEditText().getText().toString().trim();
                password = passwordChef.getEditText().getText().toString().trim();
                confPassword = confirmPassChef.getEditText().getText().toString().trim();
                area = areaChef.getEditText().getText().toString().trim();
                house = houseNoChef.getEditText().getText().toString().trim();
                pinCode = pinCodeChef.getEditText().getText().toString().trim();
            }
        });
    }

    public boolean isValid(){
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

        boolean isValid=false, isValidHouseNo=false, isValidLName=false, isValidName=false, isValidEmail=false, isValidPassword=false, isValidConfPassword=false, isValidMobileNum=false, isValidArea=false, isValidPinCode=false;
        if(TextUtils.isEmpty(fname)){
            firstNameChef.setErrorEnabled(true);
            firstNameChef.setError("Enter First Name");
        } else{
            isValidName = true;
        }
        if(TextUtils.isEmpty(lname)){
            lastNameChef.setErrorEnabled(true);
            lastNameChef.setError("Enter Last Name");
        } else{
            isValidLName = true;
        }
        if(TextUtils.isEmpty(emailId)){
            emailChef.setErrorEnabled(true);
            emailChef.setError("Email Is Required");
        } else{
            if(emailId.matches(emailPattern)){
                isValidEmail = true;
            } else{
                emailChef.setErrorEnabled(true);
                emailChef.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Area Is Required");
        }else{
            isValidarea = true;
        }
        if(TextUtils.isEmpty(Pincode)){
            pincode.setErrorEnabled(true);
            pincode.setError("Please Enter Pincode");
        }else{
            isValidpincode = true;
        }
        if(TextUtils.isEmpty(house)){
            houseno.setErrorEnabled(true);
            houseno.setError("Fields Can't Be Empty");
        }else{
            isValidhouseno = true;
        }

        isValid = (isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidhouseno && isValidlname) ? true : false;
        return isValid;

    }
}
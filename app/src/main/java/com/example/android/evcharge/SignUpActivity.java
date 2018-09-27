package com.example.android.evcharge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private EditText etEmailId;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etName;
    private RadioGroup rgTypeOfPlan;
    private RadioButton rbprepaid;
    private RadioButton rbpostpaid;
    private RadioButton rbregular;
    private Button btSignUp;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseFirestore db;

    //get the user data
    private String Name;
    private String emailId;
    private String password;
    private String confirmPassword;
    private String typeOfPlan;

    private static final String TAG = SignUpActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        rgTypeOfPlan=(RadioGroup)findViewById(R.id.rg_type_plan);
        rbpostpaid=(RadioButton)findViewById(R.id.rb_postpaid);
        rbprepaid=(RadioButton)findViewById(R.id.rb_prepaid);
        rbregular=(RadioButton)findViewById(R.id.rb_regular);
        etEmailId=(EditText)findViewById(R.id.etv_email_id_reg);
        etPassword=(EditText)findViewById(R.id.etv_password_reg);
        etConfirmPassword=(EditText)findViewById(R.id.etv_confirm_password_reg);
        etName=(EditText)findViewById(R.id.etv_name_reg);
        btSignUp=(Button)findViewById(R.id.bt_sign_up);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailId=etEmailId.getText().toString();
                password=etPassword.getText().toString();
                confirmPassword=etConfirmPassword.getText().toString();
                Name=etName.getText().toString();
                int selectedId=rgTypeOfPlan.getCheckedRadioButtonId();
                switch (selectedId)
                {
                    case R.id.rb_postpaid:
                        typeOfPlan="postpaid";
                        break;
                    case R.id.rb_prepaid:
                        typeOfPlan="prepaid";
                        break;
                    case R.id.rb_regular:
                        typeOfPlan="regular";
                        break;
                }
                signUp();
            }
        });


        //auth

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null ){

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    addNewUser();



                }

            }
        };




    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    public void addNewUser() {


        Customer newcustomer=new Customer(Name,emailId,password,typeOfPlan,"default","0");


        String userID = mAuth.getCurrentUser().getUid();

        db.collection("Customer").document(userID)
                .set(newcustomer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this,"Customer DocumentSnapshot successfully written!",Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "Faculty DocumentSnapshot successfully written!");
                        Intent i=new Intent(SignUpActivity.this,SelectModeActivity.class);
                        startActivity(i);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(SignUpActivity.this,"Error writing document..try again",Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }



    public void signUp() {

        //get the user data
        emailId=etEmailId.getText().toString();
        password=etPassword.getText().toString();
        confirmPassword=etConfirmPassword.getText().toString();
        Name=etName.getText().toString();

        // check if the fields are left blank
        if(TextUtils.isEmpty(Name)||
                TextUtils.isEmpty(emailId)||TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPassword)||!(password.equals(confirmPassword))
                ){
            Toast.makeText(SignUpActivity.this,"Enter valid input to login",Toast.LENGTH_SHORT).show();
        }
        //if not blank
        else
            {

            //passing into auth function, and checking if the login is completed
            mAuth.signInWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if the login is completed successfully or not
                    if(!task.isSuccessful()){
//                        Toast.makeText(Login.this,"unable to login",Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignUpActivity.this,"unable to login",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(SignUpActivity.this,"Registered",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}

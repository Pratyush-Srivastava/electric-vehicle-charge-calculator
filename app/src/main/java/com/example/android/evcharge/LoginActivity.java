package com.example.android.evcharge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    //  FIREBASE declaration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    // UI references.
    private EditText etEmailId;
    private EditText etPassword;
    private View mProgressView;
    private View mLoginFormView;

    private Button btSignUp;
    private Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        etEmailId= (EditText) findViewById(R.id.email);
        etPassword= (EditText) findViewById(R.id.password);

        btSignIn=(Button)findViewById(R.id.bt_sign_in);
        btSignUp=(Button)findViewById(R.id.bt_not_a_customer);
        btSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        btSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //login
                login();
            }
        });
        //check if user is authenticated
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  if yes send him inside
                if(firebaseAuth.getCurrentUser()!=null){

                    String userID = firebaseAuth.getCurrentUser().getUid();


                    startActivity(new Intent(LoginActivity.this,SelectModeActivity.class).putExtra("userID",userID));

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void login(){
        // auth strings to pass into the Firebase auth functions
        String email=etEmailId.getText().toString();
        String password=etPassword.getText().toString();

        // check if the fields are left blank
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty((password))){

            Toast.makeText(LoginActivity.this,"Invalid Entry ", Toast.LENGTH_SHORT).show();
        }
        //if not blank
        else{

            //passing into auth dfunction, and checking if the login is completed
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if the login is completed successfully or not

                    if(!task.isSuccessful()){
                          Toast.makeText(LoginActivity.this,"unable to login",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(LoginActivity.this,"logging in",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }





}


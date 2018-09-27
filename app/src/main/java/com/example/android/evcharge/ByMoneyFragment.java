package com.example.android.evcharge;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ByMoneyFragment extends Fragment {
    private String money;
    private TextView duration;
    private TextView power;
    private Button btChargeYourVehicle;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    private static final String TAG = ByMoneyFragment.class.getSimpleName();



    public ByMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_by_money, container, false);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_by_money);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.by_money_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                money=parent.getItemAtPosition(position).toString()+" Rs.";
                String EstDuration;
                String EstPower;
                //formula
                EstDuration=(Integer.parseInt(money)*1.42857)+" mins";
                EstPower=(Integer.parseInt(money)*0.16667)+" kWh";
                //setting text
                duration.setText(EstDuration);
                power.setText(EstPower);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        duration=(TextView)view.findViewById(R.id.tv_time_by_money);
        power=(TextView)view.findViewById(R.id.tv_power_by_money);
        btChargeYourVehicle=(Button)view.findViewById(R.id.bt_by_money);
        btChargeYourVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action
                Calendar c = Calendar.getInstance();


                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                SimpleDateFormat tf = new SimpleDateFormat("hh:mm a");
                String formattedTime = tf.format(c.getTime());
                Transaction transaction=new Transaction(power.getText().toString(),duration.getText().toString(),money,"location",formattedTime+" "+formattedDate);

                pushingToDatabase(transaction);

            }
        });
        return view;

    }

    private void pushingToDatabase(Transaction transaction){

        db.collection("Customer").document(userID).collection("Transaction").document(transaction.getTimeStamp())
                .set(transaction)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"Customer DocumentSnapshot successfully written!",Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "Faculty DocumentSnapshot successfully written!");
                        Intent i=new Intent(getActivity(),SelectModeActivity.class);
                        startActivity(i);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(),"Error writing document..try again",Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}

package com.example.android.evcharge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvTypeCustomer;
    private TextView tvCar;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        tvName=(TextView)v.findViewById(R.id.tv_name_profile);
        tvEmail=(TextView)v.findViewById(R.id.tv_email_profile);
        tvTypeCustomer=(TextView)v.findViewById(R.id.tv_type_customer_profile);
        tvCar=(TextView)v.findViewById(R.id.tv_car_details_profile);


        db.collection("Customer").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Customer customer = documentSnapshot.toObject(Customer.class);
                settingValues(customer);
            }
        });

        return v;
    }
    private void settingValues(Customer customer){
        tvName.setText(customer.getName());
        tvEmail.setText(customer.getEmailId());
        tvCar.setText(customer.getCar());
        tvTypeCustomer.setText(customer.getTypeOfPlan());

    }

}

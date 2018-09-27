package com.example.android.evcharge;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private String moneyToBeAdded;
    private Button btAddMoney;
    private LinearLayout llAddMoney;
    private TextView tvAccount;
    private TextView tvAccountValue;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_account, container, false);
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();




        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_add_money_wallet);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.by_money_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moneyToBeAdded=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
        btAddMoney=(Button)view.findViewById(R.id.bt_add_money_wallet);
        llAddMoney=(LinearLayout)view.findViewById(R.id.ll_add_money_wallet);
        tvAccount=(TextView)view.findViewById(R.id.tv_account);
        tvAccountValue=(TextView)view.findViewById(R.id.tv_account_value);

        btAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=moneyToBeAdded;

            }
        });
        if(((EVCharge) getActivity().getApplication()).isPostpaid()) {
            llAddMoney.setVisibility(View.INVISIBLE);
            tvAccount.setText("Balance");
            }




        db.collection("Customer").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Customer customer = documentSnapshot.toObject(Customer.class);
                settingValues(customer);
            }
        });




        return  view;

    }
    private void settingValues(Customer customer){
        tvAccountValue.setText(customer.getAccount());
    }



}

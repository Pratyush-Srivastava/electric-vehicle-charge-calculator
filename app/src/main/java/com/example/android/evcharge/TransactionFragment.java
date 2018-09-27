package com.example.android.evcharge;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;

    private ArrayList<String> duration;
    private ArrayList<String> power;
    private ArrayList<String> money;
    private ArrayList<String> location;
    private ArrayList<String> timeStamp;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;


    private static final String TAG = TransactionFragment.class.getSimpleName();

    public TransactionFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        duration=new ArrayList<>();
        power=new ArrayList<>();
        money=new ArrayList<>();
        location=new ArrayList<>();
        timeStamp=new ArrayList<>();
        database();



    }


    public void database()
    {
        db.collection("Customer").document(userID).collection("Transaction")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            accessTransaction(task);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

    }
    private void accessTransaction(Task<QuerySnapshot> task){

        for (QueryDocumentSnapshot document : task.getResult()) {
            Log.d(TAG, document.getId() + " => " + document.getData());
            Transaction transaction=document.toObject(Transaction.class);
            duration.add(transaction.getDuration());
            power.add(transaction.getPower());
            money.add(transaction.getMoney());
            location.add(transaction.getLocation());
            timeStamp.add(transaction.getTimeStamp());
            adapter=new TransactionAdapter(TransactionFragment.this,duration,power,money,location,timeStamp);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.rv_transaction);
        recyclerView.setHasFixedSize(false);



        adapter=new TransactionAdapter(this,duration,power,money,location,timeStamp);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);




        return view;
    }

}

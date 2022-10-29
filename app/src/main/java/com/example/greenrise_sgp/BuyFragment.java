package com.example.greenrise_sgp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BuyFragment<paymentsClient> extends Fragment {

    RecyclerView rv;
    cartAdapter adapter;
    Button btn;
    int tP=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        btn = view.findViewById(R.id.button2);
        rv=(RecyclerView) view.findViewById(R.id.conView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<cartModel> options =
                new FirebaseRecyclerOptions.Builder<cartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart"),cartModel.class)
                        .build();
        adapter=new cartAdapter(options);
        rv.setAdapter(adapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference cart = db.getReference("Cart");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tP=0;
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            tP+=Integer.parseInt(snapshot1.child("totalprice").getValue().toString());
                        }


                        AppCompatActivity activity = (AppCompatActivity)view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new checkoutFragment(String.valueOf(tP))).addToBackStack(null).commit();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
package com.example.greenrise_sgp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profileFragment extends Fragment {
    Button switchtoseller,orderuser,wishlistuser,infouser,logout,login;
    TextView name;
    ImageView uimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        switchtoseller=view.findViewById(R.id.Usts);
        orderuser = view.findViewById(R.id.Uorders);
        wishlistuser = view.findViewById(R.id.Uwish);
        infouser = view.findViewById(R.id.UInfo);
        logout = view.findViewById(R.id.Ulog);
        name = view.findViewById(R.id.Uname);
        uimage = view.findViewById(R.id.imageView);
        login = view.findViewById(R.id.Ulogin);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        switchtoseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SellerLoginActivity.class);
                startActivity(intent);
            }
        });
        orderuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        infouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyInfoActivity.class);
                startActivity(intent);
            }
        });
        wishlistuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyWishlistActivity.class);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        name.setText(CurrentUser.currentUser.getName());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserLoginActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserLoginActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
package com.example.greenrise_sgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerProfileActivity extends AppCompatActivity {
    TextView buyer,name,email,type,upi;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        buyer = findViewById(R.id.Buyer);
        name = findViewById(R.id.Sname);
        email = findViewById(R.id.Semail);
        type = findViewById(R.id.Stype);
        upi = findViewById(R.id.UPI);
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        bnv = findViewById(R.id.bottomnav);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProfileActivity.this, homePage.class);
                startActivity(intent);
            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Sellers");
        name.setText(CurrentSeller.currentSeller.getName());
        email.setText(CurrentSeller.currentSeller.getEmail());
        type.setText(CurrentSeller.currentSeller.getSelltype());
        upi.setText(CurrentSeller.currentSeller.getUpiid());
        bnv.setSelectedItemId(R.id.profilei);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homei:
                        Intent intent1 = new Intent(SellerProfileActivity.this,SellerHomeActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.profilei:
                        return true;
                    case R.id.carti:
                        Intent intent2 = new Intent(SellerProfileActivity.this,SellerOrdersActivity.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });
    }
}
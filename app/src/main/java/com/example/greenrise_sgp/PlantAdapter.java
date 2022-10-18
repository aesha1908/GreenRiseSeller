package com.example.greenrise_sgp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.myViewHolder> {
    ArrayList<Plant> list;
    Context context;
    DatabaseReference databaseReference;

    public PlantAdapter(Context context, ArrayList<Plant> list){
        this.context = context;
        this.list = list;

    }

//    public PlantAdapter(DatabaseReference databaseReference) {
//        this.databaseReference = databaseReference;
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//    }

    @NonNull
    @Override
    public PlantAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plantentry,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.myViewHolder holder, int position) {
       // String key = databaseReference.child("Plants").push().getKey();

        Plant plant = list.get(position);
        holder.namev.setText(plant.getName());
        holder.aboutv.setText(plant.getAbout());
        holder.pricev.setText(String.valueOf(plant.getPrice()));
        holder.quantv.setText(String.valueOf(plant.getQuantity()));
        Glide.with(context).load(plant.getImage()).into(holder.imagev);
        //String pos = plant.getKey();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Plants");
        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imagev.getContext())
                        .setContentHolder(new ViewHolder(R.layout.plantupdate))
                        .setExpanded(true,2200)
                        .create();

                View v = dialogPlus.getHolderView();

                EditText etname = v.findViewById(R.id.upname);
                EditText etabout = v.findViewById(R.id.upabout);
                EditText etprice = v.findViewById(R.id.upprice);
                EditText etquantity = v.findViewById(R.id.upquantity);

                Button up = v.findViewById(R.id.update);

                etname.setText(plant.getName());
                etabout.setText(plant.getAbout());
                etprice.setText(String.valueOf(plant.getPrice()));
                etquantity.setText(String.valueOf(plant.getQuantity()));
                dialogPlus.show();
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Query query = FirebaseDatabase.getInstance().getReference().child("Plants")
//                                .orderByChild("key").equalTo(plant.getKey());

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    if (dataSnapshot.child("name").getValue().toString().equals(holder.namev.getText()))
                                    {
                                        HashMap map = new HashMap();
                                        map.put("name",etname.getText().toString());
                                        map.put("about",etabout.getText().toString());
                                        map.put("price",etprice.getText().toString());
                                        map.put("quantity",etquantity.getText().toString());
                                        reference.child(dataSnapshot.child("key").getValue().toString()).updateChildren(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(holder.namev.getContext(),"Done",Toast.LENGTH_SHORT);
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(holder.namev.getContext(),"Oops!",Toast.LENGTH_SHORT);
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });
              //  dialogPlus.show();
            }
        });
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Plants").child(plant.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(holder.namev.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.namev.getContext(), "Error occured!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView namev,aboutv,pricev,quantv;
        ImageView imagev,updatebtn,delbtn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namev = itemView.findViewById(R.id.plantname);
            aboutv = itemView.findViewById(R.id.plantabout);
            pricev = itemView.findViewById(R.id.plantprice);
            quantv = itemView.findViewById(R.id.plantquantity);
            imagev = itemView.findViewById(R.id.plantimage);
            updatebtn = itemView.findViewById(R.id.updatelogo);
            delbtn = itemView.findViewById(R.id.deletelogo);
        }
    }
}

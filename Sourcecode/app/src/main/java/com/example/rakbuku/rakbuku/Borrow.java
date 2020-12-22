package com.example.rakbuku.rakbuku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Borrow extends AppCompatActivity {

    //UI
    private ImageButton midBuLend, midBuProfil;

    //CloudFirestore
    FirebaseFirestore db ;
    RecyclerView mRecyclerView;
    ArrayList<BorrowBook> borrowBookArrayList;
    MyRecyclerViewAdapter adapter;


    //Definisi List
    //List<BorrowBook> lstBook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        midBuLend = (ImageButton) findViewById(R.id.idBuLend);
        midBuProfil = (ImageButton) findViewById(R.id.idBuProfil);
        borrowBookArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();



    }

    private void loadDataFromFirebase(){
        db.collection("Book")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            BorrowBook borrowBook = new BorrowBook(querySnapshot.getString("Judul")
                                    , querySnapshot.getString("Email")
                                    , querySnapshot.getString("UrlBook")
                                    , querySnapshot.getString("Penulis")
                                    , querySnapshot.getString("Penerbit")
                                    , querySnapshot.getString("Harga")
                                    , querySnapshot.getString("Tipe"));
                            borrowBookArrayList.add(borrowBook);
                        }

                        adapter = new MyRecyclerViewAdapter(Borrow.this, borrowBookArrayList);


                        mRecyclerView.setAdapter(adapter);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Borrow.this, "Bermasalah, Mohon ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.v("..............", e.getMessage());
                    }
                });

        midBuProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Borrow.this, Account.class);
                startActivity(intent);
                finish();
            }
        });

        midBuLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Borrow.this, Lend.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private  void  setUpFireBase(){
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerview_id);
        MyRecyclerViewAdapter  myAdapter = new MyRecyclerViewAdapter(this,borrowBookArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(myAdapter);


    }
}
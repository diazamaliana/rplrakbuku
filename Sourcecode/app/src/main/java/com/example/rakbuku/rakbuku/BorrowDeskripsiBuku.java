package com.example.rakbuku.rakbuku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class BorrowDeskripsiBuku extends AppCompatActivity {

    private static final String TAG = "BorrowDeskripsiBuku";

    private TextView mTextJudulBuku, mTextPenulisBuku, mTextPenerbitBuku, mTextTipeBuku, mTextHargaBuku, mTextPemilik, mTextEmailPemilik, mTextDomisili;
    private Button midFixBorrow;
    private ImageButton midBuLend, midBuProfil;
    private ImageView midFotoDeskBuku, midFotoPemilik;


    //CloudFirestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Book");
    private DocumentReference usRef = db.document("");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_deskripsi_buku);

        mTextJudulBuku = (TextView) findViewById(R.id.TextJudulBuku);
        mTextPenulisBuku = (TextView) findViewById(R.id.TextPenulisBuku);
        mTextPenerbitBuku = (TextView) findViewById(R.id.TextPenerbitBuku);
        mTextTipeBuku = (TextView) findViewById(R.id.TextTipeBuku);
        mTextHargaBuku = (TextView) findViewById(R.id.TextHargaBuku);
        mTextPemilik = (TextView) findViewById(R.id.NamaPemilik);
        mTextDomisili = (TextView) findViewById(R.id.DomisiliPemilik);
        mTextEmailPemilik = (TextView) findViewById(R.id.EmailPemilik);
        midFixBorrow = (Button) findViewById(R.id.idFixBorrow);
        midFotoDeskBuku = (ImageView) findViewById(R.id.idFotoDeskBuku);
        midFotoPemilik = (ImageView) findViewById(R.id.idFotoPemilik);


        midBuLend = (ImageButton) findViewById(R.id.idBuLend);
        midBuProfil = (ImageButton) findViewById(R.id.idBuProfil);

        // Recieve data
        Intent intent = getIntent();
        String Judul = intent.getExtras().getString("Judul");
        String Email = intent.getExtras().getString("Email");
        String UrlBook = intent.getExtras().getString("UrlBook");
        String Harga = intent.getExtras().getString("Harga");
        String Tipe = intent.getExtras().getString("Tipe");
        String Penulis = intent.getExtras().getString("Penulis");
        String Penerbit = intent.getExtras().getString("Penerbit");

        String harga = "";

        harga += "Harga Sewa : Rp." + Penulis;

        mTextJudulBuku.setText(Judul);
        mTextPenulisBuku.setText(Harga);
        mTextPenerbitBuku.setText(Penerbit);
        mTextTipeBuku.setText(Tipe);
        mTextHargaBuku.setText(harga);


        //set Display profil Image
        Picasso.with(BorrowDeskripsiBuku.this).load(UrlBook).fit().centerCrop().into(midFotoDeskBuku);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("User")
                .whereEqualTo("Email",Email )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot documentSnapshot : task.getResult()) {

                                Note note = documentSnapshot.toObject(Note.class);
                                note.setDocumentId(documentSnapshot.getId());

                                String documentId = note.getDocumentId();
                                String Nama = note.getNama();
                                String Email = note.getEmail();
                                String UrlImage = note.getUrlImage();
                                String Domisili = note.getLokasi();

                                //set Display profil Image
                                Picasso.with(BorrowDeskripsiBuku.this).load(UrlImage).fit().centerCrop().into(midFotoPemilik);

                                //Set Display user profil

                                mTextPemilik.setText(Nama);
                                mTextEmailPemilik.setText(Email);
                                mTextDomisili.setText(Domisili);
                            }

                        } else {
                            Log.d(TAG, "Gagal, Ulangi Kembali", task.getException());
                        }

                        midFixBorrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BorrowDeskripsiBuku.this, BorrowLetterAgree.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        midBuLend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BorrowDeskripsiBuku.this, Lend.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        midBuProfil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BorrowDeskripsiBuku.this, Account.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                });
        }

    }


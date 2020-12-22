package com.example.rakbuku.rakbuku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class Account extends AppCompatActivity {

    private static final String TAG = "Account";

    private static final String KEY_NAMA = "Nama";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_LOKASI = "Lokasi";
    private static final String KEY_URL = "UrlImage";

    //UI
    private TextView midEmail, midDisplayNama , midLokasiAkun;
    private ImageView mprofilimage;
    private ImageButton midBuBorrow, midBuLend;

    //Auth
    private FirebaseAuth mauth;

    //Storage
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    private DocumentReference usRef = db.document("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mauth = FirebaseAuth.getInstance();
        midEmail = (TextView) findViewById(R.id.idEmailSignUp);
        midDisplayNama = (TextView) findViewById(R.id.idDisplayNama);
        midLokasiAkun = (TextView) findViewById(R.id.idLokasiAkun);
        mprofilimage = (ImageView) findViewById(R.id.profile_image);
        midBuBorrow =(ImageButton) findViewById(R.id.idBuBorrow);
        midBuLend =(ImageButton) findViewById(R.id.idBuLend);

        //Auth
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Menampilkan Email yg nyambung ke Auth
        setDataToView(user);

        //Cloud Firestore
        db.collection("User")
                .whereEqualTo("Email",user.getEmail() )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {

                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                //    Log.d(TAG, document.getId() + " => " + document.getData());

                                Note note = documentSnapshot.toObject(Note.class);
                                note.setDocumentId(documentSnapshot.getId());

                                String documentId = note.getDocumentId();
                                String Nama = note.getNama();
                                String Email = note.getEmail();
                                String UrlImage = note.getUrlImage();
                                String Lokasi = note.getLokasi();

                                //set Display profil Image
                                Picasso.with(Account.this).load(UrlImage).fit().centerCrop().into(mprofilimage);

                                //Set Display user profil
                                midDisplayNama.setText(Nama);
                                midLokasiAkun.setText(Lokasi);
                            }

                        } else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

        //Image Button Borrow
        midBuBorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Borrow.class));
            }
        });

        //Image Button Lend
        midBuLend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Lend.class));
            }
        });
    }

    //Menampilkan Email yg nyangkut ke Auth
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        midEmail.setText(user.getEmail());
    }

}

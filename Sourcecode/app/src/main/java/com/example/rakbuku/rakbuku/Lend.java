package com.example.rakbuku.rakbuku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Lend extends AppCompatActivity {

        //UI
        private EditText midJudulLend, midPenulisLend, midPenerbitLend, midTipeLend, midKotaLender, midEmailLender, midHargaLend;
        private Button midLendBtn;
        private ImageButton midUploadBuku;


        //Auth
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        //Cloud Firestore
        private FirebaseFirestore db = FirebaseFirestore.getInstance();

        private static final String TAG = "FiturLend";

        private static final String KEY_JUDUL = "Judul";
        private static final String KEY_EMAIL = "Email";
        private static final String KEY_PENULIS = "Penulis";
        private static final String KEY_PENERBIT = "Penerbit";
        private static final String KEY_KOTALENDER = "KotaLender";
        private static final String KEY_TIPE = "Tipe";
        private static final String KEY_HARGA = "Harga";
        private static final String KEY_URLBOOK = "UrlBook";



        //Storage
        FirebaseStorage storage;
        StorageReference storageRef, imageRef;
        private static final int Selected = 100;
        ProgressDialog progressDialog;
        UploadTask uploadTask;
        Uri uriBook;
        //TextView YourUriImage;
        TextView YourUriBook;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lend);

            midJudulLend = (EditText) findViewById(R.id.idJudulLend);
            midPenulisLend = (EditText) findViewById(R.id.idPenulisLend);
            midPenerbitLend = (EditText) findViewById(R.id.idPenerbitLend);
            midEmailLender = (EditText) findViewById(R.id.idEmailLender);
            midTipeLend = (EditText) findViewById(R.id.idTipeLend);
            midHargaLend = (EditText) findViewById(R.id.idHargaLend);
            midKotaLender =(EditText) findViewById(R.id.KotaLender);

            midLendBtn = (Button) findViewById(R.id.LendBtn);
            midUploadBuku = (ImageButton) findViewById(R.id.UploadBook);
            YourUriBook = (TextView) findViewById(R.id.TextUrlBook);

            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();

            midUploadBuku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChooseImage();
                }
            });

        }

        public void ChooseImage(){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Selected);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
            super.onActivityResult(requestCode, resultCode,imageReturnedIntent);
            switch (requestCode){
                case Selected:
                    if (resultCode == RESULT_OK){
                        uriBook = imageReturnedIntent.getData();
                        UploadFoto();
                    }
            }
        }

        public void UploadFoto() {



            imageRef = storageRef.child("books").child(uriBook.getLastPathSegment());

            progressDialog = new ProgressDialog(this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Mengunggah...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            uploadTask = imageRef.putFile(uriBook);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    progressDialog.incrementProgressBy((int) progress);
                }
            });

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String urlbook = downloadUrl.toString();
                    progressDialog.dismiss();

                    YourUriBook.setText(urlbook);

                }
            });
        }

        public void lend(View v) {
            String Judul = midJudulLend.getText().toString();
            String Penulis = midPenulisLend.getText().toString();
            String Penerbit  = midPenerbitLend.getText().toString();
            String Tipe = midTipeLend.getText().toString();
            String KotaLender = midKotaLender.getText().toString();
            String Email = midEmailLender.getText().toString();
            String Harga = midHargaLend.getText().toString();
            String UrlBook = YourUriBook.getText().toString();

            mAuth = FirebaseAuth.getInstance();

            if (TextUtils.isEmpty(Judul))
            {
                Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(Penulis))
            {
                Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(Penerbit))
            {
                Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(Tipe))
            {
                Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
                return;
            }

            else if (TextUtils.isEmpty(KotaLender))
            {
                Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(Email))
            {
                Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(Harga))
            {
                Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(UrlBook))
            {
                Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
                return;
            }


            else {

                //mauth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                  //  @Override
                    //public void onComplete(@NonNull Task<AuthResult> task) {
                      //  Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        //if (task.isSuccessful()) {

                            //Simpan User di Database
                            Map<String, Object> rakbukulend = new HashMap<>();
                            rakbukulend.put(KEY_JUDUL, Judul);
                            rakbukulend.put(KEY_EMAIL, Email);
                            rakbukulend.put(KEY_PENULIS, Penulis);
                            rakbukulend.put(KEY_PENERBIT, Penerbit);
                            rakbukulend.put(KEY_KOTALENDER, KotaLender);
                            rakbukulend.put(KEY_TIPE, Tipe);
                            rakbukulend.put(KEY_HARGA, Harga);
                            rakbukulend.put(KEY_URLBOOK, UrlBook);

                            db.collection("Book").document().set(rakbukulend)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Lend.this, "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Lend.this, "Coba Lagi!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }
                                    });

                            //Masuk ke Account
                            Intent SignUpintent = new Intent(Lend.this, Account.class);
                            startActivity(SignUpintent);
                            finish();

                        }
                    }

        }


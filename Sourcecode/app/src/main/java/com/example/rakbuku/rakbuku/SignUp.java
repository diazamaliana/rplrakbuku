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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    //UI
    private EditText midNamaPanjang, midEmail, midLokasi, midNoId, midPass;
    private Button midBuSignUp;
    private ImageButton midUpload;

    //Auth
    private FirebaseAuth mauth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    //Cloud Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "FiturSignUp";

    private static final String KEY_NAMA = "Nama";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_LOKASI = "Lokasi";
    private static final String KEY_NOID = "NoID";
    private static final String KEY_PASS = "Pass";
    private static final String KEY_URL = "UrlImage";

    //Storage
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    private static final int Selected = 100;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    Uri uriImage;
    TextView YourUriImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        midNamaPanjang = (EditText) findViewById(R.id.idNama);
        midEmail = (EditText) findViewById(R.id.idEmailSignUp);
        midLokasi = (EditText) findViewById(R.id.idLokasiAkun);
        midNoId = (EditText) findViewById(R.id.idNoID);
        midPass = (EditText) findViewById(R.id.idPassSignUp);

        midBuSignUp = (Button) findViewById(R.id.idSignUp);
        midUpload = (ImageButton) findViewById(R.id.idUpload);
        YourUriImage = (TextView) findViewById(R.id.myUri);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        midUpload.setOnClickListener(new View.OnClickListener() {
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
                    uriImage = imageReturnedIntent.getData();
                    UploadFoto();
                }
        }
    }

    public void UploadFoto() {



        imageRef = storageRef.child("users").child(uriImage.getLastPathSegment());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Mengunggah...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        uploadTask = imageRef.putFile(uriImage);

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
                String urlIMAGE = downloadUrl.toString();
                progressDialog.dismiss();

               YourUriImage.setText(urlIMAGE);

            }
        });
    }

    public void signup(View v) {
        String Nama = midNamaPanjang.getText().toString();
        String Email = midEmail.getText().toString();
        String Lokasi  = midLokasi.getText().toString();
        String NoID = midNoId.getText().toString();
        String Pass = midPass.getText().toString();
        String UriImage = YourUriImage.getText().toString();

        mauth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(Nama))
        {
            Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Lokasi))
        {
            Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(NoID))
        {
            Toast.makeText(getApplicationContext(), "Data belum terisi dengan lengkap", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(Pass))
        {
            Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(UriImage))
        {
            Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (Pass.length() < 6)
        {
            Toast.makeText(getApplicationContext(), "Password terlalu pendek, Masukkan minimum 6 Karakter!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            mauth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignUp.this, "Selamat anda sudah terdaftar!" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                    if (task.isSuccessful()) {

                        String Nama = midNamaPanjang.getText().toString();
                        String Email = midEmail.getText().toString();
                        String Lokasi = midLokasi.getText().toString();
                        String NoID = midNoId.getText().toString();
                        String Pass = midPass.getText().toString();
                        String UrlImage = YourUriImage.getText().toString();

                        //Simpan User di Database
                        Map<String, Object> rakbuku = new HashMap<>();
                        rakbuku.put(KEY_NAMA, Nama);
                        rakbuku.put(KEY_EMAIL, Email);
                        rakbuku.put(KEY_LOKASI, Lokasi);
                        rakbuku.put(KEY_NOID, NoID);
                        rakbuku.put(KEY_PASS, Pass);
                        rakbuku.put(KEY_URL, UrlImage);

                        db.collection("User").document().set(rakbuku)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUp.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUp.this, "Coba Lagi!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, e.toString());
                                    }
                                });

                        //Masuk ke Account
                        Intent SignUpintent = new Intent(SignUp.this, Account.class);
                        startActivity(SignUpintent);
                        finish();

                    } else {
                        Toast.makeText(SignUp.this, "Sign Up gagal, silahkan coba lagi." + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

package com.example.rakbuku.rakbuku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText midEmail;
    private EditText midPass;
    private TextView midDaftar;

    private Button midBuSignIn;

    private FirebaseAuth mauth;

    private  FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        midEmail = (EditText) findViewById(R.id.idEmailSignUp);
        midPass = (EditText) findViewById(R.id.idPass);
        midBuSignIn = (Button) findViewById(R.id.idBuSignIn);
        midDaftar = (TextView) findViewById(R.id.idDaftar);


    }

        //Button Sign Up
        public void daftar(View v)
        {
            Intent registerIntent = new Intent(SignIn.this, SignUp.class);
            SignIn.this.startActivity(registerIntent);

        }
        //Button Sign in
        public void signin (View v){

            mauth = FirebaseAuth.getInstance();
            String Email = midEmail.getText().toString();
            final String Pass = midPass.getText().toString();

            if (TextUtils.isEmpty(Email)) {
                Toast.makeText(getApplicationContext(), "Masukkan alamat email!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Pass)) {
                Toast.makeText(getApplicationContext(), "Masukkan password!", Toast.LENGTH_SHORT).show();
                return;
            } else

            {

                mauth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignIn.this, "Selamat Datang!", Toast.LENGTH_LONG).show();
                            Intent SignInintent = new Intent(SignIn.this, Account.class);
                            startActivity(SignInintent);
                            finish();
                        }
                        else {
                            Toast.makeText(SignIn.this, "Anda Belum Terdaftar!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }


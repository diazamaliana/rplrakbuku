package com.example.rakbuku.rakbuku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class BorrowLetterAgree extends AppCompatActivity {

    private ImageButton midBuLend, midBuProfil;
    private Button midBatalkan, midSetuju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_letter_agree);

        midBatalkan = (Button) findViewById(R.id.idBatalkan);
        midSetuju = (Button) findViewById(R.id.idSetuju);
        midBuLend =(ImageButton) findViewById(R.id.idBuLend);
        midBuProfil = (ImageButton) findViewById(R.id.idBuProfil);

        midBuProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowLetterAgree.this, Account.class);
                startActivity(intent);
                finish();
            }
        });

        midBuLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowLetterAgree.this, Lend.class);
                startActivity(intent);
                finish();
            }
        });

        midSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Permintaan Dikirim", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BorrowLetterAgree.this, Account.class);
                startActivity(intent);
                finish();
            }
        });

        midBatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Permintaan Dibatalkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BorrowLetterAgree.this, Account.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

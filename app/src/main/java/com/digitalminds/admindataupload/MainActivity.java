package com.digitalminds.admindataupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addcat = findViewById(R.id.addCategory);
        Button addBook = findViewById(R.id.addBook);

        addcat.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(i);
        });

        addBook.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, BookActivity.class);
            startActivity(i);
        });
    }
}
package com.example.promotingindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class newpost extends AppCompatActivity {

    Toolbar newposttoolbaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        newposttoolbaar=findViewById(R.id.newposttoolbar);
        setSupportActionBar(newposttoolbaar);
        getSupportActionBar().setTitle("NEW  POST");

    }
}

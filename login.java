package com.example.promotingindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Button submit,register;
    EditText enteremail,enterpassword;
    ProgressBar pg;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit=findViewById(R.id.submit);
        register=findViewById(R.id.register);
        enteremail=findViewById(R.id.enteremail);
        enterpassword=findViewById(R.id.enterpassword);
        pg=findViewById(R.id.pg);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=enteremail.getText().toString();
                String password =enterpassword.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    pg.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                sendtomain();
                            }
                            else
                            {
                                String error= task.getException().getMessage();
                                Toast.makeText(getApplicationContext(),"error :"+error,Toast.LENGTH_SHORT).show();
                            }
                            pg.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),register.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            sendtomain();
        }
    }

    private void sendtomain() {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}


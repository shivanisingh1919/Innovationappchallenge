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

public class register extends AppCompatActivity {
    EditText regusername,regpassword,confpassword;
    Button register,alreadyhaveanaccount;
    ProgressBar regprogress;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regusername=findViewById(R.id.regusername);
        regpassword=findViewById(R.id.regpassword);
        register=findViewById(R.id.register);
        alreadyhaveanaccount=findViewById(R.id.alreadyhaveanaccount);
        confpassword=findViewById(R.id.confpassword);
        regprogress=findViewById(R.id.regprogress);
        fauth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=regusername.getText().toString();
                String password =regpassword.getText().toString();
                String confirmpassword= confpassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(confirmpassword))
                {
                    if(password.equals(confirmpassword))
                    {
                        regprogress.setVisibility(View.VISIBLE);
                       fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful())
                               {
                                   Intent i = new Intent(getApplicationContext(),setupactivity.class);
                                   startActivity(i);
                                   finish();

                               }
                               else
                               {

                                   String error= task.getException().getMessage();
                                   Toast.makeText(getApplicationContext(),"error :"+error,Toast.LENGTH_SHORT).show();
                               }


                           }
                       });

                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password not matches please enter again",Toast.LENGTH_SHORT).show();
                    }



                }

                else
                {
                    Toast.makeText(getApplicationContext()," please enter all details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fauth.getCurrentUser();
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

package com.example.promotingindia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class setupactivity extends AppCompatActivity {


    private Toolbar setupactivitytoolbaar;
    ProgressBar setupprogress;
    FirebaseAuth mauth;
    FirebaseFirestore firebaseFirestore;

    EditText username;
    CircleImageView userimage;
    StorageReference reference;
    Button saveaccountsettings;
    static int reqcode=1;
    static  int REQUESTCODE=1;
    Uri pickedimageurl;
    Task<Uri> downloaduri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setupactivity);
        setupactivitytoolbaar=findViewById(R.id.setupactivitytoolbaar);
        setSupportActionBar(setupactivitytoolbaar);
        getSupportActionBar().setTitle("Account  Settings");
        userimage=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        saveaccountsettings=findViewById(R.id.saveaccountsettings);
        mauth= FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        reference=FirebaseStorage.getInstance().getReference();
        setupprogress=findViewById(R.id.setupprogress);



        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= 22)
                {
                    checkandrequestpermission();
                }
                else
                {
                    opengallery();
                }

            }
        });

        saveaccountsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pickedimageurl == null)
                {
                    Toast.makeText(getApplicationContext(),"please browse image first",Toast.LENGTH_SHORT).show();
                }
                else {
                    String name_of_user = username.getText().toString();
                    if(!TextUtils.isEmpty(name_of_user)) {
                        setupprogress.setVisibility(View.VISIBLE);

                        String user_id = mauth.getCurrentUser().getUid();
                        StorageReference image_path = reference.child("profile_image").child(user_id+".jpg");
                        image_path.putFile(pickedimageurl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                  downloaduri =task.getResult().getStorage().getDownloadUrl();
                                   // Toast.makeText(getApplicationContext(),"The image is uploaded:",Toast.LENGTH_SHORT).show();

                                }
                                    else
                                {
                                    String error= task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(),"error :"+error,Toast.LENGTH_SHORT).show();

                                }


                                setupprogress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }

            }
        });
    }


    private String findMyImage(Uri pick){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(pick,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }



    private void checkandrequestpermission() {
        if (ContextCompat.checkSelfPermission(setupactivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(setupactivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(getApplicationContext(),"PLEASE ACCEPT THE REQUIRED PERMISSION ",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(setupactivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},reqcode);

            }
        }

        else
        {
            opengallery();
        }
    }

    private void opengallery() {
        //to do opengallery intent and wait for user to pick image
        Intent galleryintent= new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data!=null)
        {
            pickedimageurl = data.getData();
            userimage.setImageURI(pickedimageurl);
        }
    }
}






















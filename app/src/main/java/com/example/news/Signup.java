package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private EditText name, email, contact, password;
    private Button Register;
    private ProgressDialog loadingBar;

    private TextView signin;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.NameText);
        email = (EditText) findViewById(R.id.EmailText);
        contact = (EditText) findViewById(R.id.PhoneText);
        password = (EditText) findViewById(R.id.PasswordText);
        Register = (Button) findViewById(R.id.signinButton);
       //currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        signin = (TextView) findViewById(R.id.Signin) ;
       //   UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("profileImages");

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String useremail = email.getText().toString();
                String usercontact = contact.getText().toString();
                String userpassword = password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(useremail) || TextUtils.isEmpty(usercontact) || TextUtils.isEmpty(userpassword)) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields....", Toast.LENGTH_SHORT).show();

                } else {
           /* loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);*/

                    HashMap<String, Object> userMap = new HashMap();
                    userMap.put("username", username);
                    userMap.put("email", useremail);
                    userMap.put("contact", usercontact);
                    userMap.put("password", userpassword);

                    UserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                SendUserToLoginActivity();
                                Toast.makeText(Signup.this, "Your Account is created successfully", Toast.LENGTH_LONG).show();
                               // loadingBar.dismiss();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(Signup.this, "Error occured " + message, Toast.LENGTH_SHORT).show();
                               // loadingBar.dismiss();
                            }

                        }
                    });

                    mAuth.createUserWithEmailAndPassword(useremail,userpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Your Account is created successfully", Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        String message = task.getException().getMessage();
                                        Toast.makeText(Signup.this, "Error occured " + message, Toast.LENGTH_SHORT).show();

                                     }
                                }
                            });
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(Signup.this,LoginActivity.class);
                startActivity(mainIntent);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // if (currentUser != null) {
         //  SendUserToMainActivity();
      // }
    }




    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(Signup.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);

    }
}




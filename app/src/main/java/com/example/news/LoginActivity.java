package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private TextView signup,register;
    private EditText email, password;
    private Button login;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private ImageView Google,Facebook;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signup = (TextView) findViewById(R.id.Signup_button);
        email = (EditText) findViewById(R.id.EmailText);
        password = (EditText) findViewById(R.id.PasswordText);
        login = (Button) findViewById(R.id.Loginbutton);
        Google = (ImageView) findViewById(R.id.Google);
        Facebook = (ImageView) findViewById(R.id.Facebook);
        register=(TextView) findViewById(R.id.RegisterButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signupIntent = new Intent(LoginActivity.this, Signup.class);
                signupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(signupIntent);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();

                String userpassword = password.getText().toString();

                if (TextUtils.isEmpty(useremail)) {
                    Toast.makeText(getApplicationContext(), "Please write your email...", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(userpassword)) {
                    Toast.makeText(getApplicationContext(), "Please write your password...", Toast.LENGTH_SHORT).show();
                } else {
                   /* loadingBar.setTitle("Login");
                    loadingBar.setMessage("Please wait ,while we are allowing you to login into your account");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);*/

                    mAuth.signInWithEmailAndPassword(useremail, userpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SendUserToMainActivity();
                                        Toast.makeText(LoginActivity.this, "You are Logged In successfully", Toast.LENGTH_SHORT).show();
                                        //loadingBar.dismiss();
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(LoginActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                                        // loadingBar.dismiss();
                                    }
                                }


                            });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(LoginActivity.this,Signup.class);
                startActivity(signup);
            }
        });
    }













        @Override
        protected void onStart() {
            super.onStart();

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                SendUserToMainActivity();

            }
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            updateUI(account);
        }

    private void updateUI(GoogleSignInAccount account) {
    }




    private void AllowUserToLogin() {

        }


        private void SendUserToMainActivity() {
            Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
            //it will not allowed user to go back
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
        }

}

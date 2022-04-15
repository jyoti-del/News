package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;

    private DrawerLayout drawerLayout;
    public DatabaseReference UserRef;
    private Button logout;
    private Menu menu;

    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cardView =findViewById(R.id.CardView1);
        cardView.setRadius(5.0f);
        cardView.setCardElevation(11.0f);
        cardView.setUseCompatPadding(true);


        mAuth = FirebaseAuth.getInstance();


        //Child name is user under which we can store user names
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        logout=(Button) findViewById(R.id.buttonlogout);
    }

        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
               //
            } else {

                CheckUserExistence();
            }

        }

        private void CheckUserExistence() {
            //getUnique id of a particular user
            final String current_user_id = mAuth.getCurrentUser().getUid();
            UserRef.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //user is  authenticated  but is not present in firebase realtime database
                    //so we have to send the user in firebase database
                    //we have to send it in setup activity to upload profile pic and name etc..
                    if (!snapshot.hasChild(current_user_id)) {
                        //   SendUserToMainActivity();
                        //  Toast.makeText(getApplicationContext(), "Working ...", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    SendUserToLoginActivity();
                }
            });

        }

            private void SendUserToLoginActivity () {
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                //it will not allowed user to go back
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);


            }

        }


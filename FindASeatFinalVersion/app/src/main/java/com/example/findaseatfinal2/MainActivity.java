//base imports
package com.example.findaseatfinal2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

//other imports under here

import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    public static FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
        //setContentView(R.layout.activity_main); // Set the layout resource
        View parentLayout = findViewById(R.id.parentView);
        LoggedIn currUser = LoggedIn.getInstance();
        currUser = new LoggedIn(false, "");

        Intent intent = new Intent(MainActivity.this,Map.class);
        MainActivity.this.startActivity(intent);


//        Button button2 = findViewById(R.id.button2);
//        button2.setTag(new XMLActivityPair("registration_page", RegistrationActivity.class));
//
//        Button button3 = findViewById(R.id.button3);
//        //button3.setTag(new XMLActivityPair("profile_page", ProfileActivity.class));
//
//        Button button4 = findViewById(R.id.button4);
//        button4.setTag(new XMLActivityPair("map", Map.class));
//
//        Button button5 = findViewById(R.id.button5);
//        button5.setTag(new XMLActivityPair("reservation", ReservationActivity.class));
//
//        Button button6 = findViewById(R.id.button6);
//        button6.setTag(new XMLActivityPair("login_page", LoginActivity.class));
//
//        Button button7 = findViewById(R.id.button7);
//        button7.setTag(new XMLActivityPair("registration_page", RegistrationActivity.class));
//
//        Button button8 = findViewById(R.id.button8);
//        button8.setTag(new XMLActivityPair("login_page", LoginActivity.class));
//
//        AppCompatActivity this_class = this;
//        // Set a single OnClickListener for all buttons
//        View.OnClickListener buttonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XMLActivityPair xmlActivityPair = (XMLActivityPair) v.getTag();
//                xmlActivityPair.defaultSwitchToPage(this_class);
//
//            }
//        };
//        // Apply the same click listener to all buttons
//        //button1.setOnClickListener(buttonClickListener);
//        button2.setOnClickListener(buttonClickListener);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                MainActivity.this.startActivity(intent);
//            }
//        });
//        button4.setOnClickListener(buttonClickListener);
//        button5.setOnClickListener(buttonClickListener);
//        button6.setOnClickListener(buttonClickListener);
//        button7.setOnClickListener(buttonClickListener);
//        button8.setOnClickListener(buttonClickListener);

    }

}
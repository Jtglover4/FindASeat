package com.example.findaseatfinal2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private boolean passError = false;
    private boolean emailError = false;
    private EditText emailText;
    private EditText passwordText;

    private Button submitter;
    private Button goBack;
    private Button makeAcc;

    private TextView error;

    LoggedIn currUser = LoggedIn.getInstance();

    public boolean isPasswordValid(String password){
        return checkPasswordGood(password);
    }

    public boolean isEmailValid(String email){
        return email.endsWith("@usc.edu");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        emailText = findViewById(R.id.editTextText2);
        passwordText = findViewById(R.id.editTextText3);

        submitter = findViewById(R.id.login_submit_tv);
        goBack = findViewById(R.id.login_goback_tv);
        makeAcc = findViewById(R.id.button10);

        error = findViewById(R.id.login_error_tv);
        error.setTextColor(Color.WHITE);

        AppCompatActivity this_class = this;

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this_class,Map.class);
                this_class.startActivity(intent);
            }
        });

        makeAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this_class,RegistrationActivity.class);
                this_class.startActivity(intent);
            }
        });

        submitter.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){

               String grabEmail = emailText.getText().toString();
               String grabPassword = passwordText.getText().toString();

               if(!isPasswordValid(grabPassword)){
                   passError = true;
               }else{
                   passError = false;
               }
               if(!isEmailValid(grabEmail)){
                   emailError = true;
               }else{
                   emailError = false;
               }

               if(passError && !emailError){
                   error.setText("Invalid Password");
                   error.setTextColor(Color.RED);
               }else if(!passError && emailError){
                   error.setText("Invalid Email");
                   error.setTextColor(Color.RED);
               }else if(passError && emailError){
                   error.setText("Both Username and Password are Invalid");
                   error.setTextColor(Color.RED);
               }else{
                   error.setTextColor(Color.WHITE);
                   System.out.println(grabEmail + grabPassword);
                   validateUserAndPass(grabEmail, grabPassword);
               }

           }
        });
    }

    public void validatePass(User u, String password){
        if (u.getPassword().equals(password)){
            error.setText("Signed In");
            error.setTextColor(Color.GREEN);
            currUser.setLoggedIn(true);
            currUser.setUserID(u.getStudentID());
            Intent intent = new Intent(LoginActivity.this,Map.class);
            LoginActivity.this.startActivity(intent);


        } else {
            error.setText("Username and Password are not correct");
            error.setTextColor(Color.RED);
        }
    }

    public void validateUserAndPass(String email, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the "users" collection
        Query query = db.collection("users").whereEqualTo("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Email exists in the collection
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        User user = document.toObject(User.class);
                        System.out.println(user);
                        validatePass(user, password);
                    }
                } else {
                    // Email doesn't exist in the collection
                    error.setText("Cannot find user");
                    error.setTextColor(Color.RED);
                }
            } else {
                // Handle database error
                System.out.println("DATABASE ERROR");
            }
        });
    }

    boolean checkPasswordGood(String s){

        if(s.length() < 8){
            return false;
        }

        String regex = "[^a-zA-Z0-9]";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }

    }




}

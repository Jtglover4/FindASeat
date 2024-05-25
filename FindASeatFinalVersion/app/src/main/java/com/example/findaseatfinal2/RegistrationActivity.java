package com.example.findaseatfinal2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;
    ImageView imgGallery;

    Spinner spinner;
    String[] items = {"Select:", "Undergraduate", "Graduate", "Faculty", "Staff"};

    private EditText emailInput;
    private EditText passwordInput;
    private EditText nameInput;
    private EditText idInput;

    private Button submission;
    private Button goBack;

    private TextView errorEmail;
    private TextView errorPassword;
    private TextView errorName;
    private TextView errorID;
    private TextView errorAffiliation;
    private TextView errorImage;

    LoggedIn currUser = LoggedIn.getInstance();


    FirebaseFirestore db;

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

    public boolean isPasswordValid(String password) {
        return checkPasswordGood(password);
    }

    public boolean isIDValid(String id) {
        return id.length() == 10 && id.matches("\\d+");
    }

    public boolean isNameValid(String name) {
        return name.length() != 0;
    }

    public boolean isEmailValid(String email) {
        return email.endsWith("@usc.edu");
    }

    public boolean isAffiliationSelected(String selectedItem) {
        return !"Select:".equals(selectedItem);
    }

    public boolean isImageUploaded(Drawable drawable) {
        return drawable != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.registration_page);

        imgGallery = findViewById(R.id.imageView);
        Button buttonGallery = findViewById(R.id.button);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        emailInput = findViewById(R.id.reg_email_tv);
        passwordInput = findViewById(R.id.reg_password_tv);
        nameInput = findViewById(R.id.reg_name_tv);
        idInput = findViewById(R.id.reg_id_tv);

        submission = findViewById(R.id.reg_submit_tv);
        goBack = findViewById(R.id.reg_goback_tv);

        errorEmail = findViewById(R.id.reg_email_error_tv);
        errorPassword = findViewById(R.id.reg_password_error_tv);
        errorName = findViewById(R.id.reg_name_error_tv);
        errorID = findViewById(R.id.reg_id_error_tv);
        errorAffiliation = findViewById(R.id.reg_affiliation_error_tv);
        errorImage = findViewById(R.id.textView16);

        errorEmail.setTextColor(Color.WHITE);
        errorPassword.setTextColor(Color.WHITE);
        errorName.setTextColor(Color.WHITE);
        errorID.setTextColor(Color.WHITE);
        errorAffiliation.setTextColor(Color.WHITE);
        errorImage.setTextColor(Color.WHITE);

        AppCompatActivity this_class = this;

        buttonGallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);

            }
        });

        goBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this_class,Map.class);
                this_class.startActivity(intent);
            }
        });

        submission.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String grabEmail = emailInput.getText().toString();
                String grabPassword = passwordInput.getText().toString();
                String grabName = nameInput.getText().toString();
                String grabID = idInput.getText().toString();

                boolean isError = false;
                if (isPasswordValid(grabPassword)) {
                    errorPassword.setTextColor(Color.WHITE);
                } else {
                    errorPassword.setText("Invalid Password Input");
                    errorPassword.setTextColor(Color.RED);
                    isError = true;
                }

                if (isIDValid(grabID)) {
                    errorID.setTextColor(Color.WHITE);
                } else {
                    errorID.setText("Invalid ID");
                    errorID.setTextColor(Color.RED);
                    isError = true;
                }

                if (isNameValid(grabName)) {
                    errorName.setTextColor(Color.WHITE);
                } else {
                    errorName.setText("Invalid Input");
                    errorName.setTextColor(Color.RED);
                    isError = true;
                }

                if (isEmailValid(grabEmail)) {
                    errorEmail.setTextColor(Color.WHITE);
                } else {
                    errorEmail.setText("Invalid Email");
                    errorEmail.setTextColor(Color.RED);
                    isError = true;
                }

                if (isAffiliationSelected((String) spinner.getSelectedItem())) {
                    errorAffiliation.setTextColor(Color.WHITE);
                } else {
                    errorAffiliation.setText("Please Select An Affiliation");
                    errorAffiliation.setTextColor(Color.RED);
                    isError = true;
                }

                if (isImageUploaded(imgGallery.getDrawable())) {
                    // Do something if image is uploaded
                } else {
                    errorImage.setTextColor(Color.RED);
                    errorImage.setText("Please Upload an Image");
                    isError = true;
                }

                if(!isError){
                    //Get the file
                    Drawable drawable = imgGallery.getDrawable(); // Replace 'yourDrawable' with your drawable resource
                    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);

                    Context applicationContext = getApplicationContext();
                    File file = new File(applicationContext.getFilesDir(), "userupload.png"); // Replace "image.png" with your desired file name
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Upload file to storage, and get URL
                    // Assuming you have a reference to Firebase Storage
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

// Create a reference to the image file in Firebase Storage
                    StorageReference imageRef = storageRef.child("images/"+ grabID+ ".png");

// Create an upload task
                    UploadTask uploadTask = imageRef.putFile(Uri.fromFile(file));


                    //Made a new uesr, add url, push that user to firebase


                    User new_user = new User(
                            grabID,
                            grabName,
                            grabEmail,
                            grabPassword,
                            (String) spinner.getSelectedItem(),
                            new ArrayList<>());
                    validateUserAndID(grabEmail, grabID,new_user);

                    System.out.println("USER ADDED");
//                    currUser.setLoggedIn(true);
//                    currUser.setUserID(grabID);
//                    Intent intent = new Intent(this_class,Map.class);
//                    this_class.startActivity(intent);
                    //validateUserAndID(grabEmail, grabID);
                }


            }
        });

    }
    public void validateID(User u, String ID){
        // Reference to the document
        DocumentReference docRef = db.collection("users").document(ID);

        // Get the document
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("ID already ed");
                        errorID.setText("ID already exists");
                        errorID.setTextColor(Color.RED);
                        // Do something with the document data
                        // For example, retrieve data: Object data = document.getData();
                    } else {
                        addMyUserWithCustomId(ID, u);
                        errorImage.setText("CREATED USER");
                        errorImage.setTextColor(Color.GREEN);
                        currUser.setLoggedIn(true);
                        currUser.setUserID(u.getStudentID());
                        Intent intent = new Intent(RegistrationActivity.this,Map.class);
                        RegistrationActivity.this.startActivity(intent);
                    }
                } else {
                    System.out.println("DATA BASE ERROR");
                }
            }
        });
    }

    public void validateUserAndID(String email, String ID, User u){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the "users" collection
        Query query = db.collection("users").whereEqualTo("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Email exists in the collection
                    errorEmail.setText("User already exists");
                    errorEmail.setTextColor(Color.RED);
                } else {
                    // Email doesn't exist in the collection
                    System.out.println();
//                    for (QueryDocumentSnapshot document : querySnapshot) {
//                        User user = document.toObject(User.class);
//                        System.out.println(user);
//                        validateID(user,ID);
//                    }
                    validateID(u, ID);
                }
            } else {
                // Handle database error
                System.out.println("DATABASE ERROR");
            }
        });
    }

    public void addMyUserWithCustomId(String customDocId, User user) {
        db.collection("users").document(customDocId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // Document was successfully added with your custom ID
                    System.out.println("NEW USER MADE");
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    System.out.println("ERROR OCCURED");
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == GALLERY_REQ_CODE){
                imgGallery.setImageURI(data.getData());
            }

        }
    }

}

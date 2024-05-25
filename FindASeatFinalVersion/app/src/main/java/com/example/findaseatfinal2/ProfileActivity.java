//base imports
package com.example.findaseatfinal2;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import android.widget.PopupWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

//other imports under here

//public class ProfileActivity extends ProfileSubActivity{
public class ProfileActivity extends AppCompatActivity {

    String userEmail = "aaa";
    AppCompatActivity this_class = this;

    User currUser = null;

    LoggedIn currUserL = LoggedIn.getInstance();

    List<Reservation> userReservations;

    Reservation currUserRes = null;

    Button modify;
    Button cancel;
    Button logout;
    Button modifyYes;
    Button modifyNo;
    TextView popupTitle;

    boolean timeHasPast;


    public void queryUserByEmail(String userEmail) throws InterruptedException, ExecutionException {


        //Task<DataSnapshot> t =  fd.getReference().child("users").get();

        //System.out.println(t.isComplete());


    }
    public static int addMinutesToMilitaryTime(int militaryTime, int minutesToAdd) {
        int hours = militaryTime / 100;
        int minutes = militaryTime % 100;

        minutes += minutesToAdd;


        if (minutes > 59) {
            hours += minutes / 60;
            minutes %= 60;
        }

        hours %= 24;

        return (hours * 100) + minutes;
    }
    public static boolean validateTime(Date date, int startTime, String resdate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extract the hour of the day
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;

        String m = resdate.substring(0, 2);
        String d = resdate.substring(3, 5);

        int startIndex = startTime/2;

        if(Integer.parseInt(m) < month){
            return false;

        }else if((Integer.parseInt(d) < dayOfMonth) && (Integer.parseInt(m) == month)){

            return false;
        }else if((hourOfDay > startIndex) && (Integer.parseInt(d) == dayOfMonth) && (Integer.parseInt(m) == month)){
            return false;
        }
        return true;
    }
    private void drawPage(){
        TextView currentReservation = findViewById(R.id.textView14);
        TextView pastReservations = findViewById(R.id.textView17);
        if(userReservations.size() == 0){
            currentReservation.setText("No reservations");
            pastReservations.setText("No reservations");
            ReservationViewModelStaticParams.canModorCancel = false;
            return;
        }
        for(Reservation r : userReservations){
            System.out.println("cancelled" + r.getCancelled());
            System.out.println("indoor" + r.isIndoor());
        }
        //Get most recent res
        int maxID = -1;
        Reservation curRes = null;
        for(Reservation r : userReservations){
            if(r.getResID() > maxID){
                maxID = r.getResID();
                curRes = r;
            }
        }


        if(curRes != null){

            Date date = new Date();

            if(!validateTime(date, curRes.getStartTime(), curRes.getDate())){
                timeHasPast = true;
            }else{
                timeHasPast = false;
            }
        }

        if (curRes == null || curRes.getCancelled() || timeHasPast) {
            currUserRes = null;
            currentReservation.setText("");
            ReservationViewModelStaticParams.canModorCancel = false;
            System.out.println("Past");
            String output = "";
            for (Reservation r : userReservations){
                output += r.getResID()+ r.toString() + (r.getCancelled() ? " - cancelled" : "") + "\n";
            }
            pastReservations.setText(output);
        } else {
            currUserRes = curRes;
            ReservationViewModelStaticParams.canModorCancel = true;
            System.out.println("Current");
            currentReservation.setText(curRes.toString());
            String output = "";
            for (Reservation r : userReservations){
                if((r != curRes) || (timeHasPast)){
                    output += r.getResID()+ r.toString() + (r.getCancelled() ? " - cancelled" : "") + "\n";
                }
            }
            pastReservations.setText(output);
        }

        modify = findViewById(R.id.button11);
        cancel = findViewById(R.id.button12);
        logout = findViewById(R.id.button14);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currUserL.setLoggedIn(false);
                currUserL.setUserID(null);
                ReservationViewModelStaticParams.canModorCancel = false;
                finishAffinity();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        if(ReservationViewModelStaticParams.canModorCancel) {
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View customView = getLayoutInflater().inflate(R.layout.modify_layout, null);
                    PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);

                    popupTitle = customView.findViewById(R.id.popup_title);

                    popupTitle.setText("Are you sure you want to do this? Your previous reservation will be cancelled and you will have to make a new one.");


                    modifyYes = customView.findViewById(R.id.popup_option1);
                    modifyNo = customView.findViewById(R.id.button13);

                    modifyNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                    modifyYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            modifyReservation(currUserRes.getResID());
                        }
                    });

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View customView = getLayoutInflater().inflate(R.layout.modify_layout, null);
                    PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);

                    popupTitle = customView.findViewById(R.id.popup_title);

                    popupTitle.setText("Are you sure you want to do this? Your previous reservation will be cancelled.");

                    modifyYes = customView.findViewById(R.id.popup_option1);
                    modifyNo = customView.findViewById(R.id.button13);

                    modifyNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                    modifyYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cancelReservation(currUserRes.getResID());
                        }
                    });

                }
            });
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        //FirebaseApp.initializeApp(this);
        System.out.println("GETTING UESR");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(currUserL.isLoggedIn() == false){
            Intent intent = new Intent(this_class,LoginActivity.class);
            this_class.startActivity(intent);
            return;
        }
        ProfileActivityViewModel viewModel = new ViewModelProvider(this).get(ProfileActivityViewModel.class);
        viewModel.getLiveReservations().observe(this, data -> {
            System.out.println("B RES UPDATE" + userReservations == null);
            if (data != null) {
                userReservations = data;
                drawPage();
            } else {
                // Show loading indicator or handle the case when data is null
            }
        });
        db.collection("users").document(currUserL.getUserID())// Assumes there's an 'email' field in the document
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(!(document != null && document.exists())){
                                System.out.println();
                            }
                            User user = document.toObject(User.class);
                            currUser = user;

                            // Additional initialization and logic for your activity
                            TextView leftTab = findViewById(R.id.l_tab);
                            TextView rightTab = findViewById(R.id.r_tab);

                            ImageView image = findViewById(R.id.imageView2);

                            // Add click listeners to the TextViews
                            String URL = "https://firebasestorage.googleapis.com/v0/b/findaseatfinal2.appspot.com/o/images%2F" + currUser.getStudentID() + ".png?alt=media";
                            System.out.println("URL=> " + URL);
                            Picasso.get()
                                    .load(URL)
                                    .into(image);

                            TextView affiliationTextView = findViewById(R.id.textView9);
                            TextView nameTextView = findViewById(R.id.textView12);
                            TextView currentReservationLabel = findViewById(R.id.textView13);
                            TextView currentReservationList = findViewById(R.id.textView14);
                            TextView pastReservationsLabel = findViewById(R.id.textView15);
                            TextView userIDTextView = findViewById(R.id.textView18);
                            TextView reservationDetails = findViewById(R.id.textView17);

                            affiliationTextView.setText("Affiliation: " + currUser.getAffiliation());
                            nameTextView.setText("Name: " + currUser.getName());
                            userIDTextView.setText("ID: " + currUser.getStudentID());

                            leftTab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Handle the click on the left tab
                                    // For example, switch to a different tab or perform some action
                                    // Here, we'll show a toast message as an example
                                    System.out.println("LEFT TAB CLICKED");
                                    Intent intent = new Intent(this_class, Map.class);
                                    this_class.startActivity(intent);
                                }
                            });

                            rightTab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Handle the click on the right tab
                                    // For example, switch to a different tab or perform some action
                                    // Here, we'll show a toast message as an example
                                    System.out.println("RIGHT TAB CLICKED");
                                    Intent intent = new Intent(this_class, ProfileActivity.class);
                                    this_class.startActivity(intent);
                                }
                            });
                        } else {
                            Log.w("FB", "Error getting documents.", task.getException());

                        }
                    }

                });
        System.out.println("DONE GETTING");


    }
    public void cancelReservation(int resID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Query for the reservation document by its ID
        Query query = db.collection("reservations").whereEqualTo("resID", resID);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the document reference
                    String documentId = document.getId();

                    // Update the isCancelled field to true
                    db.collection("reservations").document(documentId).update("cancelled", true);

                    Query query2 = db.collection("buildings").whereEqualTo("buildingID", currUserRes.getBuildingID());

                    // Execute the query
                    query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println("Found building");
                                    // Handle the document data as needed
                                    Building building = document.toObject(Building.class);

                                    // Append a new reservation ID
                                    building.getReservationIds().remove(Integer.valueOf(currUserRes.getResID()));


                                    // Update the document with the modified reservationIDs
                                    DocumentReference docRef = db.collection("buildings").document(document.getId());
                                    docRef.update("reservationIds", building.getReservationIds())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> updateTask) {
                                                    if (updateTask.isSuccessful()) {
                                                        System.out.println("Document updated successfully with new reservation ID");
                                                        Intent intent = new Intent(this_class, Map.class);
                                                        this_class.startActivity(intent);
                                                    } else {
                                                        System.out.println("Error updating document"+ updateTask.getException());
                                                    }
                                                }
                                            });

                                }
                            } else {
                                System.out.println("BUILDING DB ERROR");
                            }
                        }
                    });
                }
            } else {
                // Handle query error
            }
        });
    }

    public void modifyReservation(int resID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Query for the reservation document by its ID
        Query query = db.collection("reservations").whereEqualTo("resID", resID);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the document reference
                    String documentId = document.getId();

                    // Update the isCancelled field to true
                    db.collection("reservations").document(documentId).update("cancelled", true);

                    Query query2 = db.collection("buildings").whereEqualTo("buildingID", currUserRes.getBuildingID());

                    // Execute the query
                    query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println("Found building");
                                    // Handle the document data as needed
                                    Building building = document.toObject(Building.class);

                                    // Append a new reservation ID
                                    building.getReservationIds().remove(Integer.valueOf(currUserRes.getResID()));


                                    // Update the document with the modified reservationIDs
                                    DocumentReference docRef = db.collection("buildings").document(document.getId());
                                    docRef.update("reservationIds", building.getReservationIds())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> updateTask) {
                                                    if (updateTask.isSuccessful()) {
                                                        System.out.println("Document updated successfully with new reservation ID");
//                                                        Intent intent = new Intent(this_class, Map.class);
//                                                        this_class.startActivity(intent);
                                                        ReservationViewModelStaticParams.buildingID = currUserRes.getBuildingID();
                                                        Intent intent = new Intent(this_class, ReservationActivity.class);
                                                        this_class.startActivity(intent);
                                                    } else {
                                                        System.out.println("Error updating document"+ updateTask.getException());
                                                    }
                                                }
                                            });

                                }
                            } else {
                                System.out.println("BUILDING DB ERROR");
                            }
                        }
                    });
                }
            } else {
                // Handle query error
            }
        });

    }
}


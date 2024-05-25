package com.example.findaseatfinal2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    int hour;
    int min;

//    View squareItem;
//    TextView square_num;
//    View squarebox;

    Button makeres;

    Building buildingForRes;
    List<Reservation> buildingReservations;

    int availSlotsForDay;

    TextView dateBox;
    TextView nextday;
    TextView prevday;
    Date currentDate;
    String days;
    String newdays;
    Spinner slots;
    String[] items = {"1", "2", "3", "4"};

    int lastAddedID = 0;

    int saveIndex = -1;
    int saveSlots;
    String saveDate;
    Boolean indoor = true;
    Boolean click = true;
    TextView error;
    TextView buildingName;
    TextView buildingDescription;

    List<View> indoorViews = null;
    List<TextView> indoorTextViews = new ArrayList<>();
    List<TextView> outDoorTextViews = new ArrayList<>();

    List<TextView> currTextViews = new ArrayList<>();
    List<View> outDoorViews = null;
    List<View> currViews = null;
    int addedDays = 0;

    //String currUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        ReservationViewModel viewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        // Observe the LiveData
        viewModel.getLiveBuilding().observe(this, data -> {
            System.out.println("B UPDATE" + buildingForRes == null);
            if (data != null) {
                buildingForRes = data;
                if(buildingReservations != null){

                    drawPage();
                }
            } else {
                // Show loading indicator or handle the case when data is null
            }
        });
        viewModel.getLiveReservations().observe(this, data -> {
            System.out.println("B RES UPDATE" + buildingReservations == null);
            if (data != null) {
                buildingReservations = data;
                if(buildingForRes != null){
                    drawPage();
                }
            } else {
                // Show loading indicator or handle the case when data is null
            }
        });
    }

    public void drawPage(){

        LinearLayout container = findViewById(R.id.container);
        System.out.println(buildingForRes);

        makeres = findViewById(R.id.button9);

        currentDate = new Date();

        dateBox = findViewById(R.id.textView19);

        slots = findViewById(R.id.spinner2);

        buildingName = findViewById(R.id.textView20);
        buildingDescription = findViewById(R.id.textView21);

        buildingName.setText(buildingForRes.getName());
        buildingDescription.setText(buildingForRes.getDescription());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        slots.setAdapter(adapter);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormat.format(currentDate);

        days = formattedDate.substring(3, 4);
        newdays = days;

        dateBox.setText(formattedDate);

        nextday = findViewById(R.id.textView24);
        prevday = findViewById(R.id.textView23);

        error = findViewById(R.id.textView28);
        error.setTextColor(Color.WHITE);
        renderTimes(container,true);
        LinearLayout container2 = findViewById(R.id.container2);
        renderTimes(container2, false);
        updateReservationsTaken(formattedDate);

        nextday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(addedDays < 7) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.DAY_OF_YEAR, 1); // Add one day

                    Date newDate = calendar.getTime();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedTomorrowDate = dateFormat.format(newDate);
                    updateReservationsTaken(formattedTomorrowDate);
                    dateBox.setText(formattedTomorrowDate);
                    newdays = formattedTomorrowDate.substring(3, 4);
                    currentDate = newDate;
                    addedDays++;
                }
            }
        });

        prevday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (addedDays > 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.DAY_OF_YEAR, -1); // Add one day

                    Date newDate = calendar.getTime();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedTomorrowDate = dateFormat.format(newDate);
                    updateReservationsTaken(formattedTomorrowDate);
                    dateBox.setText(formattedTomorrowDate);
                    newdays = formattedTomorrowDate.substring(3, 4);
                    currentDate = newDate;
                    addedDays--;
                }
            }
        });

        makeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();


                if((String) slots.getSelectedItem() == "Select:"){
                    error.setText("Please select the number of slots and your start time");
                    error.setTextColor(Color.RED);
                }else if(saveIndex == -1){
                    error.setText("Please select the number of slots and your start time");
                    error.setTextColor(Color.RED);
                }else if(!validTime(date, saveIndex, addedDays)){
                    error.setText("Sorry, this time has past");
                    error.setTextColor(Color.RED);
                }else{
                    error.setTextColor(Color.WHITE);
                    System.out.println(saveIndex + " ");
                    System.out.println(currentDate + " ");
                    System.out.println((String) slots.getSelectedItem() + " ");
                    System.out.println(indoor);
                    addReservationToDB();
                    System.out.println("ADDED RES TO DB");
                    Intent intent = new Intent(ReservationActivity.this, Map.class);
                    ReservationActivity.this.startActivity(intent);
                }
            }
        });
    }

    public void renderTimes(LinearLayout container, boolean isIndoor){

        if(isIndoor){
            indoorViews = new ArrayList<View>();
            currViews = indoorViews;
            currTextViews = indoorTextViews;
        } else {
            outDoorViews = new ArrayList<View>();
            currViews = outDoorViews;
            currTextViews = outDoorTextViews;
        }
        for (int i = 0; i < 48; i++) { // Adjust the number of squares as needed
            View squareItem = LayoutInflater.from(this).inflate(R.layout.square_item, null);
            TextView square_num = squareItem.findViewById(R.id.square_num);
            currTextViews.add(square_num);
            View squarebox = squareItem.findViewById(R.id.box);
            currViews.add(squarebox);
            if (i > 16 && i < 40) {
                squarebox.setBackgroundColor(Color.GREEN);
            }
            if (i % 2 == 0) {
                square_num.setText(i / 2 + ":00 | ");
                squarebox.setId(i);
            } else {
                square_num.setText(i / 2 + ":30 | ");
                squarebox.setId(i);
            }
            squarebox.setTag(2);
            // Set the width and height of each square item
            squareItem.setLayoutParams(new FrameLayout.LayoutParams(200, 100)); // Adjust the size as needed
            container.addView(squareItem);

            if(isIndoor){
                squarebox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int slotsNum = Integer.parseInt(slots.getSelectedItem().toString());

                        if(!validChecker(indoorViews, squarebox, slotsNum)){
                            error.setText("INVALID TIME SLOT");
                            error.setTextColor(Color.RED);
                            return;
                        }
                        error.setTextColor(Color.WHITE);
                        resetSquares(indoorViews);
                        resetSquares(outDoorViews);
                        if (squarebox.getId() == saveIndex) {
                            saveIndex = -1;
                            return;
                        }
                        if (((ColorDrawable) squarebox.getBackground()).getColor() == Color.GREEN) {

                            for (int j = squarebox.getId(); j < squarebox.getId()+slotsNum; j++) {
                                indoorViews.get(j).setBackgroundColor(Color.YELLOW);
                            }
                            System.out.println(squarebox.getId());
                            saveIndex = squarebox.getId();
                            saveDate = dateBox.getText().toString();
                            indoor = isIndoor;
                        }
                    }
                });
            } else {
                squarebox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int slotsNum = Integer.parseInt(slots.getSelectedItem().toString());
                        if(!validChecker(indoorViews, squarebox, slotsNum)) {
                            error.setText("INVALID TIME SLOT");
                            error.setTextColor(Color.RED);
                            return;
                        }
                        error.setTextColor(Color.WHITE);
                        resetSquares(indoorViews);
                        resetSquares(outDoorViews);
                        if (squarebox.getId() == saveIndex) {
                            saveIndex = -1;
                            return;
                        }
                        if (((ColorDrawable) squarebox.getBackground()).getColor() == Color.GREEN) {

                            for (int j = squarebox.getId(); j < squarebox.getId()+slotsNum; j++) {
                                outDoorViews.get(j).setBackgroundColor(Color.YELLOW);
                            }
                            System.out.println(squarebox.getId());
                            saveIndex = squarebox.getId();
                            saveDate = dateBox.getText().toString();
                            indoor = isIndoor;
                        }
                    }
                });
            }

            }
    }

    public boolean validChecker(List<View> tv, View squarebox, int slotsNum){

        for (int j = squarebox.getId(); j < squarebox.getId()+slotsNum; j++) {
            if((Integer)tv.get(j).getTag() <= 0){
                return false;
            }
        }
        return true;
    }

    public void resetSquares(List<View> views){
        int i = 0;
        for(View v : views){
            if (i > 16 && i < 40) {
                if((Integer)v.getTag() > 0){
                    v.setBackgroundColor(Color.GREEN);
                } else {
                    v.setBackgroundColor(Color.RED);
                }

            }
            i++;
        }
    }
    public static List<Reservation> deepCopyListOfReservations(List<Reservation> originalList) {
        List<Reservation> copiedList = new ArrayList<>();
        for (Reservation original : originalList) {
            copiedList.add(new Reservation(original));
        }
        return copiedList;
    }
    public void setTagsForViews(List<View> views, List<TextView> textViews) {
        for (int i = 0; i < views.size(); i++) {
            // For indexes from 16 to 40 (inclusive), set tag to 2
            if (i > 16 && i < 40) {
                views.get(i).setTag(2);
                textViews.get(i).setTag(2);
            } else {
                // For all other indexes, set tag to -1
                views.get(i).setTag(-1);
                textViews.get(i).setTag(-1);
            }
        }
    }
    public static void modifyTextViewsWithPipe(List<TextView> views) {
        for (TextView textView : views) {

            //TextView textView = view.findViewById(R.id.square_num);
            String text = textView.getText().toString();
            if (text.contains("|")) { // Check if the text contains "|"
                String modifiedText = text.substring(0, text.length() - 1) + textView.getTag().toString(); // Replace last character with tag
                textView.setText(modifiedText); // Set the modified text back to the TextView
            }
        }
    }

    public void updateReservationsTaken(String date){
        setTagsForViews(indoorViews, indoorTextViews);
        setTagsForViews(outDoorViews, outDoorTextViews);
        List<Reservation> thisDaysReservations = deepCopyListOfReservations(buildingReservations);
        thisDaysReservations.removeIf(item -> (!item.getDate().equals(date)));
        availSlotsForDay = 24 - thisDaysReservations.size();
        System.out.println(availSlotsForDay);

        for (Reservation reservation : thisDaysReservations){
                if(reservation.getCancelled()){
                    continue;
                }
                int startTime = reservation.getStartTime();
                int firstNum = startTime/100;
                int secondNum = startTime - (firstNum*100);
                firstNum*=2;
                int index = firstNum;
                if(secondNum == 30){
                    index++;
                }
                for (int i = index; i < index + reservation.getNumSlots(); i++) {
                    if(reservation.isIndoor()){
                        int currTaken = (Integer) indoorViews.get(i).getTag();
                        indoorViews.get(i).setTag(currTaken-1);
                        indoorTextViews.get(i).setTag(currTaken-1);
                    } else {
                        int currTaken = (Integer) outDoorViews.get(i).getTag();
                        outDoorViews.get(i).setTag(currTaken-1);
                        outDoorTextViews.get(i).setTag(currTaken-1);
                    }
                }
        }
        modifyTextViewsWithPipe(indoorTextViews);
        modifyTextViewsWithPipe(outDoorTextViews);
        resetSquares(indoorViews);
        resetSquares(outDoorViews);

    }





    public void addReservationToDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservations") // Replace with your collection name
                .orderBy("resID", Query.Direction.DESCENDING) // Assuming 'createdAt' is a timestamp field
                .limit(1) // Limits the query to only retrieve the last document
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot lastAddedDoc = queryDocumentSnapshots.getDocuments().get(0);
                            Reservation lastReservation = lastAddedDoc.toObject(Reservation.class);
                            lastAddedID = lastReservation.getResID(); // Here's your last reservation ID
                            // Do something with the last reservation ID

                        } else {
                            // Handle the case where there are no documents in the collection
                            lastAddedID = 0;
                        }
                        int time = (saveIndex/2)*100;
                        if(saveIndex%2 != 0){
                            time += 30;
                        }
                        Reservation newRes = new Reservation(lastAddedID+1,ReservationViewModelStaticParams.buildingID,indoor,saveDate,time,Integer.parseInt(slots.getSelectedItem().toString()),false);
                        db.collection("reservations").document((lastAddedID+1)+"").set(newRes)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document was successfully added!
                                        System.out.println("RES ADDED");
                                        Intent intent = new Intent(ReservationActivity.this,Map.class);
                                        ReservationActivity.this.startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle the failure
                                        System.out.println("ERROR ADDING RES + " + e);
                                    }
                                });
                        // Assume db is an instance of FirebaseFirestore and you have already initialized it.

// Reference to the document you want to update
                        LoggedIn currUser = LoggedIn.getInstance();
                        DocumentReference docRef = db.collection("users").document(currUser.getUserID());

// Element to add to the array

// Append to the array field
                        docRef.update("reservations", FieldValue.arrayUnion(newRes.getResID()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Successfully appended to the array
                                        System.out.println("ADDED RES ID TO USER RES'S");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle the error
                                        System.out.println("ERROR ADDING RES TO USER + " + e);
                                    }
                                });
                        // Query documents with a specific "buildingID" field value
                        Query query = db.collection("buildings").whereEqualTo("buildingID", ReservationViewModelStaticParams.buildingID);

                        // Execute the query
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println("Found building");
                                        // Handle the document data as needed
                                        Building building = document.toObject(Building.class);

                                        // Append a new reservation ID
                                        building.getReservationIds().add(newRes.getResID());

                                        // Update the document with the modified reservationIDs
                                        DocumentReference docRef = db.collection("buildings").document(document.getId());
                                        docRef.update("reservationIds", building.getReservationIds())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> updateTask) {
                                                        if (updateTask.isSuccessful()) {
                                                            System.out.println("Document updated successfully with new reservation ID");
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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lastAddedID = -1;
                    }
                });

    }
    public boolean validTime(Date date, int index, int add_day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int convtime = index/2;

        // Extract the hour of the day
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if(add_day == 0 && (hourOfDay > convtime)){
            return false;
        }
        return true;
    }





}

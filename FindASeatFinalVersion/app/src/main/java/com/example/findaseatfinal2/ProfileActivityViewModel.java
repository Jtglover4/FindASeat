package com.example.findaseatfinal2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileActivityViewModel extends ViewModel {
    private MutableLiveData<List<Reservation>> liveReservations = new MutableLiveData<>(null);
    public LiveData<List<Reservation>> getLiveReservations() {
        return liveReservations;
    }
    LoggedIn currUser;

    // Constructor
    public ProfileActivityViewModel() {
        currUser = LoggedIn.getInstance();
        loadData();
    }

    private void loadData() {
        // Load your data and post value to liveData
        // This could be from a database, network, etc.
        getUserFromDB();
    }

    public void getUserReservationsFromDB(User u){
        // Assuming reservationIds is a List<String> containing your reservation IDs
        System.out.println("GETTING B RES'S");
        List<Integer> reservationIds = u.getReservations();
        //Collections.sort(reservationIds);
        List<String> reservationIdStrings = reservationIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        List<Reservation> userReservations = new ArrayList<>();
        if(reservationIds.size() == 0){
            liveReservations.setValue(userReservations);
            System.out.println("SET EMPTY B'RES");
            return;
        }

// If you have more than 10 IDs, you'll need to split your list into chunks of 10 and perform multiple queries
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("reservations").whereIn(FieldPath.documentId(), reservationIdStrings);

// Now, you can perform your queries

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert the document to your Reservation class
                        Reservation reservation = document.toObject(Reservation.class);
                        // Do something with your reservation objects...
                        userReservations.add(reservation);
                    }
                    liveReservations.postValue(userReservations);
                } else {
                    // Handle the error
                }
            }
        });
    }
    private void getUserFromDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(currUser.getUserID());

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    User user = task.getResult().toObject(User.class);
                    getUserReservationsFromDB(user);
                } else {
                    // Document with the specified ID doesn't exist
                    System.out.println("USER DOESNT EXIST");

                }
            } else {
                // Handle database error
                System.out.println("DATABASE ERROR");
            }
        });
    }
}

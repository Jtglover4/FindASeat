package com.example.findaseatfinal2;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationViewModel extends ViewModel {
    private MutableLiveData<Building> liveBuilding = new MutableLiveData<>(null);
    private MutableLiveData<List<Reservation>> liveReservations = new MutableLiveData<>(null);

    private int buildingID;

    // Constructor
    public ReservationViewModel() {
        buildingID = ReservationViewModelStaticParams.buildingID;
        loadData();
    }

    // Method to get LiveData
    public LiveData<Building> getLiveBuilding() {
        return liveBuilding;
    }
    public LiveData<List<Reservation>> getLiveReservations() {
        return liveReservations;
    }


    // Method to load data
    private void loadData() {
        // Load your data and post value to liveData
        // This could be from a database, network, etc.

        getBuildingFromDB();
    }
    public void getBuildingReservationsFromDB(){
        // Assuming reservationIds is a List<String> containing your reservation IDs
        System.out.println("GETTING B RES'S");
        List<Integer> reservationIds = liveBuilding.getValue().getReservationIds();
        List<String> reservationIdStrings = reservationIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        List<Reservation> buildingReservations = new ArrayList<>();
        if(reservationIds.size() == 0){
            liveReservations.setValue(buildingReservations);
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
                            buildingReservations.add(reservation);
                        }
                        liveReservations.postValue(buildingReservations);
                    } else {
                        // Handle the error
                    }
                }
            });


    }

    public void getBuildingFromDB(){
        int buildingIdToSearch = buildingID;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println("GETTING B");
        db.collection("buildings")
                .whereEqualTo("buildingID", buildingIdToSearch)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Building retrieved");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Building building = document.toObject(Building.class);
                                System.out.println(building);
                                liveBuilding.setValue(building);
                                // Now you can use your Building object
                                // Example field
                                getBuildingReservationsFromDB();
                                // ...
                            }
                        } else {
                            System.out.println("Error getting building: " + task.getException());
                        }
                    }
                });
    }
}

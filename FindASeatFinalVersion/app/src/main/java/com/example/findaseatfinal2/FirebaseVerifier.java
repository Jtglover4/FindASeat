package com.example.findaseatfinal2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseVerifier {

    private FirebaseFirestore db;

    public FirebaseVerifier() {
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }
    public void verifyUserEnteredCorrectly(String userId, User u, IsPassed p) {
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Convert document to User object
                    User user = document.toObject(User.class);
                    if(!user.equalsTo(u)){
                        //throw new RuntimeException("User not created correctly in database");
                        p.setPassed(false);
                    } else {
                        //return success
                        p.setPassed(true);
                    }
                } else {
                    // Handle the case where the user does not exist
                    p.setPassed(false);
                }
            } else {
                // Handle the failure
                throw new RuntimeException("DB Query failed");
            }
        });
    }

    public void verifyReservationEnteredCorrectly(String resID, Reservation r, IsPassed p) {
        db.collection("reservations").document(resID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Convert document to User object
                    Reservation res = document.toObject(Reservation.class);
                    if(!res.equalsTo(r)){

                        p.setPassed(false);
                    } else {
                        //return success
                        p.setPassed(true);
                    }
                } else {
                    // Handle the case where the user does not exist
                    p.setPassed(false);
                }
            } else {
                // Handle the failure
                throw new RuntimeException("DB Query failed");
            }
        });
    }

    public void verifyReservationCancelled(String resID, IsPassed p, boolean status) {
        db.collection("reservations").document(resID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Convert document to User object
                    Reservation res = document.toObject(Reservation.class);
                    if(res == null || res.getCancelled() != status){
                        p.setPassed(false);
                    } else {
                        //return success
                        p.setPassed(true);
                    }
                } else {
                    // Handle the case where the user does not exist
                    p.setPassed(false);
                }
            } else {
                // Handle the failure
                throw new RuntimeException("DB Query failed");
            }
        });
    }

}

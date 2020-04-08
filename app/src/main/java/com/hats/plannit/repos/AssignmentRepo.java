package com.hats.plannit.repos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hats.plannit.models.Assignment;

import java.util.ArrayList;
import java.util.List;


public class AssignmentRepo {

    private static final String TAG = "AssignmentRepo";
    private static AssignmentRepo instance;
    private ArrayList<Assignment> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String currentUserEmail;
    private CollectionReference assignmentRef = db.collection("Student")
            .document(currentUser.getEmail()).collection("Assignment");
    private String idNum = "123456";

    //Singleton Pattern
    public static AssignmentRepo getInstance(){
        if(instance == null){
            instance = new AssignmentRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Assignment>> getAssignments(){
        currentUser = mAuth.getCurrentUser();
        currentUserEmail = currentUser.getEmail();
        assignmentRef = db.collection("Student")
                .document(currentUser.getEmail()).collection("Assignment");
        if(dataSet.isEmpty()) loadAssignments();
        MutableLiveData<List<Assignment>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadAssignments(){
        //listens for updates on Collection in realtime
        collectionListener();
    }

    private void collectionListener( ){
        //TODO: need to order by closest to due date.
        // possibly add a feature to flag overdue assignments in red font
        //Bug with duplicates when adding more assignments
        assignmentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG,"Listen Failed", e );
                    return;
                }

                assignmentRef.orderBy("date").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            dataSet.clear();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Assignment.class))) {
                                    Assignment newAssignment = documentSnapshot.toObject(Assignment.class);
                                    newAssignment.setDocumentId(documentSnapshot.getId()); //sets document Id for javaside
                                    dataSet.add(newAssignment);
                                }

                            }
                        }

                        Log.e(TAG, "onSuccess: added" );

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " + e.toString());
                    }
                });
            }
        });
    }

    public boolean addAssignment(final Assignment newAssignment, final Context context){

        //does not handle duplicates yet
        assignmentRef.document().set(newAssignment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, newAssignment.getAssignmentName() +" stored!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+ e.toString() );
            }
        });
        return true;
    }

    public boolean delAssignment(final Assignment assignmentToDel, final Context context){

        //does not handle duplicates yet
        Log.d(TAG, "delAssignment: " + assignmentToDel.getDocumentId());
        assignmentRef.document(assignmentToDel.getDocumentId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, assignmentToDel.getAssignmentName() +" deleted.",
                        Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );

            }
        });

        return true;
    }

    public void completeAssignment(final Assignment assignment, final Context context,
                                   Boolean isChecked) {

        assignmentRef.document(assignment.getDate() + assignment.getTime())
                .update("complete", isChecked).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,  assignment.getAssignmentName() + " completed!" , Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );
            }
        });

    }


}


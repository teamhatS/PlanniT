package com.hats.plannit.repos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
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
    private String idNum = "123456";
    private  final CollectionReference assignmentRef =
            db.collection("Student").document(idNum).collection("Assignment");


    //Singleton Pattern
    public static AssignmentRepo getInstance(){
        if(instance == null){
            instance = new AssignmentRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Assignment>> getAssignments(){
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
        assignmentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG,"Listen Failed", e );
                    return;
                }

                assignmentRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Assignment.class))) {
                                    Assignment newAssignment = documentSnapshot.toObject(Assignment.class);
                                    newAssignment.setDocumentId(documentSnapshot.getId()); //sets document Id for javaside
                                    dataSet.add(documentSnapshot.toObject(Assignment.class));
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


}


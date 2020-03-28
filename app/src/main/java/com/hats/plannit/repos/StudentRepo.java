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
import com.hats.plannit.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepo {

    private static final String TAG = "StudentRepo";
    private static StudentRepo instance;
    private ArrayList<Student> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference studentRef = db.collection("Student");

    public static StudentRepo getInstance(){
        if(instance == null){
            instance = new StudentRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Student>> getStudents(){
        if(dataSet.isEmpty()) loadStudents();
        MutableLiveData<List<Student>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadStudents(){
        collectionListener();
    }

    private void collectionListener(){
        studentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "Listen Failed", e);
                    return;
                }

                studentRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Student.class))){
                                    Student newStudent = documentSnapshot.toObject(Student.class);
                                    newStudent.setDocumentId(documentSnapshot.getId());
                                    dataSet.add(documentSnapshot.toObject(Student.class));
                                }
                            }
                        }
                        Log.e(TAG, "onSuccess: added");
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

    public boolean addStudent(final Student newStudent, final Context context){
        studentRef.document().set(newStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, newStudent.getStudentId() + " stored.", Toast.LENGTH_SHORT).show();
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

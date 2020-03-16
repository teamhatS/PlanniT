package com.hats.plannit.repos;

import android.util.Log;
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
    private List<Student> dataSet = new ArrayList<>();
    private Student currentStudent; // the student logged in;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference studentRef = db.collection("Student");

    //Singleton Pattern
    public static StudentRepo getInstance(){
        if(instance == null){
            instance = new StudentRepo();
        }
        return instance;
    }

    public  MutableLiveData<List<Student>> getStudents(){
        if(dataSet.isEmpty()) loadStudents();
        MutableLiveData<List<Student>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadStudents(){
        //listens for updates on Collection in realtime
        collectionListener();
    }

    private void collectionListener( ){

        studentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG,"Listen Failed", e );
                    return;
                }

                studentRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Student.class))) {
                                    Student newStudent = documentSnapshot.toObject(Student.class);
                                    dataSet.add(newStudent);
                                    Log.d(TAG, "onSuccess: " + dataSet);
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

    public Student getCurrentStudent(){
        for(Student student : dataSet){
            if(student.getLoggedIn()){
                student = currentStudent; //assigns to current student..probably won't need.
                return student;
            }
        }
        return null;
    }

}

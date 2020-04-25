package com.hats.plannit.repos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hats.plannit.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepo {

    private static final String TAG = "StudentRepo";
    private static StudentRepo instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private List<Student> dataSet = new ArrayList<>();
    private Student currentStudent; // the student logged in;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference studentRef = db.collection("Student")
            .document(currentUser.getEmail());
//    private final CollectionReference studentRef = db.collection("Student");
    //Singleton Pattern
    public static StudentRepo getInstance(){
        if(instance == null){
            instance = new StudentRepo();
        }
        return instance;
    }

    public Student getLoggedInStudent(){
         studentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 currentStudent = documentSnapshot.toObject(Student.class);
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
             }
         });

         return currentStudent;
    }

    public void updateStudentAccount(final Context context, Student student){
        studentRef.set(student)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Student Account Updated.", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );

            }
        });

    }

}

package com.hats.plannit.ui.courses;

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
import com.hats.plannit.models.Course;

import java.util.ArrayList;
import java.util.List;

public class AvailableCourseRepo
{
    private static final String TAG = "AvailableCourseRepo";
    private static AvailableCourseRepo instance;
    private List<Course> availableCourseList = new ArrayList<>();
    private List<Course> registeredCourseList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  final CollectionReference availableCourseRef = db.collection("AvailableCourse");
    private  final CollectionReference registeredCourseRef = db.collection("RegisteredCourse");

    public static AvailableCourseRepo getInstance()
    {
        if(instance == null)
        {
            instance = new AvailableCourseRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Course>> getAvailableCourses()
    {
        if(availableCourseList.isEmpty())
        {
            collectionListener(availableCourseRef, availableCourseList);
        }
        MutableLiveData<List<Course>> data = new MutableLiveData<>();
        data.setValue(availableCourseList);

        return data;
    }

    public MutableLiveData<List<Course>> getRegisteredCourses()
    {
        if(registeredCourseList.isEmpty())
        {
            collectionListener(registeredCourseRef, registeredCourseList);
        }
        MutableLiveData<List<Course>> data = new MutableLiveData<>();
        data.setValue(registeredCourseList);

        return data;
    }

    public void addAvailableCourse(final List<Course> newCourseList, final Context context)
    {
        addCourse(availableCourseRef, newCourseList, context);
    }

    public void addRegisteredCourse(final List<Course> newCourseList, final Context context)
    {
        addCourse(registeredCourseRef, newCourseList, context);
    }

    private void collectionListener(final CollectionReference reference, final List<Course> courseList)
    {
        reference.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                if(e != null)
                {
                    Log.w(TAG, "Listen Failed", e);
                    return;
                }

                reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot documentSnapshot: list)
                            {
                                if(!courseList.contains(documentSnapshot.toObject(Course.class)))
                                {
                                    Course newCourse = documentSnapshot.toObject(Course.class);
                                    newCourse.setCourseId(documentSnapshot.getId());
                                    courseList.add(documentSnapshot.toObject(Course.class));
                                }
                            }
                        }
                        Log.e(TAG, "onSuccess: added");
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.e(TAG, "onFailure: " + e.toString());
                    }
                });
            }
        });
    }

    private boolean addCourse(final CollectionReference reference, final List<Course> newCourseList, final Context context)
    {
        for(Course course: newCourseList)
        {
            reference.document().set(course).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {
                    Toast.makeText(context, "Courses are added!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: " + e.toString());
                }
            });
        }
        return true;
    }
}

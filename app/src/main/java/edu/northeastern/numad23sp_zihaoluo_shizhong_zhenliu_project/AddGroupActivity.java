package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

public class AddGroupActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //creating variables for our button, edit text,firebase database, database refrence, progress bar.
    private Button addCourseBtn, uploadCourseImgBtn;
    private TextInputEditText courseNameEdt, courseDescEdt, coursePriceEdt, bestSuitedEdt, courseImgEdt, courseLinkEdt;
    private ProgressBar loadingPB;
    private String courseID;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        //initializing all our variables.
        user = getIntent().getParcelableExtra("user");
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        uploadCourseImgBtn = findViewById(R.id.idUploadCourseImage);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        //on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
        //adding click listener for our add course button.

        uploadCourseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddGroupActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                //getting data from our edit text.
                String courseName = courseNameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                courseID = courseName;
                //on below line we are passing all data to our modal class.
                GroupRVModal groupRVModal = new GroupRVModal(courseID, courseName, courseDesc, coursePrice, bestSuited, courseImg, courseLink);
                // Increase createdGroup by 1
                user.setCreatedGroups(user.getCreatedGroups() + 1);
                firebaseDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
                //on below line we are calling a add value event to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //on below line we are setting data in our firebase database.
                        databaseReference.child(courseID).setValue(groupRVModal);
                        //displaying a toast message.
                        Toast.makeText(AddGroupActivity.this, "Group Added..", Toast.LENGTH_SHORT).show();
                        //starting a main activity.
//                        startActivity(new Intent(AddGroupActivity.this, StartActivity.class));
                        Intent i = new Intent(AddGroupActivity.this, MainActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //displaying a failure message on below line.
                        Toast.makeText(AddGroupActivity.this, "Fail to add Group..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            courseImgEdt.setText(fileUri.toString());
            Log.e(TAG, "onActivityResult: fileUri = " + fileUri.toString());
//            user.setProfilePic(fileUri.toString());
//            Task<Void> t1 = mDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
        }
//        Task<Void> t1 = mDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
    }
}
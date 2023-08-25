package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

public class MainActivity extends AppCompatActivity implements GroupRVAdapter.CourseClickInterface {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //creating variables for fab, firebase database, progress bar, list, adapter,firebase auth, recycler view and relative layout.
    private FloatingActionButton addCourseFAB, editPrifoleFAB, calendarSign;
    private RecyclerView courseRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<GroupRVModal> groupRVModalArrayList;
    private GroupRVAdapter groupRVAdapter;
    private RelativeLayout homeRL;
    private String currentUser;
    private User user;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing all our variables.
        courseRV = findViewById(R.id.idRVCourses);
        homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addCourseFAB = findViewById(R.id.idFABAddCourse);
        editPrifoleFAB = findViewById(R.id.idFABEditProfile);
        calendarSign = findViewById(R.id.signDate);
        firebaseDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        user = getIntent().getParcelableExtra("user");
        Log.e(TAG, "MainActivity onCreate: user = " + user.toString());
        Log.e(TAG, "MainActivity onCreate: email = " + user.getEmail());
        currentUser = user.getUsername();
//        currentUser = getIntent().getStringExtra("user");
        groupRVModalArrayList = new ArrayList<>();
        //on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Courses");

        // on below line adding a click listener for our floating action button.
        calendarSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening a new activity for checking the calendar sign.
                Intent i = new Intent(MainActivity.this, CalendarActivity.class);
                i.putExtra("user", user);
                view.getContext().startActivity(i);
            }
        });
        //on below line adding a click listener for our floating action button.
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening a new activity for adding a course.
                Intent i = new Intent(MainActivity.this, AddGroupActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });


        // on below line we are adding a click listener for our edit profile button.
        editPrifoleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening a new activity for adding a course.
                Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
        //on below line initializing our adapter class.
        groupRVAdapter = new GroupRVAdapter(groupRVModalArrayList, this, this::onCourseClick);
        //setting layout malinger to recycler view on below line.
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        //setting adapter to recycler view on below line.
        courseRV.setAdapter(groupRVAdapter);
        //on below line calling a method to fetch courses from database.
        getCourses();
    }

    private void getCourses() {
        //on below line clearing our list.
        groupRVModalArrayList.clear();
        //on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                //adding snapshot to our array list on below line.
                groupRVModalArrayList.add(snapshot.getValue(GroupRVModal.class));
                //notifying our adapter that data has changed.
                groupRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //this method is called when new child is added we are notifying our adapter and making progress bar visibility as gone.
                loadingPB.setVisibility(View.GONE);
                groupRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //notifying our adapter when child is removed.
                groupRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //notifying our adapter when child is moved.
                groupRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        //calling a method to display a bottom sheet on below line.
        //Toast.makeText(getApplicationContext(), "User Click", Toast.LENGTH_LONG).show();
        displayBottomSheet(groupRVModalArrayList.get(position));
    }

    private void displayBottomSheet(GroupRVModal modal) {
        //on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        //on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        //setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        //on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        //calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        //on below line we are creating variables for our text view and image view inside bottom sheet
        //and initialing them with their ids.
        TextView courseNameTV = layout.findViewById(R.id.idTVCourseName);
        TextView courseDescTV = layout.findViewById(R.id.idTVCourseDesc);
        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        ImageView courseIV = layout.findViewById(R.id.idIVCourse);
        //on below line we are setting data to different views on below line.
        courseNameTV.setText(modal.getGroupName());
        courseDescTV.setText(modal.getGroupDescription());
        suitedForTV.setText("Next Activity: " + modal.getActivityDetail());
        priceTV.setText("Ticket Price: " + modal.getGroupPrice());
        Picasso.get().load(modal.getGroupImg()).into(courseIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        //adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity.this, GroupChatActivity.class);
//                Log.e(TAG, "MainActivity onClickJoin: user = " + user.toString());
                //on below line we are passing our course modal
                i.putExtra("course", modal);
//                i.putExtra("user", currentUser);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
        //adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are navigating to browser for displaying course details from its url
                user.setActivities(user.getActivities() + 1);
                user.setJoinedGroups(user.getJoinedGroups() + 1);
                firebaseDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
                Toast.makeText(getApplicationContext(), "Thanks for registering! Your Information has" +
                        " been sent to the holder", Toast.LENGTH_LONG).show();
            }
        });

    }


}
package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

/**
 * Here, we will demonstrate the use of Firebase Realtime DB which syncs your data across different
 * devices that are accessing the same DB in realtime.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private final Context context = this;
    private FirebaseDatabase mDatabase;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Connect with firebase
        mDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");

        // The documentation mentions that you do not need to add the URL if your location is
        // us-central1, but at times it does not work. If it does not work, then add the url of your
        // db in the getInstance() call. eg: getInstance("https://testfirebase-default-rtdb.firebaseio.com/")
        // Update the score in realtime


    }

    public void loginPressed(View view) {
        User user;
        String username;
        String password;

        TextView usernameT = findViewById(R.id.usernameInput);
        username = usernameT.getText().toString();
        TextView passwordT = findViewById(R.id.passwordInput);
        password = passwordT.getText().toString();


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user1 = snapshot.getValue(User.class);
                if (!user1.getPassword().equals(password)) {
                    Toast.makeText(context, "Wrong password! Please try again.",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    Log.e(TAG, "LoginActivity onCreate: user = " + user1.toString());
                    Log.e(TAG, "LoginActivity onCreate: email = " + user1.getEmail());
                    i.putExtra("user", user1);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        mDatabase.getReference().child("users").child(username).addValueEventListener(valueEventListener);

    }
}

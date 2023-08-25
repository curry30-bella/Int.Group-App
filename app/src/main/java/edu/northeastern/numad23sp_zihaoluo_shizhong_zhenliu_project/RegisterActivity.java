package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

/**
 * Here, we will demonstrate the use of Firebase Realtime DB which syncs your data across different
 * devices that are accessing the same DB in realtime.
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseDatabase mDatabase;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Connect with firebase
        mDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        // mDatabase = FirebaseDatabase.getInstance("https://numad23-ab1e4-default-rtdb.firebaseio.com");
        // mDatabase = FirebaseDatabase.getInstance("https://wk08-965ee-default-rtdb.firebaseio.com/");

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


        user = new User(username, password);
        Task<Void> t1 = mDatabase.getReference().child("users").child(username).setValue(user);

        Toast.makeText(this, "created",
                        Toast.LENGTH_SHORT)
                .show();

        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
//        i.putExtra("user", username);
        i.putExtra("user", user);
        startActivity(i);
    }

}
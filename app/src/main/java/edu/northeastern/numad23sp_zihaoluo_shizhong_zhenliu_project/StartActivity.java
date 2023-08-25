package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void myClickHandler(View view) {
        // Do stuff
        int theId = view.getId();
        if (theId == R.id.button1) {
            Intent myIntent = new Intent(view.getContext(), WebServiceActivity.class);
            view.getContext().startActivity(myIntent);
        }
        if (theId == R.id.button3) {
            Intent myIntent = new Intent(view.getContext(), GroupChatActivity.class);
            view.getContext().startActivity(myIntent);
        }
        if (theId == R.id.button_4) {
            Intent myIntent = new Intent(view.getContext(), AboutMe.class);
            view.getContext().startActivity(myIntent);
        }
        if (theId == R.id.button_2) {
            Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity(myIntent);
        }

        if (theId == R.id.button_register) {
            Intent myIntent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(myIntent);
        }

    }
}
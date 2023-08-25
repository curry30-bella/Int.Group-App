package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

public class CalendarActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    // back up
    // 1秒内点击两次返回键退回主页
    long exittime; // 设定退出时间间隔
    private SignDate signDate;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        user = getIntent().getParcelableExtra("user");
        firebaseDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        //on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Users");

        signDate = findViewById(R.id.signDate);
        signDate.setOnSignedSuccess(new OnSignedSuccess() {
            @Override
            public void OnSignedSuccess() {
                Log.e("wqf", "Success");
            }
        });
        // Increase createdGroup by 1
        user.setSigneddays(user.getSigneddays() + 1);
        firebaseDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(user.getUsername()).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) { //参数：按的键；按键事件
        //  判断事件触发
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断两次点击间隔时间
            if ((System.currentTimeMillis() - exittime) > 1000) {
                Toast.makeText(this, "Double Click to go back Home！",
                        Toast.LENGTH_SHORT).show();
                exittime = System.currentTimeMillis(); // 设置第一次点击时间
            } else {
                //finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

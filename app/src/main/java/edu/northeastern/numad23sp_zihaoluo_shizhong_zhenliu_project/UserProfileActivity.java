package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;

public class UserProfileActivity extends AppCompatActivity {
    User user;
    ImageView imageProfilePic;
    DatabaseReference databaseReference;
    // add log out function
    Button logout;
    // add save function
    Button saveInput;
    // 1秒内点击两次返回键退回主页
    long exittime; // 设定退出时间间隔
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        user = getIntent().getParcelableExtra("user");
        imageProfilePic = findViewById(R.id.idProfilePicture);
        ImageView imageEditPic = findViewById(R.id.idEditPic);

        TextView textUsername = findViewById(R.id.idUsername);
        TextView textActivities = findViewById(R.id.idActivities);
        TextView textJoined = findViewById(R.id.idJoined);
        TextView textCreated = findViewById(R.id.idCreated);
        TextView textSigned = findViewById(R.id.idSigned);
        textUsername.setText(user.getUsername());
        textActivities.setText(String.valueOf(user.getActivities()));
        textJoined.setText(String.valueOf(user.getJoinedGroups()));
        textCreated.setText(String.valueOf(user.getCreatedGroups()));
        textSigned.setText(String.valueOf(user.getSigneddays()));

        // find user information id
        EditText textEmail = findViewById(R.id.idEmail);
        EditText textPhone = findViewById(R.id.idPhone);
        EditText textLocation = findViewById(R.id.idLocation);
        textEmail.setText(user.getEmail());
        textPhone.setText(user.getPhone());
        textLocation.setText(user.getLocation());

        logout = findViewById(R.id.idLogoutButton);
        saveInput = findViewById(R.id.idSaveButton);

        mDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        databaseReference = mDatabase.getReference("Users");

        Log.e(TAG, "UserProfileActivity onCreate: user = " + user.toString());


        Picasso
                .get()
                .load(user.getProfilePic())
                .fit()
                .centerInside()
                .into(imageProfilePic);

        imageEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UserProfileActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        // 为退出按钮添加监听事件实现退出 --- 用到弹框提示确认退出
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1.创建弹框对象,显示在当前页面
                AlertDialog.Builder ab = new AlertDialog.Builder(UserProfileActivity.this);
                // 2.编辑弹框样式
                // 2.1 创建标题
                ab.setTitle("Notification");
                // 2.3 设置图标
                ab.setIcon(R.mipmap.ic_launcher_round);
                // 2.4 设置内容
                ab.setMessage("Are you sure to log out？");
                // 2.5 设置按钮
                ab.setPositiveButton("Cancel", null);
                ab.setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 实现程序的退出，结束当前
                        finish();
                        Intent i = new Intent(UserProfileActivity.this, StartActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }
                });
                // 3.创建弹框
                ab.create();
                // 4.显示弹框
                ab.show();

            }
        });

        // save button function: save new edit information
        saveInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setEmail(textEmail.getText().toString());
                user.setPhone(textPhone.getText().toString());
                user.setLocation(textLocation.getText().toString());
                Task<Void> t1 = mDatabase.getReference().child("users").
                        child(user.getUsername()).setValue(user);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(user.getUsername()).setValue(user);
                        Toast.makeText(UserProfileActivity.this,
                                "Your profile has been Updated!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserProfileActivity.this, MainActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserProfileActivity.this,
                                "Fail to edit the user information...", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }

    //@Override
    // public void onBackPressed() {
    // Your custom code to handle back button click
    // Intent i = new Intent(UserProfileActivity.this, MainActivity.class);
    //i.putExtra("user", user);
    //startActivity(i);
    //}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            imageProfilePic.setImageURI(fileUri);
            Log.e(TAG, "onActivityResult: fileUri = " + fileUri.toString());
            user.setProfilePic(fileUri.toString());
            Task<Void> t1 = mDatabase.getReference().child("users").child(user.getUsername()).setValue(user);
        }
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
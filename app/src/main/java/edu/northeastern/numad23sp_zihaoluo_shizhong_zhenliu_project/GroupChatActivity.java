package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.Record;
import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models.User;
import edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.utils.Utils;

/**
 * Here, we will demonstrate the use of Firebase Realtime DB which syncs your data across different
 * devices that are accessing the same DB in realtime.
 */
public class GroupChatActivity extends AppCompatActivity {

    private static final String TAG = GroupChatActivity.class.getSimpleName();
    private static String SERVER_KEY;
    private static String CLIENT_REGISTRATION_TOKEN;
    private final Context context = this;
    private final GroupChatActivity view = this;
    private final Map<String, String> imageIdToURL = new HashMap<>();
    ChildEventListener childEventListener = null;
    private FirebaseDatabase mDatabase;
    private TableLayout table1, table2;
    private ImageView courseIV;
    private TextView courseTV, coursePriceTV;
    private User user;
    private String currentGroup, currentUser;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_realtime_database);


        table1 = findViewById(R.id.tableLayout1);


        imageIdToURL.put("1", "https://images.unsplash.com/photo-1561037404-61cd46aa615b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8ZG9nfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=700&q=60");
        imageIdToURL.put("2", "https://t4.ftcdn.net/jpg/00/97/58/97/360_F_97589769_t45CqXyzjz0KXwoBZT9PRaWGHRk5hQqQ.jpg");
        imageIdToURL.put("3", "https://i.pinimg.com/originals/b2/6c/99/b26c998d89d23f06e315107f88927e9b.jpg");

        // Connect with firebase
        mDatabase = FirebaseDatabase.getInstance("https://a8-new-default-rtdb.firebaseio.com/");
        // mDatabase = FirebaseDatabase.getInstance("https://numad23-ab1e4-default-rtdb.firebaseio.com");
        // mDatabase = FirebaseDatabase.getInstance("https://wk08-965ee-default-rtdb.firebaseio.com/");
        GroupRVModal groupRVModal;
        groupRVModal = getIntent().getParcelableExtra("course");
        currentGroup = groupRVModal.getGroupName();
//        currentUser = getIntent().getStringExtra("user");
        user = getIntent().getParcelableExtra("user");
        currentUser = user.getUsername();
        Log.e(TAG, "onCreate: user = " + user.toString());
        if (childEventListener != null)
            mDatabase.getReference().child("records").removeEventListener(childEventListener);

        childEventListener = mDatabase.getReference().child("records").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Record record = dataSnapshot.getValue(Record.class);
                        Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue().toString());

                        LayoutInflater inflater = LayoutInflater.from(context);
//                        View myChatView = inflater.inflate(R.layout.activity_chat, table1, false);


                        //count current user received
                        if (currentGroup.equalsIgnoreCase(record.groupName)) {
//                            TableRow tbrow = new TableRow(getApplicationContext());
//                            TextView t1v = new TextView(getApplicationContext());
//                            t1v.setLayoutParams(new TableRow.LayoutParams(550, 150));
//                            t1v.setText(record.username + "\n" + record.getStickerRecivedDate());
//                            t1v.setGravity(Gravity.CENTER);
//                            tbrow.addView(t1v);
//                            ImageView t3v = new ImageView(getApplicationContext());
//                            t3v.setLayoutParams(new TableRow.LayoutParams(150, 150));
//
////                            String url = imageIdToURL.get(record.imageId);
//                            String url = user.getProfilePic();
//                            Picasso
//                                    .get()
//                                    .load(url)
//                                    .into(t3v);
//                            t3v.setForegroundGravity(Gravity.CENTER);
//                            tbrow.addView(t3v);
//                            TextView t2v = new TextView(getApplicationContext());
//                            t2v.setText(record.imageId);
//                            tbrow.addView(t2v);
//                            table1.addView(tbrow);
                            View myChatView;
                            if (record.username.equals(currentUser)) {
                                myChatView = inflater.inflate(R.layout.activity_chat_right, table1, false);
                            } else {
                                myChatView = inflater.inflate(R.layout.activity_chat, table1, false);
                            }
                            ImageView profilePic = myChatView.findViewById(R.id.idProfilePic);
                            TextView username = myChatView.findViewById(R.id.idUsername);
                            TextView chatContent = myChatView.findViewById(R.id.idChatContent);
                            String profilePicURL = record.user.getProfilePic();
                            Log.e(TAG, "USER_PROFILE_PIC " + profilePicURL);
                            Picasso
                                    .get()
                                    .load(profilePicURL)
                                    .fit()
                                    .centerInside()
                                    .into(profilePic);
                            username.setText(record.username);
                            chatContent.setText(record.text);
                            table1.addView(myChatView);

//                            sendNotification(view, record.username, record.imageId);
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

                        Log.v(TAG, "onChildChanged: " + dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled:" + databaseError);
                        Toast.makeText(getApplicationContext()
                                , "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        courseIV = findViewById(R.id.idIVCourseCard);
        courseTV = findViewById(R.id.idTVCOurseNameCard);
        coursePriceTV = findViewById(R.id.idTVCousePriceCard);

        courseTV.setText(groupRVModal.getGroupName() + " \nNext Activity: " + groupRVModal.getActivityDetail());
        coursePriceTV.setText("Price: " + groupRVModal.getGroupPrice());
        Picasso.get().load(groupRVModal.getGroupImg()).into(courseIV);


        // The documentation mentions that you do not need to add the URL if your location is
        // us-central1, but at times it does not work. If it does not work, then add the url of your
        // db in the getInstance() call. eg: getInstance("https://testfirebase-default-rtdb.firebaseio.com/")
        // Update the score in realtime

//        SERVER_KEY = "key=" + Utils.getProperties(getApplicationContext()).getProperty("SERVER_KEY");
        SERVER_KEY = "key=" + "AAAAx0Di7Es:APA91bGkajWvJykL4CHYWVNzn4dbjkbzHg1RoK6eYWkrsGgLY6LC3frnO1_OdmvglWZ28RNKIy5Y6QDk63qzUNmIFF_kpvxmbQSjHA-k_tGVwDmsX4GhpgWSizIrFjYEkhZFIiO-rate";
        System.out.println(SERVER_KEY);

        // Generate the token for the first time, then no need to do later
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(GroupChatActivity.this,
                            "Something is wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (CLIENT_REGISTRATION_TOKEN == null) {
                        CLIENT_REGISTRATION_TOKEN = task.getResult();
                    }
                    Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
                    System.out.println(CLIENT_REGISTRATION_TOKEN);
                    Toast.makeText(GroupChatActivity.this,
                            "CLIENT_REGISTRATION_TOKEN Existed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // subscribe button
    // Detection of whether this topic is subscribed is important!
    public void subscribeToNews(View view) {
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                            Toast.makeText(GroupChatActivity.this,
                                    "Something is wrong!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GroupChatActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Button Handler; creates a new thread that sends off a message to all subscribed devices
     *
     * @param view
     */
    public void sendMessageToNews(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToNews();
            }
        }).start();
    }

    private void sendMessageToNews() {
        // Prepare data
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {

            jNotification.put("title", "This is a Firebase Cloud Messaging topic \"news\" message! from 'SEND MESSAGE TO NEWS TOPIC BUTTON'");
            jNotification.put("body", "News Body from 'SEND MESSAGE TO NEWS TOPIC BUTTON'");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            // Populate the Payload object.
            // Note that "to" is a topic, not a token representing an app instance
            jPayload.put("to", "/topics/news");
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String resp = Utils.fcmHttpConnection(SERVER_KEY, jPayload);
        Utils.postToastMessage("Status from Server: " + resp, getApplicationContext());
    }

    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Load button
    public void loadToken(View view) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(GroupChatActivity.this,
                            "Something is wrong, please check your Internet connection!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (CLIENT_REGISTRATION_TOKEN.length() < 1) {
                        CLIENT_REGISTRATION_TOKEN = task.getResult();
                    }
                    Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
                    Toast.makeText(GroupChatActivity.this,
                            "CLIENT_TOKEN IS: " + CLIENT_REGISTRATION_TOKEN, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendNotification(View view, String user, String imageId) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, GroupChatActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        PendingIntent callIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                new Intent(this, GroupChatActivity.class), 0);

        // Build notification
        // Need to define a channel ID after Android Oreo
        String channelId = getString(R.string.channel_id);
//        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        Bitmap bitmapImage;
        if (imageId == "1") {
            bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        } else if (imageId == "2") {
            bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        } else {
            bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.pig);
        }

        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
                //"Notification icons must be entirely white."
                .setSmallIcon(R.drawable.foo)
                .setContentTitle("New image from " + user)
                .setContentText("Subject")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmapImage)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmapImage)
                        .bigLargeIcon(null))
                // hide the notification after its selected
                .setAutoCancel(true)
                .addAction(R.drawable.foo, "Image Received", callIntent)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // // notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, notifyBuild.build());

    }


    public void sendText(View view) {
        Record record;
        String text;


        TextView t1v = findViewById(R.id.sendContent);
        text = t1v.getText().toString();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        record = new Record(user, currentUser, currentGroup, text, timestamp.toString());
        Task<Void> t1 = mDatabase.getReference().child("records").child(String.valueOf(UUID.randomUUID())).setValue(record);

        Toast.makeText(this, "A text has been sent!",
                        Toast.LENGTH_SHORT)
                .show();
        sendNotification(view, record.username, record.text);
    }


}

package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WebServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";
    private static Boolean isConstraintOne = true; //判断是否处在constraint_one的布局中
    final Context context = this;
    // dynamic layout
    ConstraintLayout constraint_one; //声名 constraint_one
    Button loadButton;
    private EditText mURLEditText;
    private TextView mTitleTextView, dogNumText1;
    private EditText dogNumText;
    private String CAT = "https://cat-fact.herokuapp.com/facts",
            DOG = "https://dog-api.kinduff.com/api/facts?number=";
    private boolean preferDog = false;
    private String dogNum = "1";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);


        //mURLEditText = (EditText)findViewById(R.id.URL_editText);
//        mTitleTextView = (TextView)findViewById(R.id.result_textview);
        dogNumText = (EditText) findViewById(R.id.editTextNumber2);
        dogNumText1 = (TextView) findViewById(R.id.textView);
        Switch mySwitch = (Switch) findViewById(R.id.switch3);
        dogNumText.setVisibility(View.INVISIBLE);
        dogNumText1.setVisibility(View.INVISIBLE);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                preferDog = isChecked;
                if (isChecked) {
                    dogNumText.setVisibility(View.VISIBLE);
                    dogNumText1.setVisibility(View.VISIBLE);
                } else {
                    dogNumText.setVisibility(View.INVISIBLE);
                    dogNumText1.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask();
        try {

            // dynamic layout
            constraint_one = findViewById(R.id.constraint_one); //初始化constraint_one
            loadButton = findViewById(R.id.button2);
            //分别创建两个ConstraintSet对象，并把改变前后的两个布局给保存到其中，
            // 这里使用的是`.clone`的方法
            ConstraintSet mConstrainSet_one = new ConstraintSet();
            ConstraintSet mConstrainSet_two = new ConstraintSet();
            mConstrainSet_one.clone(constraint_one);
            mConstrainSet_two.clone(this, R.layout.constraint_two);
            if (isConstraintOne) {
                mConstrainSet_two.applyTo(constraint_one);//将改变后的布局应用进去
                //添加了此句代码后，Button的改变会以动画的形式逐渐移动到改变后的目标位置
                TransitionManager.beginDelayedTransition(constraint_one);
                isConstraintOne = false;
            } else {
                mConstrainSet_one.applyTo(constraint_one);//恢复到初始时的布局状态
                isConstraintOne = true;
            }

            dogNum = dogNumText.getText().toString();
            String url = NetworkUtil.validInput(preferDog ? DOG + dogNum : CAT);
            task.execute(url);
            // This is a security risk.  Don't let your user enter the URL in a real app.
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    // Google is deprecating Android AsyncTask API in Android 11 and suggesting to use java.util.concurrent
    // But it is still important to learn&manage how it works
    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jObject = new JSONObject();
            try {

                // Initial website is "https://jsonplaceholder.typicode.com/posts/1"

                URL url = new URL(params[0]);
                // Get String response from the url address
                String resp = NetworkUtil.httpResponse(url);
                //Log.i("resp",resp);

                // Use this if your web service returns an array of objects.  Arrays are in [ ] brackets.
                // Transform String into JSONObject
                //jObject = new JSONObject(resp);
                if (preferDog) {
                    jObject = new JSONObject(resp);


                } else {
                    JSONArray jArray = new JSONArray(resp);
                    Random rand = new Random();
                    int x = rand.nextInt(5);
                    jObject = jArray.getJSONObject(x);
                }


                //Log.i("jTitle",jObject.getString("title"));
                //Log.i("jBody",jObject.getString("body"));
                return jObject;

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
//            TextView result_view = (TextView)findViewById(R.id.result_textview);

//            try {
//
//                if(preferDog){
//                    //jObject.getJSONArray("facts") is a array with length of dogNum
//                    String addedString = "";
//
//                    for (int i = 0; i < Integer.parseInt(dogNum); i++) {
//                        addedString += " \n TIP: " + jObject.getJSONArray("facts").getString(i) ;
//                    }
//                    result_view.setText(addedString);
//
//                }else{
//                    result_view.setText(jObject.getString("text"));
//                }
//            }
//            catch (JSONException e) {
//                result_view.setText("Something went wrong!");
//            }

            List<String> tipsList = new ArrayList<>();
            RecyclerView resultRecyclerView = findViewById(R.id.result_recycler_view);
            String s;
            try {
                if (preferDog) {
                    //jObject.getJSONArray("facts") is a array with length of dogNum

                    for (int i = 0; i < Integer.parseInt(dogNum); i++) {
                        s = jObject.getJSONArray("facts").getString(i).replace(",\n", ",");
                        tipsList.add("TIP: " + s);
                    }

                } else {
//                    result_view.setText(jObject.getString("text"));

                    s = jObject.getString("text").replace(",\n", ",");
                    tipsList.add("TIP: " + s);
//                    System.out.println("TIP: " + s);
                }
//                resultRecyclerView.setHasFixedSize(true);
//                // defines the way in which the RecyclerView is oriented
//                resultRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//                // Associates the adapter with the RecyclerView
//                resultRecyclerView.setAdapter(new ResultAdapter(tipsList, context));
            } catch (JSONException e) {
                tipsList.add("Something went wrong!");
            }
            resultRecyclerView.setHasFixedSize(true);
            // defines the way in which the RecyclerView is oriented
            resultRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            // Associates the adapter with the RecyclerView
            resultRecyclerView.setAdapter(new ResultAdapter(tipsList, context));


        }
    }

}

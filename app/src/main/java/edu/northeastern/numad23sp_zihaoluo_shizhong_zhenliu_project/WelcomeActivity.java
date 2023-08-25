package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class WelcomeActivity extends AppCompatActivity {
    ImageView logo, splash;
    LottieAnimationView lottieAnimationView;
    private Runnable mGoNext = new Runnable() {
        @Override
        public void run() {
            // 活动页面跳转，从WelcomeActivity跳到StartActivity
            startActivity(new Intent(WelcomeActivity.this, StartActivity.class));
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // 当此页面进入后台后，销毁此页面
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        logo = findViewById(R.id.logo);
        splash = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottiewelcome);

        splash.animate().translationY(-3000).setDuration(4000).setStartDelay(1000);
        logo.animate().translationY(-2000).setDuration(4000).setStartDelay(1000);
        lottieAnimationView.animate().translationY(1500).setDuration(4000).setStartDelay(2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        goNextPage(); // 跳到下个页面
    }

    // 跳到下个页面
    private void goNextPage() {
        // 延迟2秒（2000毫秒）后启动任务mGoNext
        new Handler().postDelayed(mGoNext, 3000);
    }
}

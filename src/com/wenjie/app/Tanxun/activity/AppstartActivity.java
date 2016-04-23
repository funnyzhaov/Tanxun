package com.wenjie.app.Tanxun.activity;

import com.wenjie.app.Tanxun.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
/**
 * 启动页动画
 * @author dell
 *
 */
public class AppstartActivity extends Activity {
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        final View view = View.inflate(this,R.layout.app_start, null);
	        setContentView(view);
	        //渐变展示启动屏
	        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
	        aa.setDuration(2000);
	        view.startAnimation(aa);
	        aa.setAnimationListener(new AnimationListener()
	        {
	            @Override
	            public void onAnimationEnd(Animation arg0) {
	                redirectTo();
	            }
	            @Override
	            public void onAnimationRepeat(Animation animation) {}
	            @Override
	            public void onAnimationStart(Animation animation) {}
	                                                                          
	        });
	    }
	                                                                  
	    /**
	     * 跳转到LoginActivity
	     */
	    private void redirectTo(){       
	        Intent intent = new Intent(this,LoginActivity.class);
	        startActivity(intent);
	        finish();
	    }
}

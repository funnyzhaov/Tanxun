package com.wenjie.app.Tanxun.activity;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.fragment.HomeFragment;
import com.wenjie.app.Tanxun.activity.fragment.PersonFragment;
import com.wenjie.app.Tanxun.activity.fragment.SearchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
/**
 * Tab切换控制界面
 * @author dell
 *
 */
public class BaseActivity extends FragmentActivity implements OnClickListener{
	private LinearLayout mTabHome;
	private LinearLayout mTabSearch;
	private LinearLayout mTabPerson;

	private ImageButton mImgHome;
	private ImageButton mImgSearch;
	private ImageButton mImgPerson;
	
	private Fragment mTab01;//主页
	private Fragment mTab02;//发现
	private Fragment mTab03;//个人
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		initView();
		initEvent();
		setSelect(0);
	}
	/**
	 * 初始化视图
	 */
	private void initView()
	{
		mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
		mTabSearch = (LinearLayout) findViewById(R.id.id_tab_serach);
		mTabPerson = (LinearLayout) findViewById(R.id.id_tab_person);

		mImgHome = (ImageButton) findViewById(R.id.id_tab_home_img);
		mImgSearch = (ImageButton) findViewById(R.id.id_tab_serach_img);
		mImgPerson= (ImageButton) findViewById(R.id.id_tab_person_img);
	}
	/**
	 * 初始化事件
	 */
	private void initEvent(){
		mTabHome.setOnClickListener(this);
		mTabSearch.setOnClickListener(this);
		mTabPerson.setOnClickListener(this);
	}
	
	private void setSelect(int i)
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new HomeFragment();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			mImgHome.setImageResource(R.drawable.home_pressed);
			break;
		case 1:
			if (mTab02 == null)
			{
				mTab02 = new SearchFragment();
				transaction.add(R.id.id_content, mTab02);
			} else
			{
				transaction.show(mTab02);
				
			}
			mImgSearch.setImageResource(R.drawable.serach_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new PersonFragment();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			mImgPerson.setImageResource(R.drawable.person_pressed);
			break;
		default:
			break;
		}
		transaction.commit();
	}
	/**
	 * 隐藏所有Fragment
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
	}
	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{
		mImgHome.setImageResource(R.drawable.home_nomal);
		mImgSearch.setImageResource(R.drawable.serach_nomal);
		mImgPerson.setImageResource(R.drawable.person_nomal);
	}
	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.id_tab_home:
			setSelect(0);
			break;
		case R.id.id_tab_serach:
			setSelect(1);
			break;
		case R.id.id_tab_person:
			setSelect(2);
			break;
		default:
			break;
		}
	}
	
}

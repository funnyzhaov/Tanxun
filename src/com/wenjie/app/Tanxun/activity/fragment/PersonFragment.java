package com.wenjie.app.Tanxun.activity.fragment;

import com.wenjie.app.Tanxun.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Person��ҳ
 * @author dell
 *
 */
public class PersonFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.person, container, false);
	}
}
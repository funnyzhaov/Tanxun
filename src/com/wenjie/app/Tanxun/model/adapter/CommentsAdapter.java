package com.wenjie.app.Tanxun.model.adapter;

import java.util.List;

import com.wenjie.app.Tanxun.model.Comments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * ∆¿¬€  ≈‰∆˜
 * @author dell
 *
 */
public class CommentsAdapter extends ArrayAdapter<Comments> {
	private Context context;
	private int resourceId;
	public CommentsAdapter(Context context, int resource, List<Comments> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resourceId=resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder viewHodler;
		if(convertView==null){
			viewHodler=new ViewHolder();
			 view=LayoutInflater.from(getContext()).inflate(resourceId,null);
			 
			 view.setTag(viewHodler);
		}else{
			view=convertView;
			viewHodler=(ViewHolder)view.getTag();
		}
		return view;
	}
	class ViewHolder{
		
	}
	

}

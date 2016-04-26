package com.wenjie.app.Tanxun.model.adapter;

import java.util.List;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.fragment.HomeFragment;
import com.wenjie.app.Tanxun.activity.fragment.Frag_activity.MyQuestionListActivity;
import com.wenjie.app.Tanxun.model.Question;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * 我的问题适配器
 * @author dell
 *
 */
public class MyQuestionAdapter extends ArrayAdapter<Question> {
	private int resourceId;
	private Context context;
	public MyQuestionAdapter(Context context, int resource, List<Question> objects) {
		super(context, resource, objects);
		this.resourceId=resource;
		this.context=context;
	}
	@Override
	   public View getView(int position, View convertView, ViewGroup parent){
	       View view;
	       ViewHodler viewHodler;
	       if(convertView==null){
	           view=LayoutInflater.from(getContext()).inflate(resourceId,null);
	           viewHodler=new ViewHodler();
	           viewHodler.quesTitle=(TextView)view.findViewById(R.id.tv_qeTitle);
	           viewHodler.quesCont=(TextView)view.findViewById(R.id.tv_qeContent);
	           viewHodler.quesTime=(TextView)view.findViewById(R.id.tv_qeTime);
	           view.setTag(viewHodler);
	       }else{
	           view=convertView;
	           viewHodler=(ViewHodler)view.getTag();
	       }
	       
	       Question question=(Question)getItem(position);
	       viewHodler.quesTitle.setText(question.getQuestionTitle());
	       viewHodler.quesCont.setText(question.getQuestionContent());
	       viewHodler.quesTime.setText(question.getCreatedAt());
	       return  view;
	   }
		@Override
		public Question getItem(int position) {
			return MyQuestionListActivity.getListData().get(position);
		}
	   class ViewHodler{
		   TextView quesTitle;
	       TextView quesCont;
	       TextView quesTime;
	   }

}

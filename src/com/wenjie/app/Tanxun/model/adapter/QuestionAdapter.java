package com.wenjie.app.Tanxun.model.adapter;

import java.util.List;



import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.fragment.HomeFragment;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.util.NetWorkImgeUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionAdapter extends ArrayAdapter<Question>  {
	private int resourceId;
	private Context context;
	public QuestionAdapter(Context context,
			int textViewResourceId, List<Question> objects) {
		super(context, textViewResourceId, objects);
		 resourceId=textViewResourceId;
		 this.context=context;
	}
	@Override
   public View getView(int position, View convertView, ViewGroup parent){
       View view;
       ViewHodler viewHodler;
       if(convertView==null){
           view=LayoutInflater.from(getContext()).inflate(resourceId,null);
           viewHodler=new ViewHodler();
           viewHodler.quesTitle=(TextView)view.findViewById(R.id.qu_title);
           viewHodler.quesCont=(TextView)view.findViewById(R.id.qu_content);
           viewHodler.quesTime=(TextView)view.findViewById(R.id.qu_time);
           viewHodler.imageIcon=(ImageView)view.findViewById(R.id.qu_image);
           view.setTag(viewHodler);
       }else{
           view=convertView;
           viewHodler=(ViewHodler)view.getTag();
       }
       Question question=(Question)getItem(position);
       viewHodler.quesTitle.setText(question.getQuestionTitle());
       viewHodler.quesCont.setText(question.getQuestionContent());
       viewHodler.quesTime.setText(question.getCreatedAt());
       String picPath=question.getStudentIcon().getFileUrl(context);
       if(picPath!=null){
    	   //设置网络加载后的图片
    	   NetWorkImgeUtil.getInstance(context).imageRequest(picPath,
    			   viewHodler.imageIcon);
       }else{
    	   viewHodler.imageIcon.setImageResource(com.wenjie.app.Tanxun.R.drawable.ic_launcher);
       }
       return  view;
   }
	@Override
	public Question getItem(int position) {
		return HomeFragment.getListData().get(position);
	}
   class ViewHodler{
	   TextView quesTitle;
       TextView quesCont;
       ImageView imageIcon;
       TextView quesTime;
   }

}

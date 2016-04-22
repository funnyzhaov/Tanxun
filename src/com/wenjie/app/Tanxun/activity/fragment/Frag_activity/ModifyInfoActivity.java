package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.Controller.IStudentInfoView;
import com.wenjie.app.Tanxun.Controller.frag_controller.IModifyInfoView;
import com.wenjie.app.Tanxun.activity.BaseActivity;
import com.wenjie.app.Tanxun.activity.fragment.PersonFragment;
import com.wenjie.app.Tanxun.model.StudentInfo;
import com.wenjie.app.Tanxun.model.frag_model.IPerson;
import com.wenjie.app.Tanxun.model.frag_model.IPersonImpl;
import com.wenjie.app.Tanxun.service.UploadIconService;
import com.wenjie.app.Tanxun.util.RoundImage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 修改个人头像Activity
 * @author dell
 *
 */
public class ModifyInfoActivity extends Activity implements IModifyInfoView{
	public static final int CHOOSE_PHOTO=3;
	private IPerson Iperson;
	private TextView stuNameText;//学生姓名文本
	private TextView stuSexText;//学生性别文本
	private TextView stuLevelText;//学生年级文本
	private TextView stuMajorText;//学生专业文本
	private ImageView personImage;//个人头像
	private TextView modifyText;//编辑文本
	private TextView uploadText;//完成文本
	private String picPath="";//图片路径
	private String personObjectId;//当前学生的ObjectId
	private String nowpicPath;//当前图片路径
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_person);
		initViewInfo();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initViewInfo();
	}

	/**
	 * 初始化视图信息
	 */
	public void initViewInfo(){
		Iperson=new IPersonImpl();
		personImage=(ImageView)findViewById(R.id.person_image);
		modifyText=(TextView)findViewById(R.id.bianji_text);
		uploadText=(TextView)findViewById(R.id.ok_upload);
		stuNameText=(TextView)findViewById(R.id.studentName);
		stuSexText=(TextView)findViewById(R.id.sex);
		stuLevelText=(TextView)findViewById(R.id.level);
		stuMajorText=(TextView)findViewById(R.id.major);
		//更新学生信息文本
		Iperson.getStudentInfo(getIntent().getStringExtra("studentName"),
				ModifyInfoActivity.this,this);
		//编辑文本注册点击事件
		modifyText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent("android.intent.action.GET_CONTENT");//构建意图
				intent.setType("image/*");//设置MIME数据类型
				startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
			}
		});
		uploadText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isChoose()){
					doUploadImage();
				}else{
					Toast.makeText(ModifyInfoActivity.this, "不想上传新头像，那就返回吧", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
	//如果此头像和加载到APP中的头像一致，不上传
	public boolean isChoose(){
		if(picPath.equals(nowpicPath)||picPath.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	@Override
	public void setTextInfo(StudentInfo stuinfo) {
		stuNameText.setText(stuinfo.getStudentName());
		stuSexText.setText(stuinfo.getStudentSex());
		stuLevelText.setText(stuinfo.getStudentLevel());
		stuMajorText.setText(stuinfo.getMajor());
		
	}
	@Override
	public void updateImage(String imagePath) {
		if(personImage.getDrawable().getCurrent().getConstantState()
				.equals(getResources().getDrawable(R.drawable.head).getConstantState())){

			if(imagePath!=null){
				nowpicPath=imagePath;
				Bitmap rawBitmap=BitmapFactory.decodeFile(imagePath);
				//得到图片原始的高宽
				int rawHeight = rawBitmap.getHeight();
				int rawWidth = rawBitmap.getWidth();
				// 设定图片新的高宽
				int newHeight = 80;
				int newWidth = 80;
				// 计算缩放因子
				float heightScale = ((float) newHeight) / rawHeight;
				float widthScale = ((float) newWidth) / rawWidth;
				// 新建立矩阵
				Matrix matrix = new Matrix();
				matrix.postScale(heightScale, widthScale);
				// 压缩后图片的宽和高以及kB大小均会变化
				Bitmap newBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, rawWidth,
						rawHeight, matrix, true);
				//回收大图的对象
				if(!rawBitmap.isRecycled())
				{
					rawBitmap.recycle();
				}     
				personImage.setImageBitmap(RoundImage.roundImage(newBitmap));
			}else{
				Toast.makeText(ModifyInfoActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		switch (requestCode) {
		case CHOOSE_PHOTO:
			if(resultCode==RESULT_OK){
				if(Build.VERSION.SDK_INT>=19){
					//4.4及以上系统使用这个方法处理图片
					handleImageOnKitKat(data);
				}else{
					//4.4以下
					handleImageBeforeKitKat(data);
				}
			}
			break;
		default:
			break;
		}
	}
	@TargetApi(19)
	private void handleImageOnKitKat(Intent data){
		String imagePath=null;
		Uri uri=data.getData();
		if(DocumentsContract.isDocumentUri(this, uri)){
			//如果是document类型的Uri,则通过document id处理
			String docId=DocumentsContract.getDocumentId(uri);
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				//解析出数字格式的id
				String id=docId.split(":")[1];
				String selection=MediaStore.Images.Media._ID+"="+id;
				imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
			}else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				Uri contentUri=ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
				imagePath=getImagePath(contentUri, null);
			}else if("content".equalsIgnoreCase(uri.getScheme())){
				//如果不是document类型的Uri,则使用普通方式处理
				imagePath=getImagePath(uri, null);
			}

		}
	}
	private String getImagePath(Uri uri,String selection){
		String path=null;
		//通过Uri和Selection来获取真实的图片路径
		Cursor cursor=getContentResolver().query(uri, null, selection, null, null);
		if(cursor!=null){
			if(cursor.moveToFirst()){
				path=cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();
		}
		return path;


	}
	private void handleImageBeforeKitKat(Intent data){
		Uri uri=data.getData();
		String imagePath=getImagePath(uri, null);
		displayImage(imagePath);
	}
	private void displayImage(String imagePath){
		if(imagePath!=null){
			Bitmap rawBitmap=BitmapFactory.decodeFile(imagePath);
			//得到图片原始的高宽
			int rawHeight = rawBitmap.getHeight();
			int rawWidth = rawBitmap.getWidth();
			// 设定图片新的高宽
			int newHeight = 90;
			int newWidth = 90;
			// 计算缩放因子
			float heightScale = ((float) newHeight) / rawHeight;
			float widthScale = ((float) newWidth) / rawWidth;
			// 新建立矩阵
			Matrix matrix = new Matrix();
			matrix.postScale(heightScale, widthScale);
			// 压缩后图片的宽和高以及kB大小均会变化
			Bitmap newBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, rawWidth,
					rawHeight, matrix, true);
			//回收大图的对象
			if(!rawBitmap.isRecycled())
			{
				rawBitmap.recycle();
			}     
			personImage.setImageBitmap(RoundImage.roundImage(newBitmap));
			Log.d("Show", imagePath);
			picPath=imagePath;
		}else{
			Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 上传个人头像至服务器并保存到StudentInfo表中
	 */
	public void doUploadImage(){
		final BmobFile bmobFile = new BmobFile(new File(picPath));
		bmobFile.uploadblock(ModifyInfoActivity.this, new UploadFileListener() {
			@Override
			public void onSuccess() {
				StudentInfo stu=new StudentInfo();
				stu.setStudentIcon(bmobFile);
				stu.update(ModifyInfoActivity.this, personObjectId, new UpdateListener() {

					@Override
					public void onSuccess() {
						Toast.makeText(ModifyInfoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
						clearPicPath();
						initViewInfo();
						Intent intent=new Intent(ModifyInfoActivity.this,BaseActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(ModifyInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
			@Override
			public void onProgress(Integer value) {
				// 返回的上传进度（百分比）
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.d("Show","不能上传中文命名的图片哦！");
			}
		});
	}

	@Override
	public void getStudentObjectId(StudentInfo stuinfo) {
		personObjectId=stuinfo.getObjectId();
	}
	/**
	 * 清除PicPath
	 */
	public void clearPicPath(){
		picPath="";
		BmobQuery.clearAllCachedResults(getApplicationContext());
	}
	@Override
	public void startServiceForupload(BmobFile fileIcon) {
		Intent intentService=new Intent(this,UploadIconService.class);
		String IconUrl=fileIcon.getFileUrl(this);
		String fileName=fileIcon.getFilename();
		intentService.putExtra("IconUrl",IconUrl);
		intentService.putExtra("fileName", fileName);
		startService(intentService);
		
	}


}

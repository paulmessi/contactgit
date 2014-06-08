package com.contact.view;

import java.io.File;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.contact.login.R;
import com.contact.base.FileService;
import com.contact.base.Utility;


public class viewRegisiter extends Activity {

	private EditText editTextEmailReg;
	private EditText editTextPwdReg;
	private EditText editTextPwdAgnReg;
//	private RadioGroup radioGroupGender;
//	private CheckBox checkBoxFootball;
//	private CheckBox checkBoxBaskterball;
//	private CheckBox checkBoxVolleyball;
	private Button buttonRegisiter;
	private String dataPhone;

	private String dataPwd;
	private String dataPwdAgn;
	
	private ImageView faceImage;

	private String[] items = new String[] { "选择本地图片", "拍照" };
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
//	private String dataGender;
//	private ArrayList<String> dataInterestArr = new ArrayList<String>();
//	private String dataInterest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("test onCreate");
		setContentView(R.layout.layout_reg);
		init();
	}

	private void init() {

		System.out.println("test init");
		editTextEmailReg = (EditText) findViewById(R.id.editTextEmailReg);
		editTextPwdReg = (EditText) findViewById(R.id.editTextPwdReg);
		editTextPwdAgnReg = (EditText) findViewById(R.id.editTextPwdAgnReg);
//		radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGenderReg);
//		checkBoxFootball = (CheckBox) findViewById(R.id.checkBoxInterestFootball);
//		checkBoxBaskterball = (CheckBox) findViewById(R.id.checkBoxInterestBaskterball);
//		checkBoxVolleyball = (CheckBox) findViewById(R.id.checkBoxInterestVolleyball);
		faceImage = (ImageView) findViewById(R.id.editPhotoReg);
		faceImage.setOnClickListener(new faceImageOnclick());
		
		buttonRegisiter = (Button) findViewById(R.id.buttonRegisiter);		
		buttonRegisiter.setOnClickListener(new btnRegisiterOnclick());
		
//		radioGroupGender.setOnCheckedChangeListener(new radioGrpGenderOnChecked());
//		dataGender = "性别无数据";
//		checkBoxFootball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
//		checkBoxBaskterball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
//		checkBoxVolleyball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
//		dataInterestArr.add("兴趣无数据");
	}

//	class checkBoxInterestOnchecked implements
//			android.widget.CompoundButton.OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			dataInterestArr.clear();
//			if (checkBoxFootball.isChecked()) {
//				dataInterestArr.add(checkBoxFootball.getText().toString());
//			}
//			if (checkBoxBaskterball.isChecked()) {
//				dataInterestArr.add(checkBoxBaskterball.getText().toString());
//			}
//			if (checkBoxVolleyball.isChecked()) {
//				dataInterestArr.add(checkBoxVolleyball.getText().toString());
//			}
//
//		}
//
//	}
//
//	class radioGrpGenderOnChecked implements OnCheckedChangeListener {
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			int radioId = group.getCheckedRadioButtonId();
//			RadioButton currentlyRadioButton = (RadioButton) findViewById(radioId);
//			dataGender = currentlyRadioButton.getText().toString();
//		}
//	}
	
	class faceImageOnclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			showDialog();
		}
	}
	
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (FileService.hasSdcard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
				case IMAGE_REQUEST_CODE:
					startPhotoZoom(data.getData());
					break;
				case CAMERA_REQUEST_CODE:
					if (FileService.hasSdcard()) {
						File tempFile = new File(
								Environment.getExternalStorageDirectory()
										+ IMAGE_FILE_NAME);
						startPhotoZoom(Uri.fromFile(tempFile));
					} else {
						Toast.makeText(viewRegisiter.this, "未找到存储卡，无法存储照片！",
								Toast.LENGTH_LONG).show();
					}
	
					break;
				case RESULT_REQUEST_CODE:
					if (data != null) {
						getImageToView(data);
					}
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageDrawable(drawable);
		}
	}
	
	class btnRegisiterOnclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			System.out.println("test onClick");
			dataPhone = editTextEmailReg.getText().toString();
			dataPwd = editTextPwdReg.getText().toString();
			dataPwdAgn = editTextPwdAgnReg.getText().toString();
//			dataInterest = dataInterestArr.toString();
//			dataInterest = dataInterest.substring(1, dataInterest.length() - 1);

			Utility.showDialog(viewRegisiter.this, "您将要提交的数据如下:",
					"phone:" + dataPhone 
							+ new postDataClick(), null);

		}

		class postDataClick implements DialogInterface.OnClickListener {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 这里写提交给服务器的操作
				String hostName = getResources().getString(R.string.hostName);
				String url = hostName + "reg.php";
				new postDataAsyncTask().execute(url, dataPhone, dataPwd);
			}

		}
	}

	class postDataAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// 这里是数据准备处理
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 这里可以更新进度条
		}

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String email = params[1];
			String pwd = params[2];

			ArrayList<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("email", email));
			paramsList.add(new BasicNameValuePair("pwd", pwd));

			String result = Utility.postData(url, paramsList);
			return result.substring(1);
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
					.show();
			if (result.equals("注册成功")) {
				Intent intentGotoLogin = new Intent();
				intentGotoLogin.setClass(viewRegisiter.this, viewLogin.class);
				intentGotoLogin.putExtra("email", dataPhone);
				intentGotoLogin.putExtra("pwd", dataPwd);
				// startActivity(intentGotoLogin);
				setResult(1, intentGotoLogin);
				finish();
			}

		}
	}
}

package com.contact.view;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.contact.login.R;
import com.contact.base.Utility;

public class viewRegisiter extends Activity {

	private EditText editTextEmailReg;
	private EditText editTextPwdReg;
	private RadioGroup radioGroupGender;
	private CheckBox checkBoxFootball;
	private CheckBox checkBoxBaskterball;
	private CheckBox checkBoxVolleyball;
	private Button buttonRegisiter;
	private String dataEmail;
	private String dataPwd;
	private String dataGender;
	private ArrayList<String> dataInterestArr = new ArrayList<String>();
	private String dataInterest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reg);
		init();
	}

	private void init() {

		editTextEmailReg = (EditText) findViewById(R.id.editTextEmailReg);
		editTextPwdReg = (EditText) findViewById(R.id.editTextPwdReg);
//		radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGenderReg);
//		checkBoxFootball = (CheckBox) findViewById(R.id.checkBoxInterestFootball);
//		checkBoxBaskterball = (CheckBox) findViewById(R.id.checkBoxInterestBaskterball);
//		checkBoxVolleyball = (CheckBox) findViewById(R.id.checkBoxInterestVolleyball);
		buttonRegisiter = (Button) findViewById(R.id.buttonRegisiter);

//		buttonRegisiter.setOnClickListener(new btnRegisiterOnclick());
//		radioGroupGender.setOnCheckedChangeListener(new radioGrpGenderOnChecked());
//		dataGender = "性别无数据";
//		checkBoxFootball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
//		checkBoxBaskterball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
//		checkBoxVolleyball.setOnCheckedChangeListener(new checkBoxInterestOnchecked());
		dataInterestArr.add("兴趣无数据");
	}

	class checkBoxInterestOnchecked implements
			android.widget.CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			dataInterestArr.clear();
			if (checkBoxFootball.isChecked()) {
				dataInterestArr.add(checkBoxFootball.getText().toString());
			}
			if (checkBoxBaskterball.isChecked()) {
				dataInterestArr.add(checkBoxBaskterball.getText().toString());
			}
			if (checkBoxVolleyball.isChecked()) {
				dataInterestArr.add(checkBoxVolleyball.getText().toString());
			}

		}

	}

	class radioGrpGenderOnChecked implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int radioId = group.getCheckedRadioButtonId();
			RadioButton currentlyRadioButton = (RadioButton) findViewById(radioId);
			dataGender = currentlyRadioButton.getText().toString();
		}
	}

	class btnRegisiterOnclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			dataEmail = editTextEmailReg.getText().toString();
			dataPwd = editTextPwdReg.getText().toString();
			dataInterest = dataInterestArr.toString();
			dataInterest = dataInterest.substring(1, dataInterest.length() - 1);

			Utility.showDialog(viewRegisiter.this, "您将要提交的数据如下:",
					"email:" + dataEmail + "\n性别:" + dataGender + "\n兴趣:"
							+ dataInterest, new postDataClick(), null);

		}

		class postDataClick implements DialogInterface.OnClickListener {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 这里写提交给服务器的操作
				String hostName = getResources().getString(R.string.hostName);
				String url = hostName + "reg.php";
				new postDataAsyncTask().execute(url, dataEmail, dataPwd,
						dataGender, dataInterest);
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
			String gender = params[3];
			String interest = params[4];

			ArrayList<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("email", email));
			paramsList.add(new BasicNameValuePair("pwd", pwd));
			paramsList.add(new BasicNameValuePair("gender", gender));
			paramsList.add(new BasicNameValuePair("interest", interest));

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
				intentGotoLogin.putExtra("email", dataEmail);
				intentGotoLogin.putExtra("pwd", dataPwd);
				// startActivity(intentGotoLogin);
				setResult(1, intentGotoLogin);
				finish();
			}

		}
	}
}

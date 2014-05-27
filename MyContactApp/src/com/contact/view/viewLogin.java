package com.contact.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.contact.login.R;
import com.contact.base.SharedPreferenceService;
import com.contact.base.Utility;
import com.contact.base.loginApplication;
import com.contact.model.userModel;

public class viewLogin extends Activity {
	private EditText editTextEmail;
	private EditText editTextPwd;
	private Button buttonLogin;
	private Button buttonReg;
	private loginApplication loginAPP;

	// private FileService fileService = new FileService(this);
	// private String dirPath;
	// private String fileName;

	private SharedPreferenceService sharedPreferenceService = new SharedPreferenceService(
			this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		init();
	}

	private void init() {
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPwd = (EditText) findViewById(R.id.editTextPwd);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		buttonReg = (Button) findViewById(R.id.buttonReg);

		buttonReg.setOnClickListener(new regBtnOnclick());
		buttonLogin.setOnClickListener(new loginBtnOnclick());

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap = (HashMap<String, String>) sharedPreferenceService
				.readFromSharedPreference("userInfo");

		if (hashMap.size() != 0) {

			String email = hashMap.get("userName");
			String pwd = hashMap.get("pwd");

			loginHandler(email, pwd);

		}

	}

	// class autoLoginAsyncTask extends AsyncTask<Void, integer, JSONObject> {
	//
	// @Override
	// protected JSONObject doInBackground(Void... params) {
	// JSONObject tmpObject = null;
	//
	// String tmpMsg = fileService.readFileFromSdCard(dirPath, fileName);
	//
	// try {
	// tmpObject = new JSONObject(tmpMsg);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return tmpObject;
	// }
	//
	// @Override
	// protected void onPostExecute(JSONObject result) {
	// try {
	// String email = result.getString("userName");
	// String pwd = result.getString("pwd");
	// editTextEmail.setText(email);
	// editTextPwd.setText(pwd);
	//
	// loginHandler();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// }

	class loginBtnOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String email = editTextEmail.getText().toString();
			String pwd = editTextPwd.getText().toString();
			loginHandler(email, pwd);
		}
	}

	private void loginHandler(String email, String pwd) {

		if (email.length() == 0 || pwd.length() == 0) {
			// 用户名密码不允许为空
			Utility.showDialog(viewLogin.this, "提醒", "用户名密码不允许为空！");
		} else {
			String hostName = getResources().getString(R.string.hostName);
			String url = hostName + "login.php";
			new loginAsyncTask().execute(url, email, pwd);
		}

	}

	class loginAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Integer... values) {

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

			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				JSONObject jsonObj = new JSONObject(result);
				String error = jsonObj.getString("error");
				String msg = jsonObj.getString("msg");

				Toast.makeText(viewLogin.this, msg, Toast.LENGTH_SHORT).show();

				if (error.equals("0")) {
					JSONObject dataField = jsonObj.getJSONObject("dataField");

					userModel userModel = new userModel();
					userModel.setId(dataField.getString("id"));
					userModel.setUserName(dataField.getString("userName"));
					userModel.setPwd(dataField.getString("pwd"));
					userModel.setGender(dataField.getString("gender"));
					userModel.setInterest(dataField.getString("interest"));

					HashMap<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("userName", dataField.getString("userName"));
					hashMap.put("pwd", dataField.getString("pwd"));

					sharedPreferenceService.saveToSharedPreference("userInfo",
							hashMap);

					// String outMsg = dataField.toString();
					// fileService.saveFileToSdCard(dirPath, fileName, outMsg);

					loginAPP = (loginApplication) getApplication();
					loginAPP.setUserModel(userModel);

					Intent intentGotoMain = new Intent();
					intentGotoMain.setClass(viewLogin.this, viewMain.class);
					startActivity(intentGotoMain);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class regBtnOnclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intentGotoReg = new Intent();
			intentGotoReg.setClass(viewLogin.this, viewRegisiter.class);
			startActivityForResult(intentGotoReg, 1);
			// startActivity(intentGotoReg);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 1:
			if (data != null) {
				String email = data.getStringExtra("email");
				String pwd = data.getStringExtra("pwd");
				editTextEmail.setText(email);
				editTextPwd.setText(pwd);
			}
			break;
		default:
			break;

		}
	}

}

package com.contact.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contact.base.SharedPreferenceService;
import com.contact.base.loginApplication;
import com.contact.model.userModel;

public class viewMain extends Activity {

	private loginApplication loginAPP;
	// private FileService fileService = new FileService(this);
	// private String dirPath;
	// private String fileName;
	private SharedPreferenceService sharedPreferenceService = new SharedPreferenceService(
			this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginAPP = (loginApplication) getApplication();

		LinearLayout layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL);

		userModel userModel = new userModel();
		userModel = loginAPP.getUserModel();
		TextView tv = new TextView(this);
		tv.setText(userModel.getUserName() + "\n" + userModel.getGender()
				+ "\n" + userModel.getInterest());

		Button btnChangeUser = new Button(this);
		btnChangeUser.setText("切换用户");

		btnChangeUser.setOnClickListener(new btnChangeUserOnClick());

		layout.addView(tv);
		layout.addView(btnChangeUser);

		setContentView(layout);

	}

	class btnChangeUserOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {

			// dirPath = getResources().getString(R.string.AppPath);
			// fileName = getResources().getString(R.string.userInfoFileName);
			//
			// String filePath = fileService.getSDCardSysPath() + "/" + dirPath
			// + "/" + fileName;
			// fileService.delDirOrFile(filePath);
			sharedPreferenceService.clearSharedPreference("userInfo");

			Intent intentGoToLogin = new Intent();
			intentGoToLogin.setClass(viewMain.this, viewLogin.class);
			startActivity(intentGoToLogin);
			finish();

		}
	}

}

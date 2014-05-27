package com.contact.base;

import android.app.Application;

import com.contact.model.userModel;

public class loginApplication extends Application {
	private userModel userModel;

	public userModel getUserModel() {
		return userModel;
	}

	public void setUserModel(userModel userModel) {
		this.userModel = userModel;
	}

}

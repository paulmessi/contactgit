package com.contact.view;

import java.util.ArrayList;

import com.contact.adapter.UserListAdapter;
import com.contact.client.R;
import com.contact.model.group.GetGroupInfo;
import com.contact.model.group.GroupInfo;
import com.contact.model.user.GetUserInfo;
import com.contact.model.user.UserInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public class UserInfoActivity extends Activity implements OnClickListener{
	
	private UserInfo user = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		
		ImageView userPhoto = (ImageView)findViewById(R.id.user_image);
		EditText userName = (EditText)findViewById(R.id.user_name);
		EditText userAddress = (EditText)findViewById(R.id.user_address);
		
		Intent intent = this.getIntent();
		userPhoto.setBackgroundResource(intent.getIntExtra("photo", R.drawable.user_photo));
		userName.setText(intent.getStringExtra("username"));
		userAddress.setText(intent.getStringExtra("address"));
		
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
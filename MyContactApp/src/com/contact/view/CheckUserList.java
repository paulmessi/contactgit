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
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

public class CheckUserList extends Activity implements OnClickListener{
	
	private ArrayList<ArrayList<UserInfo>> childData=null;
	private ArrayList<GroupInfo> groupData=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_list);
		
		GetUserInfo getUserInfo = new GetUserInfo();
		GetGroupInfo getGroupInfo = new GetGroupInfo();
		getGroupInfo.getGroupsInPhone(this);
		this.groupData = getGroupInfo.getGroupData();
		this.childData = new ArrayList<ArrayList<UserInfo>>();
		int groupSize = this.groupData.size();
		for(int i = 0;i < groupSize;i ++)
		{
			ArrayList<UserInfo> user = getUserInfo.getUserInfoInPhoneByGroupID(Integer.valueOf(this.groupData.get(i).getGroupId()), this);
			this.childData.add(user);
			this.groupData.get(i).setChildCount(user.size());
		}
		
		GroupInfo noGroup = new GroupInfo();
		ArrayList<UserInfo> user = getUserInfo.getAllUser(this);
		noGroup.setGroupName("所有联系人");
		noGroup.setChildCount(user.size());
		this.groupData.add(noGroup);
		this.childData.add(user);
		
		UserListAdapter userListAdapter = new UserListAdapter(this,this.groupData,R.layout.group_item,this.childData,R.layout.user_itrm);
		ExpandableListView userList=(ExpandableListView)findViewById(R.id.user_groups);
		userList.setAdapter(userListAdapter);
		userList.expandGroup(0);
		//userList.setGroupIndicator(null);	
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
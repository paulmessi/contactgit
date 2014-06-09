package com.contact.model.group;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Groups;



@SuppressWarnings("deprecation")
public class GetGroupInfo
{
	private ArrayList<GroupInfo> groupData=null;
	
	
	public ArrayList<GroupInfo> getGroupData() {
		return groupData;
	}


	public void setGroupData(ArrayList<GroupInfo> groupData) {
		this.groupData = groupData;
	}

	public void getGroupsInPhone(Context context)
	{
		this.groupData = new ArrayList<GroupInfo>();
		ContentResolver resolver = context.getContentResolver();  
		//通过内容提供者去查找数据
		@SuppressWarnings("deprecation")
		Cursor cursor = resolver.query(Groups.CONTENT_URI,new String[] { ContactsContract.Groups._ID,ContactsContract.Groups.TITLE }, null, null, null);
		while (cursor.moveToNext()) {
			GroupInfo groupInfo = new GroupInfo();
			groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(Groups._ID)));
			String groupName = cursor.getString(cursor.getColumnIndex(Groups.TITLE));
			groupInfo.setGroupName(groupName);
			this.groupData.add(groupInfo);
			}

	}
	
}
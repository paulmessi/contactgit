package com.contact.model.user;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Data;

public class GetUserInfo
{
	public ArrayList<UserInfo> getUserInfoInPhoneByGroupID(int groupId,Context context)
	{
		ArrayList<UserInfo> userListByGroupId = new ArrayList<UserInfo>();
		String[] RAW_PROJECTION = new String[] { ContactsContract.Data.RAW_CONTACT_ID, };

		String RAW_CONTACTS_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
				+ "=?"
				+ " and "
				+ ContactsContract.Data.MIMETYPE
				+ "="
				+ "'"
				+ ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
				+ "'";
		// 通过分组的id outid；查询得到RAW_CONTACT_ID
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,
				RAW_CONTACTS_WHERE, new String[] { "" + groupId }, "data1 asc");

		while (cursor.moveToNext()) {
			//HashMap<String, String> map = new HashMap<String, String>();
					// RAW_CONTACT_ID
			int contactId = cursor.getInt(cursor.getColumnIndex("raw_contact_id"));
			userListByGroupId.add(getUserInfoByContactID(context,String.valueOf(contactId)));
		}
		return userListByGroupId;
	}
	

	// 查询没有分组的联系人
		public ArrayList<UserInfo> getContactsByNoGroup(Context context) {
			//Log.e(TAG, "开始查询没有分组的联系人********************");
			ArrayList<UserInfo> userListNoGroup = new ArrayList<UserInfo>();
			// 思路 我们通过组的id 去查询 RAW_CONTACT_ID, 通过RAW_CONTACT_ID去查询联系人
			// 查询未分组联系人的过滤条件
			String RAW_CONTACTS_IN_NO_GROUP_SELECTION = 
					 Data.RAW_CONTACT_ID + " not in( select "
					+ ContactsContract.Data.RAW_CONTACT_ID + " from ContactsContract.Data.CONTENT_URI where "
					+ ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
					+ "') group by " + Data.RAW_CONTACT_ID;

			Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,
					null, RAW_CONTACTS_IN_NO_GROUP_SELECTION, null, null);

			while (cursor.moveToNext()) {
				// RAW_CONTACT_ID
				int contactId = cursor.getInt(cursor
						.getColumnIndex("raw_contact_id"));
				userListNoGroup.add(getUserInfoByContactID(context,String.valueOf(contactId)));
			}
			//Log.e(TAG, "结束查询没有分组的联系人，返回结合********************" + mymaplist.size());
			return userListNoGroup;
		}
		
		public UserInfo getUserInfoByContactID(Context context,String contactId)
		{
			UserInfo user = new UserInfo();
			String[] RAW_PROJECTION02 = new String[] { ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, };

			String RAW_CONTACTS_WHERE02 = ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID
					+ "=?"
					+ " and "
					+ ContactsContract.Data.MIMETYPE
					+ "="
					+ "'"
					+ ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
					+ "'";

			Cursor cursor01 = context.getContentResolver().query(
					ContactsContract.Data.CONTENT_URI, RAW_PROJECTION02,
					RAW_CONTACTS_WHERE02, new String[] { "" + contactId },
					"data1 asc");
			String contacts_name = null;
			while (cursor01.moveToNext()) {
				contacts_name = cursor01.getString(cursor01
						.getColumnIndex("data1"));

				//Log.e(TAG, "联系人姓名:" + contacts_name);
			}
			//map.put(COLUMN_NAME, contacts_name);
			user.setContact_id(String.valueOf(contactId));
			user.setName(contacts_name);
			// 有多个号码时

			String[] RAW_PROJECTION03 = new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER, };

			String RAW_CONTACTS_WHERE03 = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
					+ "=?"
					+ " and "
					+ ContactsContract.Data.MIMETYPE
					+ "="
					+ "'"
					+ ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
					+ "'";
			Cursor cursor02 = context.getContentResolver().query(
					ContactsContract.Data.CONTENT_URI, RAW_PROJECTION03,
					RAW_CONTACTS_WHERE03, new String[] { "" + contactId },
					"data1 asc");
			HashMap<String,String> phone = new HashMap<String,String>();
			while (cursor02.moveToNext()) {
				String phoneNum = cursor02.getString(cursor02.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				String phoneName = cursor02.getString(cursor02.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				phone.put(phoneName, phoneNum);
			}
			user.setPhone(phone);
			
			String[] RAW_PROJECTION04 = new String[] { ContactsContract.CommonDataKinds.Email.ADDRESS,ContactsContract.CommonDataKinds.Email.DISPLAY_NAME, };
			Cursor emails = context.getContentResolver().query( ContactsContract.CommonDataKinds.Email.CONTENT_URI, RAW_PROJECTION04, 
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null); 
			
			HashMap<String,String> email = new HashMap<String,String>();
			while (emails.moveToNext()) { 
                String emailAddress = emails.getString(emails .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                String emailName = emails.getString(emails .getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME)); 
                email.put(emailName, emailAddress);
            } 
			user.setEmail(email);
			
			Cursor address = context.getContentResolver() 
                    .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, 
                            null, 
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID 
                                    + " = " + contactId, null, null); 
			String city = null;
			while (address.moveToNext()) { 
				city = address 
                        .getString(address 
                                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)); 
			}
			user.setAddress(city);
			
			return user;
			
		}
		
		public ArrayList<UserInfo> getAllUser(Context context)
		{
			ArrayList<UserInfo> allUsers = new ArrayList<UserInfo>();
			
			String[] PHONES_PROJECTION = new String[] {  
				       Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
			// 获取手机联系人  
			Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null); 

			while (cursor.moveToNext()) {
				//HashMap<String, String> map = new HashMap<String, String>();
						// RAW_CONTACT_ID
				int contactId = cursor.getInt(cursor.getColumnIndex(Phone.CONTACT_ID));
				allUsers.add(getUserInfoByContactID(context,String.valueOf(contactId)));
			}
			
			return allUsers;
		}
}
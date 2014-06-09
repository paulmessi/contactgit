package com.contact.model.user;

import java.util.ArrayList;
import java.util.HashMap;

import com.contact.client.R;

public class UserInfo{
	private String contact_id;
	private String name = null;
	//0为未设置性别   1为男  2为女
	private int male = 0;
	private int photoId = R.drawable.user_photo;
	
	private HashMap<String,String> phone = null ;
	private HashMap<String,String> email = null;
	private String address = null;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int isMale() {
		return male;
	}
	public void setMale(int male) {
		this.male = male;
	}
	public HashMap<String,String> getEmail() {
		return email;
	}
	public void setEmail(HashMap<String,String> email) {
		this.email = email;
	}
	public HashMap<String, String> getPhone() {
		return phone;
	}
	public void setPhone(HashMap<String, String> phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
}
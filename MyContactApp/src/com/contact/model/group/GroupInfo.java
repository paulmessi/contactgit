package com.contact.model.group;

import com.contact.client.R;

public class GroupInfo{
	private String groupId = "0";
	private int[] groupImage = {R.drawable.not_expanded,R.drawable.expanded};
	private String groupName = null;
	private int childCount = 0;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public int[] getGroupImage() {
		return groupImage;
	}
	public void setGroupImage(int[] groupImage) {
		this.groupImage = groupImage;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
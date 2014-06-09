package com.contact.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.contact.client.R;
import com.contact.model.group.GroupInfo;
import com.contact.model.user.UserInfo;
import com.contact.view.CheckUserList;
import com.contact.view.UserInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserListAdapter extends BaseExpandableListAdapter{
	
	private ArrayList<GroupInfo> groupData=null;
    int groupLayout=0;
    private ArrayList<ArrayList<UserInfo>> childData=null;
    int childLayout=0;
	private Context context = null;  
    private LayoutInflater inflater = null;
    
    public UserListAdapter(Context context, ArrayList<GroupInfo> groupData,
            int groupLayout,ArrayList<ArrayList<UserInfo>> childData, int childLayout) {
        super();
        this.context = context;
        this.groupData = groupData;
        this.groupLayout = groupLayout;
        this.childData = childData;
        this.childLayout = childLayout;
    }

	@Override
	public Object getChild(int groupPo, int userPo) {
		// TODO Auto-generated method stub
		if(this.childData.size() > groupPo && this.childData.get(groupPo).size() > userPo)
		{
			return this.childData.get(groupPo).get(userPo);
		}
		return null;
	}

	@Override
	public long getChildId(int groupPo, int userPo) {
		// TODO Auto-generated method stub
		int id = 0;
		for(int i = 0;i < groupPo;i ++ )
		{
			id += this.childData.get(i).size();
		}
		return id+userPo;
	}
	
	/**头像点击事件监听类**/
    class ImageClickListener implements OnClickListener{

        ChildViewHolder holder=null;
        int group;
        int po;
        public ImageClickListener(ChildViewHolder holder,int group,int po){
            this.holder=holder;
            this.group = group;
            this.po = po;
        }
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(context, childData.get(this.group).get(this.po).getName()+" is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, UserInfoActivity.class);
            intent.putExtra("username",childData.get(this.group).get(this.po).getName());
            intent.putExtra("address",childData.get(this.group).get(this.po).getAddress());
            intent.putExtra("photo",childData.get(this.group).get(this.po).getPhotoId());
            context.startActivity(intent);
        }
        
    }
    class ChildViewHolder{
        ImageButton userImage=null;
        TextView userName=null;
        TextView phone=null;
    }

	@Override
	public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        /**
         * 这里isLastChild目前没用到，如果出现异常再说
         */
        ChildViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(childLayout,null);
                                         //感觉这里需要把root设置成ViewGroup 对象
            /**
             * ERROR!!这里不能把null换成parent，否则会出现异常退出，原因不太确定，可能是inflate方法获得的这个item的View
             * 并不属于某个控件组，所以使用默认值null即可
             */
            holder=new ChildViewHolder();
            holder.userImage=(ImageButton)convertView.findViewById(R.id.user_list_photo);
            holder.userName=(TextView)convertView.findViewById(R.id.user_list_name);
            holder.phone=(TextView)convertView.findViewById(R.id.user_list_phone);
            convertView.setTag(holder);
        }
        else{
            holder=(ChildViewHolder)convertView.getTag();
        }
        
        holder.userImage.setBackgroundResource((Integer)(childData.get(groupPosition).get(childPosition).getPhotoId()));
        holder.userName.setText(childData.get(groupPosition).get(childPosition).getName());
        if(childData.get(groupPosition).get(childPosition).getPhone().size() <= 0)
        {
        	holder.phone.setText("无号码");
        }
        else
        {
        	Iterator iter = childData.get(groupPosition).get(childPosition).getPhone().entrySet().iterator();
        	if(iter.hasNext())
        	{
        		Map.Entry entry = (Map.Entry) iter.next();
        		String key = (String)entry.getKey();
        		String val = (String)entry.getValue();
        		holder.phone.setText(val);
        	}
        	else
        	{
        		holder.phone.setText("无号码");
        	}
        }
        //holder.phone.setText(childData.get(groupPosition).get(childPosition).getPhone().get("MOBILE"));
        holder.userImage.setOnClickListener(new ImageClickListener(holder,groupPosition,groupPosition));
        
        return convertView;
    }

	@Override
	public int getChildrenCount(int groupPo) {
		// TODO Auto-generated method stub
		return this.childData.get(groupPo).size();
	}

	@Override
	public Object getGroup(int groupPo) {
		// TODO Auto-generated method stub
		return this.childData.get(groupPo);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.childData.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	
	class GroupViewHolder{
	    ImageView image=null;
	    TextView groupName=null;
	    TextView childCount=null;
	}
	

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        GroupViewHolder holder=null;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(groupLayout, null);
            holder=new GroupViewHolder();
            //holder.image=(ImageView)convertView.findViewById(R.id.groupImage);
            holder.groupName=(TextView)convertView.findViewById(R.id.groupName);
            holder.childCount=(TextView)convertView.findViewById(R.id.groupNum);
            convertView.setTag(holder);
        }
        else{
            holder=(GroupViewHolder)convertView.getTag();
        }
        
        //isExpanded = true;
        int[] groupIndicator=(int[])groupData.get(groupPosition).getGroupImage();
        //holder.image.setBackgroundResource(groupIndicator[isExpanded?1:0]);
        holder.groupName.setText(groupData.get(groupPosition).getGroupName());
        System.out.println(groupData.get(groupPosition).getChildCount());
        holder.childCount.setText(String.valueOf(groupData.get(groupPosition).getChildCount()));
        
        return convertView;
        /**
         * 不要在适配器中调用适配器的内部方法，不然会出现奇怪的异常
         * 
         */
    }

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
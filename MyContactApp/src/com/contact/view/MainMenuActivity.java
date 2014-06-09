package com.contact.view;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.contact.client.*;

public class MainMenuActivity extends Activity implements OnClickListener{
	private Button checkListButton = null;
	private Button synNoButton = null;
	private Button publishNoButton = null;
	private Button collectNoButton = null;
	private Button radarNoButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        this.checkListButton = (Button)findViewById(R.id.check_list);
        checkListButton.setOnClickListener(this);
        this.collectNoButton = (Button)findViewById(R.id.collect_no);
        collectNoButton.setOnClickListener(this);
        this.publishNoButton = (Button)findViewById(R.id.publish_no);
        publishNoButton.setOnClickListener(this);
        this.radarNoButton = (Button)findViewById(R.id.no_radar);
        radarNoButton.setOnClickListener(this);
        this.synNoButton = (Button)findViewById(R.id.synchronous_no);
        synNoButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.check_list:
			Intent intent = new Intent(this,CheckUserList.class);
			startActivity(intent);
			break;
		case R.id.collect_no:
			break;
		case R.id.no_radar:
			break;
		case R.id.publish_no:
			break;
		case R.id.synchronous_no:
			break;
		default:
				
		}
	}
    
}

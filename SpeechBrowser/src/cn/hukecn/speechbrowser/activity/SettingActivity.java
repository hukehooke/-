/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package cn.hukecn.speechbrowser.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import cn.hukecn.speechbrowser.R;
import cn.hukecn.speechbrowser.DAO.MySharedPreferences;

/**
 * @author liweigao 2015��9��15��
 */
public class SettingActivity extends Activity {
    /*
     * @param savedInstanceState
     */
	ToggleButton btn_autoRead = null;
	ToggleButton btn_blind = null;
	ToggleButton btn_shake = null;
	ToggleButton btn_saving = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        ((TextView)findViewById(R.id.tv_titlebar)).setText("���������");
        
        btn_autoRead = (ToggleButton) findViewById(R.id.btn_autoread);
        btn_blind = (ToggleButton) findViewById(R.id.btn_blind);
        btn_shake = (ToggleButton) findViewById(R.id.btn_shake);
        btn_saving = (ToggleButton) findViewById(R.id.btn_saving);
        final SharedPreferences sp = MySharedPreferences.getInstance(getApplicationContext());
        boolean autoread = sp.getBoolean("autoread", false);
        boolean blind = sp.getBoolean("blind", false);
        boolean shake = sp.getBoolean("shake", true);
        boolean saving = sp.getBoolean("saving", false);
        btn_autoRead.setChecked(autoread);
        btn_autoRead.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = sp.edit();
				edit.putBoolean("autoread", isChecked);
				edit.commit();
			}
		});
        
        btn_blind.setChecked(blind);
        btn_blind.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = sp.edit();
				edit.putBoolean("blind", isChecked);
				edit.commit();
				
				if(isChecked)
				{
					btn_shake.setChecked(true);
					btn_autoRead.setChecked(true);
				}
			}
		});
        
        btn_shake.setChecked(shake);
        btn_shake.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = sp.edit();
				edit.putBoolean("shake", isChecked);
				edit.commit();
			}
		});
        
        btn_saving.setChecked(saving);
        btn_saving.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = sp.edit();
				edit.putBoolean("saving", isChecked);
				edit.commit();
			}
		});
    }

}

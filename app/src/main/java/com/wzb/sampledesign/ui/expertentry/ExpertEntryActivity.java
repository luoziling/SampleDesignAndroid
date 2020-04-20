package com.wzb.sampledesign.ui.expertentry;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.ui.expertentry.fragment.OtherFragment;
import com.wzb.sampledesign.ui.expertentry.fragment.ProjectFragment;
import com.wzb.sampledesign.ui.expertentry.fragment.SaveFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ExpertEntryActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expert_entry);
		BottomNavigationView navView = findViewById(R.id.nav_view_expert);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
//		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//				R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//				.build();

		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_project, R.id.navigation_save, R.id.navigation_other)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_expert);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
	}

//	private ProjectFragment projectFragment;
//	private SaveFragment saveFragment;
//	private OtherFragment otherFragment;
//
////    private TextView mTextMessage;
//
//	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//			= new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//		@Override
//		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//			switch (item.getItemId()) {
//				case R.id.navigation_project:
//					getFragment(0);
////                    mTextMessage.setText(R.string.title_home);
//					return true;
//				case R.id.navigation_save:
//					getFragment(1);
////                    mTextMessage.setText(R.string.title_dashboard);
//					return true;
//				case R.id.navigation_other:
//					getFragment(2);
////                    mTextMessage.setText(R.string.title_notifications);
//					return true;
//			}
//			return false;
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_expert_entry);
//
//		//严格模式
//		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//		StrictMode.setVmPolicy(builder.build());
//		builder.detectFileUriExposure();
//
//		getFragment(0);
//
////        mTextMessage = (TextView) findViewById(R.id.message);
//		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_expert);
//		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//	}
//
//
//	/**
//	 *
//	 * @param i
//	 * @description:用于替换fragment_container中的fragment
//	 */
//	private void getFragment(int i){
//		//Fragment管理器
//		FragmentManager manager = getSupportFragmentManager();
//		//事务
//		FragmentTransaction transaction = manager.beginTransaction();
//
//		switch (i){
//			case 0:
//				if(projectFragment == null){
//					projectFragment = new ProjectFragment();
//				}
//				transaction.replace(R.id.fragment_container,projectFragment);
//				break;
//			case 1:
//				if(saveFragment == null){
//					saveFragment = new SaveFragment();
//				}
//				transaction.replace(R.id.fragment_container,saveFragment);
//				break;
//			case 2:
//				if(otherFragment == null){
//					otherFragment = new OtherFragment();
//				}
//				transaction.replace(R.id.fragment_container,otherFragment);
//				break;
//		}
//
//		//提交事务
//		transaction.commit();
//	}

}

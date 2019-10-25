package com.wzb.sampledesign.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.adapter.DetailAdapter;
import com.wzb.sampledesign.pojo.DocInfo2;

public class DetailActivity extends AppCompatActivity {

	@BindView(R.id.detailListView)
	ListView detailListView;

	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		ButterKnife.bind(this);

		init();


	}

	private void init(){
		Intent intent = getIntent();
		gson = new Gson();
		Bundle bundle = intent.getExtras();
		String docJson = bundle.getString("conDetail");
		DocInfo2 docInfo2 = gson.fromJson(docJson,DocInfo2.class);

		DetailAdapter detailAdapter = new DetailAdapter(this,docInfo2);
		detailListView.setAdapter(detailAdapter);

	}
}

package com.wzb.sampledesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzb.sampledesign.pojo.Conclusion;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/10/14
 * Author:Satsuki
 * Description:
 * 新项目
 */
public class ConclusionAdapater extends BaseAdapter {

	private Context context;
	private List<Conclusion> datas = new ArrayList<>();

	public ConclusionAdapater() {
	}

	public ConclusionAdapater(Context context, List<Conclusion> datas) {
		this.context = context;
		this.datas = datas;
	}

	//添加item数据
	public void addData(Conclusion conclusion){
		datas.add(conclusion);

//        if(datas != null){
//            datas.add(conclusion);
//        }
	}

	//移除item数据
	public void delData(int position){
		try {
			datas.remove(position);
		}catch (Exception e){
			e.printStackTrace();
		}
//        if(datas != null && datas.size()>0){
//            datas.remove(position);
//        }
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int i) {
		return datas.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		Log.e("getView","111");

		View itemView = view;
		if (itemView == null){
			itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2,viewGroup,false);
		}

		TextView line1 =(TextView)itemView.findViewById(android.R.id.text1);
		TextView line2 =(TextView)itemView.findViewById(android.R.id.text2);
		line1.setTextColor(Color.parseColor("#000000"));
		line2.setTextColor(Color.parseColor("#000000"));

		line1.setGravity(Gravity.CENTER);
		line2.setGravity(Gravity.CENTER);

		//获取对应的item
		Conclusion conclusion = (Conclusion) getItem(i);

		if(conclusion.getPlan()!=null){
			//显示计划名
			line1.setText("计划名："+conclusion.getPlan());
		}

		if(conclusion.getPriority()!=null){
			//显示优先值
			line2.setText("优先值：" + conclusion.getPriority().toString());
		}

		return itemView;
	}
}

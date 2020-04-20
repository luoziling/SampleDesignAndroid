package com.wzb.sampledesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.DocInfo2;

/**
 * Date: 2019/10/14
 * Author:Satsuki
 * Description:
 */
public class DetailAdapter extends BaseAdapter {

	private Context context;
//	private List<DocInfo2> datas = new ArrayList<>();
	private DocInfo2 data;

	public DetailAdapter() {
	}

	public DetailAdapter(Context context, DocInfo2 data) {
		this.context = context;
		this.data = data;
	}



	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int i) {
		return data;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		View itemView = view;
		if (itemView == null){
			itemView = LayoutInflater.from(context).inflate(R.layout.simple_list_item_5,viewGroup,false);
		}

		TextView line1 =(TextView)itemView.findViewById(R.id.text1);
		TextView line2 =(TextView)itemView.findViewById(R.id.text2);
		TextView line3 =(TextView)itemView.findViewById(R.id.text3);
		TextView line4 =(TextView)itemView.findViewById(R.id.text4);
		TextView line5 =(TextView)itemView.findViewById(R.id.text5);
		TextView line6 =(TextView)itemView.findViewById(R.id.text6);
		TextView line7 =(TextView)itemView.findViewById(R.id.text7);
		TextView line8 =(TextView)itemView.findViewById(R.id.text8);


		line1.setTextColor(Color.parseColor("#000000"));
		line2.setTextColor(Color.parseColor("#000000"));
		line3.setTextColor(Color.parseColor("#000000"));
		line4.setTextColor(Color.parseColor("#000000"));
		line5.setTextColor(Color.parseColor("#000000"));
		line6.setTextColor(Color.parseColor("#000000"));
		line7.setTextColor(Color.parseColor("#000000"));
		line8.setTextColor(Color.parseColor("#000000"));

		line1.setGravity(Gravity.CENTER);
		line2.setGravity(Gravity.CENTER);
		line3.setGravity(Gravity.CENTER);
		line4.setGravity(Gravity.CENTER);
		line5.setGravity(Gravity.CENTER);
		line6.setGravity(Gravity.CENTER);
		line7.setGravity(Gravity.CENTER);
		line8.setGravity(Gravity.CENTER);

		//获取对应的item
//		Conclusion conclusion = (Conclusion) getItem(i);

		DocInfo2 docInfo2 = data;

//		if(conclusion.getPlan()!=null){
//			//显示计划名
//			line1.setText("所属医院：："+conclusion.getPlan());
//		}
//
//		if(conclusion.getPriority()!=null){
//			//显示优先值
//			line2.setText("优先值：" + conclusion.getPriority().toString());
//		}

		line1.setText("医院科室:" + docInfo2.getDepartments());
		line2.setText("职位:" + docInfo2.getOcc());
		line3.setText("预约量:" + docInfo2.getNapm());
		line4.setText("问诊量:" + docInfo2.getNacc());
		line5.setText("综合评分:" + docInfo2.getErate());
		line6.setText("图文问诊价格:" + docInfo2.getTuweiprice());
		line7.setText("视话问诊价格:" + docInfo2.getShihuaprice());
		line8.setText("总评论人数:" + docInfo2.getNcomment());

		return itemView;
	}
}

package com.wzb.sampledesign.ui.expertentry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.wzb.sampledesign.pojo.ProjectInformation;

import java.util.List;

/**
 * Date: 2019/5/3
 * Author:Satsuki
 * Description:
 */
public class SaveAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProjectInformation> projectInformationList;

    public SaveAdapter(Context context, List<ProjectInformation> list){
        mContext = context;
        projectInformationList = list;
    }

    @Override
    public int getCount() {
        Log.e("size", String.valueOf(projectInformationList.size()));
        return projectInformationList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectInformationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
//            itemView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2,parent,false);
            itemView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2,parent,false);
        }
        TextView line1 =(TextView)itemView.findViewById(android.R.id.text1);
        TextView line2 =(TextView)itemView.findViewById(android.R.id.text2);
        line1.setTextColor(Color.parseColor("#000000"));
        line2.setTextColor(Color.parseColor("#000000"));

        //获取对应的item
        ProjectInformation projectInformation = (ProjectInformation) getItem(position);

        Log.e("projectInformation",projectInformation.toString());

        //显示项目名
        line1.setText("项目名："+projectInformation.getProjectName());
        //显示层数
        line2.setText("层数："+projectInformation.getLayer().toString());//Resources$NotFoundException String resource ID #0x1


//        return null;//java.lang.NullPointerException: Attempt to invoke virtual method 'int android.view.View.getImportantForAccessibility()' on a null object reference
        return itemView;

    }

    public void refresh(List<ProjectInformation> data){
        projectInformationList = data;
        notifyDataSetChanged();
    }
}

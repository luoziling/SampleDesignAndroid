package com.wzb.sampledesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzb.sampledesign.pojo.ProjectInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/5/10
 * Author:Satsuki
 * Description:
 * 旧项目
 */
public class ModelAdapter extends BaseAdapter {

    private Context context;
    private static List<ProjectInformation> datas = new ArrayList<>();

    public ModelAdapter(Context context, List<ProjectInformation> datas){
        this.context = context;
        this.datas = datas;
    }

    //添加item数据
    public void addData(ProjectInformation projectInformation){
        datas.add(projectInformation);

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
    public Object getItem(int position) {
        return datas.get(position);
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
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        }

        TextView line1 =(TextView)itemView.findViewById(android.R.id.text1);
        line1.setTextColor(Color.parseColor("#000000"));
        line1.setGravity(Gravity.CENTER);

        //获取对应的item
        ProjectInformation projectInformation = (ProjectInformation)getItem(position);
        String projectName = projectInformation.getProjectName();

        if(null!=projectName){
            //显示模型名
            line1.setText("模型名："+projectName);
        }




        return itemView;
    }
}

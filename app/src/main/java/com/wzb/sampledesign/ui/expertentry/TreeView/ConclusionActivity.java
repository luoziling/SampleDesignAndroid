package com.wzb.sampledesign.ui.expertentry.TreeView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.unnamed.b.atv.model.TreeNode;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.adapter.ConclusionAdapter;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.result.CommonResult;
import com.wzb.sampledesign.pojo.result.ConclusionResult;
import com.wzb.sampledesign.ui.asynctask.expertTask.DeleteConclusionThread;
import com.wzb.sampledesign.ui.asynctask.expertTask.GetConclusionsThread;
import com.wzb.sampledesign.ui.asynctask.expertTask.SaveConclusionThread;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2019/5/10
 * Author:Satsuki
 * Description:
 */
public class ConclusionActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.conclusion_listView)
    public ListView listView;

    @BindView(R.id.add_conclusion_button)
    public QMUIRoundButton roundButton;

    private ConclusionAdapter conclusionAdapter;

//    private ConclusionDao conclusionDao;

    private List<Conclusion> initList;

    private TreeNode myRoot;

    private Handler mHandler;

    private Conclusion conclusion;

    private int mPosition;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion_editable);
        ButterKnife.bind(this);
        roundButton.setOnClickListener(this);

        mHandler = new MyHandler(this);


        //get DAO DAO(Data Access Object)
//        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
//        DaoSession daoSession = ((App)getApplication().getApplicationContext()).getDaoSession();
//        conclusionDao = daoSession.getConclusionDao();

        initList = new ArrayList<>();

        // todo:
//        Init init = new Init();
//        init.execute();
        GetConclusionsThread getConclusionsThread = new GetConclusionsThread(mHandler);
        Thread asyncThread = new Thread(getConclusionsThread);
        asyncThread.start();



        listView.setOnItemLongClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_conclusion_button:
                //添加listView的item
                final EditText et = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("添加")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "添加内容不能为空！",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    conclusion = new Conclusion();
                                    conclusion.setPlan(input);
                                    conclusion.setProjectName(Constant.PROJECTNAMEEXPERT);
                                    conclusion.setProjectId(Constant.PROJECTID);
                                    conclusion.setUserId(Constant.loginUser.getId());



                                    // 启动线程
                                    SaveConclusionThread saveConclusionThread = new SaveConclusionThread(mHandler,conclusion);
                                    Thread asyncThread = new Thread(saveConclusionThread);
                                    asyncThread.start();

                                    // 放到handler中
//                                    conclusionAdapter.addData(conclusion);
//                                    conclusionAdapter.notifyDataSetChanged();
                                    //todo:保存至数据库

//                                    SaveConclusion saveConclusion = new SaveConclusion(conclusion);
//                                    saveConclusion.execute();

                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle("是否删除该结论")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo:从数据库中删除记录
                        Conclusion conclusion = (Conclusion) conclusionAdapter.getItem(position);

                        conclusion.setUserId(Constant.loginUser.getId());
                        conclusion.setProjectId(Constant.PROJECTID);
                        mPosition = position;

                        Log.e("delData",conclusion.toString());
                        // 启动线程
                        DeleteConclusionThread deleteConclusionThread = new DeleteConclusionThread(mHandler,conclusion);
                        Thread asyncThread = new Thread(deleteConclusionThread);
                        asyncThread.start();

//                        DeleteConclusion deleteConclusion = new DeleteConclusion(conclusion);
//
//                        deleteConclusion.execute();

                        //在UI中删除
//                        conclusionAdapter.delData(position);
//                        conclusionAdapter.notifyDataSetChanged();


                    }
                })
                .setNegativeButton("否",null)
                .show();
        return false;
    }

    // todo:放到后台
//    List<Conclusion> search(){
//        QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//        initList = cqb.where(ConclusionDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)).list();
//        Log.e("initList",initList.toString());
//        return initList;
//    }

    // todo:放到后台
//    void search1(){
//        QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//        Log.e("initList",cqb
//                .where(ConclusionDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT))
//                .list().toString());
//    }

    // todo:放到后台
//    public class Init extends AsyncTask<Void, Integer, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            search();
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            //初始化ListView
//            Log.e("initList",initList.toString());
//
//            conclusionAdapter = new ConclusionAdapter(getApplicationContext(),initList);
//            conclusionAdapter.notifyDataSetChanged();
//
//            listView.setAdapter(conclusionAdapter);
//        }
//    }

    // todo:放到后台
//    public class SaveConclusion extends AsyncTask<Void, Integer, Boolean> {
//        private Conclusion conclusion;
//        SaveConclusion(Conclusion conclusion){
//            this.conclusion = conclusion;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            conclusion.setProjectName(Constant.PROJECTNAMEEXPERT);
//            conclusionDao.insert(conclusion);
//            search1();
//
//            return null;
//        }
//    }

    // todo:放到后台
//    public class DeleteConclusion extends AsyncTask<Void, Integer, Boolean> {
//        private Conclusion conclusion;
//        DeleteConclusion(Conclusion conclusion){
//            this.conclusion = conclusion;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//            DeleteQuery<Conclusion> dcqb;
//
//            dcqb = cqb.where(
//                    cqb.and(ConclusionDao.Properties.Plan.eq(conclusion.getPlan())
//                            ,ConclusionDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT))
//            ).buildDelete();
//
//            dcqb.executeDeleteWithoutDetachingEntities();
//
//            search1();
//
//            return null;
//        }
//    }



    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        // 显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setTitle("返回");
    }


    @Override
    public void onBackPressed() {

        /**
         * 　　// 完全由自己控制返回键逻辑，系统不再控制，但是有个前提是：
         * 　　// 不要在Activity的onKeyDown或者OnKeyUp中拦截掉返回键
         *
         * 　　// 拦截：就是在OnKeyDown或者OnKeyUp中自己处理了返回键
         * 　　//（这里处理之后return true.或者return false都会导致onBackPressed不会执行）
         *
         * 　　// 不拦截：在OnKeyDown和OnKeyUp中返回super对应的方法
         * 　　//（如果两个方法都被覆写就分别都要返回super.onKeyDown,super.onKeyUp）
         */

        Log.e("on","onBackPressed");

        Bundle b = new Bundle();
        b.putString("ProjectName",Constant.PROJECTNAMEEXPERT);
        b.putString("Origin","Save");
        //启动TreeViewActivity
        Intent myIntent = new Intent(this, TreeViewActivity.class);
        myIntent.putExtras(b);
        startActivity(myIntent);


    }
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private class MyHandler extends Handler{
        private Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle msgBundle;
            switch (msg.what){
                case Constant.GETCONCLUSIONS:
                    // 处理逻辑
                    msgBundle = msg.getData();

                    // 看看http请求是否成功
                    if (!msgBundle.getBoolean("result")){
                        showToast("网络出错请重试");
                    }else {
                        // http请求成功
                        // 回复的json转为对象
                        ConclusionResult result = FastjsonUtil.from(msgBundle.getString("response"),ConclusionResult.class);
                        Log.e("result",result.toString());
                        // 显示验证信息
                        showToast(result.getReviews());
                        // 判断验证是否成功
                        if (result.isFlag()){
                            // UI更新
                            conclusionAdapter = new ConclusionAdapter(getApplicationContext(),result.getConclusions());
                            conclusionAdapter.notifyDataSetChanged();

                            listView.setAdapter(conclusionAdapter);

                        }else {
//                            保存失败要重试
                        }
                    }
                    break;
                case Constant.SAVECONCLUSIONS:
                    // 处理逻辑
                    msgBundle = msg.getData();

                    // 看看http请求是否成功
                    if (!msgBundle.getBoolean("result")){
                        showToast("网络出错请重试");
                    }else {
                        // http请求成功
                        // 回复的json转为对象
                        CommonResult result = FastjsonUtil.from(msgBundle.getString("response"),CommonResult.class);
                        Log.e("result",result.toString());
                        // 显示验证信息
                        showToast(result.getReviews());
                        // 判断验证是否成功
                        if (result.isFlag()){
                            Log.e("addData",conclusion.toString());
                            // UI更新
                            conclusionAdapter.addData(conclusion);
                            conclusionAdapter.notifyDataSetChanged();

                        }else {
//                            保存失败要重试
                        }
                    }
                    break;
                case Constant.DELETECONCLUSIONS:
                    // 处理逻辑
                    msgBundle = msg.getData();

                    // 看看http请求是否成功
                    if (!msgBundle.getBoolean("result")){
                        showToast("网络出错请重试");
                    }else {
                        // http请求成功
                        // 回复的json转为对象
                        CommonResult result = FastjsonUtil.from(msgBundle.getString("response"),CommonResult.class);
                        Log.e("result",result.toString());
                        // 显示验证信息
                        showToast(result.getReviews());
                        // 判断验证是否成功
                        if (result.isFlag()){
                            Log.e("DELETE",conclusion.toString());
                            // UI更新
                            //在UI中删除
                            conclusionAdapter.delData(mPosition);
                            conclusionAdapter.notifyDataSetChanged();

                        }else {
//                            保存失败要重试
                        }
                    }
                    break;
            }
        }
    }



}

package com.wzb.sampledesign.ui.expertentry.SmartTable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.selected.BaseSelectFormat;
import com.bin.david.form.data.table.FormTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.pojo.Conclusion;
import com.wzb.sampledesign.pojo.MatrixStorage;
import com.wzb.sampledesign.pojo.TreeNodeContent;
import com.wzb.sampledesign.pojo.result.CommonResult;
import com.wzb.sampledesign.pojo.result.MatrixResult;
import com.wzb.sampledesign.ui.asynctask.SaveMSThread;
import com.wzb.sampledesign.ui.asynctask.expertTask.GetMatrixThread;
import com.wzb.sampledesign.ui.asynctask.expertTask.SaveMatrixThread;
import com.wzb.sampledesign.ui.expertentry.TreeView.TreeViewActivity;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by huang on 2018/4/10.
 */

public class FormModeActivity extends AppCompatActivity {

    private SmartTable<Form> table;
    private View llBottom;
    private Button searchBtn;
    private EditText editText;
    private Form selectForm;

//    private TreeNodeContentDao treeNodeContentDao;
//    private AdjacentClosureDao adjacentClosureDao;
//    private ConclusionDao conclusionDao;
//    private MatrixStorageDao matrixStorageDao;
//    private NormalizationWeightDao normalizationWeightDao;


    List<AdjacentClosure> adjacentClosureList;
    List<Conclusion> conclusionList;
    List<String> tableNode = new ArrayList<>();
    List<AdjacentClosure> depthOneAdj = new ArrayList<>();
    List<MatrixStorage> matrixStorageList;

    Form[][] myForm;
    int n;

    String nodeValue;

    private Handler mHandler;

    FormTableData<Form> tableData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);

        mHandler = new MyHandler(this);

        //get DAO DAO(Data Access Object)
//        DaoSession daoSession = ((App)getApplication()).getDaoSession();
//        treeNodeContentDao = daoSession.getTreeNodeContentDao();
//        adjacentClosureDao = daoSession.getAdjacentClosureDao();
//        conclusionDao = daoSession.getConclusionDao();
//        matrixStorageDao = daoSession.getMatrixStorageDao();
//        normalizationWeightDao = daoSession.getNormalizationWeightDao();

        table = (SmartTable<Form>) findViewById(R.id.table);
        int dp5 = DensityUtils.dp2px(this,10);
        table.getConfig().setVerticalPadding(dp5)
        .setTextLeftOffset(dp5);
        llBottom = findViewById(R.id.ll_bottom);
        searchBtn = (Button) findViewById(R.id.tv_query);
        Bundle bundle = getIntent().getExtras();
        nodeValue = bundle.get("itemText").toString();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //设置点击监听事件 修改表格中的值
            public void onClick(View v) {
//                if(selectForm !=null){
//                    selectForm.setName(editText.getText().toString());
//                    table.invalidate();
//                    editText.setText("");
//                }
                // 监听事件保存全表数值
                // 获取数据
                Double[][] data = new Double[n][n];
                for(int i = 1;i<n+1;i++){
                    for(int j = 1;j<n+1;j++){
                        if(myForm[i][j].getName()!=""&&myForm[i][j].getName()!=null){
                            //当获取的不为空
                            data[i-1][j-1] = Double.valueOf(myForm[i][j].getName());
                        }else {
                            //否则赋值为0
                            data[i-1][j-1]=0.0;
                        }

                    }
                }
                // 开启子线程传输数据与处理
                SaveMatrixThread saveMatrixThread = new SaveMatrixThread(mHandler,data,nodeValue,tableNode);
                Thread asyncThread = new Thread(saveMatrixThread);
                asyncThread.start();


            }
        });
        editText = (EditText)findViewById(R.id.edit_query) ;
        List<Form> formList = new ArrayList<>();
        for(int i=0;i<10;i++){
            formList.add(new Form("1", Paint.Align.LEFT));
        }

//        Form[][] forms = {
//                {
////                    for(int i = 0 ; i<formList.size();i++){
////                        formList.get(i),
////                    }
//                        Form.Empty,
////                        for(int i = 0;i<10;i++){
////                            new Form("i:" + i,Paint.Align.LEFT),
////                        }
//                        formList.get(1),
//
//                        new Form("姓名", Paint.Align.LEFT), Form.Empty,
//                        new Form("性别", Paint.Align.LEFT), Form.Empty,
//                        new Form("出生日期", Paint.Align.LEFT),Form.Empty,
//                        new Form("民族", Paint.Align.LEFT), Form.Empty,
//                        new Form("婚否", Paint.Align.LEFT), Form.Empty,
//                        new Form(1, 4, "照片")
//                },
//                {
//                        new Form("学历", Paint.Align.LEFT), Form.Empty,
//                        new Form("专业", Paint.Align.LEFT), new Form(3, 1, ""),
//                        new Form("何种语言", Paint.Align.LEFT), new Form(3, 1, "")
//                },
//                {
//                        new Form("籍贯", Paint.Align.LEFT), Form.Empty,
//                        new Form(2, 1, "户口所在地", Paint.Align.LEFT), new Form(3, 1, ""),
//                        Form.Empty, new Form(2, 1, "")
//                },
//                {
//                        new Form(2, 1, "现住址电话", Paint.Align.LEFT), new Form(8, 1, "")
//
//                },
//                {
//                        new Form(2, 1, "身份证号码", Paint.Align.LEFT), new Form(4, 1, "")
//                        , new Form(2, 1, "暂住证号码", Paint.Align.LEFT), new Form(3, 1, "")
//                },
//                {
//                        new Form(2, 1, "应急联系人及电话", Paint.Align.LEFT), new Form(4, 1, "")
//                        , new Form(2, 1, "联系人电话号码", Paint.Align.LEFT), new Form(3, 1, "")
//                },
//                {
//                        new Form(2, 1, "申请职位", Paint.Align.LEFT), new Form(4, 1, "")
//                        , new Form(2, 1, "本人要求待遇", Paint.Align.LEFT), new Form(3, 1, "")
//                },
//               {
//                        new Form(11, 1, "家庭成员及主要社会关系")
//                },
//
//                {
//                        new Form(2, 1, "姓名"),
//                        new Form(2, 1, "与本人关系"),
//                        new Form(7, 1, "单位及职务"),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(2, 1, ""),
//                        new Form(2, 1, ""),
//                        new Form(7, 1, ""),
//                },
//                {
//                        new Form(11, 1, "工作经历")
//                },
//                {
//                        new Form(4, 1, "起止时间"),
//                        new Form(6, 1, "单位"),
//                        new Form(1, 1, ""),
//                },
//                {
//                        new Form(4, 1, ""),
//                        new Form(6, 1, ""),
//                        new Form(1, 1, ""),
//                },
//                {
//                        new Form(4, 1, ""),
//                        new Form(6, 1, ""),
//                        new Form(1, 1, ""),
//                },
//                {
//                        new Form(4, 1, ""),
//                        new Form(6, 1, ""),
//                        new Form(1, 1, ""),
//                },
//                {
//                        new Form(11, 1, "本人保证以下资料全部属实，否则本人愿意承担由此造成的一切后果")
//                },
//                {
//                         new Form(2, 1, "申请人签名"), new Form(4, 1, "")
//                        , new Form(2, 1, "日期"), new Form(3, 1, "")
//                }
//
//        };


//        String nodeValue = bundle.get("itemText").toString();

//        //从数据库中获取数据
//        GetData getData = new GetData(bundle,getApplicationContext());
//        getData.execute();

        // 数据初始化
        init();
        //从数据库中加载数据
        //n:子数组个数要产生一个n+1*n+1的表格
//        int n = 5;
//        //n+1是为了空出第一个格子
//        Form[][] myForm = new Form[n+1][n+1];
//        //赋值与初始化
//        //第一行第一列：
//        myForm[0][0] = Form.Empty;
//        //第一行：
//        for(int i = 1;i<myForm[0].length;i++){
//            myForm[0][i] = new Form("子节点名", Paint.Align.LEFT);
//        }
//        //第一列：
//        for(int i = 1;i<n+1;i++){
//            myForm[i][0] = new Form("子节点名", Paint.Align.LEFT);
//        }
//        //第二行第二列至最后一行最后一列：
//        for(int i = 1;i<n+1;i++){//行循环第二行至最后一行
//            for(int j = 1;i<n+1;j++){
//                //每一行第二列至最后一列
//                myForm[i][j] = Form.Empty;
//                //或者
////                myForm[i][j] = new Form("",Paint.Align.LEFT);
//            }
//        }

//        final FormTableData<Form> tableData = FormTableData.create(table, "登记表", 11, forms);
//        tableData.setFormat(new IFormat<Form>() {
//            @Override
//            public String format(Form form) {
//                if (form != null) {
//                    return form.getName();
//                } else {
//                    return "";
//                }
//            }
//        });
//        table.setSelectFormat(new BaseSelectFormat());
//        tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
//            @Override
//            public void onClick(Column column, String value, Form form, int col, int row) {
//                //这边能不能使用alertDialog做
//                if(form !=null){
//                    selectForm = form;
//                    editText.setFocusable(true);
//                    editText.setFocusableInTouchMode(true);
//                    editText.requestFocus();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//
//            }
//
//        });
//        table.getConfig().setTableGridFormat(new BaseGridFormat(){
//            @Override
//            protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
//                if(row == tableData.getLineSize() -1){
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
//                if(row == tableData.getLineSize() -1){
//                    return false;
//                }
//                return true;
//            }
//        });
//        table.setTableData(tableData);


    }

    private void init(){
        // 初始化界面
        // 如果有下一层节点
        if (Constant.expertNodes!=null&&Constant.expertNodes.size()>0){
            // 初始化清空数据
            tableNode = new ArrayList<>();
            for(TreeNodeContent treeNodeContent: Constant.expertNodes){
                tableNode.add(treeNodeContent.getValue());
            }
        }

        // 如果有结论
        if (Constant.expertCons!=null&&Constant.expertCons.size()>0){
            tableNode = new ArrayList<>();
            for(Conclusion conclusion: Constant.expertCons){
                tableNode.add(conclusion.getPlan());
            }
        }

        // 置空缓存数据
        Constant.expertCons = null;
        Constant.expertNodes = null;
        Constant.expertClos = null;

        //创建表格
        //从数据库中加载数据
        //n:子数组个数要产生一个n+1*n+1的表格
        n = tableNode.size();
        Log.e("n:",String.valueOf(n));
        //n+1是为了空出第一个格子
        myForm = new Form[n+1][n+1];
        //赋值与初始化
        //第一行第一列：
        myForm[0][0] = Form.Empty;
        //第一行：
        for(int i = 1;i<myForm[0].length;i++){
//                    myForm[0][i] = new Form("子节点名", Paint.Align.LEFT);
            myForm[0][i] = new Form(tableNode.get(i-1), Paint.Align.LEFT);
        }
        //第一列：
        for(int i = 1;i<n+1;i++){
            myForm[i][0] = new Form(tableNode.get(i-1), Paint.Align.LEFT);
        }
        //第二行第二列至最后一行最后一列：
        for(int i = 1;i<n+1;i++){//行循环第二行至最后一行
            for(int j = 1;j<n+1;j++){
//                        Log.e("i",String.valueOf(i));
//                        Log.e("j",String.valueOf(j));
                //每一行第二列至最后一列
                if(i==j){
                    //中间分割线
                    myForm[i][j] = new Form("1");
                }else{
                    //否则为空
//                            myForm[i][j] = Form.Empty;
                    //否则为1，初始化置1消除bug（测试 (测试成功
                    myForm[i][j] = new Form("1");
                }

                //或者
//                myForm[i][j] = new Form("",Paint.Align.LEFT);
            }
        }

        // 查询是否数据库已有数据，如果有则恢复数据
        MatrixStorage matrixStorage = new MatrixStorage();
        matrixStorage.setProjectId(Constant.PROJECTID);
        matrixStorage.setValue(nodeValue);
        GetMatrixThread getMatrixThread = new GetMatrixThread(mHandler,matrixStorage);
        Thread asyncThread = new Thread(getMatrixThread);

        asyncThread.start();


//        //如果在数据库中已经存储过
//        if(matrixStorageList.size()>0){
//            //从数据库中加载数据
////                    int size = matrixStorageList.size();
//            int size=0;
//            //第二行第二列至最后一行最后一列：
//            for(int i = 1;i<n+1;i++) {//行循环第二行至最后一行
//                for (int j = 1; j < n + 1; j++) {
//                    myForm[i][j] = new Form(matrixStorageList.get(size).getMatrixValue().toString());
//                    size++;
//                    Log.e("myForm[i][j]",myForm[i][j].getName());
//                }
//            }
//        }

//        //创建表格
//        tableData = FormTableData.create(table, "测试表", 11, myForm);
//        tableData.setFormat(new IFormat<Form>() {
//            @Override
//            public String format(Form form) {
//                if (form != null) {
//                    return form.getName();
//                } else {
//                    return "";
//                }
//            }
//        });
//        table.setSelectFormat(new BaseSelectFormat());
//        tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
//            @Override
//            public void onClick(Column column, String value, Form form, int col, int row) {
////                        Log.e("column",String.valueOf(column));
////                        Log.e("value",String.valueOf(value));
////                        Log.e("form",form.toString());
////                        Log.e("col",String.valueOf(col));
////                        Log.e("row",String.valueOf(row));
//                //这边能不能使用alertDialog做
//                if(form !=null){
//                    final EditText et = new EditText(getApplicationContext());
//                    et.setTextColor(getResources().getColor(R.color.my_dark));
//                    new AlertDialog.Builder(FormModeActivity.this)
//                            .setTitle("请输入表格内容")
//                            .setView(et)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String input = et.getText().toString();
//                                    if (input.equals("")) {
//                                        Toast.makeText(getApplicationContext(), "添加内容不能为空！" + input, Toast.LENGTH_LONG).show();
//                                    }
//                                    else {
//                                        //如果是数字
////                                                if(isNumericZidai(input)){
//                                        if(CheckNumbers(input)){
//                                            //表格添加输入的数据
////                                                    form.setName(input);//应该是这里出错了直接将整个form都改成了1
//                                            Log.e("input:",input);
//
//                                            //获取Double数据
//                                            //保留三位小数
////                                                    DecimalFormat df = new DecimalFormat("0.000");
//                                            //保留二位小数
//                                            DecimalFormat df = new DecimalFormat("0.00");
//                                            Double data = Double.valueOf(input);
//                                            Double reciprocal = 1/data;
//                                            Log.e("reciprocal:",String.valueOf(reciprocal));
//                                            //如何把talbe[j][i]也添加数据
//                                            //把倒数添加进[j][i]
//                                            //行
//                                            Log.e("row",String.valueOf(row));
//                                            //列
//                                            Log.e("col",String.valueOf(col));
////                                                    myForm[col][row].setName(String.valueOf(reciprocal));
//                                            //在此处添加输入值即可
//                                            myForm[row][col].setName(input);
//                                            myForm[col][row].setName(df.format(reciprocal));
//                                            // 放到按钮监听，点击存储。
////                                            //表格存入数据库
////                                            SaveTable saveTable = new SaveTable(row,col);
////                                            saveTable.execute();
//
//                                        }else{
//                                            Toast.makeText(getApplicationContext(), "添加内容不能为非数字内容！" + input, Toast.LENGTH_LONG).show();
//                                        }
//
//                                    }
//
//                                }
//                            })
//                            .setNegativeButton("取消",null)
//                            .show();
//
//                }
//
//            }
//
//        });
//        table.getConfig().setTableGridFormat(new BaseGridFormat(){
//            @Override
//            protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
//                if(row == tableData.getLineSize() -1){
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
//                if(row == tableData.getLineSize() -1){
//                    return false;
//                }
//                return true;
//            }
//        });
//        //固定表格行列
//        table.getConfig().setFixedFirstColumn(true);
//        table.getConfig().setFixedCountRow(true);
//        table.setTableData(tableData);


    }

    public class GetData extends AsyncTask<Void, Integer, Boolean> {
        //在这边要做加载的初始化
        private Bundle bundle;
        private boolean flag = false;
        private Context context;
        public GetData(Bundle bundle, Context context){
            this.bundle = bundle;
            this.context = context;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            //todo:从数据库中查看当前节点有没有下一层节点
            //若没有则查询当前项目有没有结论/计划
//            QueryBuilder<AdjacentClosure> aqb = adjacentClosureDao.queryBuilder();
//            QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//            QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();

            nodeValue = bundle.get("itemText").toString();
            //todo:根据内容获取ID
//            Long myID = tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)
//                    ,TreeNodeContentDao.Properties.Value.eq(nodeValue)))
//                    .unique().getId();
//            Log.e("myID",myID.toString());
            //todo:根据该节点查询以该节点为祖先节点的所有记录
            //查询该节点有多少后裔节点
//            adjacentClosureList = aqb.where(aqb.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)
//                    ,AdjacentClosureDao.Properties.Ancestor.eq(myID)))
//                    .list();
            Log.e("adjaList",adjacentClosureList.toString());

            //节点之间深度差为1就代表了是该结点的子节点用于构建评估矩阵
            for(AdjacentClosure adjacentClosure : adjacentClosureList){
                if(adjacentClosure.getDepth().equals(1)){
                    depthOneAdj.add(adjacentClosure);
                }
            }

            //查询该项目有没有结论
//            conclusionList = cqb.where(ConclusionDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)).list();
            //上述内容要放在之前的activity中

            //如果有上述两个中的一个则创建表格
            //如果都没有则显示提示什么都不做
            if(depthOneAdj.size()>0 || conclusionList.size()>0){
                flag = true;
            }

            String value = "";
            if(adjacentClosureList.size()>1){

                //有下一层
                for(AdjacentClosure adjacentClosure : depthOneAdj){
                    //每次使用查询都得初始化不然会有保留的内存查询会出错
//                    tqb = treeNodeContentDao.queryBuilder();
//                        if(adjacentClosure.getAncestor()!=adjacentClosure.getDescendant()){
//                            //非自身节点是后裔节点（下一层节点）
//                            tableNode.add(adjacentClosure.get)
//                        }
                    Log.e("adjacentClosure",adjacentClosure.toString());
                    //所有下一层的节点
                    //todo:根据后裔ID找到对应的节点内容
//                    Log.e("tqb",tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT),
//                            TreeNodeContentDao.Properties.Id.eq(adjacentClosure.getDescendant().longValue())))
//                            .unique().toString());
//
//                    value = tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT),
//                            TreeNodeContentDao.Properties.Id.eq(adjacentClosure.getDescendant().longValue())))
//                            .unique().getValue();
                    Log.e("value",value);
                    tableNode.add(value);

                }
            }else if(conclusionList.size()>0){
                //有结论
                for(Conclusion conclusion : conclusionList){
                    tableNode.add(conclusion.getPlan());
                }

            }else {
                //do nothing

            }

            if(flag){
                Log.e("MatrixStorage","");
                //todo:如果可以创建表格
                //查询当前表格是否已经保存过
//                QueryBuilder<MatrixStorage> mqb = matrixStorageDao.queryBuilder();
//                matrixStorageList = mqb.where(mqb.and(MatrixStorageDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)
//                        ,MatrixStorageDao.Properties.Value.eq(nodeValue)))
//                        .list();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(flag){
                //创建表格
                //从数据库中加载数据
                //n:子数组个数要产生一个n+1*n+1的表格
                n = tableNode.size();
                Log.e("n:", String.valueOf(n));
                //n+1是为了空出第一个格子
                myForm = new Form[n+1][n+1];
                //赋值与初始化
                //第一行第一列：
                myForm[0][0] = Form.Empty;
                //第一行：
                for(int i = 1;i<myForm[0].length;i++){
//                    myForm[0][i] = new Form("子节点名", Paint.Align.LEFT);
                    myForm[0][i] = new Form(tableNode.get(i-1), Paint.Align.LEFT);
                }
                //第一列：
                for(int i = 1;i<n+1;i++){
                    myForm[i][0] = new Form(tableNode.get(i-1), Paint.Align.LEFT);
                }
                //第二行第二列至最后一行最后一列：
                for(int i = 1;i<n+1;i++){//行循环第二行至最后一行
                    for(int j = 1;j<n+1;j++){
//                        Log.e("i",String.valueOf(i));
//                        Log.e("j",String.valueOf(j));
                        //每一行第二列至最后一列
                        if(i==j){
                            //中间分割线
                            myForm[i][j] = new Form("1");
                        }else{
                            //否则为空
//                            myForm[i][j] = Form.Empty;
                            //否则为1，初始化置1消除bug（测试 (测试成功
                            myForm[i][j] = new Form("1");
                        }

                        //或者
//                myForm[i][j] = new Form("",Paint.Align.LEFT);
                    }
                }

                //如果在数据库中已经存储过
                if(matrixStorageList.size()>0){
                    //从数据库中加载数据
//                    int size = matrixStorageList.size();
                    int size=0;
                    //第二行第二列至最后一行最后一列：
                    for(int i = 1;i<n+1;i++) {//行循环第二行至最后一行
                        for (int j = 1; j < n + 1; j++) {
                            myForm[i][j] = new Form(matrixStorageList.get(size).getMatrixValue().toString());
                            size++;
                            Log.e("myForm[i][j]",myForm[i][j].getName());
                        }
                    }
                }

                //创建表格
                final FormTableData<Form> tableData = FormTableData.create(table, "测试表", 11, myForm);
                tableData.setFormat(new IFormat<Form>() {
                    @Override
                    public String format(Form form) {
                        if (form != null) {
                            return form.getName();
                        } else {
                            return "";
                        }
                    }
                });
                table.setSelectFormat(new BaseSelectFormat());
                tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
                    @Override
                    public void onClick(Column column, String value, Form form, int col, int row) {
//                        Log.e("column",String.valueOf(column));
//                        Log.e("value",String.valueOf(value));
//                        Log.e("form",form.toString());
//                        Log.e("col",String.valueOf(col));
//                        Log.e("row",String.valueOf(row));
                        //这边能不能使用alertDialog做
                        if(form !=null){
                            final EditText et = new EditText(getApplicationContext());
                            et.setTextColor(getResources().getColor(R.color.my_dark));
                            new AlertDialog.Builder(FormModeActivity.this)
                                    .setTitle("请输入表格内容")
                                    .setView(et)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String input = et.getText().toString();
                                            if (input.equals("")) {
                                                Toast.makeText(getApplicationContext(), "添加内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                //如果是数字
//                                                if(isNumericZidai(input)){
                                                if(CheckNumbers(input)){
                                                    //表格添加输入的数据
//                                                    form.setName(input);//应该是这里出错了直接将整个form都改成了1
                                                    Log.e("input:",input);

                                                    //获取Double数据
                                                    //保留三位小数
//                                                    DecimalFormat df = new DecimalFormat("0.000");
                                                    //保留二位小数
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    Double data = Double.valueOf(input);
                                                    Double reciprocal = 1/data;
                                                    Log.e("reciprocal:", String.valueOf(reciprocal));
                                                    //如何把talbe[j][i]也添加数据
                                                    //把倒数添加进[j][i]
                                                    //行
                                                    Log.e("row", String.valueOf(row));
                                                    //列
                                                    Log.e("col", String.valueOf(col));
//                                                    myForm[col][row].setName(String.valueOf(reciprocal));
                                                    //在此处添加输入值即可
                                                    myForm[row][col].setName(input);
                                                    myForm[col][row].setName(df.format(reciprocal));
                                                    // todo:
                                                    //表格存入数据库
//                                                    SaveTable saveTable = new SaveTable(row,col);
//                                                    saveTable.execute();

                                                }else{
                                                    Toast.makeText(getApplicationContext(), "添加内容不能为非数字内容！" + input, Toast.LENGTH_LONG).show();
                                                }

                                            }

                                        }
                                    })
                                    .setNegativeButton("取消",null)
                                    .show();

//                            selectForm = form;
//                            editText.setFocusable(true);
//                            editText.setFocusableInTouchMode(true);
//                            editText.requestFocus();
//                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }

                    }

                });
                table.getConfig().setTableGridFormat(new BaseGridFormat(){
                    @Override
                    protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                        if(row == tableData.getLineSize() -1){
                            return false;
                        }
                        return true;
                    }

                    @Override
                    protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                        if(row == tableData.getLineSize() -1){
                            return false;
                        }
                        return true;
                    }
                });
                //固定表格行列
                table.getConfig().setFixedFirstColumn(true);
                table.getConfig().setFixedCountRow(true);
                table.setTableData(tableData);


            }else {
                Toast.makeText(context,"没有下一层数据或方案", Toast.LENGTH_LONG).show();
            }
        }
    }

    // todo:放到后台了
//    public class SaveTable extends AsyncTask<Void, Integer, Boolean> {
//        int row;
//        int col;
//        Double CR;
//        int compareValue;
//        Double[][] data;
//        Double[] W;
//        public SaveTable(int row,int col){
//            this.row = row;
//            this.col = col;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //首先把表格转换为double型二维数字矩阵
//            Log.e("第一次进入","1");
//            data = new Double[n][n];
//            for(int i = 1;i<n+1;i++){
//                for(int j = 1;j<n+1;j++){
//                    if(myForm[i][j].getName()!=""&&myForm[i][j].getName()!=null){
//                        //当获取的不为空
//                        data[i-1][j-1] = Double.valueOf(myForm[i][j].getName());
//                    }else {
//                        //否则赋值为0
//                        data[i-1][j-1]=0.0;
//                    }
//
//                }
//            }
////            for(int i = 1;i<n+1;i++){
////                for(int j = 1;j<n+1;j++) {
////                    Log.e("ijvalue",String.valueOf(data[i-1][j-1]));
////                }
////            }
//
//            //归一化后的矩阵data1
//            Double[][] data1 = new Double[n][n];
//            //每一列都归一化
//            //data[i][j] = data[i][j]/∑1到n a[i][j]
//            Double sum0;
//            //sum0计算出错
//            for(int i = 0;i<n;i++){
//                //一共n列要归一n次
//                //求和置零
//                sum0 = 0.0;
//                //计算总和
//                for(int k=0;k<n;k++){
//                    sum0 = sum0 + data[k][i];
//
//                }
//                Log.e("sum0", String.valueOf(sum0));
//                for(int j = 0;j<n;j++){
//                    //每一列都需要归一n次
//
//                    //归一
//                    data1[j][i] = data[j][i]/sum0;
//                }
//            }
//            //将每一列经归一化处理后的判断矩阵按行相加
//            W = new Double[n];
//            for(int i = 0; i<n;i++){
//                //一共n行
//                //求和置零
//                sum0 = 0.0;
//                for(int j=0;j<n;j++){
//                    sum0 = sum0 + data1[i][j];
//                }
//                //一行求和完毕
//                W[i] = sum0;
//
//            }
//            //对向量W进行归一化处理
//            //求和置零
//            sum0 = 0.0;
//            for(int i = 0;i<n;i++){
//                sum0 = sum0+W[i];
//            }
//            for(int i = 0;i<n;i++){
//                //归一
//                W[i] = W[i]/sum0;
//                Log.e("W[i]", String.valueOf(W[i]));
//            }
//            //计算判断矩阵最大特征根lambda λmax
//            //λmax = 1/n * ∑i=1到n  ∑j=1到n a[i][j]*W[j] /W[i]
//            Double lambdaMax = 0.0;
//            //求和置零
//            sum0 = 0.0;
//            Double sum1;
//            for(int i = 0;i<n;i++){
//                //求和置零
//                sum1 = 0.0;
//                for(int j = 0;j<n;j++){
//                    sum1 = sum1 + data[i][j] * W[j];
//                }
//                sum0 = sum0+sum1/W[i];
//                Log.e("sum1", String.valueOf(sum1));
//                Log.e("sum01", String.valueOf(sum0));
//            }
//
////            lambdaMax = 1/n * sum0;
//            lambdaMax = sum0/n;
//            Log.e("lambdaMax", String.valueOf(lambdaMax));
//            //决定各矩阵的一致性指标
//            //判断矩阵一致性指标C.I.(Consistency   Index)
//            Double CI = (lambdaMax - n)/(n-1);
//            Log.e("CI", String.valueOf(CI));
//            //获取RI
//            Double myRI = Double.valueOf(RI.RILIST.get(n-1));
//            Log.e("myRI", String.valueOf(myRI));
//            //计算C.R.
//            CR = CI/myRI;
//            Log.e("CR", String.valueOf(CR));
//            //当 C.R.<  0.10  时，便认为判断矩阵具有可以接受的一致性。
//            // 当C.R. ≥0.10  时，就需要调整和修正判断矩阵，
//            // 使其满足C.R.<  0.10 ，从而具有满意的一致性。
//
//            //public int compareTo(Double anotherDouble)
//            //如果anotherDouble在数值上等于此Double，则此方法返回值0;
//            // 如果此Double在数字上小于anotherDouble，则小于0的值;
//            // 如果此Double在数字上大于anotherDouble，则值大于0。
//            compareValue = CR.compareTo(0.1);
//            Log.e("CR0.1", String.valueOf(compareValue));
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            //在UI上显示
//            //CR打印三位小数
//            DecimalFormat df = new DecimalFormat("0.000");
//            editText.setText("C.R.: " + df.format(CR));
//            SaveMatrix saveMatrix = new SaveMatrix(data,W);
////                saveMatrix.execute();
//            Log.e("1","12");
//            saveMatrix.execute();
////            if(compareValue<0){
////                Log.e("1","11");
////
////                //todo:满足要求则保存入数据库
////                //要保存归一的值和原本矩阵值两种
////                SaveMatrix saveMatrix = new SaveMatrix(data,W);
//////                saveMatrix.execute();
//////                Log.e("1","12");
////                saveMatrix.execute();
////
////            }else{
////                //否则将UI上输入的数值清空并且提示输入的数据不合格
////                myForm[row][col].setName("");
////                Toast.makeText(getApplicationContext(),"输入的数据不合格请重新输入",Toast.LENGTH_LONG);
////            }
//        }
//    }



    // todo:放到后台了
    public class SaveMatrix extends AsyncTask<Void, Integer, Boolean> {
        //矩阵
        Double data[][];
        //归一权重 (最大特征向量)
        Double W[];
        //还需要点击的树形节点的value和对应下一层的value
        private List<String> nextList;

        public SaveMatrix(Double data[][], Double W[] ){
            Log.e("1","22");
            this.data = data;
            this.W = W;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.e("a","1");
            //获取下一层或结论的value 就是tableNode
            //保存矩阵
            Log.e("SaveMatrix","11");
            MatrixStorage matrixStorage;
            MatrixStorage savedM;
            for(int i = 0;i<n;i++){
                for(int j = 0;j<n;j++){
//                    savedM = new MatrixStorage();
//                    QueryBuilder<MatrixStorage> mqb = matrixStorageDao.queryBuilder();
                    matrixStorage = new MatrixStorage();
                    matrixStorage.setI(i);
                    matrixStorage.setJ(j);
                    matrixStorage.setValue(nodeValue);
                    matrixStorage.setProjectName(Constant.PROJECTNAMEEXPERT);
                    matrixStorage.setMatrixValue(data[i][j]);
//                    matrixStorageDao.insert(matrixStorage);
                    //要使用insertOrReplace
                    //todo:根据其他的所有属性查询有没有相同的ID
//                    savedM = mqb.where(mqb.and(MatrixStorageDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT),
//                            MatrixStorageDao.Properties.I.eq(i),
//                            MatrixStorageDao.Properties.J.eq(j),
//                            MatrixStorageDao.Properties.Value.eq(nodeValue))).unique();
//                    if(savedM!=null){
//                        Log.e("savedM",savedM.toString());
//                        //如果已经保存过则获取ID
//                        matrixStorage.setId(savedM.getId());
//                    }

//                    matrixStorageDao.insertOrReplace(matrixStorage);
                    Log.e("matrixStorage",matrixStorage.toString());
                }

            }

            //todo:保存归一化的权重
//            NormalizationWeight normalizationWeight;
//
//            NormalizationWeight savedN;
//            for(int i = 0;i<tableNode.size();i++){
////                savedN = new NormalizationWeight();
//                QueryBuilder<NormalizationWeight> nqb = normalizationWeightDao.queryBuilder();
//                normalizationWeight = new NormalizationWeight();
//                normalizationWeight.setValue(nodeValue);
//                normalizationWeight.setNextVlue(tableNode.get(i));
//                normalizationWeight.setWeight(W[i]);
//                normalizationWeight.setProjectName(Constant.PROJECTNAMEEXPERT);
//                //在数据库中查询有没有相同ID（是否已保存过
//                savedN = nqb.where(nqb.and(NormalizationWeightDao.Properties.ProjectName.eq(Constant.PROJECTNAMEEXPERT)
//                        ,NormalizationWeightDao.Properties.Value.eq(nodeValue)
//                        ,NormalizationWeightDao.Properties.NextVlue.eq(tableNode.get(i))))
//                        .unique();
//                if(savedN!=null){
//                    Log.e("savedN",savedN.toString());
//                    //如果已经保存过则获取ID
//                    normalizationWeight.setId(savedN.getId());
//                }
//                normalizationWeightDao.insertOrReplace(normalizationWeight);
//                Log.e("normalizationWeight",normalizationWeight.toString());
//            }

            return null;
        }
    }

    //判断输入是否为数字
    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证字符串是否为整数或小数
     * @param str
     * @return
     */
    public boolean CheckNumbers(String str)
    {
        //Pattern pattern = Pattern.compile("/^[\\+\\-]?\\d*?\\.?\\d*?$/");   //错误
        Pattern pattern = Pattern.compile("\\d+(.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
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
        b.putString("ProjectName", Constant.PROJECTNAMEEXPERT);
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
                case Constant.GETMATRIX:
                    // 处理逻辑
                    msgBundle = msg.getData();

                    // 看看http请求是否成功
                    if (!msgBundle.getBoolean("result")){
                        showToast("网络出错请重试");
                    }else {
                        // http请求成功
                        // 回复的json转为对象
                        MatrixResult result = FastjsonUtil.from(msgBundle.getString("response"),MatrixResult.class);
                        Log.e("result",result.toString());
                        // 显示验证信息
                        showToast(result.getReviews());
                        // 判断验证是否成功
                        if (result.isFlag()){
                            // 填装已有信息
                            //如果在数据库中已经存储过
                            if (result.getMatrixStorages()!=null){
                                matrixStorageList = result.getMatrixStorages();
                                if(matrixStorageList.size()>0){
                                    //从数据库中加载数据
//                    int size = matrixStorageList.size();
                                    int size=0;
                                    //第二行第二列至最后一行最后一列：
                                    for(int i = 1;i<n+1;i++) {//行循环第二行至最后一行
                                        for (int j = 1; j < n + 1; j++) {
                                            myForm[i][j] = new Form(matrixStorageList.get(size).getMatrixValue().toString());
                                            size++;
                                            Log.e("myForm[i][j]",myForm[i][j].getName());
                                        }
                                    }
//                                    // 更新表格
//                                    tableData.setform
                                }
                            }



                        }else {
                            // 重试
                        }
                        //创建表格
                        tableData = FormTableData.create(table, "测试表", 11, myForm);
                        tableData.setFormat(new IFormat<Form>() {
                            @Override
                            public String format(Form form) {
                                if (form != null) {
                                    return form.getName();
                                } else {
                                    return "";
                                }
                            }
                        });
                        table.setSelectFormat(new BaseSelectFormat());
                        tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
                            @Override
                            public void onClick(Column column, String value, Form form, int col, int row) {
//                        Log.e("column",String.valueOf(column));
//                        Log.e("value",String.valueOf(value));
//                        Log.e("form",form.toString());
//                        Log.e("col",String.valueOf(col));
//                        Log.e("row",String.valueOf(row));
                                //这边能不能使用alertDialog做
                                if(form !=null){
                                    final EditText et = new EditText(getApplicationContext());
                                    et.setTextColor(getResources().getColor(R.color.my_dark));
                                    new AlertDialog.Builder(FormModeActivity.this)
                                            .setTitle("请输入表格内容")
                                            .setView(et)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String input = et.getText().toString();
                                                    if (input.equals("")) {
                                                        Toast.makeText(getApplicationContext(), "添加内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                                    }
                                                    else {
                                                        //如果是数字
//                                                if(isNumericZidai(input)){
                                                        if(CheckNumbers(input)){
                                                            //表格添加输入的数据
//                                                    form.setName(input);//应该是这里出错了直接将整个form都改成了1
                                                            Log.e("input:",input);

                                                            //获取Double数据
                                                            //保留三位小数
//                                                    DecimalFormat df = new DecimalFormat("0.000");
                                                            //保留二位小数
                                                            DecimalFormat df = new DecimalFormat("0.00");
                                                            Double data = Double.valueOf(input);
                                                            Double reciprocal = 1/data;
                                                            Log.e("reciprocal:",String.valueOf(reciprocal));
                                                            //如何把talbe[j][i]也添加数据
                                                            //把倒数添加进[j][i]
                                                            //行
                                                            Log.e("row",String.valueOf(row));
                                                            //列
                                                            Log.e("col",String.valueOf(col));
//                                                    myForm[col][row].setName(String.valueOf(reciprocal));
                                                            //在此处添加输入值即可
                                                            myForm[row][col].setName(input);
                                                            myForm[col][row].setName(df.format(reciprocal));
                                                            // 放到按钮监听，点击存储。
//                                            //表格存入数据库
//                                            SaveTable saveTable = new SaveTable(row,col);
//                                            saveTable.execute();

                                                        }else{
                                                            Toast.makeText(getApplicationContext(), "添加内容不能为非数字内容！" + input, Toast.LENGTH_LONG).show();
                                                        }

                                                    }

                                                }
                                            })
                                            .setNegativeButton("取消",null)
                                            .show();

                                }

                            }

                        });
                        table.getConfig().setTableGridFormat(new BaseGridFormat(){
                            @Override
                            protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                                if(row == tableData.getLineSize() -1){
                                    return false;
                                }
                                return true;
                            }

                            @Override
                            protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                                if(row == tableData.getLineSize() -1){
                                    return false;
                                }
                                return true;
                            }
                        });
                        //固定表格行列
                        table.getConfig().setFixedFirstColumn(true);
                        table.getConfig().setFixedCountRow(true);
                        table.setTableData(tableData);
                    }
                    break;
                case Constant.SAVEMATRIX:
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
                            // 保存成功
                        }else {
                            // 重试
                        }
                    }
                    break;
            }
        }
    }
}

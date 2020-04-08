package com.wzb.sampledesign.ui.expertentry.TreeView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.ui.expertentry.ui.home.HomeFragment;
import com.wzb.sampledesign.util.Constant;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class FolderStructureFragment extends Fragment implements View.OnClickListener {
    private TextView statusBar;
    private AndroidTreeView tView;

    private TreeNode root;
    private TreeNode myRoot;

//    private ProjectInformationDao projectInformationDao;
//    private TreeNodeContentDao treeNodeContentDao;
//    private AdjacentClosureDao adjacentClosureDao;
//    private ConclusionDao conclusionDao;
//
//    private NormalizationWeightDao normalizationWeightDao;

    private List<Integer> nodeIdList;
    private List<AdjacentClosure> adjacentClosureList;
    private List<TreeNode> treeNodeList;
    private List<AdjacentClosure> depthOneList;

    @BindView(R.id.conclusion_RoundButton)
    public QMUIRoundButton conclusionButton;

    private Handler mHandler;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //这边就是一个示例

        View rootView = inflater.inflate(R.layout.fragment_treeview_default, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        ButterKnife.bind(this,rootView);

        statusBar = (TextView) rootView.findViewById(R.id.status_bar);

        mHandler = new MyHandler(getContext());

        Log.e("on","folder");

        //get DAO DAO(Data Access Object)
//        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
//        projectInformationDao = daoSession.getProjectInformationDao();
//        treeNodeContentDao = daoSession.getTreeNodeContentDao();
//        adjacentClosureDao = daoSession.getAdjacentClosureDao();
//        conclusionDao = daoSession.getConclusionDao();
//        normalizationWeightDao = daoSession.getNormalizationWeightDao();
//
//        Log.e(TAG,"afterGetDao");

        //todo:后续可以根据传过来数据的途径不同进行绘制UI
        //获取项目名
//        Bundle b = getIntent().getExtras();
        Bundle b = getArguments();
        String projectName = b.getString("ProjectName");
//        String projectName = getArguments().getBundle();
        Log.e("projectName:",projectName);

        //将projectName设置为常数方便后续调用
        Constant.PROJECT_NAME = projectName;

        String origin = b.getString("Origin");
//        Log.e("origin:",origin);//NULL EXCEPTION
        //根节点
        root = TreeNode.root();
        if(origin == null){
            //创建的新项目

//
//            TreeNode projectRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop,projectName));
//
//            root.addChildren(projectRoot);
            Log.e("new:","");

            //创建root
            myRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop,projectName));
            // 放到了ExpertEntryActivity
            //保存至数据库
//            SaveTask saveTask = new SaveTask(projectName);
//            saveTask.execute();

            root.addChildren(myRoot);



        }else if (origin.equals("Save")){
            Constant.IS_Save = true;

            //创建root
            myRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop,projectName));
            root.addChildren(myRoot);

            //从保存过来的需要加载
//            List<Integer> nodeIdList = new LinkedList<>();
            //从数据库中取深度为0的节点
            Log.e("from","save");
            //todo: 重组矩阵
//            GetZeroNode getZeroNode = new GetZeroNode();
//            getZeroNode.execute();
//            //创建所有节点
//            GetAndCreateTreeNode getAndCreateTreeNode = new GetAndCreateTreeNode();
//            getAndCreateTreeNode.execute();
//            //从数据库中获取深度为1的节点并且组合成树形结构
//            GetOneNodeAndCombinnation getOneNodeAndCombinnation = new GetOneNodeAndCombinnation();
//            getOneNodeAndCombinnation.execute();



        }else{
            //do nothing
        }



//        TreeNode computerRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "My Computer"));
//
//        TreeNode myDocuments = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "My Documents"));
//        TreeNode downloads = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Downloads"));
//        TreeNode file1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file, "Folder 1"));
//        TreeNode file2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file, "Folder 2"));
//        TreeNode file3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file, "Folder 3"));
//        TreeNode file4 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file, "Folder 4"));
//        fillDownloadsFolder(downloads);
//        downloads.addChildren(file1, file2, file3, file4);
//
//        TreeNode myMedia = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo_library, "Photos"));
//        TreeNode photo1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Folder 1"));
//        TreeNode photo2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Folder 2"));
//        TreeNode photo3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Folder 3"));
//        myMedia.addChildren(photo1, photo2, photo3);
//
//        myDocuments.addChild(downloads);
//        computerRoot.addChildren(myDocuments, myMedia);
//
//        root.addChildren(computerRoot);

//        try{
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }



        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);
//


        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        conclusionButton.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.treeview_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expandAll:
                tView.expandAll();
                break;

            case R.id.collapseAll:
                tView.collapseAll();
                break;

            case R.id.saveTreeNode:
                //todo:总体的保存（整个树形结构
                //暂时不考虑就用原来想的办法实时保存
//                root.getValue();
//                root.getChildren();
                break;
            case R.id.addConclusion:
                //开启添加结论的activity
                Intent myIntent = new Intent(getActivity(),ConclusionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSer
                startActivity(myIntent);
                break;
            case R.id.CalculationResults:
                //todo:
//                ConclusionCalculation conclusionCalculation = new ConclusionCalculation();
//                conclusionCalculation.execute();
                break;
        }
        return true;
    }

    private int counter = 0;

    private void fillDownloadsFolder(TreeNode node) {
        TreeNode downloads = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Downloads" + (counter++)));
        node.addChild(downloads);
        if (counter < 5) {
            fillDownloadsFolder(downloads);
        }
    }


    //todo:在这边设置点击监听事件
    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            statusBar.setText("Last clicked: " + item.text);
        }
    };

    //todo:长点击应该后续执着中要设置添加数据的按钮 绘制表格添加数据 有没有模态框一类的可以使用
    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Toast.makeText(getActivity(), "Long click: " + item.text, Toast.LENGTH_SHORT).show();
            //长按监听点击事件：
            //开启新的表格
            Bundle bundle = new Bundle();
            bundle.putString("itemText",item.text);

            //todo:
//            StartTable startTable = new StartTable(getActivity(),bundle);
//            startTable.execute();

            return true;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.conclusion_RoundButton:
                //开启添加结论的activity
                Intent myIntent = new Intent(getActivity(),ConclusionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("");
                startActivity(myIntent);
                break;
        }
    }

    //todo:放到后台
//    public class SaveTask extends AsyncTask<Void, Integer, Boolean> {
//        private String projectName;
//
//        SaveTask(String name){
//            projectName = name;
//
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //保存至数据库
//            // 创建时候就添加一个根节点 并保存
//            // 存入comment table（TreeNodeTable）与comment_path table(AdjacentClosureTable)
//            //首先存入ProjectInformation
//            Log.e("开始","保存");
//            ProjectInformation projectInformation = new ProjectInformation();
//            projectInformation.setLayer(1);
//            projectInformation.setProjectName(projectName);
//            //todo:在插入数据之前要先查询有没有相同数据
//            projectInformationDao.insert(projectInformation);
//            Log.e("保存的数据：",
//                    projectInformationDao.
//                            queryBuilder()
//                            .where(ProjectInformationDao.Properties.ProjectName.eq(projectName)).unique().toString());
//
//            AdjacentClosure adjacentClosure = new AdjacentClosure();
//
//            //其次存入TreeNodeContent
//            TreeNodeContent treeNodeContent = new TreeNodeContent();
//            treeNodeContent.setValue(projectName);
//            treeNodeContent.setProjectName(projectName);
//            treeNodeContentDao.insert(treeNodeContent);
//
//            Log.e("treenodecontent:",
//                    treeNodeContentDao.queryBuilder()
//                            .where(TreeNodeContentDao.Properties.ProjectName.eq(projectName))
//                            .list().toString());
//
//
//
//            //根据值查询ID
//            Integer ancestor = treeNodeContentDao.
//                    queryBuilder().
//                    where(TreeNodeContentDao.Properties.Value.eq(projectName)).unique().getId().intValue();
//            adjacentClosure.setProjectName(projectName);
//            adjacentClosure.setAncestor(ancestor);
//            adjacentClosure.setDescendant(ancestor);
//            adjacentClosure.setDepth(0);
//            //保存
//            adjacentClosureDao.save(adjacentClosure);
//            Log.e("adjacent:",
//                    adjacentClosureDao.queryBuilder().
//                    where(AdjacentClosureDao.Properties.ProjectName.eq(projectName))
//                            .list().toString());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
////            root.addChildren(myRoot);
////
////            tView = new AndroidTreeView(getActivity(), root);
////            tView.setDefaultAnimation(true);
////            tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
////            tView.setDefaultViewHolder(IconTreeItemHolder.class);
////            tView.setDefaultNodeClickListener(nodeClickListener);
////            tView.setDefaultNodeLongClickListener(nodeLongClickListener);
//
//
//        }
//    }

    /**
     * 从数据库中取深度为0的节点
     */
    //todo:放到后台
//    public class GetZeroNode extends AsyncTask<Void, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //初始化
//            nodeIdList = new LinkedList<>();
//            adjacentClosureList = new LinkedList<>();
//            AdjacentClosure adjacentClosure = new AdjacentClosure();
//            //从数据库取值
//            //需要添加项目名
//            QueryBuilder<AdjacentClosure> qb = adjacentClosureDao.queryBuilder();
//            adjacentClosureList = qb
//                    //深度为0且项目名为当前项目名
////                    .where(AdjacentClosureDao.Properties.Depth.eq(0))
//                    .where(qb.and(AdjacentClosureDao.Properties.Depth.eq(0),AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)))
//                    //升序排序
//                    .orderAsc(AdjacentClosureDao.Properties.Ancestor)
//                    //取List
//                    .list();
//            Log.e("adjacentClosureList:",adjacentClosureList.toString());
//            for(int i = 0;i<adjacentClosureList.size();i++){
//                adjacentClosure = adjacentClosureList.get(i);
//                //祖先等于后裔也就是深度为0的时候取出所有节点
//                if(adjacentClosure.getAncestor().equals(adjacentClosure.getDescendant()) ){
//                    //获取所有节点的ID
//                    nodeIdList.add(adjacentClosure.getAncestor());
//                }
//            }
//            Log.e("nodeIdList",nodeIdList.toString());
//            return null;
//        }
//    }

    //todo:放到后台
//    public class GetAndCreateTreeNode extends AsyncTask<Void, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //根据ID获取TreeNode的value并且创建
//            treeNodeList = new LinkedList<>();
//            TreeNodeContent treeNodeContent = new TreeNodeContent();
//            for(int i = 0;i<nodeIdList.size();i++){
//                //根据ID获取Value
//                treeNodeContent = treeNodeContentDao.queryBuilder()
//                        //ID相等根据ID查询
//                        .where(TreeNodeContentDao.Properties.Id.eq(nodeIdList.get(i)))
//                        //只查询一个
//                        .unique();
//                //创建所有节点
//                Log.e("create",treeNodeContent.getValue());
//                treeNodeList.add(new TreeNode
//                        (new IconTreeItemHolder
//                                .IconTreeItem(R.string.ic_folder,treeNodeContent.getValue())));
//            }
//
//            Log.e("treeNodeListSize", String.valueOf(treeNodeList.size()));
//            //将节点链表中的根节点替换为myRoot
//            //这是一个将链表变为树形结构的过程
//            for(int i = 0;i<treeNodeList.size();i++){
////                Log.e("treeNode",treeNodeList.get(i).getValue().toString());
//                IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem)treeNodeList.get(i).getValue();
////                Log.e("itemText",item.text);
////                Log.e("itemIcon",String.valueOf(item.icon));//Icon是hash数值没用
//                if(item.text.equals(Constant.PROJECT_NAME)){
//                    //替换
//
//                    treeNodeList.set(i,myRoot);
//                }
//
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//        }
//    }

    //todo:放到后台
//    public class GetOneNodeAndCombinnation extends AsyncTask<Void, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //获取深度为1的节点
//            //获取了深度为1的节点也就获取了所有具有父子关系的信息可以通过这些信息将treeNode组合成一棵树
//            depthOneList = new LinkedList<>();
//            QueryBuilder<AdjacentClosure> qb = adjacentClosureDao.queryBuilder();
//            depthOneList = qb
//                    //深度为1项目名为当前项目名
//                    .where(qb.and(AdjacentClosureDao.Properties.Depth.eq(1),AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)))
//                    //根据祖先按照升序排序
//                    .orderAsc(AdjacentClosureDao.Properties.Ancestor)
//                    //获取list
//                    .list();
//
//            Log.e("depthOneListSize", String.valueOf(depthOneList.size()));
////            Log.e("depthOneListSize",String.valueOf(depthOneList.toArray().toString()));
//
//            //组合
//            AdjacentClosure myAdjacentClosure = new AdjacentClosure();
//            int ancestor;
//            int descendant;
//
//            TreeNodeContent ancestorTreeNodeContent = new TreeNodeContent();
//            TreeNodeContent descendantTreeNodeContent = new TreeNodeContent();
//
//
//
//
//            for(int i = 0;i<depthOneList.size();i++){
//                //有多少次父节点和子节点就要组合多少次
//                myAdjacentClosure = depthOneList.get(i);
//                Log.e("myAdjacentClosure",myAdjacentClosure.toString());
//                ancestor = myAdjacentClosure.getAncestor();
//                descendant = myAdjacentClosure.getDescendant();
//                //这里的祖先和后裔的差距只有1也就代表了这两个是父子节点
//                // 祖先节点等于父节点，后裔节点等于子节点，进行组合即可
//                //找祖先节点（j）
//                //找出深度差距为0的节点也就找出了全部节点
//                // 深度差距为1的节点，就代表祖先和后裔的深度差距为1就找到了节点之间的关系父子关系，进行组合即可
//                for(int j=0;j<treeNodeList.size();j++){
//                    IconTreeItemHolder.IconTreeItem item =(IconTreeItemHolder.IconTreeItem) treeNodeList.get(j).getValue();
////                   Log.e("nodeID",String.valueOf(treeNodeList.get(j).getId()));//无法根据ID来判断
//                   Log.e("itemText",item.text);
//                   //根据祖先节点的ID找对应的值
//                    QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();
//                    ancestorTreeNodeContent = tqb
//                            .where(TreeNodeContentDao.Properties.Id.eq(ancestor))
//                            .unique();
//                    //如果当前节点的值与祖先节点值相同则找到了祖先节点
//                    if(item.text.equals(ancestorTreeNodeContent.getValue())){
////                    if(treeNodeList.get(j).getId() == ancestor){
////                        treeNodeList.get(j).addChild()
//                        //找后裔节点（k)
//                        for(int k = 0;k<treeNodeList.size();k++){
//                            //获取当前节点的值
//                            IconTreeItemHolder.IconTreeItem descendantItem =(IconTreeItemHolder.IconTreeItem) treeNodeList.get(k).getValue();
//                            //根据后裔节点的ID找对应的值
//                            QueryBuilder<TreeNodeContent> tqb1 = treeNodeContentDao.queryBuilder();
//                            descendantTreeNodeContent = tqb1
//                                    .where(TreeNodeContentDao.Properties.Id.eq(descendant))
//                                    .unique();
//                            //如果当前节点的值与后裔节点值相同则找到了后裔节点
//                            if(descendantItem.text.equals(descendantTreeNodeContent.getValue())){
////                            if(treeNodeList.get(k).getId() == descendant){
//                                //祖先和后裔都找到了
//                                //添加子节点
//                                treeNodeList.get(j).addChild(treeNodeList.get(k));
//                                Log.e("子节点和父节点:", String.valueOf(k) + String.valueOf(j));
//                            }
//                        }
//                    }
//                }
//            }
//
//            //设置treeNodeList的第一个节点为根节点
//            //应该设值为项目名的节点为根击点
////            for(int i = 0;i<treeNodeList.size();i++){
////                if(treeNodeList.get(i).getValue())
////            }
//            myRoot = treeNodeList.get(0);
//            Log.e("treeNodeList0", String.valueOf(treeNodeList.get(0).getLevel()));
//            root.addChildren(myRoot);
//
//            Log.e("rootSize", String.valueOf(root.size()));
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            //回显UI
//
//        }
//    }

    //todo:放到后台
//    public class StartTable extends AsyncTask<Void, Integer, Boolean> {
//        private Context context;
//        private boolean flag = false;
//        private Bundle bundle;
//        public StartTable(Context context, Bundle bundle){
//            this.context = context;
//            this.bundle = bundle;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //从数据库中查看当前节点有没有下一层节点
//            //若没有则查询当前项目有没有结论/计划
//            QueryBuilder<AdjacentClosure> aqb = adjacentClosureDao.queryBuilder();
//            QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//            QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();
//            String nodeValue = bundle.get("itemText").toString();
//            //根据内容获取ID
//            Long myID = tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                    ,TreeNodeContentDao.Properties.Value.eq(nodeValue)))
//                    .unique().getId();
//            Log.e("myID",myID.toString());
//            //根据该节点查询以该节点为祖先节点的所有记录
//            //查询该节点有多少后裔节点
//            List<AdjacentClosure> adjacentClosureList = aqb.where(aqb.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                    ,AdjacentClosureDao.Properties.Ancestor.eq(myID)))
//                    .list();
//            //找到深度为1的后裔节点
//            List<AdjacentClosure> depthOneAdj = new ArrayList<>();
//            for(AdjacentClosure adjacentClosure : adjacentClosureList){
//                if(adjacentClosure.getDepth().equals(1)){
//                    depthOneAdj.add(adjacentClosure);
//                }
//            }
//            Log.e("adjaList",adjacentClosureList.toString());
//            //查询该项目有没有结论
//            List<Conclusion> conclusionList = cqb.where(ConclusionDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)).list();
//            Log.e("conclusionList",conclusionList.toString());
//            //如果该项目有结论或者该节点的后裔节点除了他自身外有其他的后裔节点即可开启新的表格activity
//            if(depthOneAdj.size()>0 || conclusionList.size()>0){
//                flag = true;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            //开启新的activity
//            if(flag){
//                Intent tableIntent = new Intent(getActivity(), FormModeActivity.class);
//                tableIntent.putExtras(bundle);
//                startActivity(tableIntent);
//            }else {
//                Toast.makeText(context,"没有下一层数据或方案", Toast.LENGTH_LONG).show();
//            }
//
//        }
//    }

    //todo:放到后台
//    public class ConclusionCalculation extends AsyncTask<Void, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            int depth=0;
//            //获取项目名代表的节点ID（根节点ID
//            TreeNodeContent rootTreeNode = new TreeNodeContent();
//            QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();
//            rootTreeNode = tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                    ,TreeNodeContentDao.Properties.Value.eq(Constant.PROJECT_NAME)))
//                    .unique();
//            Log.e("rootTreeNode",rootTreeNode.toString());
//            Long rootID = rootTreeNode.getId();
//
//            //获取项目深度
//            //以根节点为祖先的所有节点
//            List<AdjacentClosure> rootAnscstor = new ArrayList<>();
//            QueryBuilder<AdjacentClosure> aqb = adjacentClosureDao.queryBuilder();
//            rootAnscstor = aqb.where(aqb.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                    ,AdjacentClosureDao.Properties.Ancestor.eq(rootID)))
//                    .list();
//
//            //建立一个列表代表最深的一层的所有节点
//            List<AdjacentClosure> deepest = new ArrayList<>();
//            //获取最深的深度
//            for(AdjacentClosure adjacentClosure : rootAnscstor){
//                if(adjacentClosure.getDepth()>depth){
//                    depth = adjacentClosure.getDepth();
//                }
//            }
//
//            for(AdjacentClosure adjacentClosure : rootAnscstor){
//                if(adjacentClosure.getDepth().equals(depth)){
//                    deepest.add(adjacentClosure);
//                }
//            }
//
//            //最后一层权重
//            List<Double> lastWeight = new ArrayList<>();
//
//            //todo:将最后一层与结论层的归一矩阵 转换成一个m*n的矩阵
//            // m->中间层（准则层最后一层）的准则个数 n->结论个数
//            //获取所有结论
//            List<Conclusion> conList = new ArrayList<>();
//            List<Double> wcList = new ArrayList<>();
//
//            QueryBuilder<Conclusion> cqb = conclusionDao.queryBuilder();
//            conList = cqb.where(ConclusionDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)).list();
//
//            if(conList == null){
//                Log.e("请先添加结论！","1");
//                return null;
//            }
//
//            Long nodeID;
//            String nowValue;
//
//            for(int i = 0;i<deepest.size();i++){
//                //获取当前节点ID
//                AdjacentClosure nowA = deepest.get(i);
//                nodeID = Long.valueOf(nowA.getDescendant());
//                //根据ID获取值
//                QueryBuilder<TreeNodeContent> tqb1 = treeNodeContentDao.queryBuilder();
//                TreeNodeContent myTreeNode = tqb1.where(tqb1.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                        ,TreeNodeContentDao.Properties.Id.eq(nodeID)))
//                        .unique();
//                Log.e("myTreeNode",myTreeNode.toString());
//                nowValue = myTreeNode.getValue();
//                //从归一权重中取值
//                QueryBuilder<NormalizationWeight> nqb = normalizationWeightDao.queryBuilder();
//                NormalizationWeight normalizationWeight = new NormalizationWeight();
//                normalizationWeight = nqb.where(nqb.and(NormalizationWeightDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                        ,NormalizationWeightDao.Properties.NextVlue.eq(nowValue)))
//                        .unique();
//                //给当前节点赋值初始值为以当前节点为nextValue的归一权重表中的数据
//                lastWeight.add(normalizationWeight.getWeight());
//
//                //从数据库归一权重表中查找以最后一层节点为当前值 以conclusion为nextValue的归一权重加入wc
//                //取最后一层与结论层的值
//                Log.e("nqb",nqb.list().toString());
//                Log.e("conList.size()", String.valueOf(conList.size()));
//                for(int j = 0;j<conList.size();j++){
//                    nqb=normalizationWeightDao.queryBuilder();
//                    Log.e("nqb1",nqb.list().toString());
//                    Log.e("nowValue",nowValue);
//                    Log.e("conList.get(j)",conList.get(j).toString());
//                    NormalizationWeight myNor = new NormalizationWeight();
//                    myNor = nqb.where(nqb.and(NormalizationWeightDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                            ,NormalizationWeightDao.Properties.Value.eq(nowValue)
//                            ,NormalizationWeightDao.Properties.NextVlue.eq(conList.get(j).getPlan())))
//                            .unique();
//                    Log.e("myNor",myNor.toString());
//                    wcList.add(myNor.getWeight());
//                }
////                Log.e("wcList",wcList.toString());
//
//                //从数据库中查询当前节点有没有父节点
//                //父节点就是在邻接矩阵中后裔ID为当前节点ID且深度为1的节点
//                QueryBuilder<AdjacentClosure> aqb1 = adjacentClosureDao.queryBuilder();
//                AdjacentClosure fatherAdj = aqb1.where(aqb1.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                        ,AdjacentClosureDao.Properties.Descendant.eq(nodeID)
//                        ,AdjacentClosureDao.Properties.Depth.eq(1)))
//                        .unique();
//
//                //当父节点不为根节点且存在
//                //root节点一般代表项目名的节点
//                while((fatherAdj.getAncestor()!=rootID.intValue())&&(fatherAdj!=null)){
//
//                    //获取父节点权重
//                    //父节点ID
//                    Long fNodeID = Long.valueOf(fatherAdj.getAncestor());
//                    TreeNodeContent ftnc = tqb.where(tqb.and(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                            ,TreeNodeContentDao.Properties.Id.eq(fNodeID)))
//                            .unique();
//                    String fValue = ftnc.getValue();
//                    NormalizationWeight fnw = nqb.where(nqb.and(NormalizationWeightDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                            ,NormalizationWeightDao.Properties.NextVlue.eq(fValue)))
//                            .unique();
//                    //当前节点权重乘以父节点权重
//                    lastWeight.set(i,lastWeight.get(i)*fnw.getWeight());
//                    //再往上找父节点直到找到根节点为止
//                    fatherAdj = aqb1.where(aqb1.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)
//                            ,AdjacentClosureDao.Properties.Descendant.eq(fNodeID)
//                            ,AdjacentClosureDao.Properties.Depth.eq(1)))
//                            .unique();
//                }
//            }
//
//            Log.e("lastWeight",lastWeight.toString());
//            //到此得到了一个n*1的矩阵（waph）
//
//
//            //获取wc
//            //行应该是结论
//            //列应该是准则层最后一层
//            int n = conList.size();//行
//            int m = lastWeight.size();//列
//            Log.e("wcList", String.valueOf(wcList.size()));
//
//            Double[][]  wc = new Double[n][m];
//            int count=0;
//
////            for(int i = 0;i<n;i++){
////                for(int j = 0;j<m;j++){
////                    //从list中依次取值并且count自增
////                    wc[i][j] = wcList.get(count++);
////                    Log.e("wc[i][j]",String.valueOf(wc[i][j]));
////                }
////            }
//            //上述的是错误的 因为wcList.get(count++);是从数据库中取出来的排列是一列一列的
//            //n=3,m=6
//            for(int i = 0;i<m;i++){
//                for(int j = 0;j<n;j++){
//                    //从list中依次取值并且count自增
//                    Log.e("j and i", String.valueOf(j + " " + i));
//                    wc[j][i] = wcList.get(count++);
//                    Log.e("wc[i][j]", String.valueOf(wc[j][i]));
//                }
//            }
//
//            Log.e("depth:", String.valueOf(depth));
//
//            //矩阵计算wc * waph
//            List<Double> resList = new ArrayList<>();
//            Double res;
//            for(int i = 0;i<n;i++){
//                res=0.0;
//                for(int k = 0;k<m;k++){
//                    Log.e("wc[i][k]*last", String.valueOf(wc[i][k]*lastWeight.get(k)));
//                    res += wc[i][k]*lastWeight.get(k);
//                }
//                resList.add(res);
//                Log.e("res", String.valueOf(res));
//            }
//
//            //存入数据库
//            Conclusion myCon = new Conclusion();
//            for(int i = 0;i<resList.size();i++){
//                myCon = conList.get(i);
//                myCon.setPriority(Float.valueOf(resList.get(i).toString()));
//                conclusionDao.update(myCon);
//            }
//
////            for(int i = 0;i<depth-1;i++){
////
////            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            //启动结论activity
//            Intent myIntent = new Intent(getActivity(),ConclusionActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putSer
//            startActivity(myIntent);
//        }
//    }

    private class MyHandler extends Handler{
        private Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle msgBundle;
            switch (msg.what){
                case 1:
                    break;
            }
        }
    }

}

package com.wzb.sampledesign.ui.expertentry.TreeView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.wzb.sampledesign.R;
import com.wzb.sampledesign.pojo.AdjacentClosure;
import com.wzb.sampledesign.util.Constant;


import java.util.List;


/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    private Context myContext;

//    private AdjacentClosureDao adjacentClosureDao;
//    private TreeNodeContentDao treeNodeContentDao;

    static int mydepth = 0;
    private boolean firstSaveFlag = true;

    private List<AdjacentClosure> adjacentClosureList;

    private List<Integer> deleteDescendant;

    private List<AdjacentClosure> deleteList;

    private List<Integer> nodeIdList;
//    private List<AdjacentClosure> adjacentClosureList;
    private List<TreeNode> treeNodeList;
    private List<AdjacentClosure> depthOneList;

    public IconTreeItemHolder(Context context) {

        super(context);
        myContext = context;
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value) {//如果定义为final我后续想要修改有点困难（复制一份即可
//    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        // 将字体文件保存在assets/fonts/目录下，在程序中通过如下方式实例化自定义字体：
//        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/DroidSansThai.ttf");
// 应用字体
//        textView.setTypeface(typeFace);

        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        //get DAO DAO(Data Access Object)
//        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
//        DaoSession daoSession = ((App)myContext.getApplicationContext()).getDaoSession();
//        adjacentClosureDao = daoSession.getAdjacentClosureDao();
//        treeNodeContentDao = daoSession.getTreeNodeContentDao();

        //获取项目名也就是根节点的value
//        String projectName = node.getRoot().getValue().toString();
//        String projectName = node.getChildren().get(0).getValue().toString();
        String projectName = Constant.PROJECT_NAME;
        Log.e("IconName:",projectName);


        //todo:点击后出现一个输入的editTextView模态框 下面有确认按钮 然后获取文字 添加新的node
        //后续需要获取树形结构进行保存
        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TreeNode newFolder = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "New Folder"));
//                getTreeView().addNode(node, newFolder);


                final EditText et = new EditText(myContext);

                new AlertDialog.Builder(myContext).setTitle("添加")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(myContext, "添加内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    //这边treeNode创建新的之后要通过treeView来添加才在UI上显示
                                    //todo:从保存读取的时候也需要在treeView上添加
                                    TreeNode newFolder = new TreeNode(new IconTreeItem(R.string.ic_folder, input));
                                    //在这边已经将子节点添加了(并没有
//                                    Log.e("getChildren0",String.valueOf(node.getChildren().toArray()));
                                    getTreeView().addNode(node, newFolder);
//                                    Log.e("getChildren1",String.valueOf(node.getChildren().indexOf(0)));
//                                    Log.e("getChildren2",String.valueOf(node.getChildren().indexOf(1)));
                                    //自行添加新节点（也不行）不对 思考错了
                                    //子节点已经添加只不过当前节点还是父节点
//                                    node.addChild(newFolder);
//                                    Log.e("getChildren2",String.valueOf(node.getLevel()));
                                    //todo:保存至数据库(思如泉涌）要注意与数据库交互是否属于耗时操作
                                    try {
                                        //获取子节点
                                        TreeNode childNode = node.getChildren().get(0);
                                        IconTreeItem treeItem = (IconTreeItem)childNode.getValue();
                                        Log.e("childNodeVlue",treeItem.text);

                                        //当前节点ID（后裔ID

                                        Integer myID = 0;
//                                        SaveTask saveTask  = new SaveTask(node,input);
                                        // todo:
                                        //使用子节点
//                                        SaveTask saveTask  = new SaveTask(childNode,input);
//                                        saveTask.execute();

                                        //根节点在创建项目时就保存了
//                                        if (node.isRoot()){

//                                        if (firstSaveFlag){
//                                            //这个IF好像完全没用
////                                            firstSaveFlag = false;
//
//                                            //如果是root
//                                            //通过getRoot获取根节点存入ancestor字段
//                                            // 通过mLastId属性存入descendant
//                                            // 字段通过getLevel获取当前深度并存入
//                                            //root 祖先和后裔都是根节点的ID 深度为0
//
//                                            //每次保存时都需要先保存自己
//                                            String nodeValue = node.getValue().toString();
//                                            TreeNodeContent treeNodeContent = new TreeNodeContent();
//                                            treeNodeContent.setValue(nodeValue);
//                                            treeNodeContentDao.save(treeNodeContent);
//                                            AdjacentClosure adjacentClosure = new AdjacentClosure();
//                                            //根据值查询ID
//                                            myID = treeNodeContentDao.
//                                                    queryBuilder().
//                                                    where(TreeNodeContentDao.Properties.Value.eq(nodeValue)).unique().getId().intValue();
//                                            //每次保存时都需要先保存自己的祖先和后裔都是自己深度为0
//                                            adjacentClosure.setAncestor(myID);
//                                            adjacentClosure.setDescendant(myID);
//                                            adjacentClosure.setDepth(0);
//                                            //保存
//                                            adjacentClosureDao.save(adjacentClosure);
//
//                                        }
//                                        //复制一份即可
//                                        TreeNode myNode = node;
//
////                                        myNode=myNode.getParent();
//                                        while(myNode.getParent()!=null){
////                                            node = node.getParent();
//                                            //若存在父节点则要保存邻接表深度+1
//                                            mydepth++;
//                                            //当前节点ID（后裔ID)就是myID
//                                            //获取父节点
//                                            myNode=myNode.getParent();
//                                            //获取父节点ID
//                                            Integer parentID = treeNodeContentDao.
//                                                    queryBuilder().
//                                                    where(TreeNodeContentDao.Properties.Value.eq(myNode.getValue())).unique().getId().intValue();
//                                            //保存
//                                            AdjacentClosure myAdjacentClosure = new AdjacentClosure();
//                                            //祖先
//                                            myAdjacentClosure.setAncestor(parentID);
//                                            //后裔
//                                            myAdjacentClosure.setDescendant(myID);
//                                            //深度
//                                            myAdjacentClosure.setDepth(mydepth);
//                                            //项目名
//                                            myAdjacentClosure.setProjectName(projectName);
//                                            adjacentClosureDao.save(myAdjacentClosure);
//
////                                            myNode
//                                            //todo:如何获取父节点
//                                            if(!node.isRoot()){
//                                                //如果节点的父节点不为空且不为root节点
//                                                //就保存三种属性
//                                                //通过getParent获取根节点存入ancestor字段
//                                                // 通过mLastId属性存入descendant
//                                                // 字段通过getLevel获取当前深度并存入
//                                            }
//
//                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }



//                                    Intent intent = new Intent();
//                                    intent.putExtra("content", input);
//                                    intent.setClass(MainActivity.this, SearchActivity.class);
//                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(myContext).setTitle("警告")
                        .setMessage("是否删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getTreeView().removeNode(node);
                                //删除功能，需要从数据库中也一起删除相关信息
                                //获取节点value
                                IconTreeItem nowItem = (IconTreeItem) node.getValue();
                                // todo:
//                                DeleteTask deleteTask = new DeleteTask(nowItem.text);
//                                deleteTask.execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        }).show();




            }
        });

        //if My computer
        //隐藏掉根节点的删除按钮
        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }

//        if(Constant.IS_Save){
//            //todo:如果要从数据库中加载初始化
//
//        }

        return view;
    }

    public void addFolder(){
        final EditText et = new EditText(myContext);

        new AlertDialog.Builder(myContext).setTitle("搜索")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(myContext, "搜索内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {

//                                    Intent intent = new Intent();
//                                    intent.putExtra("content", input);
//                                    intent.setClass(MainActivity.this, SearchActivity.class);
//                                    startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
    }

    // todo:放到后台
//    public class SaveTask extends AsyncTask<Void, Integer, Boolean> {
//
//        private TreeNode myNode;
//        private String inputValue;
//
//        SaveTask(TreeNode node, String value){
//            myNode = node;
//            inputValue = value;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            //深度置零
//            mydepth = 0;
//            //当前节点ID（后裔ID
//            Integer myID = 0;
//
//            //todo:每次保存时都需要先保存自己
////            String nodeValue = node.getValue().toString();
//            TreeNodeContent treeNodeContent = new TreeNodeContent();
//            treeNodeContent.setValue(inputValue);
//            treeNodeContent.setProjectName(Constant.PROJECT_NAME);
//            Log.e("newtreeNodeContent",treeNodeContent.toString());
//            treeNodeContentDao.insert(treeNodeContent);
//            AdjacentClosure adjacentClosure = new AdjacentClosure();
//            //根据值查询ID
//            myID = treeNodeContentDao.
//                    queryBuilder().
//                    where(TreeNodeContentDao.Properties.Value.eq(inputValue)).unique().getId().intValue();
//            //每次保存时都需要先保存自己的祖先和后裔都是自己深度为0
//            adjacentClosure.setAncestor(myID);
//            adjacentClosure.setDescendant(myID);
//            adjacentClosure.setDepth(0);
//            //todo:别忘记了projectName 同时 新添是insert不是save
//            adjacentClosure.setProjectName(Constant.PROJECT_NAME);
//            //保存
//            adjacentClosureDao.insert(adjacentClosure);
//            //查看数据库中的值
//            Log.e("treeNodeContent0:",treeNodeContentDao
//                    .queryBuilder()
//                    .where(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                    .list().toString());
//            Log.e("AdjacentClosure0",adjacentClosureDao
//                    .queryBuilder()
//                    .where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                    .list().toString());
//
//            //根节点与空思考一下
//            //非根节点
//            while(!myNode.getParent().isRoot()){
//
////                                            node = node.getParent();
//                //若存在父节点则要保存邻接表深度+1
//                //深度在每次开启任务时候需要置零
//                mydepth++;
//                //当前节点ID（后裔ID)就是myID
//                //获取父节点
//                myNode=myNode.getParent();
//                //获取父节点ID
//
//                //获取value
//                IconTreeItem treeItem = (IconTreeItem)myNode.getValue();
//                Log.e("nowNodeVlue",treeItem.text);
//                Log.e("getParentLevel:" , String.valueOf(myNode.getParent().getLevel()));
//
//                Log.e("myNodeValue:" , myNode.getValue().toString());
//
//                Log.e("获取父节点ID",treeNodeContentDao.
//                        queryBuilder().
//                        where(TreeNodeContentDao.Properties.Value.eq(treeItem.text)).unique().toString());
//                Integer parentID = treeNodeContentDao.
//                        queryBuilder().
//                        where(TreeNodeContentDao.Properties.Value.eq(treeItem.text)).unique().getId().intValue();
//                //保存
//                AdjacentClosure myAdjacentClosure = new AdjacentClosure();
//                //祖先
//                myAdjacentClosure.setAncestor(parentID);
//                //后裔
//                myAdjacentClosure.setDescendant(myID);
//                //深度
//                myAdjacentClosure.setDepth(mydepth);
//                //项目名
//                //todo:在从数据库加载时也需要设置项目名
//                myAdjacentClosure.setProjectName(Constant.PROJECT_NAME);
//                adjacentClosureDao.insert(myAdjacentClosure);
//
////                                            myNode
//                //todo:如何获取父节点
////                if(!node.isRoot()){
////                    //如果节点的父节点不为空且不为root节点
////                    //就保存三种属性
////                    //通过getParent获取根节点存入ancestor字段
////                    // 通过mLastId属性存入descendant
////                    // 字段通过getLevel获取当前深度并存入
////                }
//                Log.e("一次循环","结束");
//
//            }
//            //查看数据库中的值
//            Log.e("treeNodeContent1:",treeNodeContentDao
//                    .queryBuilder()
//                    .where(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                    .list().toString());
//            Log.e("AdjacentClosure1",adjacentClosureDao
//                    .queryBuilder()
//                    .where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                    .list().toString());
//
//            return null;
//        }
//    }

    // todo:放到后台
//    public class DeleteTask extends AsyncTask<Void, Integer, Boolean> {
//        private String nodeValue;
//        DeleteTask(String nodeValue){
//            this.nodeValue = nodeValue;
//        }
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            Log.e("nodevalue:",nodeValue);
//            //获取当前节点值对应的ID
//            Integer myID = 0;
//            QueryBuilder<TreeNodeContent> queryBuilder = treeNodeContentDao.queryBuilder();
//            queryBuilder.where(queryBuilder.and(TreeNodeContentDao.Properties.Value.eq(nodeValue),
//                    TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)));
////            queryBuilder.and(TreeNodeContentDao.Properties.Value.eq(nodeValue),
////                    TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME));
//            Log.e("查询结果",queryBuilder.list().toString());
//            TreeNodeContent content = queryBuilder.unique();
//            myID = content.getId().intValue();
//            Log.e("myID", String.valueOf(myID));
//
//            //获取所有以当前node为祖先的记录
//            QueryBuilder<AdjacentClosure> adjacentClosureQueryBuilder = adjacentClosureDao.queryBuilder();
//            adjacentClosureQueryBuilder.where(adjacentClosureQueryBuilder.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME),
//                    AdjacentClosureDao.Properties.Ancestor.eq(myID)));
////            adjacentClosureQueryBuilder.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME),
////                    AdjacentClosureDao.Properties.Ancestor.eq(myID));
//            adjacentClosureList = new ArrayList<>();
//            adjacentClosureList = adjacentClosureQueryBuilder.list();
//            Log.e("adjacentClosureList",adjacentClosureList.toString());
//            //获取以相应ID为祖先的所有要删除的后裔记录（只要把表中祖先或后裔与该列表相关的记录全部删除即可）
//            //这个后裔记录包括以自身为祖先的记录
//            deleteDescendant = new ArrayList<>();
//            for(AdjacentClosure adjacentClosure : adjacentClosureList){
//                Log.e("getDescendant", String.valueOf(adjacentClosure.getDescendant().intValue()));
//                deleteDescendant.add(adjacentClosure.getDescendant().intValue());
//            }
//            Log.e("deleteDescendant",deleteDescendant.toString());
//            //删除前记录：
//            Log.e("beforeDelete:",
//                    adjacentClosureDao
//                            .queryBuilder()
//                            .where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                            .list().toString());
//
//            Log.e("beforeTreeNodeContent:",treeNodeContentDao
//                    .queryBuilder()
//                    .where(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                    .list().toString());
//
//            //删除邻接闭包表所有相关记录
////            QueryBuilder<AdjacentClosure> qb = adjacentClosureDao.queryBuilder();
////            QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();
////            DeleteQuery<AdjacentClosure> dq;
////            DeleteQuery<TreeNodeContent> tdq;
//
//            //会不会产生数据冗余
//            //只删除了祖先后裔为同一ID的记录
//            //若存在深度关联是否无法删除
//            //可以使用递归删除 递归效率太低
//            List<AdjacentClosure> myList;
//            for(Integer deleteInt : deleteDescendant){
//                Log.e("deleteInt:", String.valueOf(deleteInt));
//                QueryBuilder<AdjacentClosure> qb = adjacentClosureDao.queryBuilder();
//                QueryBuilder<TreeNodeContent> tqb = treeNodeContentDao.queryBuilder();
//                DeleteQuery<AdjacentClosure> dq;
//                DeleteQuery<TreeNodeContent> tdq;
//
//                dq=qb.where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME),
//                        qb.or(AdjacentClosureDao.Properties.Ancestor.eq(deleteInt),
//                                AdjacentClosureDao.Properties.Descendant.eq(deleteInt))).buildDelete();
//
//                dq.executeDeleteWithoutDetachingEntities();
//
//
//
////                qb.and(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME),
////                             qb.or(AdjacentClosureDao.Properties.Ancestor.eq(deleteInt),
////                                AdjacentClosureDao.Properties.Descendant.eq(deleteInt)));
//
//                //最后还需要删除在treeNodeContent中的记录
//
//                tdq = tqb.where(TreeNodeContentDao.Properties.Id.eq(deleteInt)).buildDelete();
//                tdq.executeDeleteWithoutDetachingEntities();
//
//                Log.e("执行一次循环：",adjacentClosureDao
//                        .queryBuilder()
//                        .where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                        .list().toString());
//                Log.e("1：",treeNodeContentDao
//                        .queryBuilder()
//                        .where(TreeNodeContentDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                        .list().toString());
//
////                adjacentClosureDao.detachAll();
////                treeNodeContentDao.detachAll();
//
//
//            }
//
//            //删除后记录：
//            Log.e("afterDelete:",
//                    adjacentClosureDao
//                            .queryBuilder()
//                            .where(AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME))
//                            .list().toString());
//
//
//
//            return null;
//        }
//    }

    // todo:放到后台
//    public class InitTask extends AsyncTask<Void, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            //取出该项目的所有节点
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
//
//            //根据ID获取TreeNode的value并且创建新节点
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
//                treeNodeList.add(new TreeNode
//                        (new IconTreeItemHolder
//                                .IconTreeItem(R.string.ic_folder,treeNodeContent.getValue())));
//            }
//
//            Log.e("treeNodeListSize", String.valueOf(treeNodeList.size()));
//
//            //获取深度为1的节点 并且组合
//            depthOneList = new LinkedList<>();
//            QueryBuilder<AdjacentClosure> qb1 = adjacentClosureDao.queryBuilder();
//            depthOneList = qb1
//                    //深度为1项目名为当前项目名
//                    .where(qb1.and(AdjacentClosureDao.Properties.Depth.eq(1),AdjacentClosureDao.Properties.ProjectName.eq(Constant.PROJECT_NAME)))
//                    //根据祖先按照升序排序
//                    .orderAsc(AdjacentClosureDao.Properties.Ancestor)
//                    //获取list
//                    .list();
//
//            Log.e("depthOneListSize", String.valueOf(depthOneList.size()));
//
//            //组合
//            AdjacentClosure myAdjacentClosure = new AdjacentClosure();
//            int ancestor;
//            int descendant;
//
//
//
//
//            for(int i = 0;i<depthOneList.size();i++){
//                //有多少次父节点和子节点就要组合多少次
//                myAdjacentClosure = depthOneList.get(i);
//                ancestor = myAdjacentClosure.getAncestor();
//                descendant = myAdjacentClosure.getDescendant();
//                //找祖先节点（j）
//                for(int j=0;j<treeNodeList.size();j++){
//                    if(treeNodeList.get(j).getId() == ancestor){
////                        treeNodeList.get(j).addChild()
//                        //找后裔节点（k)
//                        for(int k = 0;k<treeNodeList.size();k++){
//                            if(treeNodeList.get(k).getId() == descendant){
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
//            return null;
//        }
//    }


}

package com.zhubo.fm.activity.main.fragement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.AnimUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BaseBean;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.corelibray.ui.common.DialogController;
import com.andy.ui.libray.component.NavigationBar;
import com.andy.ui.libray.dialog.AlertDialog;
import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhubo.control.activity.common.DeleteLayout;
import com.zhubo.control.activity.common.MessagePopView;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.bussiness.bean.ColumnCommentsListBean;
import com.zhubo.control.bussiness.bean.ComlumnBean;
import com.zhubo.control.bussiness.bean.MessageGroupListBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.message.MessageCommentAdapter;
import com.zhubo.fm.bll.message.MusicCommentAdapter;
import com.zhubo.fm.bll.request.PrivateMessageRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 私信评论
 * Created by andy_lv on 2014/8/24.
 */
public class MessageCommentFragement extends BaseFragment implements MessagePopView.MessagePopViewCallBack,DeleteLayout.DeleteClickListener{

    private final String TAG = "MessageCommentFragement";

    private View rootView;
    private DeleteLayout deleteLayout;
    private ListView listView;
    private PullToRefreshListView comlumnListView;
    private MessageCommentAdapter messageCommentAdapter;
    private NavigationBar navigationBar;
    private MessagePopView messagePopView;
    private MusicCommentAdapter musicCommentAdapter;

    private PrivateMessageRequest privateMessageRequest;

    private int currentPage = 1;
    private int comlumnId = 0;
    private enum ListEnum{
       MESSAGE_LIST,COMMENTS_LIST
    }

    private ListEnum currentList;

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
      this.navigationBar = navigationBar;
      navigationBar.setBackBtnVisibility(View.INVISIBLE);
      navigationBar.setActionCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.delete,0);
      navigationBar.setTitle(R.string.private_messgae_comment);
      setNavigationBarTitleImg(R.drawable.down_arrow);
      navigationBar.setActionBtnVisibility(View.VISIBLE);


    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
         if(navBarItem == NavigationBar.NavigationBarItem.action){
            doAction();
         }else if(navBarItem == NavigationBar.NavigationBarItem.title){
              this.messagePopView.doAction();
         }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == rootView){
           rootView = inflater.inflate(R.layout.fragement_message_comment_layout,container,false);
           initView();
           initData();
           setListener();
        }else{
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    /**
     * 初始化Ui
     */
    private void initView(){
       this.deleteLayout = (DeleteLayout) rootView.findViewById(R.id.fragement_delete_view);
       this.listView     = (ListView)rootView.findViewById(R.id.fragement_message_comment_listview);
       this.comlumnListView  = (PullToRefreshListView)rootView.findViewById(
               R.id.fragement_message_comment_column_listview);
    }

    /**
     * 私信评论
     */
    private void initData(){
        this.messageCommentAdapter = new MessageCommentAdapter(getActivity());
        this.listView.setAdapter(messageCommentAdapter);
        this.musicCommentAdapter   = new MusicCommentAdapter(getActivity());
        this.comlumnListView.getAdapterView().setAdapter(musicCommentAdapter);

        this.deleteLayout.setDeleteClickListener(this);
        this.privateMessageRequest = new PrivateMessageRequest(getActivity());
    }


    /**
     * 设置监听事件
     */
    private void setListener(){
        this.comlumnListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                currentPage = 1;
                loadColumnComments(currentPage, comlumnId + "");
            }

            @Override
            public void onPullUpToRefresh() {
                loadColumnComments(currentPage, comlumnId + "");
            }
        });
    }

    /**
     * 显示栏目列表
     * @param isShow
     */
    private void showComlumnList(boolean isShow){
     this.listView.setVisibility( isShow? View.GONE:View.VISIBLE);
     this.comlumnListView.setVisibility(isShow ? View.VISIBLE : View.GONE);
     navigationBar.setActionBtnVisibility(isShow?View.INVISIBLE:View.VISIBLE);
    }

    /**
     * 操作DeleteLayout
     */
    public void doAction(){
        if(deleteLayout.getVisibility() == View.VISIBLE){
            hide();
        }else{
            show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] data =new String[]{};
        if(null == messagePopView){
            this.messagePopView = new MessagePopView(getActivity(),navigationBar.getTitleView(),data);
            this.messagePopView.setMessagePopViewCallBack(this);
            loadComlunData();
        }
    }

    /**
     * 显示删除布局
     */
    private void show(){
        showCheckBox(true);
        deleteLayout.setVisibility(View.VISIBLE);
        /*Animation animation = AnimUtil.getTransAnim(0.0f,1.0f,0.0f,1.0f,500,false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                deleteLayout.setVisibility(View.VISIBLE);
                deleteLayout.setFocusable(true);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        deleteLayout.clearAnimation();
        deleteLayout.startAnimation(animation);*/
    }

    /**
     * 隐藏布局
     */
    private void hide(){
        if(deleteLayout.isShown()){
            showCheckBox(false);
            deleteLayout.setVisibility(View.GONE);
        }
      /*  Animation animation = AnimUtil.getTransAnim(1.0f,0.0f,1.0f,0.0f,500,false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                deleteLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        deleteLayout.clearAnimation();
        deleteLayout.startAnimation(animation);*/
    }

    /**
     * 设置导航栏Title右边图片
     * @param drawableId
     */
    private void setNavigationBarTitleImg(int drawableId){
       navigationBar.setTitleCompoundDrawablesWithIntrinsicBounds(0,0,drawableId,0);
    }


    @Override
    public void click(int selectPosition, ComlumnBean itemData) {
        switch (selectPosition){
            case 0:
                 hide();
                 currentList = ListEnum.MESSAGE_LIST;
                 showComlumnList(false);
                 loadMessage();
                 break;
            default:
                 hide();
                 currentList = ListEnum.COMMENTS_LIST;
                 currentPage = 1;
                 showComlumnList(true);
                 comlumnId = itemData.getId();
                 loadColumnComments(currentPage,comlumnId+"");
                break;
        }
        navigationBar.setTitle(itemData.getName());
    }

    @Override
    public void open() {
        setNavigationBarTitleImg(R.drawable.down_arrow);
    }

    @Override
    public void close() {
        setNavigationBarTitleImg(R.drawable.down_arrow);
    }


    @Override
    public void click(DeleteLayout.ButtonType buttonType) {
        switch (buttonType){
            case DELETE_BUTTON:
                delete();
                 break;
            case CLEAR_BUTTON:
                 clear();
                 break;
            case CANCEL_BUTTON:
                 cancel();
                 break;
        }
    }

    /**
     * 删除
     */
    private void delete(){
        switch (currentList){
            case COMMENTS_LIST:
                String ids = musicCommentAdapter.getAllCheckedIds();
                Log.e(TAG,"----delet music ids="+ids);
                if(TextUtils.isEmpty(ids)){
                    ToastUtil.toast(this.getActivity(),"请选择您要删除的评论");
                }else{
                    showDeleteDialog("确认删除此评论吗？",ids,1);
                }
                break;
            case MESSAGE_LIST:
                String messageIds = messageCommentAdapter.getAllCheckedIds();
                Log.e(TAG,"----delet message ids="+messageIds);
                if(TextUtils.isEmpty(messageIds)){
                    ToastUtil.toast(this.getActivity(),"请选择您要删除的消息");
                }else{
                    showDeleteDialog("确认删除此消息吗？",messageIds,0);
                }
                break;
        }

    }

    /**
     *
     * @param message
     * @param idStr
     * @param type
     */
    public void showDeleteDialog(String message,final String idStr,final int type){
        DialogController.getInstance().showAlertDialog(this.getActivity(),"",message,"取消","确定",
                new AlertDialog.Builder.AlertDialogClickListener() {
                    @Override
                    public void clickCallBack(AlertDialog.Builder.ButtonTypeEnum typeEnum, AlertDialog alertDialog) {
                        if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.LEFT_BUTTON){
                            alertDialog.dismiss();
                        }else if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON){
                            hide();
                            String loadMessage = type == 0?"消息删除中...":"评论删除中....";
                            DeleteAyncTask deleteAyncTask =new DeleteAyncTask(loadMessage,getActivity());
                            deleteAyncTask.execute(new String[]{idStr,type+""});
                        }
                    }
                });
    }

    /**
     * 清除数据
     */
    private void clear(){
        switch (currentList){
            case COMMENTS_LIST:
                 musicCommentAdapter.checkAll(true);
                 musicCommentAdapter.notifyDataSetChanged();
                 String ids = musicCommentAdapter.getAllCheckedIds();
                 Log.e(TAG,"----clear music ids="+ids);
                 showDeleteDialog("确认要清除所有评论吗？",ids,1);
                 break;
            case MESSAGE_LIST:
                 messageCommentAdapter.checkAll(true);
                 messageCommentAdapter.notifyDataSetChanged();
                 String messageIds = messageCommentAdapter.getAllCheckedIds();
                 Log.e(TAG,"----clear message ids="+messageIds);
                 showDeleteDialog("确认清除所有消息吗？",messageIds,0);
                 break;
        }
    }

    /**
     * 取消
     */
    private void cancel(){
        hide();
    }

    /**
     *
     * @param isSHow
     */
    private  void showCheckBox(boolean isSHow){
        switch (currentList){
            case COMMENTS_LIST:
                musicCommentAdapter.checkAll(false);
                musicCommentAdapter.showCheckbox(isSHow);
                musicCommentAdapter.notifyDataSetChanged();
                break;
            case MESSAGE_LIST:
                messageCommentAdapter.checkAll(false);
                messageCommentAdapter.showCheckbox(isSHow);
                messageCommentAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 请求私信列表数据
     */
    private void loadMessage(){
        privateMessageRequest.cancel();
        privateMessageRequest.getPrivateMessageList(
                new BusinessResponseHandler(this.getActivity(),true){
            @Override
            public void success(String response) {
                super.success(response);
                Gson gson =new Gson();
                MessageGroupListBean messageGroupListBean =
                        gson.fromJson(response,MessageGroupListBean.class);
                messageCommentAdapter.setData(messageGroupListBean.getGroups(),true);
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
            }
        });
    }

    /**
     * 得到栏目数据
     * @param page
     */
    private void loadColumnComments(final  int page,String columnId){
        privateMessageRequest.cancel();
        privateMessageRequest.getComlunCommentsList(page,columnId,new BusinessResponseHandler(getActivity(),true,"加载数据中...",false,true){
            @Override
            public void success(String response) {
                comlumnListView.onRefreshComplete();
                super.success(response);
                Log.e(TAG,"-----------栏目评论数据---resposne=="+response);
                Gson gson =new Gson();
                ColumnCommentsListBean commentsListBean =
                        gson.fromJson(response,ColumnCommentsListBean.class);
                if(commentsListBean != null){
                    musicCommentAdapter.setData(commentsListBean.getComments(),page == 1? true:false);
                    Log.e(TAG,"-----------栏目评论数据---count=="+musicCommentAdapter.getCount());
                    if(commentsListBean.isHasNext()){
                        currentPage = currentPage+1;
                    }else{
                        if(page !=1){
                            ToastUtil.toast(getActivity(), "没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
                comlumnListView.onRefreshComplete();
            }

            @Override
            public void cancel() {
                super.cancel();
                comlumnListView.onRefreshComplete();
            }
        });
    }


    /**
     * 加载节目列表数据
     */
    private void loadComlunData(){
        privateMessageRequest.getColumns(new BusinessResponseHandler() {
            @Override
            public void success(String response) {
                try{

                    Log.e(TAG, "----栏目数据==" + response);
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        ArrayList<ComlumnBean> list =
                                gson.fromJson(jsonObject.optString("columns"),
                                        new TypeToken<List<ComlumnBean>>() {
                                        }.getType()
                                );
                        messagePopView.setData(list);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(MessageException exception) {
            }
        });
    }


    private class DeleteAyncTask extends AsyncTask<String,Long,String> {
       private String message ;
       private String type ;
       private FragmentActivity activity;

       public DeleteAyncTask(String message,FragmentActivity activity) {
           this.message = message;
           this.activity = activity;
       }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogController.getInstance().showProgressDialog(activity,message);
        }

        @Override
        protected String doInBackground(String[] objects) {
            type = objects[1];
            String result = privateMessageRequest.deleteMessage(objects[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DialogController.getInstance().dismiss();
            if(!TextUtils.isEmpty(s)){
                Gson gson =new Gson();
                BaseBean baseBean = gson.fromJson(s,BaseBean.class);
                if(baseBean.getSuccess()){
                    if(type.equals("0")){
                        ToastUtil.toast(activity,"消息删除成功");
                        loadMessage();
                    }else if(type.equals("1")){
                        ToastUtil.toast(activity,"栏目评论删除成功");
                        currentPage =1;
                        loadColumnComments(currentPage,comlumnId+"");
                    }
                }else{
                    ToastUtil.toast(activity,"删除失败");
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}

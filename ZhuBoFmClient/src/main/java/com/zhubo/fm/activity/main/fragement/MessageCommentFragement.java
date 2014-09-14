package com.zhubo.fm.activity.main.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;

import com.andy.commonlibrary.util.AnimUtil;
import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.common.DeleteLayout;
import com.zhubo.control.activity.common.MessagePopView;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.message.MessageCommentAdapter;
import com.zhubo.fm.bll.message.MusicCommentAdapter;

/**
 * 私信评论
 * Created by andy_lv on 2014/8/24.
 */
public class MessageCommentFragement extends BaseFragment implements MessagePopView.MessagePopViewCallBack,DeleteLayout.DeleteClickListener{

    private View rootView;
    private DeleteLayout deleteLayout;
    private ListView listView;
    private MessageCommentAdapter messageCommentAdapter;
    private NavigationBar navigationBar;
    private MessagePopView messagePopView;
    private MusicCommentAdapter musicCommentAdapter;


    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
      this.navigationBar = navigationBar;
      navigationBar.setBackBtnVisibility(View.INVISIBLE);
      navigationBar.setActionCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.delete,0);
      navigationBar.setTitle(R.string.private_messgae_comment);
      setNavigationBarTitleImg(R.drawable.down_arrow);
      navigationBar.setActionBtnVisibility(View.VISIBLE);

     String[] data = getResources().getStringArray(R.array.message_comment_popview_data);
     this.messagePopView = new MessagePopView(getActivity(),navigationBar.getTitleView(),data);
     this.messagePopView.setMessagePopViewCallBack(this);
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
    }

    /**
     * 私信评论
     */
    private void initData(){
        this.messageCommentAdapter = new MessageCommentAdapter(getActivity());
        this.listView.setAdapter(messageCommentAdapter);
        this.deleteLayout.setDeleteClickListener(this);
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

    /**
     * 显示删除布局
     */
    private void show(){
        Animation animation = AnimUtil.getTransAnim(0.0f,1.0f,0.0f,1.0f,500,false);
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
        deleteLayout.startAnimation(animation);
    }

    /**
     * 隐藏布局
     */
    private void hide(){
        Animation animation = AnimUtil.getTransAnim(1.0f,0.0f,1.0f,0.0f,500,false);
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
        deleteLayout.startAnimation(animation);
    }

    /**
     * 设置导航栏Title右边图片
     * @param drawableId
     */
    private void setNavigationBarTitleImg(int drawableId){
       navigationBar.setTitleCompoundDrawablesWithIntrinsicBounds(0,0,drawableId,0);
    }


    @Override
    public void click(int selectPosition, String itemData) {
        switch (selectPosition){
            case 0:
                  if(null == messageCommentAdapter){
                       messageCommentAdapter = new MessageCommentAdapter(getActivity());
                  }
                  listView.setAdapter(messageCommentAdapter);
                 break;
            case 1:
                 if(null == musicCommentAdapter){
                     musicCommentAdapter = new MusicCommentAdapter(getActivity());
                 }
                 listView.setAdapter(musicCommentAdapter);
                 break;
            case 3:
                if(null == musicCommentAdapter){
                    musicCommentAdapter = new MusicCommentAdapter(getActivity());
                }
                listView.setAdapter(musicCommentAdapter);
                break;
        }
        navigationBar.setTitle(itemData);
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

    }
}

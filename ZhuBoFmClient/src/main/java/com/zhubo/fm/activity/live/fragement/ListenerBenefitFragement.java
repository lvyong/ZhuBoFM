package com.zhubo.fm.activity.live.fragement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.fm.R;

/**
 * 听众福利
 * Created by andy_lv on 2014/8/30.
 */
public class ListenerBenefitFragement extends BaseFragment {

    private View rootView;
    private ListView listView;
    private RelativeLayout popLayout;
    private ImageView closeImageView;

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        navigationBar.setTitle(R.string.important_recommend);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setBackButtonBackground(R.drawable.back);
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
         if(navBarItem == NavigationBar.NavigationBarItem.back){
             getActivity().finish();
         }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == rootView){
            rootView = inflater.inflate(R.layout.fragement_listener_benefit,container,false);
            initView();
            setListener();
            initData();
        }else{
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initView(){
      this.listView       = (ListView) rootView.findViewById(R.id.fragement_listener_benefit_listview);
      this.popLayout      = (RelativeLayout)rootView.findViewById(R.id.fragement_listener_benefit_pop_layout);
      this.closeImageView = (ImageView)rootView.findViewById(R.id.fragement_listener_benefit_pop_close_img);
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        this.closeImageView.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        this.listView.setAdapter(new ListenerBenefitAdapter(getActivity()));
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.fragement_listener_benefit_pop_close_img:
                 popLayout.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Adapter
     */
    private class ListenerBenefitAdapter extends BaseAdapter{
       private Context context;

       public ListenerBenefitAdapter(Context context){
         this.context = context;
       }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(R.layout.fragement_listener_benefit_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.imageView       = (ImageView) contentView.findViewById(R.id.fragememnt_listener_benefit_item_img);
               viewHolder.labelTextView   = (TextView)contentView.findViewById(R.id.fragement_listener_benefit_item_label);
               viewHolder.priceTextView   = (TextView)contentView.findViewById(R.id.fragement_listener_benefit_item_price);
               viewHolder.recommendButton = (Button) contentView.findViewById(R.id.fragement_listener_benefit_item_recommend_button);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder) contentView.getTag();
           }
           return contentView;
        }

        private class ViewHolder{
            ImageView imageView;
            TextView labelTextView;
            TextView priceTextView;
            Button recommendButton;
        }
    }
}

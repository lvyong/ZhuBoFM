package com.zhubo.fm.activity.live.fragement;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.ProgramRecommendFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 听众福利
 * Created by andy_lv on 2014/8/30.
 */
public class ListenerBenefitFragement extends BaseFragment {

    private View rootView;
    private ListView listView;
    private RelativeLayout popLayout;
    private ImageView closeImageView;
    private ProgramRecommendFactory programRecommendFactory;
    private ListenerBenefitAdapter adapter;
    private int programId;

    private final int RECOMMEND_PRODUCT = 1000;

    /**
     *
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RECOMMEND_PRODUCT:
                     recommendProduct((String)msg.obj);
                     break;
            }
        }
    };

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
            loadData();
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
        programRecommendFactory = new ProgramRecommendFactory(getActivity());
        adapter = new ListenerBenefitAdapter(getActivity());
        this.listView.setAdapter(adapter);
        Bundle bundle = getArguments();
        if(null != bundle){
             programId = bundle.getInt(FmConstant.PROGRAM_ID);
        }
    }

    /**
     *
     */
    private void loadData(){
        programRecommendFactory.getRecommendProduct(programId+"",
                new BusinessResponseHandler(getActivity(),true,"加载中..."){
                    @Override
                    public void success(String response) {
                        try{
                            Gson gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                ArrayList<ProductBean> list =
                                        gson.fromJson(jsonObject.optString("products"),
                                                new TypeToken<List<ProductBean>>() {
                                                }.getType());
                                adapter.setData(list);
                            }

                        }catch(Exception e){

                        }
                    }

                    @Override
                    public void fail(MessageException exception) {
                        super.fail(exception);
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                    }
                }
        );
    }

    /**
     * 推荐产品
     * @param productId
     */
    private void recommendProduct(String productId){
        programRecommendFactory.cancel();
        programRecommendFactory.recommendProudct(programId+"",productId,
                new BusinessResponseHandler(getActivity(),true,"产品推荐中..."){
                    @Override
                    public void success(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.optBoolean("success")){
                                ToastUtil.toast(getActivity(),"产品推荐");
                            }
                            loadData();
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void fail(MessageException exception) {
                        super.fail(exception);
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        programRecommendFactory.cancel();
                    }
                });
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

       private ArrayList<ProductBean> dataList = null;

       public ListenerBenefitAdapter(Context context){
         this.context = context;
       }

        /**
         *
         * @param list
         */
       public void setData(ArrayList<ProductBean> list){
           if(null == dataList){
               dataList = new ArrayList<ProductBean>();
           }else{
               dataList.clear();;
           }
           if(list != null && list.size()>0){
               this.dataList.addAll(list);
               notifyDataSetChanged();
           }
       }

        @Override
        public int getCount() {
           if(dataList == null){
               return 0;
           }
          return dataList.size();
        }

        @Override
        public ProductBean getItem(int position) {
            if(null == dataList){
                return null;
            }
            return dataList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           final ProductBean productBean = dataList.get(position);
           if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(
                       R.layout.fragement_listener_benefit_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.imageView       = (ItotemImageView) contentView.findViewById(
                       R.id.fragememnt_listener_benefit_item_img);
               viewHolder.labelTextView   = (TextView)contentView.findViewById(
                       R.id.fragement_listener_benefit_item_product_name);
               viewHolder.priceTextView   = (TextView)contentView.findViewById(
                       R.id.fragement_listener_benefit_item_price);
               viewHolder.descriptionTextView = (TextView)contentView.findViewById(R.id.
                       fragement_listener_benefit_item_product_description);
               viewHolder.recommendButton = (Button) contentView.findViewById(
                       R.id.fragement_listener_benefit_item_recommend_button);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder) contentView.getTag();
           }
           if(null != productBean){
               viewHolder.labelTextView.setText(productBean.getName());
               viewHolder.descriptionTextView.setText(productBean.getDescription());
               setPriceText("¥ "+productBean.getPrice(),viewHolder.priceTextView);
               viewHolder.recommendButton.setText("立即推荐"+productBean.getRecommendTimes());
               viewHolder.imageView.setDefault(R.drawable.default_img);
               if (!TextUtils.isEmpty(productBean.getImageUrl())
                       && URLUtil.isHttpUrl(productBean.getImageUrl())) {
                   if (viewHolder.imageView.getTag() == null
                           || !((String) viewHolder.imageView.getTag())
                           .equals(productBean.getImageUrl())) {
                       viewHolder.imageView.setUrl(productBean.getImageUrl());
                       viewHolder.imageView.setTag(productBean.getImageUrl());
                       viewHolder.imageView.setIsLoad(false);
                       viewHolder.imageView.reload(false);
                   }
               }
               viewHolder.recommendButton.setOnClickListener(new View.OnClickListener(){
                   @Override
                   public void onClick(View view) {
                       Message msg = new Message();
                       msg.what = RECOMMEND_PRODUCT;
                       msg.obj = productBean.getId();
                       handler.sendMessage(msg);
                   }
               });
           }
           return contentView;
        }


        private void setPriceText(String text,TextView textView){
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                SpannableStringBuilder builder = new SpannableStringBuilder(text);
                builder.setSpan(redSpan, 0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
        }

        private class ViewHolder{
            ItotemImageView imageView;
            TextView labelTextView;
            TextView descriptionTextView;
            TextView priceTextView;
            Button recommendButton;
        }
    }
}

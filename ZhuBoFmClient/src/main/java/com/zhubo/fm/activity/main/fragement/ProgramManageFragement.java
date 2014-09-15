package com.zhubo.fm.activity.main.fragement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.AppUtil;
import com.andy.commonlibrary.util.DateUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.CircleImageView;
import com.andy.ui.libray.component.NavigationBar;
import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.google.gson.Gson;
import com.imageloader.core.ImageLoader;
import com.imageloader.core.assist.FailReason;
import com.imageloader.core.assist.ImageLoadingListener;
import com.imageloader.core.download.URLConnectionImageDownloader;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.activity.common.LabelTextView;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.bussiness.bean.MainProgram;
import com.zhubo.control.bussiness.bean.ProgramBean;
import com.zhubo.fm.activity.common.EditNoteActivity;
import com.zhubo.fm.activity.live.LiveActivity;
import com.zhubo.fm.activity.search.AddProductResultActivity;
import com.zhubo.fm.activity.search.SearchProductActivity;
import com.zhubo.fm.activity.setting.SettingActivity;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.ProgramManagerRequestFactory;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.zhubo.fm.R;

/**
 * '节目管理'
 * Created by andy_lv on 2014/8/24.
 */
public class ProgramManageFragement extends BaseFragment{

    private View rootView;
    private PullToRefreshListView  listView;
    private RelativeLayout toplayout;
    private TextView topTextView;
    private TextView middleTextView;
    private TextView bottomTextView;
    private LinearLayout buttonLayout;
    private Button leftButton,rightButton;

    private ProgamManageAdapter adapter;
    private ProgramManagerRequestFactory requestFactory;
    private ImageView peopleImage;
    private ImageView settingsButton;
    private TextView  peopleTextView;

    private int currentPage = 1;

    private ProgramBean liveProgramBean;
    private NavigationBar navigationBar;

    private boolean isLive = false;


    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
       this.navigationBar = navigationBar;
       navigationBar.setVisibility(View.GONE);
       navigationBar.setBackBtnVisibility(View.VISIBLE);
       navigationBar.setActionCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.settings,0);
       navigationBar.setActionBtnVisibility(View.VISIBLE);
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if(null == rootView){
          rootView = inflater.inflate(R.layout.fragement_program_management_layout,container,false);
          initView();
          setLitener();
          initData();
       }else{
           ((ViewGroup)rootView.getParent()).removeView(rootView);
       }
       return rootView;
    }

    /**
     * 初始化View
     */
    private void initView(){
         this.listView = (PullToRefreshListView) rootView.findViewById(
                 R.id.fragement_program_management_listview);
         this.toplayout = (RelativeLayout) rootView.findViewById(
                 R.id.fragment_program_management_top_layout);
         this.topTextView = (TextView) rootView.findViewById(
                 R.id.fragment_program_management_top_label_textView);
         this.middleTextView = (TextView)rootView.findViewById(
                 R.id.fragment_program_management_top_middle_textView);
         this.bottomTextView = (TextView)rootView.findViewById(R.id.
                 fragment_program_management_top_bottom_textView);
         this.buttonLayout = (LinearLayout)rootView.findViewById(
                 R.id.fragement_program_management_bottom_linear);
         this.leftButton = (Button)rootView.findViewById(
                 R.id.fragement_program_management_bottom_leftButton);
         this.rightButton = (Button)rootView.findViewById(
                 R.id.fragement_program_management_bottom_rightButton);
         this.peopleImage = (ImageView)rootView.findViewById(
                 R.id.fragement_program_management_people_image);
         this.settingsButton = (ImageView)rootView.findViewById(
                 R.id.fragement_program_management_settings_imageview);
        this.peopleTextView = (TextView)rootView.findViewById(
                R.id.fragement_program_management_people_name);
    }

    /**
     * 设置监听事件
     */
    private void setLitener(){
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);

        this.listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                currentPage = 1;
                loadData(currentPage);
            }

            @Override
            public void onPullUpToRefresh() {
                loadData(currentPage);
            }
        });
       this.toplayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(null!= liveProgramBean && isLive){
                   Intent intent = new Intent(getActivity(),LiveActivity.class);
                   Bundle bundle = new Bundle();
                   bundle.putInt(FmConstant.PROGRAM_ID,liveProgramBean.getId());
                   bundle.putString(FmConstant.PROGRAM_NOTE,liveProgramBean.getPresentation());
                   intent.putExtra(FmConstant.BUNDLE_DATA,bundle);
                   startActivityForResult(intent,FmConstant.START_LIVE_ACTIVITY);
               }
           }
       });
       this.settingsButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(), SettingActivity.class));
           }
       });
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.fragement_program_management_bottom_leftButton:
                 goProduct(liveProgramBean.getProductNum(),liveProgramBean.getId());
                 break;
            case R.id.fragement_program_management_bottom_rightButton:
                 goEditNote(liveProgramBean.getPresentation(),liveProgramBean.getId());
                 break;
        }
    }

    /**
     * 去查看节目文稿
     * @param note
     */
    private void goEditNote(String note,int programId){
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
       if(!TextUtils.isEmpty(note)){
           intent.putExtra(FmConstant.PROGRAM_NOTE,note);
       }
       intent.putExtra(FmConstant.PROGRAM_ID,programId);
       startActivityForResult(intent, FmConstant.PROGRAM_MANAGE_TO_EDITNOTE_REQUESTCODE);
    }

    /**
     * 跳转到产品页面
     * @param productNum
     */
    private void goProduct(int productNum,int program_id){
        if(productNum == 0){
            Intent intent = new Intent(getActivity(),SearchProductActivity.class);
            intent.putExtra(FmConstant.PROGRAM_ID,program_id);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(),AddProductResultActivity.class);
            intent.putExtra(FmConstant.PROGRAM_ID,program_id);
            intent.putExtra(FmConstant.FROM_PAGE,ProgramManageFragement.class.getSimpleName());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FmConstant.PROGRAM_MANAGE_TO_EDITNOTE_REQUESTCODE:
                 if(resultCode == Activity.RESULT_OK){
                     currentPage = 1;
                     loadData(currentPage);
                 }
                 break;
            case FmConstant.START_LIVE_ACTIVITY:
                if(resultCode == Activity.RESULT_OK){
                    currentPage = 1;
                    loadData(currentPage);
                }
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData(){
        loadData(1);
        //测试
        loadPeopleImag("http://e.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1b39441ad2ad51f3deb48f65bf.jpg");
        peopleTextView.setText("张小溪");
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationBar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        navigationBar.setVisibility(View.VISIBLE);
    }

    /**
     * 加载数据
     * @param page
     */
    private void loadData(final int page){
        if(null == requestFactory){
            requestFactory = new ProgramManagerRequestFactory(getActivity());
        }else{
            requestFactory.cancle();
        }
        requestFactory.getMainFmProgram(page,new BusinessResponseHandler(getActivity(),true){
            @Override
            public void success(String response) {
                Gson gson = new Gson();
                MainProgram mainProgram = gson.fromJson(response,MainProgram.class);
                if(null != mainProgram){
                    if(null == adapter){
                        adapter = new ProgamManageAdapter(getActivity());
                        listView.getAdapterView().setAdapter(adapter);
                    }
                    adapter.addData(mainProgram.getContents(),page == 1? true: false);
                    adapter.notifyDataSetChanged();
                    if(mainProgram.isHasNext()){
                        currentPage = currentPage+1;
                    }else{
                        if(page != 1){
                            ToastUtil.toast(getActivity(),"没有更多数据");
                        }
                    }
                     //设置直播节目
                     if(mainProgram.getContents().size()>0 && page == 1){
                         setLiveProgramBeanData(adapter.getItem(0));
                     }
                }
                listView.onRefreshComplete();
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
                listView.onRefreshComplete();
            }

            @Override
            public void cancel() {
                super.cancel();
                listView.onRefreshComplete();
            }
        });

    }


    /**
     * 设置直播节目数据
     * @param programBeanData
     */
    private void setLiveProgramBeanData(ProgramBean programBeanData){
        liveProgramBean = programBeanData;
        isLive = isLiveProgram(programBeanData);
      //  isLive = true;
        if(isLive){
            Drawable img1 = getActivity().getResources().getDrawable(R.drawable.triple_arrow);
            img1.setBounds(0, 0, 22, 29);
            topTextView.setCompoundDrawables(img1, null,null, null);
            topTextView.setText("正在直播");

            Drawable img = getActivity().getResources().getDrawable(R.drawable.right_arrow);
            img.setBounds(0, 0, 18, 27);
            middleTextView.setCompoundDrawables(null, null, img, null);
            bottomTextView.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);

            String startTime = programBeanData.getStartAt();
            String endTime   = programBeanData.getEndAt();
            if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                String starHour = startTime.split(" ")[1].substring(0,5);
                String endHour  = endTime.split(" ")[1].substring(0,5);
                bottomTextView.setText(starHour+"~"+endHour);
            }
        }else{
            String startTime = programBeanData.getStartAt().substring(0,
                    programBeanData.getStartAt().lastIndexOf(":"));
            topTextView.setText("暂无直播内容,"+startTime+" 即将播出");
            bottomTextView.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
        }
        middleTextView.setText(programBeanData.getName());

        if(buttonLayout.getVisibility() == View.VISIBLE){
            if(programBeanData.getProductNum() != 0){
                leftButton.setText("查看产品");
            }else{
                String html = getResources().getString(R.string.choose_product) ;
                leftButton.setText(html);
            }

            String rightText = "";
            if(TextUtils.isEmpty(programBeanData.getPresentation())){
                rightText = getResources().getString(
                        R.string.create_note);
            }else{
                rightText = getResources().getString(R.string.read_note);
            }
            rightButton.setText(rightText);
        }
    }

    /**
     *  加载当前用户头像
     * @param url
     */
    private void loadPeopleImag(final  String url){
        ImageLoader.getInstance().displayImage(url,peopleImage,new ImageLoadingListener() {
            @Override
            public void onLoadingStarted() {

            }

            @Override
            public void onLoadingFailed(FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(Bitmap loadedImage) {
                peopleImage.setImageBitmap(AppUtil.getCircleBitmap(loadedImage));
            }

            @Override
            public void onLoadingCancelled() {

            }
        });
    }

    /**
     * 判断当前节目是否正在直播
     * @param programBean
     * @return
     */
    private boolean isLiveProgram(ProgramBean programBean){
        boolean isLive = false;
        Date date = new Date();
       long currentMilisconds  = date.getTime();
       long startTime = DateUtil.getMiliseconds(programBean.getStartAt(),"yyyy-mm-dd hh:mm:ss");
       long endTime   = DateUtil.getMiliseconds(programBean.getEndAt(),"yyyy-mm-dd hh:mm:ss");
       if(startTime <= currentMilisconds && currentMilisconds <= endTime) {
         isLive = true;
       }
        return isLive;
    }


    /**
     * 适配器
     */
    private class ProgamManageAdapter extends BaseAdapter{

        private Context context;
        private ArrayList<ProgramBean> data ;

        public ProgamManageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
           if(null == data){
               return 0;
           }
           return data.size();
        }

        @Override
        public ProgramBean getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        /**
         *
         * @param list
         * @param clear
         */
        public  void addData(List<ProgramBean> list,boolean clear){
           if(clear){
               clear();
           }
           if(null == data){
               data = new ArrayList<ProgramBean>();
           }
           if(null != list && list.size() > 0){
              this.data.addAll(list);
           }
        }

        /**
         *
         */
        private void clear(){
            if(null != data){
                data.clear();
            }
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            final ProgramBean programBean = getItem(position);
            if(null == contentView){
                contentView = LayoutInflater.from(context).inflate(R.layout.fragement_program_management_list_item,null);
                viewHolder = new ViewHolder();
                viewHolder.topTextView = (TextView) contentView.findViewById(R.id.fragment_program_management_item_left_textview);
                viewHolder.bottomTextView = (TextView) contentView.findViewById(R.id.fragment_program_management_item_bottom_textview);
                viewHolder.leftLabelTextView = (LabelTextView) contentView.findViewById(R.id.fragement_program_management_item_left_button);
                viewHolder.rightLabelTextView = (LabelTextView) contentView.findViewById(R.id.fragement_program_management_item_right_button);
                contentView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) contentView.getTag();
            }
            if(null != programBean){
                 String startTime = programBean.getStartAt();
                if(!TextUtils.isEmpty(startTime) && startTime.split(" ").length ==2){
                     long miliseconds = DateUtil.getMiliseconds(startTime,"yyyy-mm-dd hh:mm:ss");
                     String html = startTime.split(" ")[0]
                             +"  "+DateUtil.getWeekOfSomeDay(miliseconds)+"  "+
                             startTime.split(" ")[1];
                     viewHolder.topTextView.setText(html);
                }
                viewHolder.bottomTextView.setText(programBean.getName());

                if(programBean.getProductNum() != 0){
                    String html = "查看产品(已选" + programBean.getProductNum() + ")" ;
                    viewHolder.leftLabelTextView.setText(Html.fromHtml(html).toString());
                }else{
                    String html = getResources().getString(R.string.choose_product) ;
                    viewHolder.leftLabelTextView.setText(Html.fromHtml(html).toString());
                    viewHolder.leftLabelTextView.setImageView(R.drawable.choose_product);
                }
                viewHolder.leftLabelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goProduct(programBean.getProductNum(),programBean.getId());
                    }
                });
                viewHolder.rightLabelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goEditNote(programBean.getPresentation(),programBean.getId());
                    }
                });

                String rightText = "";
                if(TextUtils.isEmpty(programBean.getPresentation())){
                    rightText = getResources().getString(
                            R.string.create_note);
                    viewHolder.rightLabelTextView.setImageView(R.drawable.send_file);
                }else{
                    rightText = getResources().getString(R.string.read_note);
                }
                viewHolder.rightLabelTextView.setText(rightText);
            }
            return contentView;
        }

        class ViewHolder{
            TextView topTextView ;
            TextView bottomTextView ;
            LabelTextView leftLabelTextView;
            LabelTextView rightLabelTextView;
        }
    }
}

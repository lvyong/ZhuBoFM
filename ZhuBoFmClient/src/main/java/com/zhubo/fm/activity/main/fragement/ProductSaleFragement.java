package com.zhubo.fm.activity.main.fragement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.AppUtil;
import com.andy.commonlibrary.util.DateUtil;
import com.andy.commonlibrary.util.StringUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.google.gson.Gson;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.bussiness.bean.MainProgram;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.control.bussiness.bean.ProductSaleBean;
import com.zhubo.control.bussiness.bean.ProgramBean;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.common.ProductDetailActivity;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.ProductSaleFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 产品销量
 * Created by andy_lv on 2014/8/24.
 */
public class ProductSaleFragement extends BaseFragment {
    private final String TAG = "ProductSaleFragement";
    private View rootView;
    private PullToRefreshListView listView;
    private TextView leftDateTextView,rightDateTextView;
    private LinearLayout leftDateLinear,rightDateLinear;
    private TextView  saleTextView,sumTextView;
    private TextView  productCountTextView;
    private ListAdapter  listAdapter;

    private final int SHOW_LEFT_DATE_DIALOG = 1;
    private final int SHOW_RIGHT_DATE_DIALOG = 2;
    private int leftYear,leftMonth,leftDay;
    private int rightYear,rightMonth,rightDay;

    private DatePickerDialog leftDailog,rihgtDialog;
    private ProductSaleFactory productSaleFactory;
    private int currentPage  = 1;

    private int totalProductCount = 0,saleSum = 0;

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        navigationBar.setBackBtnVisibility(View.INVISIBLE);
        navigationBar.setTitle(R.string.product_sale);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         if(null == rootView){
             rootView = inflater.inflate(R.layout.fragement_program_sale_layout,container,false);
             initView();
             setListener();
             initData();
             loadData(currentPage);
         }else{
             ((ViewGroup)rootView.getParent()).removeView(rootView);
         }
         return rootView;
    }

    /**
     * 初始化view
     */
    private void initView(){
       this.leftDateTextView = (TextView)rootView.findViewById(
               R.id.fragement_program_sale_left_date_textview);
       this.rightDateTextView = (TextView)rootView.findViewById(
               R.id.fragement_program_sale_right_date_textview);

       this.leftDateLinear  = (LinearLayout)rootView.findViewById(R.id.
               fragement_program_sale_top_left_layout);
       this.rightDateLinear = (LinearLayout)rootView.findViewById(R.id.
               fragement_program_sale_top_right_layout);

       this.saleTextView = (TextView)rootView.findViewById(R.id.
               fragement_program_sale_top_left_textview);
       this.sumTextView = (TextView)rootView.findViewById(R.id.
               fragement_program_sale_top_right_textview);
       this.productCountTextView = (TextView)
               rootView.findViewById(R.id.fragement_program_sale_count_textview);

       this.listView    = (PullToRefreshListView)rootView.findViewById(R.id.fragement_program_sale_listview);
    }

    /**
     * 加载数据
     */
    private void initData(){
        listAdapter = new ListAdapter(getActivity());
        listView.getAdapterView().setAdapter(listAdapter);
        initDatePickData();
        productSaleFactory = new ProductSaleFactory(getActivity());
    }

    /**
     * 初始化日期数据
     */
    private void initDatePickData(){
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);

        leftYear  = rightYear  = year;
        leftMonth = rightMonth = month;
        leftDay   = rightDay   = day;
        setDateText(DatePickEnum.LEFT);
        setDateText(DatePickEnum.RIGHT);
        this.leftDailog  = (DatePickerDialog) onCreateDialog(SHOW_LEFT_DATE_DIALOG);
        this.rihgtDialog = (DatePickerDialog)onCreateDialog(SHOW_RIGHT_DATE_DIALOG);
    }

    /**
     * 设置日期值
     * @param datePickEnum
     */
    private void setDateText(DatePickEnum datePickEnum){
         switch (datePickEnum){
             case LEFT:
                 int month1 = leftMonth + 1;
                 String leftMonthValue  = (month1) < 10 ? "0"+(month1) : month1+"" ;
                 String leftDayValue    = leftDay < 10 ? "0"+leftDay : leftDay+"";
                 leftDateTextView.setText(leftYear + "-" + leftMonthValue + "-" + leftDayValue);
                 break;
             case RIGHT:
                 int month2 = rightMonth + 1;
                 String rightMonthValue  = (month2) < 10 ? "0"+(month2) : month2+"" ;
                 String rightDayValue    = rightDay < 10 ?   "0"+rightDay   : rightDay+"";
                 rightDateTextView.setText(rightYear + "-" + rightMonthValue + "-" + rightDayValue);
                 break;
         }
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        this.listView.getAdapterView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(FmConstant.PRODUCT_ID,listAdapter.getItem(position-1).getId());
                intent.putExtra(FmConstant.START_DATE,leftDateTextView.getText());
                intent.putExtra(FmConstant.END_DATE,rightDateTextView.getText());
                startActivity(intent);
            }
        });
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
       this.leftDateLinear.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
              leftDailog.show();
           }
       });
       this.rightDateLinear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               rihgtDialog.show();
           }
       });
    }

    /**
     * 创建DatePickDialog
     * @param id
     * @return
     */
    private Dialog onCreateDialog(int id) {
        switch (id) {
            case SHOW_LEFT_DATE_DIALOG:
                return new DatePickerDialog(this.getActivity(), new DatePickListener(DatePickEnum.LEFT),
                        leftYear,leftMonth,leftDay);
            case SHOW_RIGHT_DATE_DIALOG:
                return new DatePickerDialog(this.getActivity(),  new DatePickListener(DatePickEnum.RIGHT),
                        rightYear,rightMonth,rightDay);
        }
        return null;
    }


    public  enum DatePickEnum{
        LEFT,RIGHT
    }


    class DatePickListener implements  DatePickerDialog.OnDateSetListener{

         private DatePickEnum datePickEnum;
         public DatePickListener(DatePickEnum datePickEnum){
            this.datePickEnum = datePickEnum;
         }

         @Override
         public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
             Log.e(TAG,"---year=="+year+"----monthOfYear="+monthOfYear+"-----dayOfMonth=="+dayOfMonth);
             boolean isInValid = isDateChooseValid(year,monthOfYear,dayOfMonth,datePickEnum);
             if(!isInValid){
                 if(datePickEnum == DatePickEnum.LEFT){
                     ToastUtil.toast(getActivity(),"开始日期不能大于结束日期");
                 }else if(datePickEnum == DatePickEnum.RIGHT){
                     ToastUtil.toast(getActivity(),"结束日期不能小于开始日期");
                 }
             }else{
                 if(datePickEnum == DatePickEnum.LEFT){
                     leftYear  = year;
                     leftMonth = monthOfYear;
                     leftDay   = dayOfMonth ;
                 }else if(datePickEnum == DatePickEnum.RIGHT){
                     rightYear  = year;
                     rightMonth = monthOfYear;
                     rightDay   = dayOfMonth;
                 }
                 setDateText(datePickEnum);
                 currentPage = 1;
                 loadData(currentPage);
             }
         }
    }

    /**
     * 日期选择是否合法
     * @param choosedYear
     * @param choosedMonth
     * @param choosedDay
     * @return
     */
    private boolean isDateChooseValid(int choosedYear,int choosedMonth,int choosedDay,DatePickEnum datePickEnum){
     boolean isValid = false;
     int month1 = choosedMonth + 1;
     String leftMonthValue  = (month1) < 10 ? "0"+(month1) : month1+"" ;
     String leftDayValue    = choosedDay < 10 ? "0"+choosedDay : choosedDay+"";
     String date = choosedYear+"-"+leftMonthValue +"-"+leftDayValue;
     long chooseMilisecond = DateUtil.getMiliseconds(date,"yyyy-MM-dd");
     long lastMilisecond = 0;
     if(datePickEnum == DatePickEnum.LEFT){
         String rightValue = rightDateTextView.getText().toString();
         lastMilisecond = DateUtil.getMiliseconds(rightValue,"yyyy-MM-dd");
         isValid = lastMilisecond - chooseMilisecond >= 0;
     }else if(datePickEnum == DatePickEnum.RIGHT){
         String leftValue  = leftDateTextView.getText().toString();
         lastMilisecond = DateUtil.getMiliseconds(leftValue,"yyyy-MM-dd");
         isValid = chooseMilisecond - lastMilisecond >= 0;
     }
     return isValid;
    }

    private void setTopLableValue(int totalProductCount,int totalSale){
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().
                getColor(R.color.red_fe6902));
        SpannableStringBuilder builder = new SpannableStringBuilder(getDays()+"天，共销售"+
                totalProductCount);
        int length = new String(totalProductCount+"").length();
        int firstLength = new String(getDays()+"天,共销售").length();
        builder.setSpan(redSpan, firstLength, firstLength+length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        saleTextView.setText(builder);

        SpannableStringBuilder builder1 = new SpannableStringBuilder("合计:¥ "+
                StringUtil.formatAmount(totalSale+""));
        int length1 =StringUtil.formatAmount(totalSale+"").length();
        builder1.setSpan(new AbsoluteSizeSpan(18,true), 3, 5+length1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder1.setSpan(redSpan, 3,5+length1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sumTextView.setText(builder1);
    }


    private long getDays(){
        long day = 0;
        String startDay = leftDateTextView.getText().toString();
        String endDay   = rightDateTextView.getText().toString();
        long sartMiliseconds = DateUtil.getMiliseconds(startDay,"yyyy-MM-dd");
        long endMiliseconds  = DateUtil.getMiliseconds(endDay,"yyyy-MM-dd");
        long cha = endMiliseconds - sartMiliseconds;
        if(cha ==0){
            day = 1;
        }else{
            day = cha/1000/60/60/24;
        }
        return day;
    }

    /**
     *
     * @param productCount
     */
    public void setProductCountValue(int productCount){
        productCountTextView.setText("产品【" +productCount +"】");
    }

    /**
     *
     * @param page
     */
    private void loadData(final int page){
      productSaleFactory.cancel();
      productSaleFactory.getProductSale(leftDateTextView.getText().toString(),
              rightDateTextView.getText().toString(),
              page,
              new BusinessResponseHandler(getActivity(),true,"加载中..."){

                  @Override
                  public void success(String response) {
                      Gson gson = new Gson();
                      ProductSaleBean productSaleBean = gson.fromJson(response,ProductSaleBean.class);
                      if(null != productSaleBean){
                          listAdapter.addData(productSaleBean.getProducts(), page == 1 ? true : false);
                          listAdapter.notifyDataSetChanged();
                          if(page == 1){
                              totalProductCount = productSaleBean.getQuantity();
                              saleSum = productSaleBean.getAmount();
                          }
                          if(productSaleBean.isHasNext()){
                              currentPage = currentPage+1;
                              totalProductCount += productSaleBean.getQuantity();
                              saleSum += productSaleBean.getAmount();
                          }else{
                              if(page !=1){
                                  ToastUtil.toast(getActivity(), "没有更多数据");
                              }
                          }
                          setTopLableValue(totalProductCount,saleSum);
                          setProductCountValue(listAdapter.getCount());
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
                      productSaleFactory.cancel();
                      listView.onRefreshComplete();
                  }
              });
    }


    /**
     * listview Adapter
     */
    private class ListAdapter extends BaseAdapter{

        private Context context;
        private ArrayList<ProductBean> data ;

        public ListAdapter(Context context){
            this.context = context;
        }

        /**
         *
         * @param list
         * @param clear
         */
        public  void addData(List<ProductBean> list,boolean clear){
           if(clear){
                clear();
            }
            if(null == data){
                data = new ArrayList<ProductBean>();
            }
            if(null != list && list.size() > 0){
                this.data.addAll(list);
            }
        }

        private void clear(){
            if(null != data){
                data.clear();
            }
        }

        @Override
        public int getCount() {
           if(null == data){
               return 0;
           }
           return data.size();
        }

        @Override
        public ProductBean getItem(int position) {
            if(null == data){
                return null;
            }
           return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           ProductBean productBean = getItem(position);
           if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(R.layout.fragement_program_sale_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.imageView    = (ItotemImageView)contentView.findViewById(R.id.fragement_program_sale_list_item_imageview);
               viewHolder.programName  = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_name);
               viewHolder.saleTextView = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_sale);
               viewHolder.sumTextView  = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_sum);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder)contentView.getTag();
           }
           if(null != productBean){
               viewHolder.programName.setText(productBean.getName());
               viewHolder.saleTextView.setText("销量："+productBean.getQuantity());
               setSaleText(viewHolder.sumTextView,"营业额: ¥"+productBean.getAmount());
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
           }
           return contentView;
        }


        private void setSaleText(TextView textView,String value){
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().
                    getColor(R.color.red_fe6804));
            SpannableStringBuilder builder = new SpannableStringBuilder(value);
            int index = value.indexOf("¥");
            builder.setSpan(redSpan, index, index+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);
        }

        class ViewHolder{
            ItotemImageView imageView;
            TextView  programName;
            TextView  saleTextView;
            TextView  sumTextView;
        }

    }
}

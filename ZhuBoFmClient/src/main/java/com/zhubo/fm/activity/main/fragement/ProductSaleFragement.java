package com.zhubo.fm.activity.main.fragement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.common.ProductDetailActivity;

import java.util.Calendar;

/**
 * 产品销量
 * Created by andy_lv on 2014/8/24.
 */
public class ProductSaleFragement extends BaseFragment {

    private View rootView;
    private ListView listView;
    private TextView leftDateTextView,rightDateTextView;
    private LinearLayout leftDateLinear,rightDateLinear;
    private TextView  saleTextView,sumTextView;
    private ListAdapter  listAdapter;

    private final int SHOW_LEFT_DATE_DIALOG = 1;
    private final int SHOW_RIGHT_DATE_DIALOG = 2;
    private int leftYear,leftMonth,leftDay;
    private int rightYear,rightMonth,rightDay;

    private DatePickerDialog leftDailog,rihgtDialog;

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        navigationBar.setCircleImageViewVisibility(View.GONE);
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

       this.listView    = (ListView)rootView.findViewById(R.id.fragement_program_sale_listview);


    }

    /**
     * 加载数据
     */
    private void loadData(){
        listAdapter = new ListAdapter(getActivity());
        listView.setAdapter(listAdapter);
        initDatePickData();
    }

    /**
     * 初始化日期数据
     */
    private void initDatePickData(){
        final Calendar c = Calendar.getInstance();
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
                 String leftMonthValue  = (leftMonth+1) < 10 ? "0"+(leftMonth+1) : leftMonth+"" ;
                 String leftDayValue    = leftDay < 10 ? "0"+leftDay : leftDay+"";
                 leftDateTextView.setText(leftYear + "-" + leftMonthValue + "-" + leftDayValue);
                 break;
             case RIGHT:
                 String rightMonthValue  = (rightMonth+1) < 10 ? "0"+(rightMonth+1) : rightMonth+"" ;
                 String rightDayValue    = rightDay < 10 ?   "0"+rightDay   : rightDay+"";
                 rightDateTextView.setText(rightYear + "-" + rightMonthValue + "-" + rightDayValue);
                 break;
         }
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
               startActivity(intent);
            }
        });
       this.leftDateLinear.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
              leftDailog.updateDate(leftYear,leftMonth,leftDay);
              leftDailog.show();
           }
       });
       this.rightDateLinear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               rihgtDialog.updateDate(rightYear,rightMonth,rightDay);
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
                        leftYear,leftMonth -1,leftDay);
            case SHOW_RIGHT_DATE_DIALOG:
                return new DatePickerDialog(this.getActivity(),  new DatePickListener(DatePickEnum.RIGHT),
                        rightYear,rightMonth -1,rightDay);
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
         }
    }

    /**
     * listview Adapter
     */
    private class ListAdapter extends BaseAdapter{

        private Context context;


        public ListAdapter(Context context){
            this.context = context;
        }


        public void setData(){
            notifyDataSetChanged();
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
               contentView = LayoutInflater.from(context).inflate(R.layout.fragement_program_sale_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.imageView    = (ImageView)contentView.findViewById(R.id.fragement_program_sale_list_item_imageview);
               viewHolder.programName  = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_name);
               viewHolder.saleTextView = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_sale);
               viewHolder.sumTextView  = (TextView)contentView.findViewById(R.id.fragement_program_sale_list_item_program_sum);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder)contentView.getTag();
           }
           return contentView;
        }

        class ViewHolder{
            ImageView imageView;
            TextView  programName;
            TextView  saleTextView;
            TextView  sumTextView;
        }

    }
}

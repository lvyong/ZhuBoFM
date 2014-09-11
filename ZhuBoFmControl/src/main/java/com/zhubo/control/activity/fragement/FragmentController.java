package com.zhubo.control.activity.fragement;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by LL on 14-1-10.
 */
public class FragmentController {
    private SparseArray<BaseFragment> fragmentHashMap = new SparseArray<BaseFragment>();
    private FragmentActivity context;

    /**
     * fragment切换容器Id
     */
    private int containerId;

    /**
     * 容器
     */
    private ViewGroup container;

    /**
     * 当前Fragment标识
     */
    private int currentTag;

    public FragmentController(FragmentActivity context, int containerId){
        this.context        = context;
        this.containerId    = containerId;
        this.container      = (ViewGroup) context.findViewById(containerId);
    }

    /**
     * 获取当前Fragment
     * @return  currentFragment
     */
    public BaseFragment getCurrentFragment(){
        return fragmentHashMap.get(currentTag);
    }

    /**
     * 切换Fragment
     * @param id
     * @param fragmentName
     * @param baseFragment
     */
    public void switchFragment(int id,String fragmentName,BaseFragment baseFragment){
        BaseFragment current = getCurrentFragment();
        if (id == currentTag && current != null){
            return;
        }
        //将当前Fragment移出
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        if (current != null){
            transaction.detach(current);
        }
        //首先手动清空Fragment视图
        container.removeAllViews();
        //获取新Fragment
        BaseFragment newFragment = fragmentHashMap.get(id);
        //之前添加过,则直接attach
        if (newFragment != null){
            transaction.attach(newFragment);
        } else {
        //如果之前没有添加过,或者被回收掉,则新建一个Fragment添加到管理中
           // newFragment = (BaseFragment) Fragment.instantiate(context, fragmentName, arguments);
            newFragment = baseFragment;
            transaction.add(containerId,newFragment,String.valueOf(id));
        }
        //标记当前Fragment
        currentTag = id;
        fragmentHashMap.put(currentTag, newFragment);
        transaction.commitAllowingStateLoss();
        context.getSupportFragmentManager().executePendingTransactions();
    }

}

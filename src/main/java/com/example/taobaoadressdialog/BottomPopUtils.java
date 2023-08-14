package com.example.taobaoadressdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BottomPopUtils
 * @Description: java类作用描述
 * @Author: Fuduo
 * @CreateDate: 2023/8/7 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/7 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BottomPopUtils {

    public ProjectTitleAdapter titleAdapter;
    public ProjectAdapter adapter1;
    public PopupWindow popupWindow;
    public View contentView;
    public StringBuilder result;
    public StringBuilder resultCode;
    //顶部地区列表点击的下标
    private int topClickPosition;

    public void showPopWindow(List<ProjectTitleItemBean> listTop, List<ProjectItemBean> listContent, Activity context, Finish finish) {
        result = new StringBuilder();
        resultCode = new StringBuilder();
        Map<Integer, List<ProjectItemBean>> nowMap = new HashMap<>();

        //加载弹出框的布局
        contentView = LayoutInflater.from(context).inflate(
                R.layout.popupwindow, null);
        // 设置按钮的点击事件
        TextView teCancel = contentView.findViewById(R.id.te_cancel);
        TextView teSelectArea = contentView.findViewById(R.id.te_select_area);

        RecyclerView rl = contentView.findViewById(R.id.rl);
        RecyclerView rlTitle = contentView.findViewById(R.id.rl_title);

        //取出第一级的数据
        List<ProjectItemBean> item = new ArrayList<>();
        for (int i = 0; i < listContent.size(); i++) {
            if (listContent.get(i).getLevel() == 0) {
                item.add(listContent.get(i));
            }
        }
        List<ProjectItemBean> firstMap = new ArrayList<>(item);
        nowMap.put(0, firstMap);

        titleAdapter = new ProjectTitleAdapter(context, listTop, (itemBean, position) -> {
            //每次用户点击顶部集合的时候，移除掉点击下标后的所有数据
//            int size = listTop.size();
//            for (int i = position + 1; i < size; i++) {
//                listTop.remove(position + 1);
//            }
//
//            titleAdapter.notifyDataSetChanged();

//            Iterator<Map.Entry<Integer, List<ProjectItemBean>>> iterator = nowMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<Integer, List<ProjectItemBean>> entry = iterator.next();
//                Integer key = entry.getKey();
//                if (key > position) {
//                    iterator.remove();
//                }
//            }
            //选中的时候更新顶部地区列表点击的下标
            topClickPosition = position;
            List<ProjectItemBean> items = new ArrayList<>();
            ////根据点击的下标从hashmap集合内获取对应的地区数据集合
            List<ProjectItemBean> listNow = nowMap.get(position);
            //将点击的item项设置choose选中
            if (listNow != null) {
                for (int i = 0; i < listNow.size(); i++) {
                    if (listNow.get(i).getName().equals(itemBean.getName())) {
                        listNow.get(i).setChoose(true);
                    } else {
                        listNow.get(i).setChoose(false);
                    }
                    items.add(listNow.get(i));
                }
            }
            //清除下面的地区数据并重新赋值刷新列表
            item.clear();
            item.addAll(items);
            //清除保存的字母列表数据 防止数据重复
            adapter1.letterAddedList.clear();
            adapter1.notifyDataSetChanged();
        });

        rlTitle.setLayoutManager(new LinearLayoutManager(context));
        rlTitle.setAdapter(titleAdapter);

        adapter1 = new ProjectAdapter(context, item, itemBean -> {
            //因为默认不显示顶部集合 在用户选择城市之后就显示
            rlTitle.setVisibility(View.VISIBLE);
            //每次用户点击底部集合的时候，移除掉点击下标后面的所有数据
            int size = listTop.size();
            for (int i = topClickPosition + 1; i < size; i++) {
                listTop.remove(topClickPosition + 1);
            }
            titleAdapter.notifyDataSetChanged();

            Iterator<Map.Entry<Integer, List<ProjectItemBean>>> iterator = nowMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<ProjectItemBean>> entry = iterator.next();
                Integer key = entry.getKey();
                if (key > topClickPosition) {
                    iterator.remove();
                }
            }
            AddressHelper addressHelper = new AddressHelper();
            int level = itemBean.getLevel();
            //判断hashmap集合有没有存储当前展开的地区集合
            if (!nowMap.containsKey(level)) {
                List<ProjectItemBean> mapItem = new ArrayList<>(item);
                //没有则添加
                nowMap.put(level, mapItem);
            }
            //将所有item项的Choose置为false
            for (int i = 0; i < item.size(); i++) {
                item.get(i).setChoose(false);
            }
            //将当前选中的Choose置为true
            itemBean.setChoose(true);
            //更新顶部地区列表对应level的数据并刷新
            listTop.get(level).setCode(itemBean.getCode());
            listTop.get(level).setName(itemBean.getName());
            listTop.get(level).setChoose(true);
            titleAdapter.notifyDataSetChanged();

            //如果当前选择的数据是否有子集合
            if (itemBean.getChildList() != null && itemBean.getChildList().size() > 0) {
                //清除当前列表显示的数据
                item.clear();
                //当前列表level的下一级level
                int nextLevel = itemBean.getLevel() + 1;
                //添加下一级的地区数据
                item.addAll(addressHelper.sortListByLetter(itemBean.getChildList()));
                ProjectTitleItemBean bean = new ProjectTitleItemBean();
                //根据下一级的level更新页面上的提示和顶部地区列表的"请选择xx"提示
                switch (nextLevel) {
                    case 1:
                        bean.setName(context.getString(R.string.select_city));
                        teSelectArea.setText(R.string.city);
                        break;
                    case 2:
                        bean.setName(context.getString(R.string.select_county));
                        teSelectArea.setText(R.string.county);
                        break;
                    case 3:
                        bean.setName(context.getString(R.string.select_street));
                        teSelectArea.setText(R.string.street);
                        break;
                }

                //设置未选中
                bean.setChoose(false);
                //添加顶部数据
                listTop.add(bean);
                //顶部列表点击的下标加一
                topClickPosition++;
                //适配器中是否是手动点击的字段置为false
                titleAdapter.isSingleClick = false;
                //刷新适配器
                titleAdapter.notifyDataSetChanged();
            } else {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //获取选中的地区名字和code
                    for (int i = 0; i < listTop.size(); i++) {
                        String name = listTop.get(i).getName();
                        String code = listTop.get(i).getCode();
                        if (i == listTop.size() - 1) {
                            result.append(name);
                            resultCode.append(code);
                        } else {
                            result.append(name + " ");
                            resultCode.append(code + ",");
                        }
                        Log.i("已选中的值", name);
                    }
                    //接口回传选中的地区名字和code
                    finish.finish(result.toString(), resultCode.toString());
                    popupWindow.dismiss();
                    //将window透明度还原
                    backgroundAlpha(1.0f, context);
                }
            }
            //清除保存的字母列表数据 防止数据重复
            adapter1.letterAddedList.clear();
            //刷新底部列表
            adapter1.notifyDataSetChanged();
        });
        rl.setLayoutManager(new LinearLayoutManager(context));
        rl.setAdapter(adapter1);

        teCancel.setOnClickListener(v -> {
            popupWindow.dismiss();
            backgroundAlpha(1.0f, context);
        });

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(false);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(false);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.ipopwindow_anim_style);
        openPopWindow(context);
        backgroundAlpha(0.4f, context);

    }

    public void showPopWindow(int outsidePosition, Map<Integer, List<ProjectItemBean>> nowMap, List<ProjectTitleItemBean> listTop,
                              List<ProjectItemBean> listContent, Activity context, Finish finish) {
        //从外部传过来的数据最后一级的下标 赋值
        topClickPosition = outsidePosition;
        result = new StringBuilder();
        resultCode = new StringBuilder();

        //加载弹出框的布局
        contentView = LayoutInflater.from(context).inflate(
                R.layout.popupwindow, null);
        // 设置按钮的点击事件
        TextView teCancel = contentView.findViewById(R.id.te_cancel);
        TextView teSelectArea = contentView.findViewById(R.id.te_select_area);

        RecyclerView rl = contentView.findViewById(R.id.rl);
        RecyclerView rlTitle = contentView.findViewById(R.id.rl_title);
        rlTitle.setVisibility(View.VISIBLE);
        // 取出最后一级的数据
        int lastIndex = nowMap.size() - 1;
        List<ProjectItemBean> mapList = nowMap.get(lastIndex);
        List<ProjectItemBean> item = new ArrayList<>(mapList);

        titleAdapter = new ProjectTitleAdapter(context, listTop, (itemBean, position) -> {
            //            //每次用户点击顶部集合的时候，移除掉点击下标后的所有数据
//            int size = listTop.size();
//            for (int i = position + 1; i < size; i++) {
//                listTop.remove(position + 1);
//            }
//
//            titleAdapter.notifyDataSetChanged();
//
//            Iterator<Map.Entry<Integer, List<ProjectItemBean>>> iterator = nowMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<Integer, List<ProjectItemBean>> entry = iterator.next();
//                Integer key = entry.getKey();
//                if (key > position) {
//                    iterator.remove();
//                }
//            }
            //选中的时候更新顶部地区列表点击的下标
            topClickPosition = position;
            List<ProjectItemBean> newItems = new ArrayList<>();
            //根据点击的下标从hashmap集合内获取对应的地区数据集合
            List<ProjectItemBean> listNow = nowMap.get(position);
            //将点击的item项设置choose选中
            if (listNow != null) {
                for (int i = 0; i < listNow.size(); i++) {
                    if (listNow.get(i).getName().equals(itemBean.getName())) {
                        listNow.get(i).setChoose(true);
                    } else {
                        listNow.get(i).setChoose(false);
                    }
                    newItems.add(listNow.get(i));
                }
            }
            //清除下面的地区数据并重新赋值刷新列表
            item.clear();
            item.addAll(newItems);
            //清除保存的字母列表数据 防止数据重复
            adapter1.letterAddedList.clear();
            adapter1.notifyDataSetChanged();
        });

        rlTitle.setLayoutManager(new LinearLayoutManager(context));
        rlTitle.setAdapter(titleAdapter);

        adapter1 = new ProjectAdapter(context, item, itemBean -> {
            //每次用户点击底部集合的时候，移除掉点击下标后面的所有数据
            int size = listTop.size();
            for (int i = topClickPosition + 1; i < size; i++) {
                listTop.remove(topClickPosition + 1);
            }
            titleAdapter.notifyDataSetChanged();
            Iterator<Map.Entry<Integer, List<ProjectItemBean>>> iterator = nowMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<ProjectItemBean>> entry = iterator.next();
                Integer key = entry.getKey();
                if (key > topClickPosition) {
                    iterator.remove();
                }
            }

            AddressHelper addressHelper = new AddressHelper();
            int level = itemBean.getLevel();
            //判断hashmap集合有没有存储当前展开的地区集合
            if (!nowMap.containsKey(level)) {
                //没有则添加
                List<ProjectItemBean> mapItem = new ArrayList<>(item);
                nowMap.put(level, mapItem);
            }

            //将所有item项的Choose置为false
            for (int i = 0; i < item.size(); i++) {
                item.get(i).setChoose(false);
            }
            //将当前选中的Choose置为true
            itemBean.setChoose(true);
            //更新顶部地区列表对应level的数据并刷新
            listTop.get(level).setCode(itemBean.getCode());
            listTop.get(level).setName(itemBean.getName());
            listTop.get(level).setChoose(true);
            titleAdapter.notifyDataSetChanged();

            //如果当前选择的数据是否有子集合
            if (itemBean.getChildList() != null && itemBean.getChildList().size() > 0) {
                //清除当前列表显示的数据
                item.clear();
                //当前列表level的下一级level
                int nextLevel = itemBean.getLevel() + 1;
                //添加下一级的地区数据
                item.addAll(addressHelper.sortListByLetter(itemBean.getChildList()));

                ProjectTitleItemBean bean = new ProjectTitleItemBean();
                //根据下一级的level更新页面上的提示和顶部地区列表的"请选择xx"提示
                switch (nextLevel) {
                    case 1:
                        bean.setName(context.getString(R.string.select_city));
                        teSelectArea.setText(R.string.city);
                        break;
                    case 2:
                        bean.setName(context.getString(R.string.select_county));
                        teSelectArea.setText(R.string.county);
                        break;
                    case 3:
                        bean.setName(context.getString(R.string.select_street));
                        teSelectArea.setText(R.string.street);
                        break;
                }
                //设置未选中
                bean.setChoose(false);
                //添加顶部数据
                listTop.add(bean);
                //顶部列表点击的下标加一
                topClickPosition++;
                //适配器中是否是手动点击的字段置为false
                titleAdapter.isSingleClick = false;
                //刷新适配器
                titleAdapter.notifyDataSetChanged();
            } else {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //获取选中的地区名字和code
                    for (int i = 0; i < listTop.size(); i++) {
                        String name = listTop.get(i).getName();
                        String code = listTop.get(i).getCode();
                        if (i == listTop.size() - 1) {
                            result.append(name);
                            resultCode.append(code);
                        } else {
                            result.append(name + " ");
                            resultCode.append(code + ",");
                        }
                        Log.i("已选中的值", name);
                    }
                    //接口回传选中的地区名字和code
                    finish.finish(result.toString(), resultCode.toString());
                    popupWindow.dismiss();
                    //将window透明度还原
                    backgroundAlpha(1.0f, context);
                }
            }
            //清除保存的字母列表数据 防止数据重复
            adapter1.letterAddedList.clear();
            //刷新底部列表
            adapter1.notifyDataSetChanged();
        });
        rl.setLayoutManager(new LinearLayoutManager(context));
        rl.setAdapter(adapter1);

        teCancel.setOnClickListener(v -> {
            popupWindow.dismiss();
            backgroundAlpha(1.0f, context);
        });

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(false);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(false);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.ipopwindow_anim_style);
        openPopWindow(context);
    }

    /**
     * 按钮的监听
     */
    public void openPopWindow(Activity context) {
        //从底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.4f, context);
    }

    public void backgroundAlpha(float f, Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = f;
        context.getWindow().setAttributes(lp);
    }

    public interface Finish {
        void finish(String resultName, String resultCode);
    }
}

package com.example.taobaoadressdialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @ClassName: ProjectTitleAdapter
 * @Description: 顶部的列表适配器
 * @Author: Fuduo
 * @CreateDate: 2023/8/7 16:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/7 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProjectTitleAdapter extends RecyclerView.Adapter<ProjectTitleAdapter.ViewHolder> {
    private Context context;
    private List<ProjectTitleItemBean> list;
    private ProjectTitleItemClick itemClick;
    public boolean isSingleClick = false;

    public ProjectTitleAdapter(Context context, List<ProjectTitleItemBean> list,
                               ProjectTitleItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    public ProjectTitleAdapter(Context context, List<ProjectTitleItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_time_line, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectTitleItemBean item = list.get(position);

        if (isSingleClick){
            if (item.isSelect()) {
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_1876FF));
            } else {
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            }
        }else {
            if (item.isChoose()) {
                holder.viewCircleNo.setVisibility(View.GONE);
                holder.viewCircleYes.setVisibility(View.VISIBLE);
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            } else {
                holder.viewCircleNo.setVisibility(View.VISIBLE);
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_1876FF));
                holder.viewCircleYes.setVisibility(View.GONE);
            }
        }


        holder.tvName.setText(item.getName());

        holder.calAll.setOnClickListener(view -> {
            if (item.isChoose()) {
                itemClick.itemListener(item, position);
                setSingleSelect(position);
            }
        });

    }

    private void setSingleSelect(int position) {
        isSingleClick = true;
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setSelect(true);
            } else {
                list.get(i).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final View viewCircleNo;
        private final View viewCircleYes;
        private final ConstraintLayout calAll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            viewCircleNo = itemView.findViewById(R.id.view_circle_no);
            viewCircleYes = itemView.findViewById(R.id.view_circle_yes);
            calAll = itemView.findViewById(R.id.cl_all);
        }
    }
}

package com.example.taobaoadressdialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProjectAdapter
 * @Description: java类作用描述
 * @Author: Fuduo
 * @CreateDate: 2023/8/7 16:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/7 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context context;
    private List<ProjectItemBean> list;
    private ProjectItemClick itemClick;
    public List<String> letterAddedList = new ArrayList<>();

    public ProjectAdapter(Context context, List<ProjectItemBean> list,
                          ProjectItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    public ProjectAdapter(Context context, List<ProjectItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_project, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectItemBean item = list.get(position);
//        if (item.isChoose()) {
//            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_1876FF));
//        } else {
//            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
//        }
        holder.tvName.setText(item.getName());
        String firstLetter = item.getFirstLetter();
        if (!letterAddedList.contains(firstLetter)){
            holder.teLetter.setVisibility(View.VISIBLE);
            holder.teLetter.setText(firstLetter);
            letterAddedList.add(firstLetter);
        }else {
            holder.teLetter.setVisibility(View.INVISIBLE);
        }

        holder.lineAll.setOnClickListener(view -> {
            itemClick.itemListener(item);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, teLetter;
        private final LinearLayout lineAll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            teLetter = itemView.findViewById(R.id.te_letter);
            lineAll = itemView.findViewById(R.id.line_all);
        }
    }
}

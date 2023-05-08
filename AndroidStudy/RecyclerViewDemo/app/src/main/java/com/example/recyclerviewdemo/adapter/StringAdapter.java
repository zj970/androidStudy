package com.example.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.listener.OnItemChildClickListener;
import com.example.recyclerviewdemo.listener.OnItemChildLongClickListener;
import com.example.recyclerviewdemo.listener.OnItemClickListener;
import com.example.recyclerviewdemo.listener.OnItemLongClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-05 下午4:47
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {
    private List<String> list;

    private OnItemClickListener listener;

    private OnItemChildClickListener childClickListener;

    private OnItemChildLongClickListener childLongClickListener;

    private OnItemLongClickListener longClickListener;

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setChildLongClickListener(OnItemChildLongClickListener childLongClickListener) {
        this.childLongClickListener = childLongClickListener;
    }

    public void setChildClickListener(OnItemChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public StringAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        handlerEvents(view, viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tvText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvText;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    private void handlerEvents(View view, ViewHolder viewHolder) {
        //添加视图点击事件
        view.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        //添加子控件点击事件
        //添加多个子控件点击事件
        addChildClicks(new int[]{R.id.btn_test, R.id.btn_test_2}, view, viewHolder);
        //添加视图长按事件
        view.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                return longClickListener.onItemLongClick(v, viewHolder.getAdapterPosition());
            }
            return false;
        });
        //添加视图子控件长按事件
        view.findViewById(R.id.btn_test).setOnLongClickListener(v -> {
            if (childLongClickListener != null) {
                return childLongClickListener.onItemChildLongClick(v, viewHolder.getAdapterPosition());
            }
            return false;
        });
    }


    private void addChildClicks(int[] ids, View view, ViewHolder viewHolder) {
        for (int id : ids) {
            view.findViewById(id).setOnClickListener(v -> {
                if (childClickListener != null) {
                    childClickListener.onItemChildClick(v, viewHolder.getAdapterPosition());
                }
            });
        }
    }

}

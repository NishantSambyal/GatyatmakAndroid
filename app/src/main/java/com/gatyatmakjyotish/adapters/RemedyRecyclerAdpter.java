package com.gatyatmakjyotish.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.OnClickListener;
import com.gatyatmakjyotish.R;

import java.util.ArrayList;
import java.util.List;

public class RemedyRecyclerAdpter extends RecyclerView.Adapter<RemedyRecyclerAdpter.ViewHolder> {

    private List<PublishModel> publishModelList=new ArrayList<>();
    private OnClickListener onClickListener;
    private Context context;

    public RemedyRecyclerAdpter(List<PublishModel> strings, OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.publishModelList = strings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_publish, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final PublishModel obj = publishModelList.get(i);
        viewHolder.textView.setText(Html.fromHtml(context.getResources().getString(obj.getPublish())));
        viewHolder.book.setImageResource(obj.getImage());
        viewHolder.checkBox.setChecked(obj.getValue());
        if (onClickListener != null) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        onClickListener.onClick(obj, true);
                    } else {
                        onClickListener.onClick(obj, false);
                    }
                }
            });
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.checkBox.isChecked()) {
                        viewHolder.checkBox.setChecked(false);
                    } else {
                        viewHolder.checkBox.setChecked(true);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return publishModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;
        ImageView book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            checkBox = itemView.findViewById(R.id.checkbox);
            book = itemView.findViewById(R.id.book);
        }
    }
}

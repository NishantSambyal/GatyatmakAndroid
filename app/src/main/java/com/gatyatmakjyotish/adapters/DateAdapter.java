package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.days_pkg.ObjectItem;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.pojo.DateCategory;
import com.gatyatmakjyotish.util.SaveTextSize;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private Context context;
//    private List<DateCategory> dateCategoryArrayList = new ArrayList<>();
    private List<ObjectItem> dateCategoryArrayList = new ArrayList<>();


//    public DateAdapter(List<DateCategory> dateCategoryArrayList){
//        this.dateCategoryArrayList = dateCategoryArrayList;
//    }
    public DateAdapter(List<ObjectItem> dateCategoryArrayList){
        this.dateCategoryArrayList = dateCategoryArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.datecategorylist, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            ObjectItem obj = dateCategoryArrayList.get(i);
            viewHolder.feeling.setText(obj.getFeeling());
            viewHolder.description.setText(obj.getDescription());

//        viewHolder.feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
//
            if (obj.getFeeling().equalsIgnoreCase("Normal") || obj.getFeeling().equalsIgnoreCase("सामान्य"))
                viewHolder.feeling.setTextColor(Color.parseColor("#728FCE")); // Green
            else if (obj.getFeeling().equalsIgnoreCase("Positive") || obj.getFeeling().equalsIgnoreCase("सकारात्मक"))
                viewHolder.feeling.setTextColor(Color.parseColor("#228B22")); // Green
            else if (obj.getFeeling().equalsIgnoreCase("Negative") || obj.getFeeling().equalsIgnoreCase("ऋणात्मक"))
                viewHolder.feeling.setTextColor(Color.parseColor("#802606")); // Red

//        viewHolder.description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
//
//        if(i==dateCategoryArrayList.size()-1)
//            viewHolder.bottom_view.setVisibility(View.VISIBLE);

        }catch (Exception e){}
//        try{
//            viewHolder.feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
//            viewHolder.description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
//        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return dateCategoryArrayList.size();
    }

//    public void setAdapter(List<DateCategory> dateCategoryArrayList) {
//        this.dateCategoryArrayList.clear();
//        this.dateCategoryArrayList.addAll(dateCategoryArrayList);
//        notifyDataSetChanged();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView feeling, description;
        View bottom_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feeling = itemView.findViewById(R.id.tv_feeling);
            description = itemView.findViewById(R.id.tv_description);
            bottom_view = itemView.findViewById(R.id.bottom_view);
        }
    }
}

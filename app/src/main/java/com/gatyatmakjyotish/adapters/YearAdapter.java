package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.pojo.YearResult;
import com.gatyatmakjyotish.util.SaveTextSize;

import java.util.ArrayList;
import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
    private Context context;
    private List<YearResult> resultCategoryList = new ArrayList<>();

    public YearAdapter(List<YearResult> moviesList, Context context) {
        this.resultCategoryList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_year_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        YearResult obj = resultCategoryList.get(i);

        System.out.println("### Description-"+i+": "+obj.getDescription());
        if(obj.getFeeling().equalsIgnoreCase("Normal") || obj.getFeeling().equalsIgnoreCase("सामान्य"))
            viewHolder.feeling.setTextColor(Color.parseColor("#728FCE")); // Green
        else if(obj.getFeeling().equalsIgnoreCase("Positive") || obj.getFeeling().equalsIgnoreCase("सकारात्मक"))
            viewHolder.feeling.setTextColor(Color.parseColor("#228B22")); // Green
        else if(obj.getFeeling().equalsIgnoreCase("Negative") || obj.getFeeling().equalsIgnoreCase("ऋणात्मक"))
            viewHolder.feeling.setTextColor(Color.parseColor("#802606")); // Red

        viewHolder.feeling.setText(obj.getFeeling());
        viewHolder.description.setMovementMethod(new ScrollingMovementMethod());
        viewHolder.feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
        String after = obj.getDescription().replaceAll("\\s{2,}", " ").trim();
        viewHolder.description.setText(after);
        viewHolder.description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
    }

    @Override
    public int getItemCount() {
        try {
            return resultCategoryList.size();
        }catch (Exception e){
            return 0;
        }
    }

//    public void setAdapter(List<YearResult> resultCategoryList) {
//        this.resultCategoryList.clear();
//        this.resultCategoryList.addAll(resultCategoryList);
//        notifyDataSetChanged();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView feeling,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feeling = itemView.findViewById(R.id.tv_feeling);
            description = itemView.findViewById(R.id.tv_description);
        }
    }
}

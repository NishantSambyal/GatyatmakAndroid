package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.days_pkg.ObjectItem;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.util.SaveTextSize;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private Context context;
    private List<ObjectItem> dateCategoryArrayList = new ArrayList<>();
    private SharedPreferences languagePreference;
    public DateAdapter(List<ObjectItem> dateCategoryArrayList){
        this.dateCategoryArrayList = dateCategoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        languagePreference = context.getSharedPreferences("language", MODE_PRIVATE);
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.datecategorylist, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            ObjectItem obj = dateCategoryArrayList.get(i);

            if (obj.getFeeling().equalsIgnoreCase("Normal") || obj.getFeeling().equalsIgnoreCase("सामान्य"))
                viewHolder.feeling.setTextColor(Color.parseColor("#728FCE")); // Green
            else if (obj.getFeeling().equalsIgnoreCase("Positive") || obj.getFeeling().equalsIgnoreCase("सकारात्मक"))
                viewHolder.feeling.setTextColor(Color.parseColor("#228B22")); // Green
            else if (obj.getFeeling().equalsIgnoreCase("Negative") || obj.getFeeling().equalsIgnoreCase("ऋणात्मक"))
                viewHolder.feeling.setTextColor(Color.parseColor("#802606")); // Red

            if(languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.
                    ENGLISH.getLanguage())) {
                viewHolder.feeling.setText(obj.getFeeling());
                viewHolder.description.setMovementMethod(new ScrollingMovementMethod());

                    String after = obj.getDescription().replaceAll("\\s{2,}", " ").trim();
                    viewHolder.description.setText(after);
                    System.out.println("### daily: " + after);
            }else {
                viewHolder.feeling.setText(obj.getFeelingHindi());
                viewHolder.description.setMovementMethod(new ScrollingMovementMethod());
                String after = obj.getDescriptionHindi().replaceAll("\\s{2,}", " ").trim();
                viewHolder.description.setText(after);
                System.out.println("### daily: "+after);
            }

            try{
                viewHolder.feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
                viewHolder.description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
            }catch (Exception e){}


        }catch (Exception e){}


    }

    @Override
    public int getItemCount() {
        System.out.println("### daily size: " + dateCategoryArrayList.size());
        return dateCategoryArrayList.size();
//        return 5;

    }

//    public void setAdapter(List<DateCategory> dateCategoryArrayList) {
//        this.dateCategoryArrayList.clear();
//        this.dateCategoryArrayList.addAll(dateCategoryArrayList);
//        notifyDataSetChanged();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView feeling, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feeling = itemView.findViewById(R.id.tv_feeling);
            description = itemView.findViewById(R.id.tv_description1);
        }
    }
}

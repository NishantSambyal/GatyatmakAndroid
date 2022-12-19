package com.gatyatmakjyotish.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.OnClickListener;
import com.gatyatmakjyotish.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<PublishModel> list;
    private OnClickListener onClickListener;
    Context context;
    int lastcheckpos = -1;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";

    public ServiceAdapter(List<PublishModel> list, OnClickListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        sharedPreferences = viewGroup.getContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_service, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final PublishModel obj = list.get(i);
        //viewHolder.checkBox.setText(context.getString(obj.getPublish()));
          /*  if(obj.getValue())
            {
                lastcheckpos = i;
            }*/

        System.out.println("### isHoroMatchChecked2: "+obj.getValue());
        viewHolder.checkBox.setChecked(obj.getValue());

        SharedPreferences.Editor editor = sharedPreferences.edit();

            if (obj.getValue()){
                editor.putBoolean("is_horoscope_matching", true);
                editor.commit();
            }

        if (onClickListener != null) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked)
                    {
                        obj.setChecked(true);
                        list.get(i).setChecked(true);
//                        editor.putBoolean("is_horoscope_matching", true);
//                        editor.commit();

                        onClickListener.onClick(obj, true);
                        list.get(i).setValue(true);
                        if(lastcheckpos!=-1 && lastcheckpos!=i)
                        {
                            list.get(lastcheckpos).setValue(false);
                            onClickListener.onClick(list.get(lastcheckpos), false);
                            notifyDataSetChanged();
                        }
                        lastcheckpos = i;

                    }
                    else
                    {
                        obj.setChecked(true);
                        list.get(i).setChecked(false);
//                        editor.putBoolean("is_horoscope_matching", false);
//                        editor.commit();
                        onClickListener.onClick(obj, false);
                        list.get(i).setValue(false);
                    }

                    for (int j=0; j<list.size(); j++){
                        if (list.get(j).getChecked()){
                            editor.putBoolean("is_horoscope_matching", true);
                            editor.commit();
                            break;
                        }else {
                            editor.putBoolean("is_horoscope_matching", false);
                            editor.commit();
                        }
                    }

                }
            });
        }
        viewHolder.textView.setText(context.getString(obj.getPublish()));

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_service);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}

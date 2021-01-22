package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.NotificationModal;
import com.gatyatmakjyotish.R;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NotificationModal> dataist;
    Context context;

    public NotificationAdapter(ArrayList<NotificationModal> dataist, Context context) {
        this.dataist = dataist;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notofication, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final NotificationModal data = dataist.get(position);
        ViewHolder itemViewHolder = (ViewHolder) holder;
        itemViewHolder.txtTitle.setText("Title : "+data.getTitle());
        itemViewHolder.txtDesc.setText("Description : "+data.getDescription());
        itemViewHolder.txtLink.setText("Link : "+data.getLink());

        itemViewHolder.txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.getDailyresult_status().equalsIgnoreCase("0") && !data.getLink().equalsIgnoreCase(""))
                {
                    Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(data.getLink()));
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDesc, txtLink;

        ImageView update, download;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtLink = itemView.findViewById(R.id.txtLink);

        }
    }


}


package com.gatyatmakjyotish.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.pojo.TeamCategory;
import com.gatyatmakjyotish.util.SaveTextSize;

import java.util.List;

public class OurTeamListAdapter extends RecyclerView.Adapter<OurTeamListAdapter.ViewHolder> {
    private List<TeamCategory> teamCategories;
    private Context context;

    public OurTeamListAdapter(Context context,List<TeamCategory> teamCategories) {
        this.context = context;
        this.teamCategories = teamCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.team_row_remedy, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TeamCategory team = teamCategories.get(position);

        String after = team.getDesc().replaceAll("\\s{2,}", " ").trim();
        viewHolder.team_text.setText(after);
        viewHolder.team_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
        viewHolder.team_image.setImageResource(team.getImageId());
        viewHolder.team_name.setText(team.getName());
        viewHolder.team_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());

    }

    @Override
    public int getItemCount() {
        return teamCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView team_text;
        ImageView team_image;
        TextView team_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team_text = itemView.findViewById(R.id.team_text);
            team_image = itemView.findViewById(R.id.team_image);
            team_name = itemView.findViewById(R.id.team_name);
        }
    }
}

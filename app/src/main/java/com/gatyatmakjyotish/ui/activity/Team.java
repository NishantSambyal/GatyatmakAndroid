package com.gatyatmakjyotish.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.OurTeamListAdapter;
import com.gatyatmakjyotish.pojo.TeamCategory;
import com.gatyatmakjyotish.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Team extends AppCompatActivity {
    private TextView team;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        toolbar = findViewById(R.id.toolbar);

        Util.setupToolbar(this, toolbar, getString(R.string.team));
        recyclerView = findViewById(R.id.recycler_view);

        String names[] = getResources().getStringArray(R.array.team_name);
        String descriptions[] = getResources().getStringArray(R.array.ourteam);
        Integer[] images = { R.drawable.team_image1,
                R.drawable.team_image2, R.drawable.team_image3, R.drawable.team_image4, R.drawable.team_image5,0};

        List<TeamCategory> rowItems = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
           TeamCategory item = new TeamCategory(descriptions[i],images[i], names[i]);
           rowItems.add(item);
       }

       OurTeamListAdapter adapter = new OurTeamListAdapter(this,rowItems);
        recyclerView.setAdapter(adapter);
        // recyclerView.setAdapter(new AboutAdapter(Arrays.asList(getResources().getStringArray(R.array.ourteam))));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    }



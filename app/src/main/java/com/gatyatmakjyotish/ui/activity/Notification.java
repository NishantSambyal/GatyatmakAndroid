package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gatyatmakjyotish.ModelClass.NotificationModal;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.NotificationAdapter;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {
    RecyclerView recycler_view;
    RequestQueue queue;
    String URL = Api.GET_NOTIFICATIN;
    Context context;
    TextView toolbar_title;
    private Toolbar toolbar;

    ArrayList<NotificationModal> notificationModalArrayList;
    ProgressDialog p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        recycler_view = findViewById(R.id.recycler_view);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.app_name_header));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        context=this;
        p=new ProgressDialog(this);
        p.setMessage("Please Wait...");
        toolbar_title.setText("Notifications");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Util.setupToolbar(Notification.this, toolbar, "Notifications");
        getData();

    }


    public void getData() {
        p.show();
        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                p.dismiss();
                //Toast.makeText(Notification.this, response.toString(), Toast.LENGTH_LONG).show();
                notificationModalArrayList=new ArrayList<>();
                try
                {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonArray=jsonObject.getJSONArray("object");
                    for(int i=0;i<=jsonArray.length()-1;i++)
                    {
                        NotificationModal notificationModal=new NotificationModal();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String title=jsonObject1.getString("title");
                        String description=jsonObject1.getString("description");
                        String link=jsonObject1.getString("link");
                        String dailyresult_status=jsonObject1.getString("dailyresult_status");
                        String created_at=jsonObject1.getString("created_at");
                        String updated_at=jsonObject1.getString("updated_at");
                        notificationModal.setId(id);
                        notificationModal.setTitle(title);
                        notificationModal.setDescription(description);
                        notificationModal.setLink(link);
                        notificationModal.setDailyresult_status(dailyresult_status);
                        notificationModal.setCreated_at(created_at);
                        notificationModal.setUpdated_at(updated_at);
                        notificationModalArrayList.add(notificationModal);
                    }
                    NotificationAdapter feeAdapter = new NotificationAdapter(notificationModalArrayList, context);
                    recycler_view.setAdapter(feeAdapter);
                    recycler_view.scheduleLayoutAnimation();
                }
                catch (Exception e)
                {
                    p.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p.dismiss();
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.gatyatmakjyotish.ui.activity.Books.mypreference;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<PublishModel> publishModelList = new ArrayList<>();
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int total = 0;
    public static boolean add = true;
    TextView textView;
    OnClick onClickListener;

    public CartAdapter(List<PublishModel> publishModelList, OnClick onClickListener) {
        this.publishModelList = publishModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final PublishModel obj = publishModelList.get(i);
        viewHolder.title.setText(context.getResources().getString(obj.getTitle()));

        viewHolder.price.setText("Rs. " + obj.getPrice());
        viewHolder.description.setText(Html.fromHtml(context.getResources().getString(obj.getPublish())));

        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(Html.fromHtml(context.getResources().getString(obj.getTitle()))).equalsIgnoreCase("Daily forecast") || String.valueOf(Html.fromHtml(context.getResources().getString(obj.getTitle()))).equalsIgnoreCase("दैनिक पूर्वानुमान"))
                    editor.putBoolean("dailyStatus",false);
                else if(String.valueOf(Html.fromHtml(context.getResources().getString(obj.getTitle()))).equalsIgnoreCase("Yearly forecast") || String.valueOf(Html.fromHtml(context.getResources().getString(obj.getTitle()))).equalsIgnoreCase("वार्षिक पूर्वानुमान"))
                    editor.putBoolean("yearlyStatus",false);

                editor.commit();

                onClickListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return publishModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, description,textView;
        Button remove;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            description = itemView.findViewById(R.id.tv_description);
            remove = itemView.findViewById(R.id.cart_remove);
            textView = itemView.findViewById(R.id.text);
        }
    }

    public void deleteCartItem(int position) {
        publishModelList.remove(position);

        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();*/


        editor.putString("cart", new Gson().toJson(publishModelList));
        editor.commit();
        notifyDataSetChanged();
    }

    public interface OnClick{
        void onClick(int position);
    }
}


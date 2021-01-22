package com.gatyatmakjyotish.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.OnClickListener;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.RemedyRecyclerAdpter;
import com.gatyatmakjyotish.pojo.Books;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Publish extends Fragment {
    private TextView publish;
    private RecyclerView recyclerView,recyclerView1;
    private CheckBox checkBox;
    private ImageView imageView, book;
    private List<Books> list;
    private Button button;
    OnClickListener onClickListener;
    List<PublishModel> publishList;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish, container, false);
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        publish = view.findViewById(R.id.textview);
        checkBox = view.findViewById(R.id.checkbox);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView1 = view.findViewById(R.id.recycler_view1);
        onClickListener = (OnClickListener) getActivity();
        setAdapter();
        setAdapter1();
        return view;

    }

    private void setAdapter() {
        recyclerView.setAdapter(new RemedyRecyclerAdpter(makeList(), onClickListener));
    }

    private void setAdapter1() {
        recyclerView1.setAdapter(new RemedyRecyclerAdpter(makeList1(), onClickListener));
    }



    public List<PublishModel> makeList() {
        publishList = new ArrayList<>();
        int images[] = {R.drawable.book, R.drawable.book_sangeeta};
        int description[] = {R.string.pubb1, R.string.pubb2};
        int title[] = {R.string.title_book1, R.string.title_book2};
        int[] prices = {400, 400};

        //int images[] = {R.drawable.book, R.drawable.book_sangeeta, R.drawable.book, R.drawable.book_sangeeta, R.drawable.book};
        //int description[] = {R.string.pubb1, R.string.pubb2, R.string.service5, R.string.service6, R.string.service7};
        //int title[] = {R.string.title_book1, R.string.title_book2, R.string.title_service5, R.string.title_service6, R.string.title_service7};


        for (int i = 0; i < images.length; i++) {
            publishList.add(new PublishModel(description[i], images[i], false, title[i], prices[i]));
        }
        getSavedSelection();
        return publishList;

    }

    public List<PublishModel> makeList1() {
        publishList = new ArrayList<>();
        //int images[] = {R.drawable.book, R.drawable.book_sangeeta};
        //int description[] = {R.string.pubb1, R.string.pubb2};
        //int title[] = {R.string.title_book1, R.string.title_book2};

        int images[] = {R.drawable.ebookimg, R.drawable.ebookimg1, R.drawable.ebookimg2};
        int description[] = {R.string.service5, R.string.service6, R.string.service7};
        int title[] = {R.string.title_service5, R.string.title_service6, R.string.title_service7};
        int[] prices = {200, 200, 200};

        for (int i = 0; i < images.length; i++) {
            publishList.add(new PublishModel(description[i], images[i], false, title[i], prices[i]));
        }
        getSavedSelection();
        return publishList;

    }



    private void getSavedSelection() {
        List<PublishModel> publishModelList = new ArrayList<>();
        String json = sharedPreferences.getString("cart", "");
        if (!(json != null && json.isEmpty())) {
            Type type = new TypeToken<List<PublishModel>>() {
            }.getType();
            publishModelList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
        } else {
        }
        if (publishModelList != null) {
            for (PublishModel obj : publishList) {
                for (PublishModel obj1 : publishModelList) {
                    if (obj.getPublish().equals(obj1.getPublish())) {
                        obj.setValue(true);
                    }
                }
            }
        }
    }
}
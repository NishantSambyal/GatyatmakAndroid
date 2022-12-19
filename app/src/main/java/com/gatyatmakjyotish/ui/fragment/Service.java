package com.gatyatmakjyotish.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.OnClickListener;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.ServiceAdapter;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Service extends Fragment {
    private TextView publish;
    private RecyclerView recyclerView;
    private CheckBox checkBox;
    private Button button;
    OnClickListener onClickListener;
    List<PublishModel> publishList;
    //int[] prices = {500, 1100, 500, 500, 500};
    int[] prices = {800, 800, 800, 800};
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_list, container, false);
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        publish = view.findViewById(R.id.textview);
        checkBox = view.findViewById(R.id.check_service);
        recyclerView = view.findViewById(R.id.recycler_view);
        onClickListener = (OnClickListener) getActivity();
        Util.setLinearLayoutManagerNestedScroll(getActivity(), recyclerView);

        recyclerView.setAdapter(new ServiceAdapter(makeList(), onClickListener));
        return view;
    }

    public List<PublishModel> makeList() {
        publishList = new ArrayList<>();
        int title[] = {R.string.title_service1, R.string.title_service2, R.string.title_service3, R.string.title_service4};
        int description[] = {R.string.service1, R.string.service2, R.string.service3,R.string.service4};
        for (int i = 0; i < prices.length; i++) {
            publishList.add(new PublishModel(description[i], null, false, title[i], prices[i], false));
        }
        getSavedSelection();
        return publishList;
    }

    private void getSavedSelection(){
        List<PublishModel> publishModelList = new ArrayList<>();
        String json = sharedPreferences.getString("cart", "");
        if (!(json != null && json.isEmpty())) {
            Type type = new TypeToken<List<PublishModel>>() {}.getType();
            publishModelList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);        }
            else {
        }
        if (publishModelList != null) {
            for (PublishModel obj : publishList) {
                for (PublishModel obj1: publishModelList){
                    if (obj.getPublish().equals(obj1.getPublish())){
                        obj.setValue(true);
                    }
                }
            }
        }
    }



}

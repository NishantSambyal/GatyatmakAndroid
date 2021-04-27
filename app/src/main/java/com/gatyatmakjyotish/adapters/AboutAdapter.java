package com.gatyatmakjyotish.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.BuildConfig;
import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.ui.activity.Cart;
import com.gatyatmakjyotish.ui.activity.OurMissionActivity;
import com.gatyatmakjyotish.ui.activity.SubscriptionActivity;
import com.gatyatmakjyotish.util.SaveTextSize;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;
import static com.gatyatmakjyotish.ui.activity.Books.mypreference;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private List<String> publishModelList;
    private Context context;
    public static Boolean dailyStatus = false;
    public static Boolean yearlyStatus = false;
    SharedPreferences sharedPreferences;


    public AboutAdapter(List<String> publishModelList) {

        this.publishModelList = publishModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_remedy, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String after = publishModelList.get(i).replaceAll("\\s{2,}", " ").trim();
        viewHolder.textView.setText(after);
        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
        if(context instanceof OurMissionActivity)
            viewHolder.imageView.setVisibility(View.VISIBLE);
        if(context instanceof SubscriptionActivity){
            SharedPreferences languagePreference = context.getSharedPreferences("language", MODE_PRIVATE);
            Boolean languageStatus = true;
            if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.
                    ENGLISH.getLanguage()))
                languageStatus = true;
            else
                languageStatus = false;

            String wordToFind = "";
                //String subscriptionText = "I want THIS and THIS to be clickable";
            String subscriptionText = publishModelList.get(i);
            if(languageStatus)
                wordToFind = "Subscribe For Daily";
            else
                wordToFind = "दैनिक भविष्यफल के लिएं";

            int start = 0,end = 0,start1 = 0, end1 = 0, start2 = 0, end2 = 0;
            Matcher match = Pattern.compile(wordToFind).matcher(publishModelList.get(i));
            while (match.find()) {
                start = match.start();
                end = match.end();
                break;
            }

            if(languageStatus)
                wordToFind = "Subscribe For Yearly";
            else
                wordToFind = "वार्षिक भविष्यफल लें लिएं";

            Matcher match2 = Pattern.compile(wordToFind).matcher(publishModelList.get(i));
            while (match2.find()) {
                start2 = match2.start();
                end2= match2.end();
                break;
            }
            if(languageStatus)
                wordToFind = "Get Gatyatmak Points";
            else
                wordToFind = "गत्यात्मक पॉइंट प्राप्त करें";

            Matcher match1 = Pattern.compile(wordToFind).matcher(publishModelList.get(i));
            while (match1.find()) {
                start1 = match1.start();
                end1 = match1.end();
                break;
            }

            SpannableString ss = new SpannableString(subscriptionText);

            ClickableSpan clickableSpan2 = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //yearlyStatus = true;
                    int title[] = {R.string.yearlyforecast};
                    int description[] = {R.string.yearly_cart};


                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");
                    if(sharedPreferences.getBoolean("dailyStatus",false)){
                        if(!sharedPreferences.getBoolean("yearlyStatus",false)){
                            Type type = new TypeToken<List<PublishModel>>() {}.getType();
                            publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                            publishList.add(new PublishModel(description[0],22332,true,title[0],400));
                        }
                    }
                    else{
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0],22332,true,title[0],400));
                    }

                    yearlyStatus = true;
                    editor.putBoolean("yearlyStatus",true);
                    editor.commit();
                    if(publishList.size() != 0) {
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }
                    Intent intent = new Intent(context, Cart.class);
                    context.startActivity(intent);
                }
            };

            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Util.showDialog(context, context.getString(R.string.gatyatmak_ponits_heading), context.getString(R.string.gatyatmak_points), true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            shareApp();
                        }
                    });
                }
            };

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //dailyStatus = true;
                    int title[] = {R.string.dailyforecast};
                    int description[] = {R.string.daily_cart};


                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");

                    Type type = new TypeToken<List<PublishModel>>() {}.getType();

                    if(sharedPreferences.getBoolean("yearlyStatus",false)){
                            if(!sharedPreferences.getBoolean("dailyStatus",false)){
                                publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                                publishList.add(new PublishModel(description[0],22332,true,title[0],400));
                            }
                    }
                    else{
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0],22332,true,title[0],400));
                    }

                    dailyStatus = true;
                    editor.putBoolean("dailyStatus",true);
                    editor.commit();
                    if(publishList.size() != 0){
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }
                    Intent intent = new Intent(context, Cart.class);
                    intent.putExtra("reference","SubscriptionActivity");
                    context.startActivity(intent);
                }
            };

            ss.setSpan(clickableSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(clickableSpan1,start1,end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(clickableSpan2,start2,end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.textView.setText(ss);
            viewHolder.textView.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolder.textView.setHighlightColor(Color.TRANSPARENT);

        }

    }

    public void shareApp(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            /*String shareMessage= "\nI recommend you this application, download this for accurate\n" +
                    "time-bound  day-to-day and yearly forecast for free and get points for\n" +
                    "availing services of Gatyatmak Jyotish, a new branch in Astrology.\n\n";*/
            String shareMessage= "\n"+context.getString(R.string.share_app)+"\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    @Override
    public int getItemCount() {
        return publishModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.our_mission_icon);
        }
    }
}

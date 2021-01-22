package com.gatyatmakjyotish.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.util.ImageViewer;
import com.gatyatmakjyotish.util.Util;

import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.PASSWORD;

public class WaitingForLetter extends AppCompatActivity {
    private Button button;
    private Button menuOption;
    private ImageView imageView;
    private Toolbar toolbar;
    private String path;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_letter);
        setTitle("Thankyou for signing in");
        button = findViewById(R.id.ok_button);
        imageView = findViewById(R.id.pandit);
        menuOption = findViewById(R.id.btnMenu);

        toolbar = findViewById(R.id.toolbar);

        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.thanksmessage));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitingForLetter.this, ImageViewer.class));
            }
        });

        sharedPreferences = getSharedPreferences("language", MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final String emailStr = sharedPreferences.getString(EMAIL, "");
        final String passwordStr = sharedPreferences.getString(PASSWORD, "");

       /* final MainActivity mainActivity=new MainActivity();
        mainActivity.postNewComment(getApplicationContext(), emailStr,passwordStr,editor);
*/
        button.setText("Check Process");
        //todo check letter assinged
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), DashBoard.class);
               /* Intent intent = new Intent(getActivity(), FrontScreen.class);
                startActivity(intent);
                getActivity().finish();*/
              //  mainActivity.postNewComment(getApplicationContext(), emailStr,passwordStr,editor);

            }
        });
        registerForContextMenu(menuOption);
        menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId()== R.id.btnMenu) {
            //  menu.setHeaderIcon(android.R.drawable.sym_def_app_icon);
            //menu.setHeaderTitle("Show Menu");
            menu.add(0, v.getId(), 0, R.string.pointsforliving);
            menu.add(0, v.getId(), 1, R.string.our_mission);
            menu.add(0, v.getId(), 2, R.string.team);
            menu.add(0, v.getId(), 3, R.string.remedy);
            menu.add(0, v.getId(), 4, R.string.aboutus);

            SubMenu subMenu=menu.addSubMenu(1,v.getId(),5,"Social Sites");
            subMenu.add(1, v.getId(), 0, "Facebook");
            subMenu.add(1, v.getId(), 1, "Youtube");
            subMenu.add(1, v.getId(), 1, "Twitter");
            subMenu.add(1, v.getId(), 1, "Instagram");
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == getString(R.string.pointsforliving)){
            Intent intent2 = new Intent(WaitingForLetter.this, PointstoKnow.class);
            startActivity(intent2);
        }
        if (item.getTitle() == getString(R.string.our_mission)) {
            Intent intent2 = new Intent(WaitingForLetter.this, OurMissionActivity.class);
            startActivity(intent2);
        }
        else if (item.getTitle() == getString(R.string.team)) {
            Intent intent2 = new Intent(WaitingForLetter.this, Team.class);
            startActivity(intent2);
        }
        else if (item.getTitle() == getString(R.string.remedy)) {
            Intent intent2 = new Intent(WaitingForLetter.this, Remedy.class);
            startActivity(intent2);
        }
        else if (item.getTitle() == getString(R.string.aboutus)) {
            Intent intent2 = new Intent(WaitingForLetter.this, AboutUs.class);
            startActivity(intent2);
        }
        else if (item.getTitle() == "Facebook") {
            watchFacebookVideo();
        }
        else if (item.getTitle() == "Youtube") {
            watchYoutubeVideo();
        }
        else if (item.getTitle() == "Twitter") {
            watchTwitterVideo();
        }
        else if (item.getTitle() == "Instagram") {
            watchInstagramvideo();
        }
        return true;
    }

    public void watchFacebookVideo(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/gatyatmakjyotish/"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }
    public void watchYoutubeVideo() {
//        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/channel/UCAi6aRIvEIswUm2ego5grow"));
        startActivity(webIntent);
        try {
//            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
    public  void  Instagram(View view){
        watchInstagramvideo();
    }
    public void watchInstagramvideo(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/gatyatmak_jyotish/"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    public  void  Twitter(View view){
        watchTwitterVideo();
    }
    public void watchTwitterVideo(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/gatyatmakJyotis"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreferences = getApplicationContext().getSharedPreferences(Constants.LOGIN_PREF, 0); // 0 - for private mode
               SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

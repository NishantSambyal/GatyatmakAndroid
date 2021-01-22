package com.gatyatmakjyotish.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gatyatmakjyotish.BuildConfig;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.activity.AboutUs;
import com.gatyatmakjyotish.ui.activity.Books;
import com.gatyatmakjyotish.ui.activity.Cart;
import com.gatyatmakjyotish.ui.activity.ContactUs;
import com.gatyatmakjyotish.ui.activity.MainActivity;
import com.gatyatmakjyotish.ui.activity.OurMissionActivity;
import com.gatyatmakjyotish.ui.activity.Payment;
import com.gatyatmakjyotish.ui.activity.PointstoKnow;
import com.gatyatmakjyotish.ui.activity.ReferEarn;
import com.gatyatmakjyotish.ui.activity.Remedy;
import com.gatyatmakjyotish.ui.activity.Set;
import com.gatyatmakjyotish.ui.activity.SubscriptionActivity;
import com.gatyatmakjyotish.ui.activity.Team;
import com.gatyatmakjyotish.ui.activity.UseofApp;
import com.gatyatmakjyotish.ui.activity.UserProfile;
import com.gatyatmakjyotish.ui.fragmentBase.Blog;
import com.gatyatmakjyotish.util.Util;

import java.text.DecimalFormat;

import hotchemi.android.rate.AppRate;

import static android.content.Context.MODE_PRIVATE;
import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.MOBILE;
import static com.gatyatmakjyotish.constants.Constants.NAME;
import static com.gatyatmakjyotish.constants.Constants.PROMOCODE;
import static com.gatyatmakjyotish.constants.Constants.WALLET_POINT;
public class FragmentNavigationDrawer extends Fragment {

    View containerView;
    Button langbtn;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    LinearLayout language, otherServices, payment, blog, linearOurMission;
    TextView Users, langselect, service, paid, blo, number, name, email, wallet_point;
    LinearLayout Settings, logout,about,rate,contact,team, referAndEarn,useofapp,articles,publish_services,marketing,remedy,cart,social,shareApp,subscription;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static DecimalFormat dacimalFormat = new DecimalFormat("#.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_layout, container, false);
        langbtn = view.findViewById(R.id.langbtn);
        Users = view.findViewById(R.id.users);
        referAndEarn = view.findViewById(R.id.refer_and_earn);
        paid = view.findViewById(R.id.paid);
        cart=view.findViewById(R.id.cart);
        social=view.findViewById(R.id.social);
        logout = view.findViewById(R.id.logout);
        number=view.findViewById(R.id.number);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        wallet_point = view.findViewById(R.id.wallet_point);
        payment = view.findViewById(R.id.payment);
        blog = view.findViewById(R.id.blog);
        Settings = view.findViewById(R.id.setting);
        about=view.findViewById(R.id.about);
        rate=view.findViewById(R.id.rate);
        contact=view.findViewById(R.id.contact);
        team=view.findViewById(R.id.team);
        useofapp=view.findViewById(R.id.useofapp);
        articles=view.findViewById(R.id.articles);
        linearOurMission=view.findViewById(R.id.linear_our_mission);
        publish_services=view.findViewById(R.id.publish_services);
        marketing=view.findViewById(R.id.marketing);
        remedy=view.findViewById(R.id.remedy);
        shareApp=view.findViewById(R.id.shareApp);
        subscription=view.findViewById(R.id.subscription);
        sharedPreferences = getActivity().getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        String nameStr = sharedPreferences.getString(NAME, "");
        String contactlStr = sharedPreferences.getString(MOBILE, "");
        String emailStr = sharedPreferences.getString(EMAIL,"");
        String walletPointStr = sharedPreferences.getString(WALLET_POINT,"");

        name.setText(nameStr);
        number.setText(contactlStr);
        email.setText(emailStr);
       wallet_point.setText("Gatyatmak Point : "+walletPointStr);

     //   wallet_point.setText("Gatyatmak Point  ");


rate.setOnClickListener(new View.OnClickListener(){

    @Override
    public void onClick(View v) {
//        AppRate.with(this)
//                .setInstallDays(0)
//                .setLaunchTimes(3)
//                .setRemindInterval(1)
//                .monitor();
//        AppRate.showRateDialogIfMeetsConditions(this);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getActivity().getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }
});
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
//                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                // To count with Play market backstack, After pressing back button,
//                // to taken back to our application, we need to add following flags to intent.
//                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
//                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                try {
//                    startActivity(goToMarket);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
//                }
//            }
//        });

        Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfile.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        publish_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Books.class).putExtra("Service","Publications");
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(),Cart.class);
               intent.putExtra("reference","Navigation");
               startActivity(intent);
               mDrawerLayout.closeDrawers();
           }
       });

        remedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Remedy.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PointstoKnow.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        linearOurMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), OurMissionActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

       contact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getActivity(),ContactUs.class);
               startActivity(intent);
               mDrawerLayout.closeDrawers();
           }
       });


        social.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               mDrawerLayout.closeDrawers();


           }

       });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Payment.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Set.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        useofapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UseofApp.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });


        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Blog.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });
        referAndEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReferEarn.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });



        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AboutUs.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.showDialogButton(getActivity(),"Logout Confirmation", "Do you want to logout.", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == -1){
                            sharedPreferences = getActivity().getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            /*sharedPreferences = getActivity().getSharedPreferences("language", MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();*/
                            //Intent intent = new Intent(getActivity(), FrontScreen.class);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            mDrawerLayout.closeDrawers();
                            getActivity().finish();
                        }
                        else
                            dialog.dismiss();
                    }
                });
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Team.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showDialog(getActivity(), getString(R.string.gatyatmak_ponits_heading), getString(R.string.gatyatmak_points), true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        shareApp();
                    }
                });
            }
        });

        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });


        return view;
    }

    public static FragmentNavigationDrawer newInstance() {

        return (new FragmentNavigationDrawer());

    }

    public void setPoints(String points){
        wallet_point.setText("Gatyatmak Point : " +points);
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void shareApp(){
        try {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            /*String shareMessage= "\nI recommend you this application, download this for accurate\n" +
                    "time-bound  day-to-day and yearly forecast for free and get points for\n" +
                    "availing services of Gatyatmak Jyotish, a new branch in Astrology.\n\n";*/
            String shareMessage= "\n"+getString(R.string.share_app)+"\n\n"+"Use Promo Code " +sharedPreferences.getString(PROMOCODE,"")+"\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}

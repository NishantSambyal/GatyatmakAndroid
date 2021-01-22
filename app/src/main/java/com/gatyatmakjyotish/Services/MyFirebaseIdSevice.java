package com.gatyatmakjyotish.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by hp on 4/14/2018.
 */

public class MyFirebaseIdSevice extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        System.out.println("eyuyuyuyuewyuweyuwe  " +refreshedToken);
       // Common.currentToken=refreshedToken;
    }
}

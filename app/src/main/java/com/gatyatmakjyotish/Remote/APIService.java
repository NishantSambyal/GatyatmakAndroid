package com.gatyatmakjyotish.Remote;

import com.gatyatmakjyotish.NotificationModel.MyResponse;
import com.gatyatmakjyotish.NotificationModel.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hp on 4/15/2018.
 */

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAArXzxTAA:APA91bHCiMTTawO7qjVVxwINmKb9_vOdvE_McJmH0KfYTVbPT4laZF1WexW_9Ny5tZpURr_7MNg5BO3s9K4BUkCSraPgWrkxJ6s9RlCwlac2o6yCAdjzQutlH5BUJmrCFv-V5QPtfPs6"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
    Call<MyResponse> attendanceNotification(@Body Sender body);
}

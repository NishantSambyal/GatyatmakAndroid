package com.gatyatmakjyotish.constants;

public class Constants {


    public enum Language{
        ENGLISH("english") , HINDI("hindi");
        private String language;

        Language(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }
    }
    public static final String LOGIN_PREF = "login_pref";
    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String NOTIFICATION_PREF = "notification_pref";
    public static final String TEXT_PREF = "text_pref";
    public static final String TEXT_SIZE = "text_size";
    public static final String LANGUAGE = "";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String PLACE_OF_BIRTH = "place_of_birth";
    public static final String CURRENT_PLACE = "currenPlace";
    public static final String EMAIL = "";
    public static final String WALLET_POINT = "wallet_point";
    public static final String PROMOCODE = "promocode";
    public static final String EMAIL_VERIFIED_AT = "email_verfied_at";
    public static final String ID = "id";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String TIME_OF_BIRTH = "time_of_birth";
    public static final String MOBILE = "mobile";
    public static final String GENDER="gender";
    public static final String TITLE="title";
    public static final String HINDI_NAME="hindi_name";

    public static final String USERINFO="user_info";
    public static final String  MESSAGE="message";
    public static final String CURRENT_PASSWORD="current_password";
    public static final String PASSWORD="password";
    public static final String CONFIRM_PASSWORD="confirm_password";

    public static final String CART="cart";

}

package com.wifi.yilong.yilongwifi.Infrastructure;

/**
 * Created by Administrator on 2017/1/24.
 */

public class AppConstant {
    public class REQUEST{
        public static final int  REQUEST_SIGNUP = 0;
        public static final int  REQUEST_TIP_DIALOG = 1;
        public static final int REQUEST_LOCATION_DETAIL_VIEWPAGER = 2;
        public static final int REQUEST_ADD_REVIEW = 3;
        public static final int REQUEST_LOCATION_SERVICE = 4;
        public static final int REQUEST_LOCATION_NEW_LOCATION_NOTIFICATION = 5;

    }

    public class Dialog{
        public static final String  OK = "ok";
        public static final String  CANCEL = "cancel";
        public static final String  TITLE = "title";
        public static final String  MESSAGE = "message";
    }

    public class Tag{
        public static final String  TIPDIALOGFRAGMENT = "tip_dialog_fragment";
    }

    public class SharedPreferencesKey{
        public static final String SEARCHVIEWQUERY = "query";
        public static final String ID = "id";
        public static final String IS_LOCATION_SERVICE_ON = "is_location_service_on";
        public static final String LAST_LOCATION_ID = "last_location_id";
    }
    public class Action{
        public static final String SHOW_NOTIFICATION = "com.wifi.yilong.yilongwifi.show_notification";
    }

    public class Interval{
        public static final int LOCATION_SERVICE_INTERVAL = 1000 * 60 * 5;
    }

    public class Signature{
        public static final String ACTION_SHOW_NEW_LOCATION_SIGNATURE = "";
    }

    public class Extra{
        public static final String INT_SHOW_NEW_LOCATION_NOTIFICATION_REQUEST
                = "int_show_new_location_notification_request";
        public static final String INT_SHOW_NEW_LOCATION_NOTIFICATION_NOTIFICATION
                = "int_show_new_location_notification_notification";
    }
}

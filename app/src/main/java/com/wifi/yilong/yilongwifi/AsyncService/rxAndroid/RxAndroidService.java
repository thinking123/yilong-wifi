package com.wifi.yilong.yilongwifi.AsyncService.rxAndroid;

import android.content.Context;

/**
 * Created by Administrator on 2017/2/12.
 */

public class RxAndroidService<T> {
    private static RxAndroidService mRxAndroidService;
    public static RxAndroidService get(Context context){
        if(mRxAndroidService == null){
            mRxAndroidService = new RxAndroidService(context);
        }

        return mRxAndroidService;
    }

    private Context mContext;
    private RxAndroidService(Context c){
        mContext = c;

    }




//
//    public void asyncObservable(Callable<T> asyncMethod , Subscriber<T> uiMethod){
//        Observable<T> observable = Observable
//                .fromCallable(asyncMethod);
////
////        Subscription mTvShowSubscription = observable
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(uiMethod);
//
//
//
//    }

    public void asyncSignal(){

    }


}

package com.wifi.yilong.yilongwifi.ThreadHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.wifi.yilong.yilongwifi.Http.HttpHelper;
import com.wifi.yilong.yilongwifi.Model.Review;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/30.
 */

public class TaskHandlerThead<T> extends HandlerThread {
    private Handler mRespondHandler;
    private Handler mWorkHandler;
    private HashMap<T , String> maps;
    private TaskCompleted callback;

    public static final int DOWNLOADTASK = 0;
    public static final int POSTREVIEWDATA = 1;
    public static final String TAG = "TaskHandlerThead";
    public static final String POSTHEADERS = "postheaders";

    public interface TaskCompleted{
        public void TaskCompletedCallback(Object result);
    }
    public TaskHandlerThead(TaskCompleted _callback , Handler _respondHandler) {
        super(TAG);
        callback = _callback;
        mRespondHandler = _respondHandler;
        maps = new HashMap<T,String>();
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkHandler = new Handler()
        {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case  DOWNLOADTASK:
                        handlerDownloadTaskRequest();
                        break;
                    case POSTREVIEWDATA:
                        T postData = (T)msg.obj;
                        Bundle args = msg.getData();
                        HashMap<String , String> headers = null;
                        if(args != null){
                            headers = ( HashMap<String , String>)args.getSerializable(POSTHEADERS);
                        }
                        String postUrl = maps.get(postData);
                        handlerPostReviewTaskRequest(postData , postUrl , headers);

                        break;
                }

//                msg.recycle();
            }
        };

    }

    public void queueTask(T target , String url){
        maps.put(target , url);
        mWorkHandler
                .obtainMessage(DOWNLOADTASK)
                .sendToTarget();
    }

    public void queueTask(int handlerRequest , T postData , String postUrl , HashMap<String , String> headers){
       Message msg =  mWorkHandler.obtainMessage(handlerRequest , postData);
        if(headers != null){
            Bundle args = new Bundle();
            args.putSerializable(POSTHEADERS , headers);
            msg.setData(args);
        }
        maps.put(postData , postUrl);
        msg.sendToTarget();

    }

    private void handlerDownloadTaskRequest(){
        //TODO do task

        mRespondHandler.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
//                    callback.TaskCompletedCallback();
                }

            }
        });
    }

    private void handlerPostReviewTaskRequest(T js , String postUrl , HashMap<String , String> headers){
        Review review = HttpHelper.AddReview(postUrl , (JSONObject)js , headers);
        if(callback != null){
            callback.TaskCompletedCallback(review);
        }
    }
    public void clearTaskQueue(){
        maps.clear();
        mWorkHandler.removeMessages(DOWNLOADTASK);
    }
}

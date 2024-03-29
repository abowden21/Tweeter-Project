package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class FollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {

    private final FollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followSuccessful(FollowResponse followResponse);
        void unfollowSuccessful(FollowResponse followResponse);
        void actionUnsuccessful(FollowResponse followResponse);
        void handleException(Exception ex);
    }

    public FollowTask(FollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse followResponse = null;

        try {
            followResponse = presenter.setFollow(followRequests[0]);

        } catch (IOException ex) {
            exception = ex;
        }

        return followResponse;
    }

    @Override
    protected void onPostExecute(FollowResponse followResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(followResponse.isSuccess() && followResponse.isFollowResponse()) {
            observer.followSuccessful(followResponse);
        }
        else if(followResponse.isSuccess() && !followResponse.isFollowResponse()) {
            observer.unfollowSuccessful(followResponse);
        } else {
            observer.actionUnsuccessful(followResponse);
        }
    }
}


package edu.byu.cs.tweeter.model.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowersService extends ServiceBase  {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException {
        FollowersResponse response = getServerFacade().getFollowers(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(FollowersResponse response) throws IOException {
        for(User user : response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }
}

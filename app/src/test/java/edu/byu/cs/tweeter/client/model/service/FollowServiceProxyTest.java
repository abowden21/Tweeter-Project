package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.FollowRequest;
import edu.byu.cs.tweeter.shared.model.request.FollowStatusRequest;
import edu.byu.cs.tweeter.shared.model.response.FollowResponse;
import edu.byu.cs.tweeter.shared.model.response.FollowStatusResponse;

public class FollowServiceProxyTest {

    private FollowRequest validFollowRequest;
    private FollowRequest invalidFollowRequest;
    private FollowResponse successFollowResponse;
    private FollowResponse failureFollowResponse;

    private FollowRequest validUnfollowRequest;
    private FollowRequest invalidUnfollowRequest;
    private FollowResponse successUnfollowResponse;
    private FollowResponse failureUnfollowResponse;

    private FollowStatusRequest validFollowStatusRequest;
    private FollowStatusRequest invalidFollowStatusRequest;
    private FollowStatusResponse successFollowStatusResponse;
    private FollowStatusResponse failureFollowStatusResponse;

    private FollowServiceProxy mFollowServiceProxySpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
     User currentUser = new User("FirstName", "LastName", null);
     User otherUser =  new User("FirstName2", "LastName2", null);
     AuthToken authToken = new AuthToken("<mockToken>", "test");

        // Setup request objects to use in the tests
        validFollowStatusRequest = new FollowStatusRequest(currentUser.getAlias(), otherUser.getAlias());
        invalidFollowStatusRequest = new FollowStatusRequest(null, null);

        validFollowRequest = new FollowRequest(authToken, true, currentUser.getAlias(), otherUser.getAlias());
        invalidFollowRequest = new FollowRequest(authToken, true, null, null);

        validUnfollowRequest = new FollowRequest(authToken, false, currentUser.getAlias(), otherUser.getAlias());
        invalidUnfollowRequest = new FollowRequest(authToken, false, null, null);

        // Setup a mock ServerFacade that will return known responses
        successFollowStatusResponse = new FollowStatusResponse(true, true);
        successFollowResponse = new FollowResponse(true, true);
        successUnfollowResponse = new FollowResponse(false, true);

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowStatus(validFollowStatusRequest, "/follow/followstatus")).thenReturn(successFollowStatusResponse);
        Mockito.when(mockServerFacade.setFollow(validFollowRequest, "/follow/follow")).thenReturn(successFollowResponse);
        Mockito.when(mockServerFacade.setFollow(validUnfollowRequest, "/follow/unfollow")).thenReturn(successUnfollowResponse);

        failureFollowStatusResponse = new FollowStatusResponse(false, "invalid input");
        failureFollowResponse = new FollowResponse(false, "invalid input");
        failureUnfollowResponse = new FollowResponse(false, "invalid input");

        Mockito.when(mockServerFacade.getFollowStatus(invalidFollowStatusRequest, "/follow/followstatus")).thenReturn(failureFollowStatusResponse);
        Mockito.when(mockServerFacade.setFollow(invalidFollowRequest, "/follow/follow")).thenReturn(failureFollowResponse);
        Mockito.when(mockServerFacade.setFollow(invalidUnfollowRequest, "/follow/unfollow")).thenReturn(failureUnfollowResponse);

        // Create a FollowService instance and wrap it with a spy that will use the mock service
        mFollowServiceProxySpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(mFollowServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = mFollowServiceProxySpy.getFollowStatus(validFollowStatusRequest);
        Assertions.assertEquals(successFollowStatusResponse, response);
    }

    @Test
    public void testGetFollowStatus_invalidRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = mFollowServiceProxySpy.getFollowStatus(invalidFollowStatusRequest);
        Assertions.assertEquals(failureFollowStatusResponse, response);
    }

    @Test
    public void testSetFollow_validFollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxySpy.setFollow(validFollowRequest);
        Assertions.assertEquals(successFollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidFollowRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxySpy.setFollow(invalidFollowRequest);
        Assertions.assertEquals(failureFollowResponse, response);
    }

    @Test
    public void testSetFollow_validUnfollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxySpy.setFollow(validUnfollowRequest);
        Assertions.assertEquals(successUnfollowResponse, response);
    }

    @Test
    public void testSetFollow_invalidUnfollowRequest_invalidResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = mFollowServiceProxySpy.setFollow(invalidUnfollowRequest);
        Assertions.assertEquals(failureUnfollowResponse, response);
    }
}

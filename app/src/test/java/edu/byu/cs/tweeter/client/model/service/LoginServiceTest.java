//package edu.byu.cs.tweeter.client.model.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//
//import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
//import edu.byu.cs.tweeter.shared.model.domain.User;
//import edu.byu.cs.tweeter.client.model.net.ServerFacade;
//import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
//import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
//import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
//import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
//import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
//import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
//import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class LoginServiceTest {
//
//    LoginRequest loginRequestValid;
//    LoginRequest loginRequestInvalid;
//    LoginResponse loginResponseValid;
//    LoginResponse loginResponseInvalid;
//
//    RegisterRequest registerRequestValid;
//    RegisterRequest registerRequestInvalid;
//    RegisterResponse registerResponseValid;
//    RegisterResponse registerResponseInvalid;
//
//    LogoutRequest logoutRequestValid;
//    LogoutRequest logoutRequestInvalid;
//    LogoutResponse logoutResponseValid;
//    LogoutResponse logoutResponseInvalid;
//
//    LoginServiceProxy loginServiceSpy;
//    ServerFacade serverFacadeMock;
//
//    @BeforeEach
//    public void setup() {
//        // Setup request objects to use in the tests
//        loginRequestValid = new LoginRequest("mockUser", "mockPw");
//        loginRequestInvalid = new LoginRequest("mockUser", "wrongPassword!!");
//        registerRequestValid = new RegisterRequest("newUser", "mockPw", "First", "Last", new byte[1024]);
//        registerRequestInvalid = new RegisterRequest("userNameAlreadyTaken", "mockPw", "First", "Last", new byte[1024]);
//        AuthToken mockToken = new AuthToken("abcdToken", "test");
//        AuthToken nonexistentToken = new AuthToken("<This token does not exist!>", "Test");
//        logoutRequestValid = new LogoutRequest(mockToken);
//        logoutRequestInvalid = new LogoutRequest(nonexistentToken);
//
//        // Setup a mock ServerFacade that will return known responses
//        User mockUser = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        AuthToken mockUserToken = new AuthToken("<mockToken>", "test");
//        loginResponseValid = new LoginResponse(mockUser, mockUserToken);
//        loginResponseInvalid = new LoginResponse("Username or password does not match.");
//        User newUser = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        AuthToken newUserToken = new AuthToken("<mockToken>", "test");
//        registerResponseValid = new RegisterResponse(newUser, newUserToken);
//        registerResponseInvalid = new RegisterResponse("Username is already taken.");
//        logoutResponseValid = new LogoutResponse(true);
//        logoutResponseInvalid = new LogoutResponse("Auth token invalid.");
//
//        serverFacadeMock = Mockito.mock(ServerFacade.class);
//        Mockito.when(serverFacadeMock.login(loginRequestValid)).thenReturn(loginResponseValid);
//        Mockito.when(serverFacadeMock.login(loginRequestInvalid)).thenReturn(loginResponseInvalid);
//        Mockito.when(serverFacadeMock.register(registerRequestValid)).thenReturn(registerResponseValid);
//        Mockito.when(serverFacadeMock.register(registerRequestInvalid)).thenReturn(registerResponseInvalid);
//        Mockito.when(serverFacadeMock.logout(logoutRequestValid)).thenReturn(logoutResponseValid);
//        Mockito.when(serverFacadeMock.logout(logoutRequestInvalid)).thenReturn(logoutResponseInvalid);
//
//        // Create a FollowingService instance and wrap it with a spy that will use the mock service
//        loginServiceSpy = Mockito.spy(new LoginServiceProxy());
//        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(serverFacadeMock);
//    }
//
//    @Test
//    public void testLogin_validResponse() throws IOException, TweeterRemoteException {
//        LoginResponse response = loginServiceSpy.login(loginRequestValid);
//        assertEquals(loginResponseValid, response);
//    }
//
//    @Test
//    public void testLogin_invalidResponse() throws IOException, TweeterRemoteException {
//        LoginResponse response = loginServiceSpy.login(loginRequestInvalid);
//        assertEquals(loginResponseInvalid, response);
//    }
//
//    @Test
//    public void testRegister_validResponse() throws IOException, TweeterRemoteException {
//        LoginResponse response = loginServiceSpy.register(registerRequestValid);
//        assertEquals(registerResponseValid, response);
//    }
//
//    @Test
//    public void testRegister_invalidResponse() throws IOException, TweeterRemoteException {
//        LoginResponse response = loginServiceSpy.register(registerRequestInvalid);
//        assertEquals(registerResponseInvalid, response);
//    }
//
//    @Test
//    public void testLogout_validResponse() throws IOException, TweeterRemoteException {
//        LogoutResponse response = loginServiceSpy.logout(logoutRequestValid);
//        assertEquals(logoutResponseValid, response);
//    }
//
//    @Test
//    public void testLogout_invalidResponse() throws IOException, TweeterRemoteException {
//        LogoutResponse response = loginServiceSpy.logout(logoutRequestInvalid);
//        assertEquals(logoutResponseInvalid, response);
//    }
//}

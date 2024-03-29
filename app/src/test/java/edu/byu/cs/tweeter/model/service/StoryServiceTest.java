package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceTest {
    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    private StoryService storyServiceSpy;

    @BeforeEach
    public void setup() {
        User user1 = new User("firstname1", "lastname1", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Status resultStatus1 = new Status(LocalDateTime.parse("2021-02-13T01:01:01"), "message1", user1);
        Status resultStatus2 = new Status(LocalDateTime.parse("2021-02-13T01:01:02"), "message2", user1);
        Status resultStatus3 = new Status(LocalDateTime.parse("2021-02-13T01:01:03"), "message3", user1);

        validRequest = new StoryRequest("@validAlias", 3, null);
        invalidRequest = new StoryRequest("@invalidAlias", 0, null);

        successResponse = new StoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new StoryResponse("An exception occurred");
        Mockito.when(mockServerFacade.getStory(invalidRequest)).thenReturn(failureResponse);

        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetStory_validRequest_loadsUsers() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);

        for (Status status: response.getStatuses()) {
            Assertions.assertNotNull(status.getUser());
        }
    }

    @Test
    public void testGetStory_invalidRequest_returnsNoStatuses() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}

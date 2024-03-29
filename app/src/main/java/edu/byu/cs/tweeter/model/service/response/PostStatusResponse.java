package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusResponse extends Response {
    private Status status;

    public PostStatusResponse(Status status) {
        super(true);
        this.status = status;
    }

    public PostStatusResponse(String message) {
        super(false, message);
    }
}

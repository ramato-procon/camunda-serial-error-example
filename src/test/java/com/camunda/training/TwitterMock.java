package com.camunda.training;

import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Service
public class TwitterMock extends TwitterService {

    public TwitterMock() {
    }

    public void send(final String content) throws TwitterException {
    }
}

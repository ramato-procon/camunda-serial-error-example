package com.camunda.training;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.net.UnknownHostException;

@Component
public class CreateTweetDelegate implements JavaDelegate {
    private final Logger logger = LoggerFactory.getLogger(CreateTweetDelegate.class.getName());

    TwitterService twitterService;

    @Autowired
    public CreateTweetDelegate(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String content = (String) execution.getVariable(ProcessConstants.CONTENT);
        logger.info("Publishing tweet: {}", content);
        Boolean fakeNetworkFailure = (Boolean) execution.getVariable(ProcessConstants.FAKE_NETWORKERROR);
        if (Boolean.TRUE.equals(fakeNetworkFailure)) {
            throw new UnknownHostException("Faked Network Failure detected!");
        }
        twitterService.send(content);
    }
}

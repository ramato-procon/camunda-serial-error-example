package com.camunda.training;

import org.camunda.bpm.engine.impl.pvm.PvmException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

@RunWith(MockitoJUnitRunner.class)
@Deployment(resources = "twitter.bpmn")
public class ProcessJUnitTest {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Mock
    TwitterService twitterService;

    @InjectMocks
    CreateTweetDelegate createTweetDelegate;

    @Before
    public void setup() {
        init(rule.getProcessEngine());
        Mocks.register("createTweetDelegate", new CreateTweetDelegate(new TwitterMock()));
    }

    @Test
    public void testHappyPath() {
        // Create a HashMap to put in variables for the process instance
        Map<String, Object> variables = new HashMap<>();
        variables.put(ProcessConstants.APPROVED, true);
        variables.put(ProcessConstants.CONTENT, "Exercise 4 test - Roberto - " + UUID.randomUUID());
        // Start process with Java API and variables
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQA", variables);
        assertThat(processInstance).isWaitingAt("REVIEW_TWEET_TASK");
        complete(task());
        assertThat(processInstance).isWaitingAt("Post_Tweet");
        execute(job());
        // Make assertions on the process instance
        assertThat(processInstance).isEnded();
    }

    @Test(expected = UnknownHostException.class)
    public void testNetworkError() throws Throwable {
        // Create a HashMap to put in variables for the process instance
        Map<String, Object> variables = new HashMap<>();
        variables.put(ProcessConstants.APPROVED, true);
        variables.put(ProcessConstants.CONTENT, "Exercise 4 test - Roberto - " + UUID.randomUUID());
        variables.put(ProcessConstants.FAKE_NETWORKERROR, true);
        // Start process with Java API and variables
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQA", variables);
        assertThat(processInstance).isWaitingAt("REVIEW_TWEET_TASK");
        complete(task());
        assertThat(processInstance).isWaitingAt("Post_Tweet");
        try {
            execute(job());
        } catch(PvmException e) {
            throw e.getCause();
        }
    }

}

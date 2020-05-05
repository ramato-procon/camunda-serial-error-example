package com.camunda.training;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Deserial implements JavaDelegate {
    Logger logger = LoggerFactory.getLogger(Deserial.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SerialObject serialObject = (SerialObject) execution.getVariable("obj");
        logger.info("Value: {}", serialObject.getText());
    }
}

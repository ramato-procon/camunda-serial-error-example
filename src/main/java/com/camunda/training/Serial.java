package com.camunda.training;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class Serial implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SerialObject serialObject = new SerialObject();
        serialObject.setText("Hallo");
        execution.setVariable("obj", Variables.objectValue(serialObject));
    }
}

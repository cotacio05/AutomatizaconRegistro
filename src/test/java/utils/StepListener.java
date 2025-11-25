package utils;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

public class StepListener implements EventListener {
        
    public static String lastStepText;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, event -> {
            if (event.getTestStep() instanceof PickleStepTestStep) {
                PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
                lastStepText = step.getStep().getText();
            }
        });
    }

}

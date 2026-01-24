package com.github.donnyk22.project.services.experimental.messageBroker;

import com.github.donnyk22.project.models.forms.ExperimentalMsBrokerForm;

public interface MsBrokerService {
    ExperimentalMsBrokerForm sendToTopicMessage(ExperimentalMsBrokerForm message);
    String sendToTopicText(String message);
}

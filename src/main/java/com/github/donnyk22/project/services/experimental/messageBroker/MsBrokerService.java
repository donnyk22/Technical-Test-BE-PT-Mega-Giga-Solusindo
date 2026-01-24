package com.github.donnyk22.project.services.experimental.messageBroker;

import com.github.donnyk22.project.models.forms.ExperimentalMsBrokerForm;

public interface MsBrokerService {
    ExperimentalMsBrokerForm sendToTopicObject(ExperimentalMsBrokerForm object);
    String sendToTopicText(String text);
}

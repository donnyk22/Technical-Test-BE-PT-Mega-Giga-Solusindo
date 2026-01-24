package com.github.donnyk22.project.models.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MsBrokerConstants {
    public static final String MESSAGE_EXCHANGE = "message.exchange";

    public static final String MESSAGE_QUEUE_MESSAGE = "message.queue.message";
    public static final String MESSAGE_QUEUE_TEXT = "message.queue.text";
    public static final String MESSAGE_QUEUE_ALL = "message.queue.all";

    public static final String MESSAGE_ROUTING_KEY_MESSAGE = "message.key.message";
    public static final String MESSAGE_ROUTING_KEY_TEXT = "message.key.text";
    public static final String MESSAGE_ROUTING_KEY_ALL = "message.key.*"; //or "message.#"
}

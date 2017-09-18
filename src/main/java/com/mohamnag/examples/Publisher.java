package com.mohamnag.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.transaction.Transactional;
import java.time.Instant;

@Singleton
public class Publisher {

    public static final String EVENTS_TOPIC = "java:/jms/topic/some-events";
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext jmsContext;

    @Resource(lookup = EVENTS_TOPIC)
    private Topic topic;

    @Transactional
    @Schedule(hour = "*", minute = "*", second = "*/10")
    public void publish() {

        String messageBody = "Event on time: '" + Instant.now() + "'";
        logger.info("publishing event: \"{}\" to topic \"{}\"", messageBody, EVENTS_TOPIC);

        jmsContext
                .createProducer()
                .setProperty("type", String.class.getCanonicalName())
                .setDeliveryMode(DeliveryMode.PERSISTENT)
                .send(topic, messageBody);
    }


}

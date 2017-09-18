package com.mohamnag.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.transaction.Transactional;
import java.time.Instant;

public class Publisher {

    public static final String COMMON_DOMAIN_EVENTS_TOPIC = "jms/topic/domain-events";
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    @Inject
    @JMSConnectionFactory("java:/jms/remote-mq")
    private JMSContext jmsContext;

    @Resource(lookup = COMMON_DOMAIN_EVENTS_TOPIC)
    private Topic topic;

    @Transactional
    @Schedule(hour = "*", minute = "*", second = "*/10")
    public void publish() {

        String messageBody = "Event on time: '" + Instant.now() + "'";
        logger.info("publishing event: \"{}\"", messageBody);

        jmsContext
                .createProducer()
                .setProperty("type", String.class.getCanonicalName())
                .setDeliveryMode(DeliveryMode.PERSISTENT)
                .send(topic, messageBody);
    }


}

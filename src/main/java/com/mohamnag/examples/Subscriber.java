package com.mohamnag.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.Transactional;

@MessageDriven(activationConfig = {

        @ActivationConfigProperty(
                propertyName = "connectionFactoryLookup",
                propertyValue = "java:/jms/remote-mq"),

        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = Publisher.EVENTS_TOPIC),

        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),

        @ActivationConfigProperty(
                propertyName = "subscriptionDurability",
                propertyValue = "Durable"),

        @ActivationConfigProperty(
                propertyName = "subscriptionName",
                propertyValue = "SubscriberSub"),

        // property "clientId" shall not be set for shared sub!
})
public class Subscriber implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    @Transactional
    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            logger.info("got message: {}", body);

        } catch (JMSException e) {
            logger.error("Could not get body form message: {}", message);
        }
    }
}

package com.mohamnag.examples;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.Transactional;

// name of pooled connection factory, using connectionFactoryLookup does not work!
// FIXME: 18/09/2017 this is jboss dependent and not standard
@ResourceAdapter("remote-activemq")
@MessageDriven(activationConfig = {

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

package com.mohamnag.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.messaging.MessagingFraction;
import org.wildfly.swarm.spi.api.OutboundSocketBinding;

public class CustomMain {

    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm(args);

        final AppConfig appConfig =
                new ObjectMapper(new YAMLFactory())
                        .readValue(
                                CustomMain
                                        .class
                                        .getResourceAsStream("/app-config.yml"),
                                AppConfig.class);

        swarm
                .outboundSocketBinding("standard-sockets",
                        new OutboundSocketBinding("remote-activemq")
                                .remoteHost(appConfig.getJmsHost())
                                .remotePort(appConfig.getJmsPort()))

                .fraction(new MessagingFraction()
                        .defaultServer(server -> {

                            server.remoteConnector("remote-activemq", connector -> {
                                connector.socketBinding("remote-activemq");
                            });

                            server.pooledConnectionFactory("remote-activemq", factory -> {
                                factory.connectors("remote-activemq");
                                factory.entries("java:/jms/remote-mq");
                                factory.user(appConfig.getJmsUserName());
                                factory.password(appConfig.getJmsUserPassword());
                            });

                            server.jmsTopic("some-events", topic -> {
                                topic.entries(appConfig.getJmsEventTopic());
                            });

                        }))

                .start()
                .deploy();
    }

}

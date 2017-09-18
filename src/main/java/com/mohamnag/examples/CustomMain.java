package com.mohamnag.examples;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.messaging.MessagingFraction;
import org.wildfly.swarm.spi.api.OutboundSocketBinding;

public class CustomMain {

    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm(args);

        swarm
                .outboundSocketBinding("standard-sockets",
                        new OutboundSocketBinding("remote-activemq")
                                .remoteHost("localhost")
                                .remotePort(61616))

                .fraction(new MessagingFraction()
                        .defaultServer(server -> {

                            server.remoteConnector("remote-activemq", connector -> {
                                connector.socketBinding("remote-activemq");
                            });

                            server.pooledConnectionFactory("remote-activemq", factory -> {
                                factory.connectors("remote-activemq");
                                factory.entries("java:/jms/remote-mq");
                                factory.user("yourUser");
                                factory.password("yourPassword");
                            });

                            server.jmsTopic("some-events", topic -> {
                                topic.entries("java:/jms/topic/some-events");
                            });

                        }))

                .start()
                .deploy();
    }

}

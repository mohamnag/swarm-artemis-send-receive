package com.mohamnag.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig {
    private String jmsHost;
    private int jmsPort;
    private String jmsUserName;
    private String jmsUserPassword;
    private String jmsEventTopic;

    @JsonCreator
    public AppConfig(
            @JsonProperty("jms-host") String jmsHost,
            @JsonProperty("jms-port") int jmsPort,
            @JsonProperty("jms-userName") String jmsUserName,
            @JsonProperty("jms-userPassword") String jmsUserPassword,
            @JsonProperty("jms-eventTopic") String jmsEventTopic) {

        this.jmsHost = jmsHost;
        this.jmsPort = jmsPort;
        this.jmsUserName = jmsUserName;
        this.jmsUserPassword = jmsUserPassword;
        this.jmsEventTopic = jmsEventTopic;
    }

    public String getJmsHost() {
        return jmsHost;
    }

    public int getJmsPort() {
        return jmsPort;
    }

    public String getJmsUserName() {
        return jmsUserName;
    }

    public String getJmsUserPassword() {
        return jmsUserPassword;
    }

    public String getJmsEventTopic() {
        return jmsEventTopic;
    }
}

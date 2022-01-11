import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQSender implements StringSender {
    private Connection brokerConnection;
    private Session brokerSession;
    private Destination destination;
    private MessageProducer producer;

    public ActiveMQSender(String brokerUrl, String topicSubject) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        brokerConnection = connectionFactory.createConnection();
        brokerConnection.start();
        brokerSession = brokerConnection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        destination = brokerSession.createTopic(topicSubject);
        producer = brokerSession.createProducer(destination);
    }

    public void sendString(String stringMessage) {
        TextMessage message = null;
        try {
            message = brokerSession.createTextMessage(stringMessage);
            producer.send(message);
        } catch (JMSException e) {
            System.out.println("Sending message to ActiveMQ failed!");
            e.printStackTrace();
        }

    }
}



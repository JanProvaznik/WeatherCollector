import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSender {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String subject = "sensor.Weather"; // Queue Name.You can create any/many queue names as per your requirement.
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;

    public MessageSender() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        destination = session.createTopic(subject);
        producer = session.createProducer(destination);
    }

    public void sendString(String stringMessage) throws JMSException {
        TextMessage message = session.createTextMessage(stringMessage);
        producer.send(message);
    }
}



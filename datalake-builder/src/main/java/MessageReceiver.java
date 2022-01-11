import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageReceiver {
    Connection connection;
    Session session;
    MessageConsumer messageConsumer;

    public MessageReceiver(String id, String topicName, String subscriptionName) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(
                        ActiveMQConnection.DEFAULT_BROKER_URL);

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(id);

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            messageConsumer = session.createDurableSubscriber(topic, subscriptionName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setListener(MessageListener messageListener) {
        try {
            messageConsumer.setMessageListener(messageListener);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

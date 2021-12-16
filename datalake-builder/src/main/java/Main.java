import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Main {
    private static final String TOPIC_NAME = "sensor.Weather";
    private static final String SUBSCRIPTION_NAME = "datalake-builder";
    private static final String ID = "1";

    public static void main(String[] args) {
        if (!validateArgs(args))
            throw new IllegalArgumentException("there must be exactly one parameter: directory name");

        LakeMaker lakeMaker = new LakeMaker(args[0]);
        try {
            Connection connection;
            Session session;
            MessageConsumer messageConsumer;
            ConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(
                            ActiveMQConnection.DEFAULT_BROKER_URL);

            connection = connectionFactory.createConnection();
            connection.setClientID(ID);

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic(TOPIC_NAME);

            messageConsumer = session.createDurableSubscriber(topic, SUBSCRIPTION_NAME);
            messageConsumer.setMessageListener(lakeMaker);
            connection.start();


            while (true) {
                Thread.sleep(100);
            }
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateArgs(String[] args) {
        if (args.length != 1) return false;
        return true;
    }
}

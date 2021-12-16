import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {


    private static final String TOPIC_NAME = "sensor.Weather";
    private static final String SUBSCRIPTION_NAME = "analytics";
    private static final String ID = "2";

    static String dbpath = "database";
    static String dbname = "";

    public static void main(String[] args) {
        //create db if not exists
        if (!validateArgs(args))
            throw new IllegalArgumentException("There must be exactly one parameter: directory for database");
        dbpath=args[0];

        String url = "jdbc:sqlite:" + dbpath;
        try (java.sql.Connection dbConnection = DriverManager.getConnection(url)) {
            DatabaseManager db = new DatabaseManager(dbConnection);
            db.createTable();


            javax.jms.Connection brokerConnection;
            Session session;
            MessageConsumer messageConsumer;
            ConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(
                            ActiveMQConnection.DEFAULT_BROKER_URL);

            brokerConnection = connectionFactory.createConnection();
            brokerConnection.setClientID(ID);

            session = brokerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic(TOPIC_NAME);

            messageConsumer = session.createDurableSubscriber(topic, SUBSCRIPTION_NAME);
            messageConsumer.setMessageListener(db);
            brokerConnection.start();



        } catch (SQLException e) {
            System.out.println("db error");
            System.out.println(e.getMessage());
        } catch (JMSException e) {
            System.out.println("broker error");
            System.out.println(e.getMessage());
        }
        // connect to db

        // connect to topic

    }

    private static boolean validateArgs(String[] args) {
        if (args.length != 1)
            return false;
        return true;
    }

}

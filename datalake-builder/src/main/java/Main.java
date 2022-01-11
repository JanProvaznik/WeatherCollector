import java.io.IOException;

public class Main {
    private static final String TOPIC_NAME = "sensor.Weather";
    private static final String SUBSCRIPTION_NAME = "datalake-builder";
    private static final String ID = "1";

    public static void main(String[] args) {
        if (!validateArgs(args))
            throw new IllegalArgumentException("there must be exactly one parameter: directory name");

        LakeMaker lakeMaker = new LakeMaker(args[0]);
        try {
            MessageReceiver receiver = new MessageReceiver(ID, TOPIC_NAME, SUBSCRIPTION_NAME);
            receiver.setListener(lakeMaker);
            receiver.start();

            System.out.println("Press enter to stop.");
            System.in.read();

        } catch (IOException e) {
            System.out.println("File system error");
            e.printStackTrace();
        }

    }

    private static boolean validateArgs(String[] args) {
        return args.length == 1;
    }
}

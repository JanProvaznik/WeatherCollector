import java.sql.ResultSet;

public interface Predictor {
    void trainAndPredict(ResultSet data);
}

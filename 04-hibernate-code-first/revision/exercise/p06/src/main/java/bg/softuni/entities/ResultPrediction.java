package bg.softuni.entities;

import bg.softuni.entities.enums.PredictionEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "result_predictions")
public class ResultPrediction {
//    Id, Prediction (possible values - Home Team Win, Draw Game, Away Team Win)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private PredictionEnum prediction;

    public ResultPrediction() {}

    public long getId() {
        return id;
    }

    public PredictionEnum getPrediction() {
        return prediction;
    }

    public ResultPrediction setPrediction(PredictionEnum prediction) {
        this.prediction = prediction;
        return this;
    }
}

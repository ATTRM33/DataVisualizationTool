import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {

    private JLabel totalPassengers;
    private JLabel survivalRate;
    private JLabel averageAge;
    private JLabel averageFare;
    private JLabel classDistribution;

    public StatsPanel(ArrayList<DataModel> data) {
        setBorder(BorderFactory.createTitledBorder("Statistics"));
        setLayout(new GridBagLayout());

        totalPassengers = new JLabel("Total Passengers");
        survivalRate = new JLabel("Survival Rate");
        averageAge = new JLabel("Average Age");
        averageFare = new JLabel("Average Fare");
        classDistribution = new JLabel("Class Distribution");

        add(totalPassengers);
        add(survivalRate);
        add(averageAge);
        add(averageFare);
        add(classDistribution);

        updateStats(data);
    }

    public void updateStats(ArrayList<DataModel> data) {

        long total = data.size();
        double survivedPercent = data.stream().filter(p -> p.survived() == DataModel.DidSurvive.DID_SURVIVE)
                .count()*100/total;

        double avgAge = data.stream()
                .filter(p->p.age() != null)
                .mapToDouble(DataModel::age)
                .average()
                .orElse(0);

        double avgFare = data.stream()
                .mapToDouble(DataModel::fare)
                .average()
                .orElse(0.0);

        Map<Integer, Long> classCounts = data.stream()
                .collect(Collectors.groupingBy(
                        DataModel::passengerClass,
                        Collectors.counting()
                ));

        totalPassengers.setText(String.format("Total Passengers: %d", total));
        survivalRate.setText(String.format("Survival Rate: %.1f%%", survivedPercent));
        averageAge.setText(String.format("Average Age: %.1f years", avgAge));
        averageFare.setText(String.format("Average Fare: £%.2f", avgFare));
        classDistribution.setText(String.format("Class Distribution: 1st: %d, 2nd: %d, 3rd: %d",
                classCounts.getOrDefault(1, 0L),
                classCounts.getOrDefault(2, 0L),
                classCounts.getOrDefault(3, 0L)));
    }
}

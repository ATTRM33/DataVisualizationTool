import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class chartPanel extends JPanel {
    private JFreeChart chart;
    private final JComboBox<String> chartType;

    public chartPanel(ArrayList<DataModel> data) {
        setLayout(new BorderLayout());

        //chart type selector
        chartType = new JComboBox<>(new String[]{"Survival by Class", "Gender Distribution", "Age Groups"});
        chartType.addActionListener(e -> updateChart(data));

        add(chartType, BorderLayout.NORTH);
        updateChart(data);
    }

    public void updateChart(ArrayList<DataModel> data) {
        removeAll();
        add(chartType, BorderLayout.NORTH);

        switch (chartType.getSelectedIndex()) {
            case 0 -> createSurvivalByClassChart(data);
            case 1 -> createGenderDistributionChart(data);
            case 2 -> createAgeGroupsChart(data);
        }

        revalidate();
        repaint();
    }

    private void createSurvivalByClassChart(ArrayList<DataModel> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        //calculate survival rates based on class
        for (int pclass = 1; pclass <= 3; pclass++) {
            int finalPclass = pclass;
            long total = data.stream()
                    .filter(p -> p.passengerClass() == finalPclass)
                    .count();
            long survived = data.stream()
                    .filter(p -> p.passengerClass() == finalPclass &&
                            p.survived() == DataModel.DidSurvive.DID_SURVIVE)
                    .count();

            dataset.addValue(survived * 100.0 / total, "Survived", "Class " + pclass);
        }

        chart = ChartFactory.createBarChart(
                "Survival Rate by Class",
                "Passenger Class",
                "Survival Rate (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        //adds new values to the jchart
        add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
    }

    private void createGenderDistributionChart(ArrayList<DataModel> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int pclass = 1; pclass <= 3; pclass++) {
            final int currentClass = pclass;

            //counts all the males from each class
            long maleCount = data.stream()
                    .filter(p ->p.passengerClass() == currentClass)
                    .filter(p->p.sex() == DataModel.Gender.MALE)
                    .count();
            //counts all females from each class
            long femaleCount = data.stream()
                    .filter(p->p.passengerClass()== currentClass)
                    .filter(p->p.sex()==DataModel.Gender.FEMALE)
                    .count();
            //adds the counted values to the data set
            dataset.addValue(maleCount, "Male", "Class " + pclass);
            dataset.addValue(femaleCount, "Female", "Class " + pclass);
        }
        chart = ChartFactory.createBarChart(
                "Survival Rate by Class",
                "Passenger Class",
                "Survival Rate (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        //adds new values to the jchart
        add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
    }

    private void createAgeGroupsChart(ArrayList<DataModel> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] ageGroups = {"0-15", "16-30", "31-50", "51+"};

        int[][] ageCounts = new int[3][4];

        //this will loop through the 2-D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                ageCounts[i][j] = 0;
            }

        }

        for (DataModel passenger : data) {
            int pclass = passenger.passengerClass() - 1;

            //Double, some passengers have null ages
            Double age = passenger.age();

            if (age != null && pclass >= 0 && pclass < 3) {
                int ageGroup;
                if (age <= 15) {
                    ageGroup = 0;
                } else if (age <= 30) {
                    ageGroup = 1;
                } else if (age <= 50) {
                    ageGroup = 2;
                } else {
                    ageGroup = 3;
                }

                ageCounts[pclass][ageGroup]++;
            }
        }
            for (int pclass = 0; pclass < 3; pclass++) {
                for (int ageGroup = 0; ageGroup < 4; ageGroup++) {
                    dataset.addValue(
                            ageCounts[pclass][ageGroup],
                            "Class" + (pclass + 1),
                            ageGroups[ageGroup]
                    );

                }

            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Age Distribution by Passenger Class",
                    "Age Groups",
                    "Number of Passengers",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

        add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        }


}

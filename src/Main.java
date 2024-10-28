import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static final int FRAME_WIDTH = 1500;
    static final int FRAME_HEIGHT = 800;
    static final int LEFT_SPLIT_LOC = 400;
    static final int MAIN_SPLIT_LOC = 600;

    public static void main(String[] args) throws IOException {
        ArrayList<DataModel> Data = DataLoader.readTitanicData("Titanic_data.csv");
        StatsPanel statsPanel = new StatsPanel(Data);
        DetailsPanel detailsPanel = new DetailsPanel();
        chartPanel chartPanel = new chartPanel(Data);
        TablePanel tablePanel = new TablePanel(Data, detailsPanel);


        FilterPanel filterPanel = new FilterPanel(Data, filteredData ->{
            tablePanel.updateTableData(filteredData);
            statsPanel.updateStats(filteredData);
            chartPanel.updateChart(filteredData);

        });


        //setting up jframe
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Titanic Data Visualization Tool");

        frame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());

        //creates a split pane with tablePanel above and details below
        JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tablePanel),
                detailsPanel);
        leftSplit.setDividerLocation(LEFT_SPLIT_LOC);

        //creates the right panel and adds stats and charts
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(statsPanel, BorderLayout.NORTH);
        rightPanel.add(chartPanel, BorderLayout.CENTER);

        //splits the pain horizonatally
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftSplit,
                rightPanel);
        mainSplit.setDividerLocation(MAIN_SPLIT_LOC);

        //add filter and mainSplit
        frame.add(filterPanel, BorderLayout.NORTH);
        frame.add(mainSplit, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

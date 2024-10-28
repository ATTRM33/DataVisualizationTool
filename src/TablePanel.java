import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TablePanel extends JTable {
    private DefaultTableModel model;
    private ArrayList<DataModel> currentData;
    private DetailsPanel detailsPanel;

    public TablePanel(ArrayList<DataModel> data, DetailsPanel details) {
        this.currentData = data;
        this.detailsPanel = details;


        String[] columnNames = {
                "Name",
                "Class",
                "Age",
                "Gender",
                "Survived",
                "Fare"
        };

        model = new DefaultTableModel(columnNames, 0) {

            //make the table read only
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(model);

        setAutoCreateRowSorter(true);

        updateTableData(data);
    }

    void updateTableData(ArrayList<DataModel> data) {
        model.setRowCount(0);

        for(DataModel passenger : data) {
            model.addRow(new Object[]{
                    passenger.getFullName(),
                    passenger.getPassengerClass(),
                    passenger.getFormattedAge(),
                    passenger.sex(),
                    passenger.survived(),
                    passenger.getFareCategory()
            });
        }

        //this allows the table data to be selected and displayed on the details panel
        this.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = this.getSelectedRow();
                if (row >= 0) {
                    DataModel selectedPassenger = currentData.get(row);
                    detailsPanel.updateDetails(selectedPassenger);
                }
            }
        });
    }
}

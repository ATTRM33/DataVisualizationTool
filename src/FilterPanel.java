import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FilterPanel extends JPanel {
    private JComboBox<String> classFilter;
    private JComboBox<String> genderFilter;
    private JCheckBox survivedFilter;
    private ArrayList<DataModel> data;
    private Consumer<ArrayList<DataModel>> onFilterApplied;

    public FilterPanel(ArrayList<DataModel> data, Consumer<ArrayList<DataModel>> filterCallback) {
        this.data = data;
        this.onFilterApplied = filterCallback;

        setBorder(BorderFactory.createTitledBorder("Filters"));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        initializeFilters();

        addFilterListeners();
    }

    private void initializeFilters() {

        classFilter = new JComboBox<>(new String[]{
                "All Classes",
                "First Class",
                "Second Class",
                "Third Class",
        });

        genderFilter = new JComboBox<>(new String[]{
                "All Genders",
                "Male",
                "Female"
        });

        survivedFilter = new JCheckBox("Survived");

        add(new JLabel("Class"));
        add(classFilter);
        add(new JLabel("Gender"));
        add(genderFilter);
    }

    private void addFilterListeners() {

        ActionListener filterListener = e -> applyFilters();

        classFilter.addActionListener(filterListener);
        genderFilter.addActionListener(filterListener);
        survivedFilter.addActionListener(filterListener);
    }

    private void applyFilters() {

        ArrayList<DataModel> filteredData = new ArrayList<>(data);

        if (classFilter.getSelectedIndex() > 0) {
            int selectedClass = classFilter.getSelectedIndex();
            filteredData = filteredData.stream()
                    .filter(passenger ->passenger.passengerClass() == selectedClass)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (genderFilter.getSelectedIndex() > 0) {
            DataModel.Gender selectedGender = genderFilter.getSelectedIndex() == 1 ? DataModel.Gender.MALE : DataModel.Gender.FEMALE;
            filteredData = filteredData.stream()
                    .filter(passenger -> passenger.sex() == selectedGender)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (survivedFilter.isSelected()) {
            filteredData = filteredData.stream()
                    .filter(passenger ->passenger.survived() == DataModel.DidSurvive.DID_SURVIVE)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        onFilterApplied.accept(filteredData);
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class DetailsPanel extends JPanel {
    private JTextArea detailsArea;

    public DetailsPanel(){

        setBorder(BorderFactory.createTitledBorder("Passenger Details"));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false); 

        JScrollPane scrollPane = new JScrollPane(detailsArea);

        setLayout(new BorderLayout());
        add(scrollPane);

        detailsArea.setText("Select a passenger to see their details");

    }

    public void updateDetails(DataModel passenger) {

        //create information of passenger to be viewed in the details area
        String details = "Name: " + passenger.getFullName() + "\n" +
                "Class: " + passenger.getPassengerClass() + "\n" +
                "Gender: " + passenger.sex() + "\n" + "Age: " + passenger.age() + "\n";
                details += "Survived: " + passenger.survived() + "\n";
                details += "Fare: " + passenger.getFormattedFare() + "\n";

        detailsArea.setText(details.toString());


    }


}

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<DataModel> Data = DataLoader.readTitanicData("Titanic_data.csv");

        //setting up jframe
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Titanic Data Visualization Tool");
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(800, 600));

        JPanel statspanel = new JPanel();
        statspanel.setLayout(new BoxLayout(statspanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(statspanel);

        System.out.println(Data.size());
        System.out.println(Data.get(0));
    }
}

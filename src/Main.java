import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<DataModel> Data = DataLoader.readTitanicData("Titanic_data.csv");

        System.out.println(Data.size());
        System.out.println(Data);
    }
}

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class DataLoader {

    public static ArrayList<DataModel> readTitanicData(String filename) throws IOException {
        String contents = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(contents);
        ArrayList<DataModel> dataModels = new ArrayList<>();
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");//ignores commas as delimiter when looking through quoted text

            //formatted data for missing values. Default values are replaced for missing data.
            String survivedStr = data.length > 0 ? data[0].trim() : "Unknown";
            String pclassStr = data.length > 1 ? data[1].trim() : "Unknown";
            String name = data.length > 2 ? data[2].trim() : "Unknown";
            String sexStr = data.length > 3 ? data[3].trim() : "Unknown";
            String ageStr = data.length > 4 ? data[4].trim() : "Unknown";
            String sibspStr = data.length > 5 ? data[5].trim() : "Unknown";
            String parchStr = data.length > 6 ? data[6].trim() : "Unknown";
            String ticket = data.length > 7 ? data[7].trim() : "Unknown";
            String fareStr = data.length > 8 ? data[8].trim() : "0.0";
            String cabin = data.length > 9 ? data[9].trim() : "Unknown";
            String embarked = data.length > 10 ? data[10].trim() : "Unknown";

            DataModel datum = new DataModel(
                    DataModel.DidSurvive.parseSurvive(survivedStr),  //survived
                    Integer.parseInt(pclassStr),  //pclass
                    name,  //name
                    DataModel.Gender.parseGender(sexStr),  //sex
                    ageStr.isEmpty() ? null : Double.parseDouble(ageStr),  //age
                    Integer.parseInt(sibspStr),  //sibsp
                    Integer.parseInt(parchStr),  //parch
                    ticket,  // ticket
                    fareStr.isEmpty() ? 0.0 : Double.parseDouble(fareStr),  //fare
                    cabin,  //cabin
                    embarked

            );
            dataModels.add(datum);
        }
        return dataModels;
    }
}

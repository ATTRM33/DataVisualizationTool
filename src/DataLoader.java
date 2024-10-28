import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {


    public static ArrayList<DataModel> readTitanicData(String filename) throws IOException {
        try (Stream<String> contents = Files.lines(Path.of(filename), StandardCharsets.UTF_8)) {
            ArrayList<DataModel> dataModels = new ArrayList<>();


            return contents
                    .skip(1)
                    .map(content ->content.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))//ignores commas as delimiter when looking through quoted text
                    .map(data -> {

                        //formatted data for missing values. default values are replaced for missing data.
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

                return new DataModel(
                        DataModel.DidSurvive.parseSurvive(survivedStr),  //survived
                        Integer.parseInt(pclassStr),  //pclass
                        name,  //name
                        DataModel.Gender.parseGender(sexStr),  //sex
                        ageStr.isEmpty() ? null : Double.parseDouble(ageStr),  //age
                        Integer.parseInt(sibspStr),  //siblings on board
                        Integer.parseInt(parchStr),  //parents on board
                        ticket,  // ticket
                        fareStr.isEmpty() ? 0.0 : Double.parseDouble(fareStr),  //fare
                        cabin,  //cabin
                        embarked

                );

            })
            .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public static ArrayList<DataModel> getSurvivors(ArrayList<DataModel> passengers) {
        return passengers.stream()
                .filter(passenger -> passenger.survived() == DataModel.DidSurvive.DID_SURVIVE)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

public record DataModel(DidSurvive survived, int passengerClass, String name, Gender sex,
                        Double age, int sibsp, int parch, String ticket, double fare,
                        String cabin, String embarked) {
                        // sibsp represents the number of sibling the passenger has on board
                        //parch represents the number of parents on board

    public enum DidSurvive {
        DID_SURVIVE, DIDNT_SURVIVE, OTHER;
        public static DidSurvive parseSurvive(String survived) {
            return switch (survived) {
                case "0" -> DIDNT_SURVIVE;
                case "1" -> DID_SURVIVE;
                default -> OTHER;
            };
        }
    }
    public enum Gender {
        MALE, FEMALE, NA;
        public static Gender parseGender(String gender) {
            return switch (gender) {
                case "male" -> MALE;
                case "female" -> FEMALE;
                default -> NA;
            };
        }


    }


}

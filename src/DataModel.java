public record DataModel(DidSurvive survived, int passengerClass, String name, Gender sex,
                        Double age, int sibsp, int parch, String ticket, double fare,
                        String cabin, String embarked) {

    public enum DidSurvive {
        DID_SURVIVE, DIDNT_SURVIVE, OTHER;

        public static DidSurvive parseSurvive(String survived) {
            return switch (survived) {
                case "0" -> DIDNT_SURVIVE;
                case "1" -> DID_SURVIVE;
                default -> OTHER;
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case DID_SURVIVE -> "Survived";
                case DIDNT_SURVIVE -> "Did Not Survive";
                case OTHER -> "Unknown";
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

        @Override
        public String toString() {
            return switch (this) {
                case MALE -> "Male";
                case FEMALE -> "Female";
                case NA -> "Not Available";
            };
        }
    }

    //passenger class
    public enum PassengerClass {
        FIRST(1, "First Class"),
        SECOND(2, "Second Class"),
        THIRD(3, "Third Class"),
        UNKNOWN(0, "Unknown");

        private final int value;
        private final String description;

        PassengerClass(int value, String description) {
            this.value = value;
            this.description = description;
        }
        //parses the integer value from the data to a string
        public static PassengerClass fromInt(int value) {
            return switch (value) {
                case 1 -> FIRST;
                case 2 -> SECOND;
                case 3 -> THIRD;
                default -> UNKNOWN;
            };
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public PassengerClass getPassengerClass() {
        return PassengerClass.fromInt(passengerClass);
    }

    public String getFormattedAge() {
        return age != null ? String.format("%.1f", age) : "N/A";
    }

    public String getFormattedFare() {
        return String.format("£%.2f", fare);
    }

    public String getFullName() {
        return name.replace("\"", "").trim();
    }

    public String getTitle() {
        String cleanName = getFullName();
        int start = cleanName.indexOf(',');
        int end = cleanName.indexOf('.');
        if (start != -1 && end != -1 && start < end) {
            return cleanName.substring(start + 1, end + 1).trim();
        }
        return "N/A";
    }

    public String getLastName() {
        String cleanName = getFullName();
        int commaIndex = cleanName.indexOf(',');
        return commaIndex != -1 ? cleanName.substring(0, commaIndex).trim() : cleanName;
    }

    public String getFirstName() {
        String cleanName = getFullName();
        String withoutTitle = cleanName.replaceAll("Mr\\.|Mrs\\.|Miss\\.|Master\\.", "").trim();
        int commaIndex = withoutTitle.indexOf(',');
        return commaIndex != -1 ? withoutTitle.substring(commaIndex + 1).trim() : withoutTitle;
    }

    public int getFamilySize() {
        return sibsp + parch + 1;
    }

    public boolean hasFamily() {
        return sibsp > 0 || parch > 0;
    }

    public String getCabinDeck() {
        if (cabin.equals("Unknown") || cabin.isEmpty()) {
            return "Unknown";
        }
        return cabin.substring(0, 1);
    }

    public String getEmbarkedLocation() {
        return switch (embarked) {
            case "C" -> "Cherbourg";
            case "Q" -> "Queenstown";
            case "S" -> "Southampton";
            default -> "Unknown";
        };
    }

    //methods for categorization
    public AgeCategory getAgeCategory() {
        if (age == null) return AgeCategory.UNKNOWN;
        return age < 18 ? AgeCategory.CHILD :
                age < 30 ? AgeCategory.YOUNG_ADULT :
                        age < 50 ? AgeCategory.ADULT :
                                AgeCategory.SENIOR;
    }

    public FareCategory getFareCategory() {
        return fare <= 10 ? FareCategory.LOW :
                fare <= 30 ? FareCategory.MEDIUM :
                        fare <= 100 ? FareCategory.HIGH :
                                FareCategory.PREMIUM;
    }

    public enum AgeCategory {
        CHILD("Child (0-17)"),
        YOUNG_ADULT("Young Adult (18-29)"),
        ADULT("Adult (30-49)"),
        SENIOR("Senior (50+)"),
        UNKNOWN("Unknown");

        private final String description;

        AgeCategory(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public enum FareCategory {
        LOW("Economy"),
        MEDIUM("Standard"),
        HIGH("Premium"),
        PREMIUM("Luxury");

        private final String description;

        FareCategory(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}


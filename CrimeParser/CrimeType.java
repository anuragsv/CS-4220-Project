import java.util.Map;
import java.util.HashMap;

public enum CrimeType {
    AGG_ASSAULT("AGG ASSAULT", "Aggravated Assault"),
    AUTO_THEFT("AUTO THEFT", "Auto Theft"),
    BURGLARY_NON_RESIDENTIAL("BURGLARY-NONRES", "Non-Residential Burglary"),
    BURGLARY_RESIDENTIAL("BURGLARY-RESIDENCE", "Residential Burglary"),
    HOMICIDE("HOMICIDE", "Homicide"),
    LARCENY_FROM_VEHICLE("LARCENY-FROM VEHICLE", "Larceny from Vehicle"),
    LARCENY_NON_VEHICLE("LARCENY-NON VEHICLE", "Larceny not from Vehicle"),
    RAPE("RAPE", "Rape"),
    ROBBERY_COMMERICIAL("ROBBERY-COMMERCIAL", "Commercial Robbery"),
    ROBBERY_PEDESTRIAN("ROBBERY-PEDESTRIAN", "Pedestrian Robbery"),
    ROBBERY_RESIDENCE("ROBBERY-RESIDENCE", "Residential Robbery");

    private static Map<String, CrimeType> dataBaseLookupMap = new HashMap<String, CrimeType>();

    public static CrimeType getCrimeTypeFromKey(String dataBaseKey) {
        return dataBaseLookupMap.get(dataBaseKey);
    }

    private String dataBaseKey;
    private String commonName;

    CrimeType(String dataBaseKey, String commonName) {
        this.dataBaseKey = dataBaseKey;
        this.commonName = commonName;
        this.dataBaseLookupMap.put(dataBaseKey, this);
    }

    public String getDataBaseKey() {
        return dataBaseKey;
    }

    public String getCommonName() {
        return commonName;
    }

    @Override
    public String toString() {
        return commonName;
    }

}

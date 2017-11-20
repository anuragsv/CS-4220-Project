import java.time.LocalDate;

public class Crime {
    private CrimeType crimeType;
    private long reportNumber;
    private LocalDate reportDate;
    private String address;
    private String city;
    private String neighborhood;
    private double latitude;
    private double longitude;

    public Crime(CrimeType crimeType, long reportNumber, LocalDate reportDate,
        String address, String city, String neighborhood, double latitude,
        double longitude) {
        this.crimeType = crimeType;
        this.reportNumber = reportNumber;
        this.reportDate = reportDate;
        this.address = address;
        this.city = city;
        this.neighborhood = neighborhood;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CrimeType getCrimeType() {
        return this.crimeType;
    }

    public long getReportNumber() {
        return this.reportNumber;
    }

    public LocalDate getReportDate() {
        return this.reportDate;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    // @Override
    // public String toString() {
    //     return "CrimeType: " + crimeType
    //         + "Report Number: " + reportNumber
    //         + "Report Date: " + reportDate
    //         + "Address: " + address + city;
    //         + "Neighborhood: "
    //
    // }
}

package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class Location {
    private Double latitude;
    private Double longitude;

    public static final int R = 6371;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (!getLatitude().equals(location.getLatitude())) return false;
        return getLongitude().equals(location.getLongitude());

    }

    @Override
    public int hashCode() {
        int result = getLatitude().hashCode();
        result = 31 * result + getLongitude().hashCode();
        return result;
    }
}

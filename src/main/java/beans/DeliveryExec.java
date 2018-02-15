package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class DeliveryExec {
    private int id;
    private Location currentLocation;
    private Double lastOrderDeliveryTime;

    public DeliveryExec(int id, Location currentLocation, Double lastOrderDeliveryTime) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.lastOrderDeliveryTime = lastOrderDeliveryTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Double getLastOrderDeliveryTime() {
        return lastOrderDeliveryTime;
    }

    public void setLastOrderDeliveryTime(Double lastOrderDeliveryTime) {
        this.lastOrderDeliveryTime = lastOrderDeliveryTime;
    }

    @Override
    public String toString() {
        return "DeliveryExec{" +
                "id=" + id +
                ", currentLocation=" + currentLocation +
                ", lastOrderDeliveryTime=" + lastOrderDeliveryTime +
                '}';
    }
}

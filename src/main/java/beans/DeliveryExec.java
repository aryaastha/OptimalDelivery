package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class DeliveryExec {


    private Integer id;
    private Location currentLocation;
    private Double lastOrderDeliveryTime;

    private static int randomId = 1;

    public DeliveryExec(Location currentLocation, Double lastOrderDeliveryTime) {
        this.id = randomId++;
        this.currentLocation = currentLocation;
        this.lastOrderDeliveryTime = lastOrderDeliveryTime;
    }

    public DeliveryExec() {
        this(new Location(0D, 0D), 0D);
    }


    public Integer getId() {
        return id;
    }


    public Location getCurrentLocation() {
        return currentLocation;
    }


    public Double getLastOrderDeliveryTime() {
        return lastOrderDeliveryTime;
    }


    @Override
    public String toString() {
        return "DeliveryExec{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryExec)) return false;

        DeliveryExec that = (DeliveryExec) o;

        if (getId() != that.getId()) return false;
        if (!getCurrentLocation().equals(that.getCurrentLocation())) return false;
        return getLastOrderDeliveryTime().equals(that.getLastOrderDeliveryTime());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getCurrentLocation().hashCode();
        result = 31 * result + getLastOrderDeliveryTime().hashCode();
        return result;
    }
}

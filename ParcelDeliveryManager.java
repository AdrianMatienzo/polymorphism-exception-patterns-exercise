import java.util.*;

public class ParcelDeliveryManager {
    private static ParcelDeliveryManager instance = new ParcelDeliveryManager();
    private List<Parcel> parcels = new ArrayList<>();

    private ParcelDeliveryManager() {}

    public static ParcelDeliveryManager getInstance() {
        return instance;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public boolean parcelInventoryIsEmpty(){
        if (parcels.isEmpty()){
            System.out.println("--------------------------------------------------------");
            System.out.println("There are currently no parcels added.");
            return true;
        }
        return false;
    }

    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public void updateDeliveryStatus(Parcel parcel){
        parcel.setDeliveryStatus("DELIVERED");
    }

    public void updateDeliveryService(Parcel parcel, DeliveryService deliveryService){       
        parcel.setDeliveryService(deliveryService);
    }

    public double updateDeliveryFee(Parcel parcel, DeliveryService deliveryService){
        return deliveryService.calculateDeliveryFee(parcel.getBaseFee());
    }

    public boolean checkIfParcelExists(Parcel parcel){
        if(parcel == null){
            System.out.println("Parcel not found!");
            return false;
        }
        return true;
    }

    public boolean checkIfParcelIsDelivered(Parcel parcel){
        if (parcel.getStatus().equalsIgnoreCase("DELIVERED")){
            System.out.println("Parcel already delivered!");
            return true;
        }
        return false;
    }

    public boolean checkSameDayDeliveryServiceAvailability(Parcel parcel){
        if (parcel.getChargeableWeight() > 10){
            System.out.println("Same Day delivery is only for parcels up to 10 kg!");
            return false;
        }
        return true;
    }

    public boolean checkIfSameDeliveryService(Parcel parcel, DeliveryService newDeliveryService, String message){
        if (newDeliveryService.getType().equalsIgnoreCase(parcel.getDeliveryService())){
            System.out.println(message);
            return true;
        }
        return false;
    }

    public Parcel getParcelByTrackingNumber(String trackingNumber) {
        for (Parcel parcel : parcels) {
            if (parcel.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                return parcel;
            }
        }
        return null;
    }

    public boolean isDuplicateParcelTrackingNumber(String trackingNumber){
        if (getParcelByTrackingNumber(trackingNumber) != null){
            return true;
        }
        return false;
    }
}

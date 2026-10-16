public abstract class Parcel {
    private String trackingNumber;
    private String receiverName;
    private double weight;
    private String status;
    private DeliveryService deliveryService;


    public Parcel (String trackingNumber, String receiverName, double weight, DeliveryService deliveryService) {
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
        this.status = "PENDING";
        this.deliveryService = deliveryService;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getWeight() {
        return weight;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryService(){
        return deliveryService.getType();
    }

    public double getDeliveryFee(){
        if (deliveryService == null) {
            throw new IllegalStateException("No delivery service has been assigned.");
        }
        return deliveryService.calculateDeliveryFee(getBaseFee());
    }

    public void setDeliveryStatus(String status){
        this.status = status;
    }

    public void setDeliveryService(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }
    
    public abstract String getParcelType();

    public abstract double getBaseFee();

    public abstract double getChargeableWeight();
    
    protected double roundUpKg(double weight) {
        return Math.ceil(weight);
    }
    
}

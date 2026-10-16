public class Box extends Parcel {
    private int length;
    private int width;
    private int height;
    
    public Box(String trackingNumber, String receiverName, double weight, DeliveryService deliveryService, int length, int width, int height) {
        super(trackingNumber, receiverName, weight, deliveryService);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override 
    public String getParcelType(){
        return "BOX";
    }

    @Override
    public double getBaseFee(){
        return 40 * getChargeableWeight();
    }

    public double getVolumetricWeight(){
        return ((double) length * width * height) / 3500.0;
    }

    @Override
    public double getChargeableWeight(){
        return roundUpKg(Math.max(getWeight(), getVolumetricWeight()));
    }
}

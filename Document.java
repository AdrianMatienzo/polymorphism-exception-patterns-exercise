public class Document extends Parcel{
    private int numberOfPages;
    
    public Document(String trackingNumber, String receiverName, double weight, DeliveryService deliveryService, int numberOfPages) {
        super(trackingNumber, receiverName, weight, deliveryService);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages(){
        return numberOfPages;
    }

    @Override 
    public String getParcelType(){
        return "DOCUMENT";
    }

    @Override 
    public double getBaseFee(){
        return 50;
    }

    @Override
    public double getChargeableWeight(){
        return getWeight();
    }
}

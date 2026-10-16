public class StandardDelivery implements DeliveryService{
    @Override
    public String getType(){
        return "STANDARD";
    }

    @Override 
    public double calculateDeliveryFee(double baseFee){
        return baseFee;
    }
}

public class ExpressDelivery implements DeliveryService{
    @Override
    public String getType(){
        return "EXPRESS";
    }

    @Override
    public double calculateDeliveryFee(double baseFee){
        return baseFee * 1.5;
    }
}

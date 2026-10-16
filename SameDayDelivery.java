public class SameDayDelivery implements DeliveryService{
    @Override
    public String getType(){
        return "SAME DAY";
    }

    @Override 
    public double calculateDeliveryFee(double baseFee){
        return (baseFee * 2) + 100;
    }
}
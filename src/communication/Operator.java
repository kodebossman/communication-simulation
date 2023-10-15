package communication;

public class Operator {
    public Operator(int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
        this.ID = ID;
        this.talkingCharge = talkingCharge;
        this.messageCost = messageCost;
        this.networkCharge = networkCharge;
        this.discountRate = discountRate;
    }

    private int ID;
    private double talkingCharge;

    private double messageCost;

    private double networkCharge;

    private int discountRate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getTalkingCharge() {
        return talkingCharge;
    }

    public void setTalkingCharge(double talkingCharge) {
        this.talkingCharge = talkingCharge;
    }

    public double getMessageCost() {
        return messageCost;
    }

    public void setMessageCost(double messageCost) {
        this.messageCost = messageCost;
    }

    public double getNetworkCharge() {
        return networkCharge;
    }

    public void setNetworkCharge(double networkCharge) {
        this.networkCharge = networkCharge;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public double calculateTalkingCost( int minute, Customer customer){

        return 0.0;
    }

    public double calculateMessageCost( int quantity, Customer customer, Customer other){
         //function to calculate message cost
        double totalCost= customer.getOperator().getMessageCost()*quantity;
        System.out.println(totalCost);
        return totalCost;
    }

    public double calculateNetworkCost( double amount){
        double totalNetworkCost= this.networkCharge*amount;
        System.out.println(totalNetworkCost);
        return totalNetworkCost;

    }
}

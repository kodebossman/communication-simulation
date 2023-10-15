package communication;

public class OperatorData {
    private int operatorID;
    private int totalTalkingTime;
    private int numberOfMessages;
    private double numberOfMegaBytes;

    public double getNumberOfMegaBytes() {
        return numberOfMegaBytes;
    }

    public void setNumberOfMegaBytes(double numberOfMegaBytes) {
        this.numberOfMegaBytes = numberOfMegaBytes;
    }

    public int getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }

    public int getTotalTalkingTime() {
        return totalTalkingTime;
    }

    public void setTotalTalkingTime(int totalTalkingTime) {
        this.totalTalkingTime = totalTalkingTime;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }



    public OperatorData createOperatorData(Customer customer, double amount, String operation){

        OperatorData customerData = new OperatorData();
        customerData.setOperatorID(customer.getOperator().getID());
        if(operation.equals("talk")){
            int round = (int)Math.round(amount);
            customerData.setTotalTalkingTime(round);

        }else if(operation.equals("connection")){
            customerData.setNumberOfMegaBytes(amount);
        }else if( operation.equals("messages")){
            int round = (int)Math.round(amount);
            customerData.setNumberOfMessages(round);
        }

        return customerData;
    }
}

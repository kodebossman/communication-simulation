package communication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class CustomerData {
    private int customerID;
    private double totalMoneySpent;
    private double currentDept;
    private int talkingTime;
    private String customerName;
    private int numberOfMessages;
    private double totalMegaBytes;

    public int getCustomerID() {
        return customerID;
    }

    public int addTalkingTime(int talkingTime) {
        int totalTime = this.getTalkingTime() + talkingTime;

        System.out.println(totalTime);
        return totalTime;

    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public void setTotalMoneySpent(double totalMoneySpent) {
        this.totalMoneySpent = totalMoneySpent;
    }

    public double getCurrentDept() {
        return currentDept;
    }

    public void setCurrentDept(double currentDept) {
        this.currentDept = currentDept;
    }

    public int getTalkingTime() {
        return talkingTime;
    }

    public void setTalkingTime(int talkingTime) {
        this.talkingTime = talkingTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public double getTotalMegaBytes() {
        return totalMegaBytes;
    }

    public void setTotalMegaBytes(double totalMegaBytes) {
        this.totalMegaBytes = totalMegaBytes;
    }

    /**
     * takes a customer object if set does not contain it
     *
     * @param customer
     * @return
     */
    public CustomerData createCustomerData(Customer customer, double amount, String operation) {

        CustomerData customerData = new CustomerData();
        customer.setID(customer.getID());
        customerData.setCustomerName(customer.getName());
        customerData.setCurrentDept(customer.getBill().getCurrentDebt());

        if (operation.equals("talk")) {
            int round = (int) Math.round(amount);
            customerData.setTalkingTime(round);

        } else if (operation.equals("connection")) {
            customerData.setTotalMegaBytes(amount);
        } else if (operation.equals("messages")) {
            int round = (int) Math.round(amount);
            customerData.setNumberOfMessages(round);
        } else if (operation.equals("pay")) {
            customerData.setTotalMoneySpent(amount);
        }else{
            System.out.println(" Operation Does not Exist for this Creator ");
        }

        return customerData;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerData that = (CustomerData) o;
        return customerID == that.customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID);
    }

    public CustomerData getCustomerDataFromSet(CustomerData customerData, HashSet<CustomerData> customerDataSet) {

        for (Iterator<CustomerData> it = customerDataSet.iterator(); it.hasNext(); ) {
            CustomerData customerData1 = it.next();
            if (customerData1.equals(customerData)) {
                return customerData1;
            }

        }
        return customerData;
    }
}

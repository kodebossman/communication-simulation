package communication;

public class Bill {

    public Bill() {

    }

    public Bill(double limitingAmount) {
        this.limitingAmount = limitingAmount;
    }

    private double limitingAmount;
    private double currentDebt;

    public double getLimitingAmount() {
        return limitingAmount;
    }

    public void setLimitingAmount(double limitingAmount) {
        this.limitingAmount = limitingAmount;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }


    public boolean check(double amount) {
        //checkCustomerLimit before adding bill
        if (this.limitingAmount > amount) {
            this.currentDebt = amount;
        } else {
            System.out.println("Customer limit exceeded for this operation");
        }
        return false;
    }

    public void add(double amount) {

        //we are adding amount to the current bill
        double newBill = this.getCurrentDebt() + amount;
        //updating the currentDebt only if its less than the currentLimit
        //check the limitAmount
        check(newBill);

    }

    public void pay(double amount) {
        // get current dept
        double previousDebt = this.currentDebt;
        double currentDebt = previousDebt - amount;
        System.out.println(currentDebt);
        this.currentDebt = currentDebt;

    }

    public void changeTheLimit(double amount) {
        this.limitingAmount=amount;
    }

}

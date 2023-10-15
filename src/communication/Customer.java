package communication;

public class Customer {

    public Customer() {

    }

    public Customer(int ID, String name, int age, Operator operator, double limitingAmount) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.operator = operator;
        this.bill = bill;
        this.limitingAmount = limitingAmount;
    }

    private int ID;
    private String name;
    private int age;
    private Operator operator;

    private Bill bill;

    private double limitingAmount;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void talk(int minute, Customer other) {
        //this is for both customers check the age range <18 ||>65 for both customers
        if (this.getAge() < 18 || this.getAge() > 65) {
            //apply discount for customer one : NB note that this referes to customer1 which is calling this obj
            double talkingCharge = (this.operator.getTalkingCharge() - ((this.operator.getDiscountRate() / 100) * this.operator.getTalkingCharge()));
            //apply discount to other customer refered as other
            double talkingChargeOtherCustomer = (other.operator.getTalkingCharge() - ((other.operator.getDiscountRate() / 100) * other.operator.getTalkingCharge()));

            //calculate total charge as per spec
            double talkingBill = minute * talkingCharge;

            //check if customer has bill or not> if not create bill and assign to customer
            if (this.getBill() != null) {
                this.getBill().add(talkingBill);
            } else {
                Bill bill = new Bill();
                bill.add(talkingBill);
                this.setBill(bill);
            }

            //calulate bill for other customer
            double talkingBillOtherCustomer = minute * talkingChargeOtherCustomer;
            //check if customer has bill or not> if not create bill and assign to customer
            if (other.getBill() != null) {
                other.getBill().add(talkingBillOtherCustomer);
            } else {
                Bill bill = new Bill();
                bill.add(talkingBillOtherCustomer);
                other.setBill(bill);
            }

        } else {
            //else if age range is inbetween 18 and 65 this is executed
            double talkingCharge = this.operator.getTalkingCharge() * minute;
            //check bill
            if (this.getBill() != null) {
                this.getBill().add(talkingCharge);
            } else {
                //else assign bill add total charge
                Bill bill = new Bill();
                bill.add(talkingCharge);
                //assign to customer1
                this.setBill(bill);

            }
            //else if age range is inbetween 18 and 65 this is executed
            double talkingChargeOtherCustomer = other.operator.getTalkingCharge() * minute;
            if (other.getBill() != null) {
                other.getBill().add(talkingChargeOtherCustomer);
            } else {
                Bill bill = new Bill();
                bill.add(talkingChargeOtherCustomer);
                other.setBill(bill);

            }
        }


    }

    public void message(int quantity, Customer other) {
        //if this.customer.operator.id= other.operator.id
        //apply discount which is associated  with that operator
        if (this.operator.getID() == other.operator.getID()) {
            double messageCost = (this.getOperator().getMessageCost() - (this.operator.getMessageCost() * (this.operator.getDiscountRate() / 100)));
            double totalCost = messageCost * quantity;
            //else if age range is inbetween 18 and 65 this is executed
            if (this.getBill() != null) {
                this.getBill().add(totalCost);
            } else {
                Bill bill = new Bill();
                bill.add(totalCost);
                this.setBill(bill);
            }
        } else {
            double totalCost = this.operator.getMessageCost() * quantity;
            //else if age range is inbetween 18 and 65 this is executed
            if (this.getBill() != null) {
                this.getBill().add(totalCost);
            } else {
                Bill bill = new Bill();
                bill.add(totalCost);
                this.setBill(bill);
            }
        }
    }
    public void connection(double amount) {

       double totalCharge= this.operator.getNetworkCharge()*amount;
        if (this.getBill() != null) {
            this.getBill().add(totalCharge);
        } else {
            Bill bill = new Bill();
            bill.add(totalCharge);
            this.setBill(bill);
        }
    }




}

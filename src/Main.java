import communication.*;

import java.io.*;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static List<Customer> customerList = new ArrayList<>();
    public static List<Operator> operatorList = new ArrayList<>();
    //when i=0 -> assign this string to number of customers;
    public static int numberOfCustomers = 0;
    public static int numberOfOperators = 0;
    // position 3 is totalEvent
    public static int totalNumberOfEvents = 0;
    public static int customerID = 0;
    public static int operatorID = 0;

    public static Map<Integer, OperatorData> operatorDataSet = new HashMap();
    public static Map<Integer, CustomerData> customerDataSet = new HashMap();


    public static void main(String[] args) throws FileNotFoundException {

        //pick the instructions from the file
        List<String> instructionList = readInputsFromFile();

        // pick the constants based on positions as given in the spec
        numberOfCustomers = Integer.parseInt(instructionList.get(0));
        numberOfOperators = Integer.parseInt(instructionList.get(1));
        totalNumberOfEvents = Integer.parseInt(instructionList.get(2));

        //processInstruction
        processInstructions(instructionList);

        // save to OutputFile
        writeOutputDataToFile();

        //save customer data to file
        // writeCustomerDataToFile(customerDataSet);

        System.out.println("Communication Simulation Concluded");

    }

    public static List<String> readInputsFromFile() throws FileNotFoundException {

        List<String> instructionList = new ArrayList<>();

        try {
            File file = new File("C:\\Projects\\communication-simmulation\\testings\\input.txt");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);      //appends line to string buffer
                instructionList.add(line);
                sb.append("\n");     //line feed
            }
            fr.close();    //closes the stream and release the resources
            System.out.println("Contents of File: ");
            System.out.println(sb.toString());   //returns a string that textually represents the object

            // iterate through instruction to do as per specification
            return instructionList;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructionList;
    }


    public static void processInstructions(List<String> instructionList) {

        for (String instruction : instructionList) {

            /**
             * Case 1  , create customer instruction
             * get operator from list
             * set operator on customer
             */
            if (instruction.startsWith("1") && instruction.length() > 3) {

                //parameters
                String[] parameters = splitParameters(instruction);

                //get customer.operator from operatorList using ID provided
                Operator operator = operatorList.get(Integer.parseInt(parameters[3]));

                // create customer from the instruction provided, use global ID variable for tracking ID
                Customer customer = new Customer(customerID, parameters[1], Integer.parseInt(parameters[2]),
                        operator, Double.parseDouble(parameters[4]));

                //increment customerID as per spec
                customerID++;

                //add customer to  global variable list
                customerList.add(customer);

                // because we have processed current instruction continue to take the next instruction
                continue;
            }

            /**
             * Case 2 , create operator instruction
             * get operatorID from static var
             * add operator on operatorList
             */
            if (instruction.startsWith("2") && instruction.length() > 3) {

                // instruction to create operator
                //split instruction to get constructor parameters
                String[] parameters = splitParameters(instruction);
                //create operator object from instruction arr and use global variable to create opID
                Operator operator = new Operator(operatorID, Double.parseDouble(parameters[1]),
                        Double.parseDouble(parameters[2]), Double.parseDouble(parameters[3]),
                        Integer.parseInt(parameters[4]));

                //increase operator
                operatorID++;
                //add to Operator List
                operatorList.add(operator);
                continue;
            }

            /**
             * Case 3 , Talk on mobile network
             * bill all customer
             * manage discount rates
             */
            if (instruction.startsWith("3") && instruction.length() > 3) {

                //split variable
                String[] parameters = splitParameters(instruction);
                //get customer 1 from the list
                int customerID = Integer.parseInt(parameters[1]);
                int customerID2 = Integer.parseInt(parameters[2]);
                Customer customer1 = customerList.get(customerID);
                // get customer 2 from the list
                Customer customer2 = customerList.get(customerID2);

                int talkingTime = Integer.parseInt(parameters[3]);

                customer1.talk(talkingTime, customer2);

                //create customerData
                //check if customerData is in the map already
                CustomerData customerData = customerDataSet.get(customerID);
                //if ! null then it exist already assign new talking time
                CustomerData customerData1 = customerDataSet.get(customerID2);
                if (customerData != null) {
                    customerData.setTalkingTime(customerData.getTalkingTime() + talkingTime);
                    customerData.setCurrentDept(customer1.getBill().getCurrentDebt());
                } else {
                    //create new data
                    customerData = new CustomerData();
                    //create new object and assign values
                    customerData = customerData.createCustomerData(customer1, talkingTime, "talk");
                    customerDataSet.put(customerID, customerData);
                }
                if (customerData1 != null) {
                    customerData1.setTalkingTime(customerData.getTalkingTime() + talkingTime);
                    customerData.setCurrentDept(customer2.getBill().getCurrentDebt());
                } else {
                    customerData1 = new CustomerData();
                    customerData1 = customerData1.createCustomerData(customer2, talkingTime, "talk");
                    customerDataSet.put(customerID2, customerData1);
                }

                //Operator Data
                OperatorData operatorData = operatorDataSet.get(customer1.getOperator().getID());

                if (operatorData != null) {
                    operatorData.setTotalTalkingTime(operatorData.getTotalTalkingTime() + talkingTime);
                } else {
                    operatorData = new OperatorData();
                    operatorData = operatorData.createOperatorData(customer1, talkingTime, "talk");
                    operatorDataSet.put(customerID, operatorData);
                }

                // operator data two
                //Operator Data
                OperatorData operatorData2 = operatorDataSet.get(customer2.getOperator().getID());

                if (operatorData2 != null) {
                    operatorData2.setTotalTalkingTime(operatorData2.getTotalTalkingTime() + talkingTime);
                } else {
                    operatorData2 = new OperatorData();
                    operatorData2 = operatorData2.createOperatorData(customer2, talkingTime, "talk");
                    operatorDataSet.put(customerID, operatorData2);
                }

                // break execution
                continue;
            }

            if (instruction.startsWith("4") && instruction.length() > 3) {

                String[] parameters = splitParameters(instruction);
                // getting customer based on id

                int customerID = Integer.parseInt(parameters[1]);
                Customer customer1 = customerList.get(customerID);
                // getting customer based on id

                Customer customer2 = customerList.get(Integer.parseInt(parameters[2]));

                int numberOfMessages = Integer.parseInt(parameters[3]);
                //customer1 sending messages to customer2
                customer1.message(numberOfMessages, customer2);

                //create customer data
                CustomerData customerData = customerDataSet.get(customerID);

                if (customerData != null) {
                    customerData.setNumberOfMessages(customerData.getNumberOfMessages() + numberOfMessages);
                    customerData.setCurrentDept(customer1.getBill().getCurrentDebt());
                } else {
                    customerData = new CustomerData();
                    customerData = customerData.createCustomerData(customer1, numberOfMessages, "messages");
                    customerDataSet.put(customerID, customerData);
                }

                //Operator Data
                OperatorData operatorData = operatorDataSet.get(customer1.getOperator().getID());

                if (operatorData != null) {
                    operatorData.setNumberOfMessages(operatorData.getNumberOfMessages() + numberOfMessages);
                } else {
                    operatorData = new OperatorData();
                    operatorData = operatorData.createOperatorData(customer1, numberOfMessages, "messages");
                    operatorDataSet.put(customerID, operatorData);
                }

                // break execution
                continue;

            }

            if (instruction.startsWith("5") && instruction.length() > 3) {

                String[] parameters = splitParameters(instruction);

                int customerID = Integer.parseInt(parameters[1]);
                //get customer 1 from list
                Customer customer = customerList.get(customerID);
                //call the connection method

                double amountInMegaBytes = Double.parseDouble(parameters[2]);
                customer.connection(amountInMegaBytes);

                //create CustomerData
                CustomerData customerData = customerDataSet.get(customerID);
                //check for null
                if (customerData != null) {
                    customerData.setTotalMegaBytes(customerData.getTotalMegaBytes() + amountInMegaBytes);
                    customerData.setCurrentDept(customer.getBill().getCurrentDebt());
                } else {
                    //create new dataset
                    customerData = new CustomerData();
                    customerData = customerData.createCustomerData(customer, amountInMegaBytes, "connection");
                    //put to map
                    customerDataSet.put(customerID, customerData);
                }

                //Operator Data
                OperatorData operatorData = operatorDataSet.get(customer.getOperator().getID());

                if (operatorData != null) {
                    operatorData.setNumberOfMegaBytes(operatorData.getNumberOfMegaBytes() + amountInMegaBytes);
                } else {
                    operatorData = new OperatorData();
                    operatorData = operatorData.createOperatorData(customer, amountInMegaBytes, "connection");
                    operatorDataSet.put(customerID, operatorData);
                }

                //break execution
                continue;

            }

            if (instruction.startsWith("6") && instruction.length() > 3) {
                String[] parameters = instruction.split(" ");

                //customerID
                int customerID = Integer.parseInt(parameters[1]);
                //get the customer which needs to pay
                Customer customer = customerList.get(customerID);

                //check if the customer has a debt tpo pay
                if (customer.getBill() != null) {
                    //we now need to make the customer pay
                    double amountPaid = Double.parseDouble(parameters[2]);
                    //now use thiis to pay
                    customer.getBill().pay(amountPaid);

                    //create customer data

                    CustomerData customerData = customerDataSet.get(customerID);
                    if (customerData != null) {
                        customerData.setTotalMoneySpent(customerData.getTotalMoneySpent() + amountPaid);
                        customerData.setCurrentDept(customer.getBill().getCurrentDebt());
                    } else {
                        customerData = new CustomerData();
                        customerData = customerData.createCustomerData(customer, amountPaid, "pay");
                        customerDataSet.put(customerID, customerData);
                    }

                } else {
                    System.out.println("customer doesnt have a bill");
                }

                //break execution
                continue;


            }

            if (instruction.startsWith("7") && instruction.length() > 3) {

                String[] parameters = instruction.split(" ");

                Customer customer1 = customerList.get(Integer.parseInt(parameters[1]));

                Operator oparator1 = operatorList.get(Integer.parseInt(parameters[2]));

                customer1.setOperator(oparator1);

                //break execution
                continue;

            }
            if (instruction.startsWith("8") && instruction.length() > 3) {

                String[] parameters = instruction.split(" ");

                Customer customer1 = customerList.get(Integer.parseInt(parameters[1]));
                //get the amount to be assigned
                double limitAmount = Double.parseDouble(parameters[2]);

                if (customer1.getBill() != null) {
                    //assign limitAmount to this
                    customer1.getBill().setLimitingAmount(limitAmount);
                    //
                } else {
                    Bill bill = new Bill();
                    bill.setLimitingAmount(limitAmount);
                    customer1.setBill(bill);
                }


            }

        }

    }

    public static String[] splitParameters(String instruction) {

        return instruction.split(" ");
    }

    public static List<String> processOperatorDataToFile(Map<Integer, OperatorData> operatorDataSet) {

        List<String> outputList = new ArrayList<>();
        for (Map.Entry<Integer, OperatorData> entry : operatorDataSet.entrySet()) {

            String output = "Operator " + entry.getKey() + " : " + entry.getValue().getTotalTalkingTime() +
                    " " + entry.getValue().getNumberOfMessages() + " " + entry.getValue().getNumberOfMegaBytes();
            outputList.add(output);

        }

        return outputList;
    }

    public static List<String> processCustomerDataForOutput(Map<Integer, CustomerData> customerDataSet) {


        List<String> outputList = new ArrayList<>();
        for (Map.Entry<Integer, CustomerData> entry : customerDataSet.entrySet()) {

            String output = "Customer " + entry.getKey() + " : " + entry.getValue().getTotalMoneySpent() +
                    " " + entry.getValue().getCurrentDept();
            outputList.add(output);

        }

        return outputList;

    }

    private static List<String> processCustomerWithMostMinutes(Map<Integer, CustomerData> customerDataSet) {

        List<CustomerData> customerDataList = new ArrayList<>(customerDataSet.values());
        List<String> outputList = new ArrayList<>();

        Optional<CustomerData> customerData = customerDataList.stream().max(Comparator.comparing(CustomerData::getTalkingTime)).stream().findFirst();

        if (customerData.isPresent()) {

            CustomerData customerData1 = customerData.get();
            String output = customerData1.getCustomerName() + " " + customerData1.getTalkingTime();
            outputList.add(output);
        }

        return outputList;
    }

    private static void writeOutputDataToFile() {

        List<String> outputList = processOperatorDataToFile(operatorDataSet);
        outputList.addAll(processCustomerDataForOutput(customerDataSet));
        outputList.addAll(processCustomerWithMostMinutes(customerDataSet));

        try {
            FileWriter writer = new FileWriter("output.txt");

            for (String str : outputList) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();

        } catch (IOException exception) {
            System.out.println("Exception Writing Customer Data to Output");
        }
    }


}




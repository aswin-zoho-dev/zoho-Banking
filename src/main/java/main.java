import java.util.*;

public class main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        double currectbalance=0.00;
        HashMap<String, Double> currentBalance = new HashMap<>();
        /*Admin Creation*/
        User admin = new User("Aswin","q","1111", "ADMIN", "ACTIVE");
        users.add(admin);

        /*Manager Creation*/
        User manager = new User("Manager","z","222", "MANAGER", "ACTIVE");
        users.add(manager);

        /*Employee Creation*/
        User employee = new User("Employee","a","33", "EMPLOYEE", "ACTIVE");
        users.add(employee);

        User employee2 = new User("Employee2","9","9", "EMPLOYEE", "ACTIVE");
        users.add(employee2);

        // Create Customer
        User custr = new User("a","a","1", "CUSTOMER", "ACTIVE");
        users.add(custr);

        User custr1 = new User("customer2","2","2", "CUSTOMER", "ACTIVE");
        users.add(custr1);

        while(true) {
        Scanner sc = new Scanner(System.in);
        System.out.println("***************************** Welcome ************************************");
        System.out.println("                            1) Login");
        System.out.println("                            2) Register");
        System.out.println("**************************************************************************");

        int action = sc.nextInt();
        if (action == 1) {
                System.out.println("***************** Login ******************");
                System.out.print("Enter Phone : ");
                String phone = sc.next();
                System.out.print("Enter Password : ");
                String password = sc.next();
                Login login = new Login();
                User user = login.authorization(phone, password, users);
                boolean logged = true;
                while(logged) {
                    if (user != null && user.type == "CUSTOMER" && user.status == "ACTIVE") {
                        System.out.println("Welcome " + user.name);
                        System.out.println("1) Deposit Amount");
                        System.out.println("2) Withdraw Amount ");
                        System.out.println("3) Balance ");
                        System.out.println("4) Log Out");
                        System.out.println("**************************************************************************");
                        for (int i = 0; i < transactions.size(); i++) {
                            if (transactions.get(i).userId == user.id) {
                                currectbalance = transactions.get(i).amount;
                            }
                        }
                        int option = sc.nextInt();
                        switch (option) {
                            case 1:
                                System.out.print("Enter Amount to be Added : ");
                                int depositeAmt = sc.nextInt();

                                double finalDepositeAmt = depositeAmt + 0.00;

                                Transaction ctransaction = new Transaction(finalDepositeAmt, "credit", "SUCCESS", user.id, currectbalance);
                                transactions.add(ctransaction);
                                List keys = new ArrayList(currentBalance.keySet());
                                if(currentBalance.size() > 0 && keys.contains(String.valueOf(user.id))) {
                                    for (int i = 0; i < currentBalance.keySet().size(); i++) {
                                        System.out.println(keys.get(i) + "  " + String.valueOf(user.id));
                                        if (keys.get(i).equals(String.valueOf(user.id))) {
                                            currentBalance.put(String.valueOf(user.id), currentBalance.get(String.valueOf(user.id))+finalDepositeAmt);
                                        }
                                    }
                                } else {
                                    currentBalance.put(String.valueOf(user.id), finalDepositeAmt);
                                }

                                System.out.println("The Amount of " + finalDepositeAmt + " credited to your Account Successfully");
                                System.out.println("Now the new Balance is Rs: " + currentBalance.get(String.valueOf(user.id)));
                                break;
                            case 2:
                                System.out.print("Enter Amount to Withdraw : ");
                                int withDrawAmt = sc.nextInt();
                                if(withDrawAmt > currectbalance) {
                                    System.out.println("Insufficient Balance");
                                    break;
                                }
                                Transaction wtransaction = new Transaction(withDrawAmt, "withdraw", "PROCESSING", user.id, currentBalance.get(String.valueOf(user.id)));
                                System.out.println("Your Transaction is in under Processing. Please be patient for a while our EMPLOYEE will approve your transaction.");
                                transactions.add(wtransaction);
                                break;
                            case 3:
                                System.out.println("Your Current Balance is " + currentBalance.get(String.valueOf(user.id)));
                                break;
                            case 4:
                                logged = false;
                                System.out.println("Logged Out Successfully");
                                System.out.println("**************************************************************************");
                                System.out.println("Thank You For Banking With Us");
                                System.out.println("**************************************************************************");
                                break;
                            default:
                                break;
                        }
                    }
                    else if (user != null && user.type == "CUSTOMER" && user.status == "PENDING") {
                        System.out.println("Please Wait Until Our Team will set You Active");
                        break;
                    }
                    else if (user != null && user.type == "EMPLOYEE" && user.status == "ACTIVE") {
                        System.out.println("Welcome To Employee Dashboard");
                        System.out.println("Check For Pending Approvals");
                        System.out.println("1) Approve Transaction");
                        System.out.println("2) Approve Customer");
                        System.out.println("3) Log Out");
                        int opt = sc.nextInt();
                        Transaction transaction = new Transaction(0,"","",33, 0.0);
                        if(opt == 1) {
                            List<Transaction> processingTransaction = transaction.getProcessingTransaction(transactions);
                            if (processingTransaction.size() > 0) {
                                processingTransaction.forEach(userTransaction -> {
                                    System.out.println(userTransaction.transactionAmount + " is Transaction By The User with ID " + userTransaction.userId );
                                    List<Transaction> approvedTransaction = new ArrayList<>();
                                    List<Transaction> rejectedTransaction = new ArrayList<>();
                                    Scanner scanner = new Scanner(System.in);
                                    System.out.println("Enter Any Option to change the Transaction Status");
                                    System.out.println("1) Approved");
                                    System.out.println("2) Rejected");
                                    int option = scanner.nextInt();
                                    switch (option) {
                                        case 1:
                                            userTransaction.transactionStatus = "PROCESSING -> APPROVED";
                                            System.out.println(userTransaction.transactionStatus);
                                            userTransaction.setAmount(userTransaction.transactionAmount, userTransaction.userId, userTransaction.transactionStatus, "SUCCESS", currentBalance.get(String.valueOf(userTransaction.userId)));
                                            approvedTransaction.add(userTransaction);
                                            currentBalance.put(String.valueOf(userTransaction.userId), currentBalance.get(String.valueOf(userTransaction.userId))-userTransaction.transactionAmount);
                                            break;
                                        case 2:
                                            userTransaction.transactionStatus = "REJECTED";
                                            System.out.println(userTransaction.transactionStatus);
                                            rejectedTransaction.add(userTransaction);
                                            break;
                                        default:
                                            System.out.println("Please Provide Valid Option");
                                    }
                                });
                            } else {
                                System.out.println("No pending Transaction");
                            }
                        }
                        else if(opt == 2) {
                            for (int i = 0; i < users.size(); i++) {
                                if(users.get(i).status.equalsIgnoreCase("PENDING") && users.get(i).type == "CUSTOMER") {
                                    System.out.println(users.get(i).name + " " + " ID : " + users.get(i).id + " USER TYPE : " + users.get(i).type);
                                    System.out.println("1) APPROVE");
                                    System.out.println("2) REJECT");
                                    int approve = sc.nextInt();
                                    switch (approve) {
                                        case 1:
                                            users.get(i).status = "ACTIVE";
                                            break;
                                        case 2:
                                            users.get(i).status = "REJECTED";
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        else if (opt == 3) {
                            System.out.println("Thank You See U Soon");
                            logged = false;
                            break;
                        }
                        System.out.println("**************************************************************************");
                        System.out.println();
                    }
                    else if (user != null && user.type == "EMPLOYEE" && user.status == "PENDING") {
                        System.out.println("Please Wait Until Our Management Team will set You Active");
                        break;
                    }
                    else if (user != null && user.type == "MANAGER") {
                        System.out.println("Welcome to Manager Panel - " + user.name);
                        System.out.println("1) Approve Employee/Customer");
                        System.out.println("2) Approve Transaction");
                        System.out.println("3) Delete Employee");
                        System.out.println("4) Log Out");
                        int managerOption = sc.nextInt();
                        Transaction transaction = new Transaction(0,"","",222, 0.0);
                            if (managerOption == 1) {
                                for (int i = 0; i < users.size(); i++) {
                                    if (users.get(i).status.equalsIgnoreCase("PENDING")) {
                                        System.out.println(users.get(i).name + " " + " ID : " + users.get(i).id + " USER TYPE : " + users.get(i).type);
                                        System.out.println(" 1) APPROVE");
                                        System.out.println(" 2) REJECT");
                                        int approve = sc.nextInt();
                                        switch (approve) {
                                            case 1:
                                                users.get(i).status = "ACTIVE";
                                                break;
                                            case 2:
                                                users.get(i).status = "REJECTED";
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                            else if(managerOption == 2) {
                                List<Transaction> processingTransaction = transaction.getProcessingTransaction(transactions);
                                if (processingTransaction.size() > 0) {
                                    processingTransaction.forEach(userTransaction -> {
                                        System.out.println(userTransaction.transactionAmount + " is Transaction By The User with ID " + userTransaction.userId);
                                        List<Transaction> approvedTransaction = new ArrayList<>();
                                        List<Transaction> rejectedTransaction = new ArrayList<>();
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("Enter Any Option to change the Transaction Status");
                                        System.out.println("1) Approved");
                                        System.out.println("2) Rejected");
                                        int option = scanner.nextInt();
                                        double currentbalance = 0.00;
                                        for (int i = 0; i < processingTransaction.size(); i++) {
                                            if (processingTransaction.get(i).userId == user.id) {
                                                currentbalance = transactions.get(i).amount;
                                            }
                                        }
                                        switch (option) {
                                            case 1:
                                                userTransaction.transactionStatus = "PROCESSING -> APPROVED";
                                                System.out.println(userTransaction.transactionStatus);
                                                userTransaction.setAmount(userTransaction.transactionAmount, userTransaction.userId, userTransaction.transactionStatus, "SUCCESS", currentbalance);
                                                approvedTransaction.add(userTransaction);
                                                break;
                                            case 2:
                                                userTransaction.transactionStatus = "REJECTED";
                                                System.out.println(userTransaction.transactionStatus);
                                                rejectedTransaction.add(userTransaction);
                                                break;
                                            default:
                                                System.out.println("Please Provide Valid Option");
                                        }
                                    });
                                } else {
                                    System.out.println("No pending Transaction");
                                }
                            }
                            else if (managerOption == 3) {
                                List ids = new ArrayList<>();
                                for (int i = 0; i < users.size(); i++) {
                                    if (users.get(i).type.equalsIgnoreCase("EMPLOYEE")) {
                                        System.out.println("ID : " + users.get(i).id + " Name : " + users.get(i).name + " INDEX : " + (i));
                                        ids.add(i);
                                    }
                                }
                                if (ids.size() > 0) {
                                    System.out.print("Enter INDEX to from Above List to Remove Employee : ");
                                    int rmId = sc.nextInt();
                                    if (ids.contains(rmId)) {
                                        users.remove(users.get(rmId));
                                        System.out.println("User was removed Successfully");
                                    } else {
                                        System.out.println("Please Enter Valid INDEX");
                                    }
                                } else {
                                    System.out.println("No Employee(s) in the DataBase");
                                }
                            }
                            else {
                                System.out.println("Thank You See U Soon");
                                logged = false;
                            }
                    }
                    else if (user != null && user.type == "ADMIN") {
                        System.out.println("Welcome to Admin Panel - " + user.name);
                        System.out.println("1) Get All Profile Details");
                        System.out.println("2) Get All Profile Transactions");
                        System.out.println("3) Log Out");
                        int adminOption = sc.nextInt();
                        if(adminOption == 1) {
                            for (int index = 0; index < users.size(); index++) {
                                System.out.println("ID : " + users.get(index).id);
                                System.out.println(" NAME : " + users.get(index).name);
                                System.out.println(" PHONE : " + users.get(index).phone);
                                System.out.println(" USER TYPE : " + users.get(index).type);
                                System.out.println("------------------------------------------------------------");
                            }
                            System.out.println("Enter 'Q' Key and Press 'Enter' To EXIT");
                            String exit = sc.next();
                        }
                        else if (adminOption == 2) {
                            if(transactions.size() > 0) {
                                for (int index = 0; index < transactions.size(); index++) {
                                    System.out.println("ID : " + transactions.get(index).userId);
                                    System.out.println(" TRANSACTION ID : " + transactions.get(index).transactionId);
                                    System.out.println(" STATUS : " + transactions.get(index).transactionStatus);
                                    System.out.println(" AMOUNT : " + transactions.get(index).transactionAmount);
                                    System.out.println(" TRANSACTION TYPE : " + transactions.get(index).transactionType);
                                    System.out.println("------------------------------------------------------------");
                                }
                            } else {
                                System.out.println("No Transactions");
                            }
                        }
                        else if (adminOption == 3) {
                            System.out.println("Thank You See U Soon");
                            logged = false;
                            break;
                        }
                    }
                    else {
                        System.out.println("Check The Credentials");
                        break;
                    }
                }

        }
        else if (action == 2) {
            System.out.println("****************************** Register ******************************");
            Scanner cust = new Scanner(System.in);
            System.out.print("Enter User Name : ");
            String name = cust.next();
            System.out.print("Enter Password : ");
            String password = cust.next();
            System.out.print("Enter Phone : ");
            String phone = cust.next();
            System.out.println("Choose User Type : 1) Customer 2) Employee ");
            System.out.println("*NOTE: If you Selected any other number mentioned above you will be considered as CUSTOMER");
            int type = cust.nextInt();
            String userType;
            if (type == 2) {
                userType = "EMPLOYEE";
            } else {
                userType = "CUSTOMER";
            }
            /* Register user */
            User customer = new User(name, password, phone, userType, "PENDING");
            users.add(customer);
            System.out.println("User of "+ userType + " Created Successfully");
        }
        }
    }
}

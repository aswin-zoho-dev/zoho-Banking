import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private static int idCount = 0;
    double amount;
    double transactionAmount;
    String transactionType;
    String transactionStatus;
    int transactionId;
    int userId;

    public double getAmount() {
        return amount;
    }

    public Transaction(double transactionAmount, String transactionType, String transactionStatus, int id, double currentBalance) {
        idCount+=1;
        this.transactionId = idCount;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        setAmount(transactionAmount, id, transactionType, transactionStatus, currentBalance);
        this.userId = id;
    }

    public void setAmount(double amount, int id, String transactionType, String transactionStatus, double currentBalance) {
        this.userId = id;
        if(transactionType == "credit") {
            this.amount = currentBalance + amount;
        } else if(transactionType == "PROCESSING -> APPROVED") {
            this.amount = currentBalance - amount;
        }
    }

    public List<Transaction> getProcessingTransaction(List<Transaction> transactions) {
        List<Transaction> processingTransactions = new ArrayList<>();
        transactions.forEach(transaction -> {
            if (transaction.transactionStatus.equalsIgnoreCase("processing")) {
                processingTransactions.add(transaction);
            };
        });
        return processingTransactions;
    }
}

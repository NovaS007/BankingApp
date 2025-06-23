import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
    private final ArrayList<BankAccount> bankAccounts;
    private double lowMoneyThreshold;
    private final Random RAND_NUM = new Random();

    public Bank(double initLowMoneyThreshold) {
        bankAccounts = new ArrayList<>();
        lowMoneyThreshold = initLowMoneyThreshold;

    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public double getLowMoneyThreshold() {
        return lowMoneyThreshold;
    }

    public void setLowMoneyThreshold(double lowMoneyThreshold) {
        if (lowMoneyThreshold >= 0)
            this.lowMoneyThreshold = lowMoneyThreshold;
    }

    public void addAccount(BankAccount newBankAccount) {
        newBankAccount.setAccountNumber(createRandomAccountNumber());
        bankAccounts.add(newBankAccount);
    }

    public boolean deleteAccountByNumber(int accountNumberToDelete) {
        boolean success = false;
        BankAccount targetAccount = findAccountByNumber(accountNumberToDelete);
        if(targetAccount != null) {
            bankAccounts.remove(targetAccount);
            success = true;
        }

        return success;
    }

    public BankAccount findAccountByNumber(int numberToSearch) {
        BankAccount tempAccount;
        BankAccount foundAccount = null;

        for (BankAccount bankAccount : bankAccounts) {
            tempAccount = bankAccount;
            if (tempAccount.getAccountNumber() == numberToSearch) {
                foundAccount = tempAccount;
                break;
            }
        }

        return foundAccount;
    }

    public double averageBalance() {
        double totalBalance = 0;
        BankAccount tempAccount;
        for (BankAccount bankAccount : bankAccounts) {
            tempAccount = bankAccount;
            totalBalance += tempAccount.getCurrentBalance();
        }

        return totalBalance / bankAccounts.size();
    }

    public double minBalance() {
        double minBalance = Double.MAX_VALUE;
        BankAccount tempAccount;
        for (BankAccount bankAccount : bankAccounts) {
            tempAccount = bankAccount;
            if (tempAccount.getCurrentBalance() < minBalance)
                minBalance = tempAccount.getCurrentBalance();
        }

        return minBalance;
    }

    public int createRandomAccountNumber() {
        int accountNumber = RAND_NUM.nextInt(1_000_000,10_000_000);
        while (findAccountByNumber(accountNumber) != null)
            accountNumber = RAND_NUM.nextInt(1_000_000,10_000_000);
        return accountNumber;
    }

    public double maxBalance() {
        double maxBalance = Double.MIN_VALUE;
        BankAccount tempAccount;
        for (BankAccount bankAccount : bankAccounts) {
            tempAccount = bankAccount;
            if (tempAccount.getCurrentBalance() > maxBalance)
                maxBalance = tempAccount.getCurrentBalance();
        }
        return maxBalance;
    }

    public ArrayList<BankAccount> findLowAccounts(){
        ArrayList<BankAccount> criticalAccounts = new ArrayList<>();
        BankAccount tempAccount;
        for (BankAccount bankAccount : bankAccounts) {
            tempAccount = bankAccount;
            if (tempAccount.getCurrentBalance() <= lowMoneyThreshold) {
                criticalAccounts.add(tempAccount);
            }
        }
        return criticalAccounts;
    }

    public void load(String pathToSave) throws Exception {
        FileInputStream fis = new FileInputStream(pathToSave);
        Scanner sc = new Scanner(fis);
        Person accountPlaceHolder;
        String accountInfoLine;

        while (sc.hasNextLine()) {
            accountInfoLine = sc.nextLine();
            if (!accountInfoLine.equals("------------------------")) {
                accountPlaceHolder = new Person("", "", "",
                        ' ', 0, 0);
                if (accountInfoLine.charAt(0) == 'c') {
                    CheckingAccount loadedCheckingAccount = new CheckingAccount(accountPlaceHolder, 0, 0, 0);
                    loadedCheckingAccount.loadFromText(accountInfoLine.substring(2));
                    bankAccounts.add(loadedCheckingAccount);
                }

                else {
                    SavingsAccount loadedSavingsAccount = new SavingsAccount(accountPlaceHolder, 0,0,0);
                    loadedSavingsAccount.loadFromText(accountInfoLine.substring(2));
                    bankAccounts.add(loadedSavingsAccount);
                }
            }
        }

        sc.close();
        fis.close();
    }

    public void save(String filePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        OutputStreamWriter osw = new OutputStreamWriter(fos);

        for (BankAccount bankAccount : bankAccounts) {
            osw.write(bankAccount.convertToText());
            System.out.println("\n");
        }

        osw.close();
        fos.close();
    }
}
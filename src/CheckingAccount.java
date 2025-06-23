import java.util.Scanner;

public class CheckingAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.05;
    private static final int NUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED = 10;
    private int numberOfConsecutiveDepositsMade;

    public CheckingAccount(Person initUser, int initAccountNumber, double initCurrentBalance,
                           double initWithdrawalLimit) {
        super(initUser, initAccountNumber, initCurrentBalance, initWithdrawalLimit);
        numberOfConsecutiveDepositsMade = 0;
    }

    public int getNumberOfConsecutiveDepositsMade() {
        return numberOfConsecutiveDepositsMade;
    }

    public void setNumberOfConsecutiveDepositsMade(int numberOfConsecutiveDepositsMade) {
        if (numberOfConsecutiveDepositsMade >= 0)
            this.numberOfConsecutiveDepositsMade = numberOfConsecutiveDepositsMade;
    }

    public boolean deposit(double amount) {
        boolean success = super.deposit(amount);
        if (success) {
            numberOfConsecutiveDepositsMade++;
            if (numberOfConsecutiveDepositsMade % NUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED == 0) {
                setCurrentBalance(getCurrentBalance() + (getCurrentBalance() * INTEREST_RATE));
            }
        }
        return success;
    }

    public boolean withdrawal(double amount) {
        boolean success = super.withdrawal(amount);
        if (success) {
            numberOfConsecutiveDepositsMade = 0;
        }
        return success;
    }

    @Override
    public void loadFromText(String textToLoad){
        Scanner sc = new Scanner(textToLoad);
        sc.useDelimiter(":");

        setNumberOfConsecutiveDepositsMade(sc.nextInt());

        getAccountHolder().setName(sc.next());

        getAccountHolder().setBirthday(sc.next());

        getAccountHolder().setAddress(sc.next());

        getAccountHolder().setGender((sc.next().charAt(0)));

        getAccountHolder().setAge(sc.nextInt());

        getAccountHolder().setPhoneNumber(sc.nextLong());

        setAccountNumber(sc.nextInt());

        setCurrentBalance(sc.nextDouble());

        setWithdrawalLimit(sc.nextDouble());

        setCurrentWithdrawalAmountTotal(sc.nextDouble());
    }

    @Override
    public String convertToText() {
        return "c:" + numberOfConsecutiveDepositsMade + ":" + super.convertToText();
    }

    @Override
    public String toString() {
        return "Account type: Checking " +
                "Number of Consecutive Deposits Made: " + getNumberOfConsecutiveDepositsMade() + " " +
                super.toString();
    }
}
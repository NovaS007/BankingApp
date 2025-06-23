import java.util.Scanner;

public class SavingsAccount extends BankAccount{
    private static final double INTEREST_RATE = 0.20;
    private static final double WITHDRAWAL_PENALTY = 0.05;
    private final int NUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED = 10;
    private final int NUMBER_OF_CONSECUTIVE_WITHDRAWALS_REQUIRED = 3;
    private int numberOfConsecutiveDepositsMade;
    private int numberOfConsecutiveWithdrawalsMade;


    public SavingsAccount(Person initUser, int initAccountNumber, double initCurrentBalance,
                          double initWithdrawalLimit) {
        super(initUser, initAccountNumber, initCurrentBalance, initWithdrawalLimit);
        numberOfConsecutiveDepositsMade = 0;
        numberOfConsecutiveWithdrawalsMade = 0;
    }

    public int getNumberOfConsecutiveWithdrawalsMade() {
        return numberOfConsecutiveWithdrawalsMade;
    }

    public void setNumberOfConsecutiveWithdrawalsMade(int numberOfConsecutiveWithdrawalsMade) {
        if (numberOfConsecutiveWithdrawalsMade >= 0)
            this.numberOfConsecutiveWithdrawalsMade = numberOfConsecutiveWithdrawalsMade;
    }

    public int getNumberOfConsecutiveDepositsMade() {
        return numberOfConsecutiveDepositsMade;
    }

    public void setNumberOfConsecutiveDepositsMade(int numberOfConsecutiveDepositsMade) {
        if (numberOfConsecutiveDepositsMade >= 0)
            this.numberOfConsecutiveDepositsMade = numberOfConsecutiveDepositsMade;
    }

    public int getNUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED() {
        return NUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED;
    }

    public int getNUMBER_OF_CONSECUTIVE_WITHDRAWALS_REQUIRED() {
        return NUMBER_OF_CONSECUTIVE_WITHDRAWALS_REQUIRED;
    }

    public boolean deposit(double amount){
        boolean success = super.deposit(amount);
        if (success){
            numberOfConsecutiveWithdrawalsMade = 0;
            numberOfConsecutiveDepositsMade ++;
            if (numberOfConsecutiveDepositsMade % NUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED == 0){
                setCurrentBalance(getCurrentBalance() + (getCurrentBalance() * INTEREST_RATE));
            }
        }
        return success;
    }

    public boolean withdrawal(double amount){
        boolean success = super.withdrawal(amount);
        if (success){
            numberOfConsecutiveDepositsMade = 0;
            numberOfConsecutiveWithdrawalsMade ++;
            if (numberOfConsecutiveWithdrawalsMade % NUMBER_OF_CONSECUTIVE_WITHDRAWALS_REQUIRED == 0){
                setCurrentBalance(getCurrentBalance() - (getCurrentBalance() * WITHDRAWAL_PENALTY));
                if (getCurrentBalance() < 0){
                    setCurrentBalance(0);
                }
            }
        }
        return success;
    }

    @Override
    public void loadFromText(String textToLoad){
        Scanner sc = new Scanner(textToLoad);
        sc.useDelimiter(":");

        setNumberOfConsecutiveDepositsMade(sc.nextInt());

        setNumberOfConsecutiveWithdrawalsMade(sc.nextInt());

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
        return "s:" + numberOfConsecutiveDepositsMade + ":" +
                numberOfConsecutiveWithdrawalsMade + ":" + super.convertToText();
    }

    @Override
    public String toString() {
        return "Account type: Savings " +
                "Number of Consecutive Deposits Made: " + getNumberOfConsecutiveDepositsMade() + " " +
                "Number of Consecutive Withdrawals Made: " + getNumberOfConsecutiveWithdrawalsMade() + " " +
                "Number of Consecutive Deposits Required: " + getNUMBER_OF_CONSECUTIVE_DEPOSITS_REQUIRED() + " " +
                "Number of Consecutive Withdrawals Required: " + getNUMBER_OF_CONSECUTIVE_WITHDRAWALS_REQUIRED() + " " +
                super.toString();
    }
}
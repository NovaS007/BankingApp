import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class UpdatedBankApplication {
    public static void main(String[] args) {
        Person user = new Person("", "", "", ' ', 0, 0L);
        BankAccount currentAccount = null;
        Bank bank = new Bank(0);
        Scanner sc = new Scanner(System.in);
        DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

        String name;
        String birthday;
        String address;

        char gender;
        int age;
        long phoneNumber;
        double startingBalance;
        double setWithdrawalLimit;

        int mainMenuChoice = 0;
        final int CREATE_ACCOUNT = 1;
        final int DELETE_ACCOUNT = 2;
        final int CHANGE_ACTIVE_ACCOUNT = 3;
        final int ACCOUNT_OPTIONS = 4;
        final int DISPLAY_AVERAGE = 5;
        final int DISPLAY_MAX_AND_MIN = 6;
        final int DISPLAY_LOW_BALANCE = 7;
        final int QUIT = 8;
        final String PATH_TO_SAVE = "/Users/nathansmith/Desktop/HW5.txt";

        try {
            bank.load(PATH_TO_SAVE);
            System.out.println("Successfully found accounts");
        }

        catch (Exception e){
            System.out.println("No previous information was found");
        }

        if (bank.getBankAccounts().isEmpty()) {
            System.out.println("Do you want to open a checking or savings account?" +
                    " Type 'C' for checking and 'S' for savings");
            if (sc.next().toLowerCase().charAt(0) == 's') {
                currentAccount = new SavingsAccount(user, 0,0,0);
            }
            else if (sc.next().toLowerCase().charAt(0) == 'c') {
                currentAccount = new CheckingAccount(user, 0,0,0);
            }

            sc.nextLine();

            System.out.println("What is your name? ");
            name = sc.nextLine();
            currentAccount.getAccountHolder().setName(name);

            System.out.println("What is your birthday?");
            birthday = sc.nextLine();
            currentAccount.getAccountHolder().setBirthday(birthday);

            System.out.println("What is your address?");
            address = sc.nextLine();
            currentAccount.getAccountHolder().setAddress(address);

            System.out.println("What is your gender? Please only enter (m/f/n)");
            gender = sc.next().charAt(0);
            currentAccount.getAccountHolder().setGender(gender);
            sc.nextLine();

            System.out.println("What is your age?");
            age = sc.nextInt();
            currentAccount.getAccountHolder().setAge(age);
            sc.nextLine();

            System.out.println("What is your phone number? Please only enter numeric digits with no special characters" +
                    "as delimiters!");
            phoneNumber = sc.nextLong();
            currentAccount.getAccountHolder().setPhoneNumber(phoneNumber);
            sc.nextLine();

            System.out.println("What is your starting balance?");
            startingBalance = sc.nextDouble();
            currentAccount.setCurrentBalance(startingBalance);
            sc.nextLine();

            System.out.println("What would you like your withdrawal limit be?");
            setWithdrawalLimit = sc.nextDouble();
            currentAccount.setWithdrawalLimit(setWithdrawalLimit);
            sc.nextLine();

            bank.addAccount(currentAccount);

            System.out.println("Your account is successfully created " + currentAccount.getAccountHolder().getName() +
                    "! Your account number is " + currentAccount.getAccountNumber() + ".");
        }

        while (currentAccount == null) {
            System.out.println("Please log in with your account's number to get started!");
            int accountNumber = sc.nextInt();
            sc.nextLine();

            if (bank.findAccountByNumber(accountNumber) == null)
                System.out.println("No account was found with that number.");
            else {
                currentAccount = bank.findAccountByNumber(accountNumber);
            }
        }

        while (mainMenuChoice != QUIT) {
            System.out.println("Hello " + currentAccount.getAccountHolder().getName() + "!");
            System.out.println("Please choose an option below:");
            System.out.println("1: Create an account.");
            System.out.println("2: Delete an account.");
            System.out.println("3: Change active account.");
            System.out.println("4: Account Options.");
            System.out.println("5: Display average of all accounts.");
            System.out.println("6: Display maximum and minimum balance.");
            System.out.println("7: Display all accounts flagged as low.");
            System.out.println("8: Quit");

            mainMenuChoice = sc.nextInt();
            sc.nextLine();

            switch (mainMenuChoice) {
                case (CREATE_ACCOUNT):
                    Person newPerson = new Person("", "","",
                            'm',0,0L);
                    BankAccount newAccount = new BankAccount(user, 0,0,0);

                    System.out.println("Do you want to open a checking or savings account?" +
                            " Type 'C' for checking and 'S' for savings");
                    if (sc.next().toLowerCase().charAt(0) == 's') {
                        newAccount = new SavingsAccount(user, 0,0,0);
                    }

                    else if (sc.next().toLowerCase().charAt(0) == 'c') {
                        newAccount = new CheckingAccount(user, 0,0,0);
                    }
                    sc.nextLine();

                    System.out.println("What is your name?");
                    name = sc.nextLine();
                    newPerson.setName(name);

                    System.out.println("What is your birthday?");
                    birthday = sc.nextLine();
                    newPerson.setBirthday(birthday);

                    System.out.println("What is your address?");
                    address = sc.nextLine();
                    newPerson.setAddress(address);

                    System.out.println("What is your gender? ('m', 'f', or 'n')");
                    gender = sc.next().charAt(0);
                    sc.nextLine();
                    newPerson.setGender(gender);

                    System.out.println("What is your age?");
                    age = sc.nextInt();
                    sc.nextLine();
                    newPerson.setAge(age);

                    System.out.println("What is your phone number? (Use only digits; no special characters)");
                    phoneNumber = sc.nextLong();
                    sc.nextLine();
                    newPerson.setPhoneNumber(phoneNumber);

                    newAccount.setAccountHolder(newPerson);
                    System.out.println("What is your current balance?");
                    startingBalance = sc.nextDouble();
                    sc.nextLine();
                    newAccount.setCurrentBalance(startingBalance);

                    System.out.println("What would you like your withdrawal limit to be?");
                    setWithdrawalLimit = sc.nextDouble();
                    sc.nextLine();
                    newAccount.setWithdrawalLimit(setWithdrawalLimit);

                    bank.addAccount(newAccount);
                    System.out.println("Account successfully created! This account's number is " +
                            newAccount.getAccountNumber() + "!");

                    System.out.println("Would you like to log in as " + newAccount.getAccountHolder().getName() +
                            " (y/n)?");

                    char response = sc.next().charAt(0);
                    sc.nextLine();
                    if (response == 'y' || response == 'Y') {
                        currentAccount = newAccount;
                        System.out.println("You are now logged in as " + currentAccount.getAccountHolder().getName());
                    }
                    else
                        System.out.println("Remaining logged in as " + currentAccount.getAccountHolder().getName());
                    break;

                case (DELETE_ACCOUNT):
                    System.out.println("Please enter the bank account number you want to delete.");
                    int accountNumberToDelete = sc.nextInt();
                    sc.nextLine();

                    if (accountNumberToDelete == currentAccount.getAccountNumber()) {
                        System.out.println("Cannot delete an account you are currently logged in." +
                                " Please log out or switch accounts.");
                    }
                    else if (bank.deleteAccountByNumber(accountNumberToDelete))
                        System.out.println("Account successfully deleted!");

                    else
                        System.out.println("Account not found. Unable to delete. Please check your account number of" +
                                " the account you want to delete.");
                    break;

                case (CHANGE_ACTIVE_ACCOUNT):
                    System.out.println("Please enter the account number of the account you want to log in to.");
                    int accountNumber = sc.nextInt();
                    sc.nextLine();
                    BankAccount tempAccount = bank.findAccountByNumber(accountNumber);
                    if (tempAccount != null) {
                        currentAccount = tempAccount;
                        System.out.println("Successfully found account! Now logged in as "
                                + currentAccount.getAccountHolder().getName() + "!");
                        break;
                    }
                    else
                        System.out.println("Unable to find account with the account number " + accountNumber + ".");
                    break;

                case (ACCOUNT_OPTIONS):
                    int secondaryOptionChoice = 0;
                    final int WITHDRAWAL = 1;
                    final int DEPOSIT = 2;
                    final int DISPLAY_ACCOUNT_DETAILS = 3;
                    final int EDIT_ACCOUNT_INFO = 4;
                    final int SECONDARY_QUIT = 5;

                    while (secondaryOptionChoice != SECONDARY_QUIT) {
                        System.out.println("What would you like to do?");
                        System.out.println("1: Withdrawal.");
                        System.out.println("2: Deposit.");
                        System.out.println("3: Display account information.");
                        System.out.println("4: Edit Account Information.");
                        System.out.println("5: Quit");
                        secondaryOptionChoice = sc.nextInt();
                        sc.nextLine();

                        switch (secondaryOptionChoice) {
                            case (WITHDRAWAL):
                                System.out.println("How much money would you like to withdrawal?");
                                double amountToWithdrawal = sc.nextDouble();
                                if (currentAccount.getClass() == CheckingAccount.class && currentAccount.withdrawal(amountToWithdrawal)) {
                                    System.out.println("Successfully withdrew " + moneyFormat.format(amountToWithdrawal) + "!" +
                                            " Your current balance is now " + moneyFormat.format(currentAccount.getCurrentBalance()) + "." +
                                            " You are allowed to withdrawal up to " +
                                            moneyFormat.format(currentAccount.getWithdrawalLimit() - currentAccount.getCurrentWithdrawalAmountTotal()) + " more.");
                                } else if (currentAccount.getClass() == SavingsAccount.class && currentAccount.withdrawal(amountToWithdrawal)){
                                    System.out.println("Successfully withdrew " + moneyFormat.format(amountToWithdrawal) + "!" +
                                            " Your current balance is now " + moneyFormat.format(currentAccount.getCurrentBalance()) + "." +
                                            " You are allowed to withdrawal up to " +
                                            moneyFormat.format(currentAccount.getWithdrawalLimit() - currentAccount.getCurrentWithdrawalAmountTotal()) + " more.");
                                } else
                                    System.out.println("Unable to withdrawal " + moneyFormat.format(amountToWithdrawal) + ". Make " +
                                            "sure you are not exceeding your withdrawal limit. You are able to withdrawal" +
                                            " up to " + moneyFormat.format(currentAccount.getWithdrawalLimit() - currentAccount.getCurrentWithdrawalAmountTotal()) +
                                            " more.");
                                break;

                            case (DEPOSIT):
                                System.out.println("How much money would you like to deposit?");
                                double amountToDeposit = sc.nextDouble();
                                if (currentAccount.getClass() == CheckingAccount.class && currentAccount.deposit(amountToDeposit)) {
                                    System.out.println("Successfully deposited " + moneyFormat.format(amountToDeposit) + "! Your current " +
                                            "balance is now " + moneyFormat.format(currentAccount.getCurrentBalance()) + ".");
                                } else if (currentAccount.getClass() == SavingsAccount.class && currentAccount.deposit(amountToDeposit)) {
                                    System.out.println("Successfully deposited " + moneyFormat.format(amountToDeposit) + "! Your current " +
                                            "balance is now " + moneyFormat.format(currentAccount.getCurrentBalance()) + ".");
                                }
                                else
                                {
                                    System.out.println("An error has occurred. Please try again.");
                                }
                                break;

                            case (DISPLAY_ACCOUNT_DETAILS):
                                System.out.println(currentAccount);
                                break;

                            case (EDIT_ACCOUNT_INFO):
                                int tertiaryOption = 0;
                                final int CHANGE_NAME = 1;
                                final int CHANGE_ADDRESS = 2;
                                final int CHANGE_GENDER = 3;
                                final int CHANGE_PHONE_NUMBER = 4;
                                final int CHANGE_WITHDRAWAL_LIMIT = 5;
                                final int RESET_CUMULATIVE_AMOUNT = 6;
                                final int TERTIARY_QUIT = 7;

                                while (tertiaryOption != TERTIARY_QUIT) {
                                    System.out.println("What would you like to edit?");
                                    System.out.println("1: Change name.");
                                    System.out.println("2: Change your address.");
                                    System.out.println("3: Change gender.");
                                    System.out.println("4: Change phone number.");
                                    System.out.println("5: Change withdrawal limit.");
                                    System.out.println("6: Reset Current Cumulative Withdrawal Amount.");
                                    System.out.println("7: Quit.");
                                    tertiaryOption = sc.nextInt();
                                    sc.nextLine();

                                    switch (tertiaryOption) {
                                        case (CHANGE_NAME):
                                            System.out.println("What is your new name?");
                                            currentAccount.getAccountHolder().setName(sc.nextLine());
                                            break;

                                        case (CHANGE_ADDRESS):
                                            System.out.println("What is your new address? " +
                                                    "Please only enter digits, no special characters");
                                            currentAccount.getAccountHolder().setAddress(sc.nextLine());
                                            break;

                                        case (CHANGE_GENDER):
                                            System.out.println("What is your new gender? ('m'/'f'/'n')");
                                            currentAccount.getAccountHolder().setGender(sc.next().charAt(0));
                                            sc.nextLine();
                                            break;

                                        case (CHANGE_PHONE_NUMBER):
                                            System.out.println("What is your new phone number? (Please only enter digits" +
                                                    " and now special characters as delimiters.");
                                            currentAccount.getAccountHolder().setPhoneNumber(sc.nextLong());
                                            sc.nextLine();
                                            break;

                                        case (CHANGE_WITHDRAWAL_LIMIT):
                                            System.out.println("What would you like to be your new withdrawal limit?");
                                            currentAccount.setWithdrawalLimit(sc.nextDouble());
                                            sc.nextLine();
                                            break;

                                        case (RESET_CUMULATIVE_AMOUNT):
                                            currentAccount.resetWithdrawalAmount();
                                            sc.nextLine();
                                            break;

                                        case (TERTIARY_QUIT):
                                            sc.nextLine();
                                            break;

                                        default:
                                            System.out.println("Invalid option! Please only choose an option labeled " +
                                                    "1-6.");
                                    }
                                }
                                break;

                            case (SECONDARY_QUIT):
                                System.out.println("Returning to the main menu.");
                                break;

                            default:
                                System.out.println("Invalid choice! Please only choose an option from 1-5.");
                        }
                    }

                    break;

                case (DISPLAY_AVERAGE):
                    System.out.println("The average balance across all bank accounts is: " +
                            moneyFormat.format(bank.averageBalance()));
                    break;

                case (DISPLAY_MAX_AND_MIN):
                    double minimumBalance = bank.minBalance();
                    double maximumBalance = bank.maxBalance();
                    System.out.println("The minimum balance across all accounts is " + moneyFormat.format(minimumBalance) +
                            " and the maximum balance across all accounts is " + moneyFormat.format(maximumBalance));
                    break;

                case (DISPLAY_LOW_BALANCE):
                    System.out.println("What is the minimum balance threshold you want to check?");
                    bank.setLowMoneyThreshold(sc.nextDouble());
                    sc.nextLine();
                    ArrayList<BankAccount> lowAccounts = bank.findLowAccounts();

                    if (lowAccounts.isEmpty()){
                        System.out.println("No accounts are below the threshold of " +
                                moneyFormat.format(bank.getLowMoneyThreshold()) + ".");
                    }

                    else {
                        System.out.println("The following accounts are at or below the threshold of " +
                                moneyFormat.format(bank.getLowMoneyThreshold()) + ": \n");
                        for (BankAccount lowAccount : lowAccounts) {
                            System.out.println(lowAccount + "\n");
                        }
                    }
                    break;

                case (QUIT):
                    System.out.println("Thank you for using this application!");
                    try{
                        bank.save(PATH_TO_SAVE);
                    }
                    catch (Exception e) {
                        System.out.println("Unable to save file.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please only choose an option labeled 1-8.");
                    break;
            }
        }
    }
}
package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class BankAccount {

    private String email;
    private double balance;
    public static String legals = "1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM.-_@";

    /**
     * @param amount amount to be checked
     * @return false if amount is invalid
     * @return true if amount is valid
     * valid is defined as 0 or >= than 1 cent
     */
    public static boolean isAmountValid(double amount){
        double checker = amount * 1000;
        if (checker % 10 > 0 || amount < 0){
            return false;
        }
        else return true;
    }

    /**
     * @param  email input email
     * @param startingBalance input balance
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (isAmountValid(startingBalance)) {
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Input Balance must be 0 or >= $0.01: " + startingBalance + " is invalid, cannot create account");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException   if amount is negative
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount cannot be negative or contain divisions of the dollar finer than .01");
        }
        if(amount>balance){
            throw new InsufficientFundsException("Cannot withdraw more than available in account");
        }
        balance -= amount;

    }

    /**
     * @param amount
     * @post update balance to reflect deposited amount
     * @throws IllegalArgumentException if amount doesn't pass validity test as defined above
     */
    public void deposit(double amount) throws IllegalArgumentException{
        if (isAmountValid(amount)){
            balance += amount;
        }
        else throw new IllegalArgumentException("The amount you wish to deposit is illegal \n" +
                "Amount must be 0, positive, and of dollar divisions of cent or greater");
        return;
    }


    public static boolean isEmailValid(String email) {
        if (email.indexOf('@') == -1){ //Checks for '@'
            return false;
        }
        if (email.indexOf('.') == 0 || email.indexOf('.') == -1){ //checks for '.' but not at beginning
            return false;
        }
        for (int i=0; i<email.length(); i++){ //Checks for an illegal character
            if(legals.indexOf(email.charAt(i)) == -1){
                return false;
            }
        }
        for (int i=0; i<email.length(); i++){ //checks entire email for any occurrence of '..' which is illegal
            if(email.charAt(i) == '.' || email.charAt(i) == '_' || email.charAt(i) == '-'){
                if(email.charAt(i+1) == '.' || email.charAt(i) == '_' || email.charAt(i) == '-'){
                    return false;
                }
            }
        }
        String[] split = email.split("@");
        if (split.length > 2){
            return false;
        }
        String prefix  = split[0];
        if ((prefix.charAt(prefix.length() - 1) == '.')
                || (prefix.charAt(prefix.length() - 1) == '-')
                || (prefix.charAt(prefix.length() - 1) == '_')){ //checks that '.', '-', or '_' are not at end of prefix
            return false;
        }
        String backHalf = split[1];
        String[] newSplit = backHalf.split("\\.", 0);
        if (newSplit.length != 2){
            return false;
        }
        if (newSplit[0].length() < 1){
            return false;
        }
        if (newSplit[1].length() < 2){
            return false;
        }
        if (split[1].length() < 2){
            return false;
        }
        else {
            return true;
        }
    }
}


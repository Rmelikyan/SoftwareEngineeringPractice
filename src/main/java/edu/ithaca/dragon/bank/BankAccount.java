package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)) {
            this.email = email;
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
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
        if(amount<0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(amount>balance){
            throw new InsufficientFundsException("Cannot withdraw more than available in account");
        }
        balance -= amount;

    }


    public static boolean isEmailValid(String email) {
        if (email.indexOf('@') == -1){ //Checks for '@'
            return false;
        }
        if (email.indexOf('.') == 0 || email.indexOf('.') == -1){ //checks for '.' but not at beginning
            return false;
        }
        if (email.indexOf('#') != -1){ //Checks for an illegal character
            return false;
        }
        for (int i=0; i<email.length(); i++){ //checks entire email for any occurrence of '..' which is illegal
            if(email.charAt(i) == '.'){
                if(email.charAt(i+1) == '.'){
                    return false;
                }
            }
        }
        String[] split = email.split("@");
        if (split.length > 2){
            return false;
        }
        String prefix  = split[0];
        if (prefix.charAt(prefix.length()-1) == '.'||prefix.charAt(prefix.length()-1) == '-'||prefix.charAt(prefix.length()-1) == '_'){ //checks that '.', '-', or '_' are not at end of prefix
            return false;
        }
        String backHalf = split[1];
        String[] newSplit = backHalf.split("\\.", 0);
        if (newSplit.length > 2){
            return false;
        }
        if (newSplit[0].length() < 1){
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


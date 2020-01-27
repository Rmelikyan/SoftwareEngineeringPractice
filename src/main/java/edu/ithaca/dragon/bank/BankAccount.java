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
    public void withdraw(double amount) {
        balance -= amount;

    }


    public static boolean isEmailValid(String email) {
        if (email.indexOf('@') == -1)
            return false;

        ArrayList<String> illegals = new ArrayList<>();
        illegals.add("#");
        illegals.add("-");
        illegals.add("..");
        illegals.add("_");

        String firstHalfEmail=email.substring(0, email.indexOf('@'));

        for (int i = 0; i < illegals.size(); i++) {
            if(i==0){
                if(firstHalfEmail.charAt(0)=='.')
                    return false;
            }
            else if(i== illegals.size()-1){
                if(firstHalfEmail.charAt(firstHalfEmail.length()-1)=='.')
                    return false;
            }
            if (firstHalfEmail.contains(illegals.get(i)))
                return false;
        }

        if (!email.endsWith(".com"))
            return false;
        else
            return true;
        }
    }


package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance()); //Equivalence case

        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount3.getBalance()); //Border case
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(0);
        assertEquals(200, bankAccount.getBalance()); //Border withdraw zero

        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance()); //Equivalence standard withdrawal


        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-1e-16)); // Border neg withdrawal and sub cent amount
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-100)); // Equivalence neg withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(201)); //Border over withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(300)); //Equivalence over withdrawal
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(1e-16)); // Border sub cent amount
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(.001)); // Equivalence sub cent amount

    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.deposit(0); //Border zero deposited
        assertEquals(200,bankAccount.getBalance());

        bankAccount.deposit(.01); //Border minimum positive amount to deposit
        assertEquals(200.01,bankAccount.getBalance());

        bankAccount.deposit(.99); //Equivalence amount to deposit
        assertEquals(201,bankAccount.getBalance());

        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(.001)); //Border sub cent
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-.01)); //Border negative
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(1e-16)); //Equivalence Sub cent
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-100)); // Equivalence negative
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //Main Equivalence Test
        assertFalse( BankAccount.isEmailValid("")); //Border case of no @
        assertFalse( BankAccount.isEmailValid("nyc.kfc")); //Equivalence no @
        assertFalse(BankAccount.isEmailValid( "a@b.c")); //Border, Domain is less than 2 characters
        assertFalse(BankAccount.isEmailValid( "a..d@b.com")); //Border of '..'
        assertFalse(BankAccount.isEmailValid( "a.....d@b.com")); //Equivalence of '..'
        assertTrue(BankAccount.isEmailValid( "a.d.c@b.com")); //Border of many '.' in prefix "ok when not sequential"
        assertFalse(BankAccount.isEmailValid( "a.d#nay@b.com")); //Border of illegal character
        assertFalse(BankAccount.isEmailValid( "a.d###nay@b.com")); //Equivalence case
        assertFalse(BankAccount.isEmailValid( "a.dnay@b")); //Equivalence domain error
        assertFalse(BankAccount.isEmailValid( ".nay@b.com")); //Border leading '.'
        assertFalse(BankAccount.isEmailValid( "...groovy@b.com")); //equivalence multi leading '.'
        assertFalse(BankAccount.isEmailValid( "a-@b.com")); //border case of '-' directly before '@'
        assertFalse(BankAccount.isEmailValid( "a.@b.com")); //border case of '.' directly before '@'
        assertFalse(BankAccount.isEmailValid( "a_@b.com")); //border case of '_' directly before '@'


    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount b1 = new BankAccount("a@b.com", 200);
        BankAccount b2 = new BankAccount("c@d.com", 200);

        b1.transfer(b2, 100); //Border case of transfer from 1 to 2
        assertEquals(100, b1.getBalance());
        assertEquals(300, b2.getBalance());

        b2.transfer(b1, 100); //Equivalence of 2 to 1
        assertEquals(200, b1.getBalance());
        assertEquals(200, b2.getBalance());

        assertThrows(IllegalArgumentException.class, ()-> b1.transfer(b2, .001)); //border invalid amount
        assertEquals(200, b1.getBalance());
        assertEquals(200, b2.getBalance());

        assertThrows(IllegalArgumentException.class, ()-> b1.transfer(b2, -.01)); //border invalid amount
        assertEquals(200, b1.getBalance());
        assertEquals(200, b2.getBalance());

        assertThrows(InsufficientFundsException.class, ()-> b1.transfer(b2, 300)); //border over withdrawal in transfer
        assertEquals(200, b1.getBalance());
        assertEquals(200, b2.getBalance());

        assertThrows(IllegalArgumentException.class, ()-> b1.transfer(b1, 100)); //border self transfer illegal
        assertEquals(200, b1.getBalance());
        assertEquals(200, b2.getBalance());
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100)); //Border invalid email
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -.01)); //Border negative amount
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", .001)); //Border less than cent amount
    }

    @Test
    void isAmountValidTest(){

        assertTrue(BankAccount.isAmountValid(0)); //Border zero case
        assertTrue(BankAccount.isAmountValid(.01)); //Border minimum valid amount
        assertTrue(BankAccount.isAmountValid(1)); //Equivalence valid amount
        assertFalse(BankAccount.isAmountValid(.001)); //Border invalid amount
        assertFalse(BankAccount.isAmountValid(100.001)); //Equivalence invalid decimal amount
        assertFalse(BankAccount.isAmountValid(-.001)); //Border negative and invalid decimal
        assertFalse(BankAccount.isAmountValid(-.01)); //Border negative amount
        assertFalse(BankAccount.isAmountValid(-100)); //Equivalence negative
        assertFalse(BankAccount.isAmountValid(1e-16)); //Border verrry small number

    }


}
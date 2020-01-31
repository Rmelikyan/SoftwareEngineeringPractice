package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance()); //Equivalence case

        BankAccount bankAccount2 = new BankAccount("a@b.com", 1e-16);
        assertEquals(1e-16, bankAccount2.getBalance()); //Border case

        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount3.getBalance()); //Border case

        BankAccount bankAccount4 = new BankAccount("a@b.com", -1);
        assertEquals(-1, bankAccount4.getBalance()); //Border case //ToDo this shouldn't work
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(0);
        assertEquals(200, bankAccount.getBalance()); //Border withdraw zero

        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance()); //Equivalence standard withdrawal

        bankAccount.withdraw(1e-16);
        assertEquals(100-1e-16, bankAccount.getBalance()); //border standard withdrawal

        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-1e-16)); // Border neg withdrawal
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-100)); // Equivalence neg withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(200+1e-16)); //Border over withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(300)); //Equivalence over withdrawal
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
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    void isAmountValidTest() {

        assertEquals(true, BankAccount.isAmountValid(0)); //Border zero case
        assertEquals(true, BankAccount.isAmountValid(.01)); //Border minimum valid amount
        assertEquals(true, BankAccount.isAmountValid(1)); //Equivalence valid amount
        assertEquals(false, BankAccount.isAmountValid(.001)); //Border invalid amount
        assertEquals(false, BankAccount.isAmountValid(100.001)); //Equivalence invalid decimal amount
        assertEquals(false, BankAccount.isAmountValid(-.001)); //Border negative and invalid decimal
        assertEquals(false, BankAccount.isAmountValid(-.01)); //Border negative amount
        assertEquals(false, BankAccount.isAmountValid(-100)); //Equivalence negative
        assertEquals(false, BankAccount.isAmountValid(1e-16)); //Border verrry small number

    }


}
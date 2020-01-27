package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() {
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


    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}
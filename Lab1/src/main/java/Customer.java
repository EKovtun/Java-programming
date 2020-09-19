public class Customer {
    private final String name;
    private final String lastName;
    private Account account;

    public Customer(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Opens account for a customer (creates Account and sets it to field "account").
     * Customer can't have greater than one opened account.
     *
     * @param accountId id of the account
     * @return true if account hasn't already created, otherwise returns false and prints "Customer fullName() already has the active account"
     */
    public boolean openAccount(long accountId) {
        // write your code here
    }

    /**
     * Closes account. Sets account to null.
     *
     * @return false if account is already null and prints "Customer fullName() has no active account to close", otherwise sets account to null and returns true
     */
    public boolean closeAccount() {
        // write your code here
    }

    /**
     * Formatted full name of the customer
     * @return concatenated form of name and lastName, e.g. "John Goodman"
     */
    public String fullName() {
        // write your code here
    }

    /**
     * Delegates withdraw to Account class
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's withdraw method
     */
    public boolean withdrawFromCurrentAccount(double amount) {
        // write your code here
    }

    /**
     * Delegates adding money to Account class
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's add method
     */
    public boolean addMoneyToCurrentAccount(double amount) {
        // write your code here
    }
}
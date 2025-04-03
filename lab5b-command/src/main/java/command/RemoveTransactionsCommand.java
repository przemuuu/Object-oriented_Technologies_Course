package command;

import model.Account;
import model.Transaction;

public class RemoveTransactionsCommand implements Command {
    private final Transaction transactionToRemove;
    private final Account account;

    public RemoveTransactionsCommand(Transaction transactionToRemove, Account account) {
        this.transactionToRemove = transactionToRemove;
        this.account = account;
    }

    @Override
    public String getName() {
        return "Removed transaction: " + transactionToRemove.toString();
    }

    @Override
    public void execute() {
        account.removeTransaction(transactionToRemove);
    }

    @Override
    public void undo() {
        account.addTransaction(transactionToRemove);
    }

    @Override
    public void redo() {
        account.removeTransaction(transactionToRemove);
    }
}

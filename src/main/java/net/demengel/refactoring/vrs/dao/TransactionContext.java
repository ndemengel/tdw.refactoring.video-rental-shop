package net.demengel.refactoring.vrs.dao;

import net.demengel.refactoring.vrs.xxx.FakeTransactionContext;

/**
 * Manages transactions. For the needs of this exercise, its implementation is delegated to an object simulating a real transactionf.
 */
public abstract class TransactionContext {

    private static final TransactionContext INSTANCE = new FakeTransactionContext();

    public static TransactionContext getInstance() {
        return INSTANCE;
    }

    protected TransactionContext() {
    }

    public abstract Transaction newTransaction();

    public abstract void commit(Transaction pTx);

    public abstract void rollback(Transaction pTx);
}

package net.demengel.refactoring.vrs.xxx;

import net.demengel.refactoring.vrs.dao.Transaction;
import net.demengel.refactoring.vrs.dao.TransactionContext;

public class FakeTransactionContext extends TransactionContext {

    @Override
    public Transaction newTransaction() {
        return new FakeTransaction();
    }

    @Override
    public void commit(Transaction pTx) {
        ((FakeTransaction) pTx).commit();
    }

    @Override
    public void rollback(Transaction pTx) {
        ((FakeTransaction) pTx).rollback();
    }
}

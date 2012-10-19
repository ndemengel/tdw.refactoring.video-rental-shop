package net.demengel.refactoring.vrs.xxx;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import net.demengel.refactoring.vrs.dao.Transaction;

class FakeTransaction implements Transaction {

    private List<Runnable> queries = newArrayList();

    void addQuery(Runnable query) {
        queries.add(query);
    }

    public void commit() {
        for (Runnable query : queries) {
            query.run();
        }
        queries.clear();
    }

    public void rollback() {
        queries.clear();
    }
}

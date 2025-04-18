package sqlancer.base.common.ast.newast;

import sqlancer.base.common.schema.AbstractTable;

public class TableReferenceNode<E, T extends AbstractTable<?, ?, ?>> {

    private final T t;

    public TableReferenceNode(T table) {
        this.t = table;
    }

    public T getTable() {
        return t;
    }

}

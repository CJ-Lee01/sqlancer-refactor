package sqlancer.base.common.ast.newast;

import sqlancer.base.common.schema.AbstractTable;
import sqlancer.base.common.schema.AbstractTableColumn;

public interface Join<E extends Expression<C>, T extends AbstractTable<C, ?, ?>, C extends AbstractTableColumn<?, ?>>
        extends Expression<C> {

    void setOnClause(E onClause);
}

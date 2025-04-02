package sqlancer.base.common.gen;

import java.util.List;

import sqlancer.base.common.ast.newast.Expression;
import sqlancer.base.common.ast.newast.Join;
import sqlancer.base.common.ast.newast.Select;
import sqlancer.base.common.schema.AbstractTable;
import sqlancer.base.common.schema.AbstractTableColumn;
import sqlancer.base.common.schema.AbstractTables;

public interface CERTGenerator<S extends Select<J, E, T, C>, J extends Join<E, T, C>, E extends Expression<C>, T extends AbstractTable<C, ?, ?>, C extends AbstractTableColumn<?, ?>> {

    CERTGenerator<S, J, E, T, C> setTablesAndColumns(AbstractTables<T, C> tables);

    E generateBooleanExpression();

    S generateSelect();

    List<J> getRandomJoinClauses();

    List<E> getTableRefs();

    List<E> generateFetchColumns(boolean shouldCreateDummy);

    String generateExplainQuery(S select);

    boolean mutate(S select);
}

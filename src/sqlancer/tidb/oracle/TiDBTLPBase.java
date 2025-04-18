package sqlancer.tidb.oracle;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.base.Randomly;
import sqlancer.base.common.gen.ExpressionGenerator;
import sqlancer.base.common.oracle.TernaryLogicPartitioningOracleBase;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.tidb.TiDBErrors;
import sqlancer.tidb.TiDBExpressionGenerator;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;
import sqlancer.tidb.TiDBSchema;
import sqlancer.tidb.TiDBSchema.TiDBTable;
import sqlancer.tidb.TiDBSchema.TiDBTables;
import sqlancer.tidb.ast.TiDBColumnReference;
import sqlancer.tidb.ast.TiDBExpression;
import sqlancer.tidb.ast.TiDBJoin;
import sqlancer.tidb.ast.TiDBSelect;
import sqlancer.tidb.ast.TiDBTableReference;
import sqlancer.tidb.gen.TiDBHintGenerator;

public abstract class TiDBTLPBase extends TernaryLogicPartitioningOracleBase<TiDBExpression, TiDBGlobalState>
        implements TestOracle<TiDBGlobalState> {

    TiDBSchema s;
    TiDBTables targetTables;
    TiDBExpressionGenerator gen;
    TiDBSelect select;

    public TiDBTLPBase(TiDBGlobalState state) {
        super(state);
        TiDBErrors.addExpressionErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        s = state.getSchema();
        targetTables = s.getRandomTableNonEmptyTables();
        gen = new TiDBExpressionGenerator(state).setColumns(targetTables.getColumns());
        initializeTernaryPredicateVariants();
        select = new TiDBSelect();
        select.setFetchColumns(generateFetchColumns());
        List<TiDBTable> tables = targetTables.getTables();
        if (Randomly.getBoolean()) {
            TiDBHintGenerator.generateHints(select, tables);
        }

        List<TiDBExpression> tableList = tables.stream().map(t -> new TiDBTableReference(t))
                .collect(Collectors.toList());
        List<TiDBExpression> joins = TiDBJoin.getJoins(tableList, state).stream().collect(Collectors.toList());
        select.setJoinList(joins);
        select.setFromList(tableList);
        select.setWhereClause(null);
    }

    List<TiDBExpression> generateFetchColumns() {
        return Arrays.asList(new TiDBColumnReference(targetTables.getColumns().get(0)));
    }

    @Override
    protected ExpressionGenerator<TiDBExpression> getGen() {
        return gen;
    }

}

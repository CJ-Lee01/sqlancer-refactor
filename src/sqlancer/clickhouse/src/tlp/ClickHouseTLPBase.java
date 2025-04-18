package src.tlp;

import static java.lang.Math.min;
import static java.util.stream.IntStream.range;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.base.ComparatorHelper;
import sqlancer.base.Randomly;
import src.ClickHouseErrors;
import src.ClickHouseProvider.ClickHouseGlobalState;
import src.ClickHouseSchema;
import src.ClickHouseSchema.ClickHouseTable;
import src.ClickHouseVisitor;
import src.ast.ClickHouseColumnReference;
import src.ast.ClickHouseExpression;
import src.ast.ClickHouseExpression.ClickHouseJoin;
import src.ast.ClickHouseSelect;
import src.ast.ClickHouseTableReference;
import src.gen.ClickHouseExpressionGenerator;
import sqlancer.base.common.gen.ExpressionGenerator;
import sqlancer.base.common.oracle.TernaryLogicPartitioningOracleBase;
import sqlancer.base.common.oracle.TestOracle;

public class ClickHouseTLPBase extends TernaryLogicPartitioningOracleBase<ClickHouseExpression, ClickHouseGlobalState>
        implements TestOracle<ClickHouseGlobalState> {

    ClickHouseSchema schema;
    List<ClickHouseColumnReference> columns;
    ClickHouseExpressionGenerator gen;
    ClickHouseSelect select;

    public ClickHouseTLPBase(ClickHouseGlobalState state) {
        super(state);
        ClickHouseErrors.addExpectedExpressionErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        gen = new ClickHouseExpressionGenerator(state);
        schema = state.getSchema();
        select = new ClickHouseSelect();
        List<ClickHouseTable> tables = schema.getRandomTableNonEmptyTables().getTables();
        ClickHouseTableReference table = new ClickHouseTableReference(
                tables.get((int) Randomly.getNotCachedInteger(0, tables.size())),
                Randomly.getBoolean() ? "left" : null);
        select.setFromClause(table);
        columns = table.getColumnReferences();

        if (state.getClickHouseOptions().testJoins && Randomly.getBoolean()) {
            List<ClickHouseJoin> joinStatements = gen.getRandomJoinClauses(table, tables);
            columns.addAll(joinStatements.stream().flatMap(j -> j.getRightTable().getColumnReferences().stream())
                    .collect(Collectors.toList()));
            select.setJoinClauses(joinStatements);
        }
        gen.addColumns(columns);
        int small = Randomly.smallNumber();
        List<ClickHouseExpression> from = range(0, 1 + small)
                .mapToObj(i -> gen.generateExpressionWithColumns(columns, 5)).collect(Collectors.toList());
        select.setFetchColumns(from);
        select.setWhereClause(null);
        initializeTernaryPredicateVariants();
        // Smoke check
        String query = ClickHouseVisitor.asString(select);
        ComparatorHelper.getResultSetFirstColumnAsString(query, errors, state);
    }

    List<ClickHouseExpression> generateFetchColumns(List<ClickHouseColumnReference> columns) {
        List<ClickHouseColumnReference> list = Randomly.extractNrRandomColumns(columns,
                min(1 + Randomly.smallNumber(), columns.size()));
        return list.stream().map(c -> (ClickHouseExpression) c).collect(Collectors.toList());
    }

    @Override
    protected ExpressionGenerator<ClickHouseExpression> getGen() {
        return gen;
    }

}

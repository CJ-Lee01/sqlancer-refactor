package sqlancer.duckdb.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.base.ComparatorHelper;
import sqlancer.base.Randomly;
import sqlancer.duckdb.DuckDBErrors;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;
import sqlancer.duckdb.DuckDBToStringVisitor;
import sqlancer.duckdb.ast.DuckDBColumnReference;
import sqlancer.duckdb.ast.DuckDBExpression;

public class DuckDBQueryPartitioningGroupByTester extends DuckDBQueryPartitioningBase {

    public DuckDBQueryPartitioningGroupByTester(DuckDBGlobalState state) {
        super(state);
        DuckDBErrors.addGroupByErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        super.check();
        select.setGroupByExpressions(select.getFetchColumns());
        select.setWhereClause(null);
        String originalQueryString = DuckDBToStringVisitor.asString(select);

        List<String> resultSet = ComparatorHelper.getResultSetFirstColumnAsString(originalQueryString, errors, state);

        select.setWhereClause(predicate);
        String firstQueryString = DuckDBToStringVisitor.asString(select);
        select.setWhereClause(negatedPredicate);
        String secondQueryString = DuckDBToStringVisitor.asString(select);
        select.setWhereClause(isNullPredicate);
        String thirdQueryString = DuckDBToStringVisitor.asString(select);
        List<String> combinedString = new ArrayList<>();
        List<String> secondResultSet = ComparatorHelper.getCombinedResultSetNoDuplicates(firstQueryString,
                secondQueryString, thirdQueryString, combinedString, true, state, errors);
        ComparatorHelper.assumeResultSetsAreEqual(resultSet, secondResultSet, originalQueryString, combinedString,
                state, ComparatorHelper::canonicalizeResultValue);
    }

    @Override
    List<DuckDBExpression> generateFetchColumns() {
        return Randomly.nonEmptySubset(targetTables.getColumns()).stream().map(c -> new DuckDBColumnReference(c))
                .collect(Collectors.toList());
    }

}

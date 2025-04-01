package sqlancer.duckdb.ast;

import sqlancer.base.common.ast.newast.NewOrderingTerm;

public class DuckDBOrderingTerm extends NewOrderingTerm<DuckDBExpression> implements DuckDBExpression {
    public DuckDBOrderingTerm(DuckDBExpression expr, Ordering ordering) {
        super(expr, ordering);
    }
}

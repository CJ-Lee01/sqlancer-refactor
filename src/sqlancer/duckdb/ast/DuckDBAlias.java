package sqlancer.duckdb.ast;

import sqlancer.base.common.ast.newast.NewAliasNode;

public class DuckDBAlias extends NewAliasNode<DuckDBExpression> implements DuckDBExpression {
    public DuckDBAlias(DuckDBExpression expr, String string) {
        super(expr, string);
    }
}

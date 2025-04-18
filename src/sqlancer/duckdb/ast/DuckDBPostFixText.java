package sqlancer.duckdb.ast;

import sqlancer.base.common.ast.newast.NewPostfixTextNode;

public class DuckDBPostFixText extends NewPostfixTextNode<DuckDBExpression> implements DuckDBExpression {
    public DuckDBPostFixText(DuckDBExpression expr, String string) {
        super(expr, string);
    }
}

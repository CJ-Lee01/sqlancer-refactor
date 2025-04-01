package sqlancer.duckdb.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;

public class DuckDBUnaryPostfixOperator extends NewUnaryPostfixOperatorNode<DuckDBExpression>
        implements DuckDBExpression {
    public DuckDBUnaryPostfixOperator(DuckDBExpression expr, BinaryOperatorNode.Operator op) {
        super(expr, op);
    }
}

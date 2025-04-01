package sqlancer.duckdb.ast;

import sqlancer.base.common.ast.BinaryOperatorNode.Operator;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class DuckDBBinaryOperator extends NewBinaryOperatorNode<DuckDBExpression> implements DuckDBExpression {
    public DuckDBBinaryOperator(DuckDBExpression left, DuckDBExpression right, Operator op) {
        super(left, right, op);
    }
}

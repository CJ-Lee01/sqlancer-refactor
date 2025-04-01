package sqlancer.yugabyte.ycql.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;

public class YCQLUnaryPostfixOperation extends NewUnaryPostfixOperatorNode<YCQLExpression> implements YCQLExpression {
    public YCQLUnaryPostfixOperation(YCQLExpression expr, BinaryOperatorNode.Operator op) {
        super(expr, op);
    }
}

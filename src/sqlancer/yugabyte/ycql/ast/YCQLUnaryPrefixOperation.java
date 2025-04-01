package sqlancer.yugabyte.ycql.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPrefixOperatorNode;

public class YCQLUnaryPrefixOperation extends NewUnaryPrefixOperatorNode<YCQLExpression> implements YCQLExpression {
    public YCQLUnaryPrefixOperation(YCQLExpression expr, BinaryOperatorNode.Operator operator) {
        super(expr, operator);
    }
}

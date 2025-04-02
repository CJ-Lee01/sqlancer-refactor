package sqlancer.yugabyte.ycql.ast;

import sqlancer.base.common.ast.BinaryOperatorNode.Operator;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class YCQLBinaryOperation extends NewBinaryOperatorNode<YCQLExpression> implements YCQLExpression {
    public YCQLBinaryOperation(YCQLExpression left, YCQLExpression right, Operator op) {
        super(left, right, op);
    }
}

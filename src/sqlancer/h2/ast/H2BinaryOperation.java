package sqlancer.h2.ast;

import sqlancer.base.common.ast.BinaryOperatorNode.Operator;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class H2BinaryOperation extends NewBinaryOperatorNode<H2Expression> implements H2Expression {
    public H2BinaryOperation(H2Expression left, H2Expression right, Operator op) {
        super(left, right, op);
    }
}

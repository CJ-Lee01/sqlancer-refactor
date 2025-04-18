package sqlancer.h2.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPrefixOperatorNode;

public class H2UnaryPrefixOperation extends NewUnaryPrefixOperatorNode<H2Expression> implements H2Expression {
    public H2UnaryPrefixOperation(H2Expression expr, BinaryOperatorNode.Operator operator) {
        super(expr, operator);
    }
}

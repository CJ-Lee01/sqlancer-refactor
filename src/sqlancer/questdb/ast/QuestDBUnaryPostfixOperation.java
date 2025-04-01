package sqlancer.questdb.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;

public class QuestDBUnaryPostfixOperation extends NewUnaryPostfixOperatorNode<QuestDBExpression>
        implements QuestDBExpression {
    public QuestDBUnaryPostfixOperation(QuestDBExpression expr, BinaryOperatorNode.Operator op) {
        super(expr, op);
    }
}

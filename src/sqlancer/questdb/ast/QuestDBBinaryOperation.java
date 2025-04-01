package sqlancer.questdb.ast;

import sqlancer.base.common.ast.BinaryOperatorNode.Operator;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class QuestDBBinaryOperation extends NewBinaryOperatorNode<QuestDBExpression> implements QuestDBExpression {
    public QuestDBBinaryOperation(QuestDBExpression left, QuestDBExpression right, Operator op) {
        super(left, right, op);
    }
}

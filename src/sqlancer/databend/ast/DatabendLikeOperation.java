package sqlancer.databend.ast;

import sqlancer.base.LikeImplementationHelper;
import sqlancer.base.Randomly;
import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;
import sqlancer.databend.DatabendSchema.DatabendDataType;

public class DatabendLikeOperation extends NewBinaryOperatorNode<DatabendExpression> implements DatabendExpression {

    public DatabendLikeOperation(DatabendExpression left, DatabendExpression right, DatabendLikeOperator op) {
        super(left, right, op);
    }

    @Override
    public DatabendDataType getExpectedType() {
        return DatabendDataType.BOOLEAN;
    }

    public DatabendExpression getLeftExpr() {
        return super.getLeft();
    }

    public DatabendExpression getRightExpr() {
        return super.getRight();
    }

    public DatabendLikeOperator getOp() {
        return (DatabendLikeOperator) op;
    }

    @Override
    public DatabendConstant getExpectedValue() {
        DatabendConstant leftVal = getLeftExpr().getExpectedValue();
        DatabendConstant rightVal = getRightExpr().getExpectedValue();
        if (leftVal == null || rightVal == null) {
            return null;
        }
        if (leftVal.isNull() || rightVal.isNull()) {
            return DatabendConstant.createNullConstant();
        } else {
            boolean result = LikeImplementationHelper.match(leftVal.asString(), rightVal.asString(), 0, 0, true);
            return DatabendConstant.createBooleanConstant(result);
        }
    }

    public enum DatabendLikeOperator implements BinaryOperatorNode.Operator {
        LIKE_OPERATOR("LIKE", "like");

        private final String[] textRepresentations;

        DatabendLikeOperator(String... text) {
            textRepresentations = text.clone();
        }

        @Override
        public String getTextRepresentation() {
            return Randomly.fromOptions(textRepresentations);
        }
    }

}

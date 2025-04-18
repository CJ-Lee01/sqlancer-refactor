package sqlancer.doris.ast;

import sqlancer.base.Randomly;
import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;
import sqlancer.doris.DorisSchema.DorisDataType;

public class DorisUnaryPostfixOperation extends NewUnaryPostfixOperatorNode<DorisExpression>
        implements DorisExpression {

    public DorisUnaryPostfixOperation(DorisExpression expr, DorisUnaryPostfixOperator op) {
        super(expr, op);
    }

    public DorisExpression getExpression() {
        return getExpr();
    }

    public DorisUnaryPostfixOperator getOp() {
        return (DorisUnaryPostfixOperator) op;
    }

    @Override
    public DorisDataType getExpectedType() {
        return DorisDataType.BOOLEAN;
    }

    @Override
    public DorisConstant getExpectedValue() {
        DorisConstant expectedValue = getExpression().getExpectedValue();
        if (expectedValue == null) {
            return null;
        }
        return getOp().apply(expectedValue);
    }

    public enum DorisUnaryPostfixOperator implements BinaryOperatorNode.Operator {
        IS_NULL("IS NULL") {
            @Override
            public DorisDataType[] getInputDataTypes() {
                return DorisDataType.values();
            }

            @Override
            public DorisConstant apply(DorisConstant value) {
                return DorisConstant.createBooleanConstant(value.isNull());
            }
        },
        IS_NOT_NULL("IS NOT NULL") {
            @Override
            public DorisDataType[] getInputDataTypes() {
                return DorisDataType.values();
            }

            @Override
            public DorisConstant apply(DorisConstant value) {
                return DorisConstant.createBooleanConstant(!value.isNull());
            }
        };

        private final String textRepresentations;

        DorisUnaryPostfixOperator(String text) {
            this.textRepresentations = text;
        }

        public static DorisUnaryPostfixOperator getRandom() {
            return Randomly.fromOptions(values());
        }

        @Override
        public String getTextRepresentation() {
            return textRepresentations;
        }

        public abstract DorisDataType[] getInputDataTypes();

        public abstract DorisConstant apply(DorisConstant value);
    }

    @Override
    public String getOperatorRepresentation() {
        return this.op.getTextRepresentation();
    }

}

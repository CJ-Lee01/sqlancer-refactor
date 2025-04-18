package sqlancer.hsqldb.ast;

import sqlancer.base.Randomly;
import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;
import sqlancer.hsqldb.HSQLDBSchema;

public class HSQLDBUnaryPostfixOperation extends NewUnaryPostfixOperatorNode<HSQLDBExpression>
        implements HSQLDBExpression {

    public HSQLDBUnaryPostfixOperation(HSQLDBExpression expr, HSQLDBUnaryPostfixOperator op) {
        super(expr, op);
    }

    public enum HSQLDBUnaryPostfixOperator implements BinaryOperatorNode.Operator {
        IS_NULL("IS NULL") {
            @Override
            public HSQLDBSchema.HSQLDBDataType[] getInputDataTypes() {
                return HSQLDBSchema.HSQLDBDataType.values();
            }
        },
        IS_NOT_NULL("IS NOT NULL") {
            @Override
            public HSQLDBSchema.HSQLDBDataType[] getInputDataTypes() {
                return HSQLDBSchema.HSQLDBDataType.values();
            }
        };

        private final String textRepresentations;

        HSQLDBUnaryPostfixOperator(String text) {
            this.textRepresentations = text;
        }

        public static HSQLDBUnaryPostfixOperator getRandom() {
            return Randomly.fromOptions(values());
        }

        @Override
        public String getTextRepresentation() {
            return textRepresentations;
        }

        public abstract HSQLDBSchema.HSQLDBDataType[] getInputDataTypes();

    }

    public HSQLDBExpression getExpression() {
        return getExpr();
    }

    @Override
    public String getOperatorRepresentation() {
        return this.op.getTextRepresentation();
    }

}

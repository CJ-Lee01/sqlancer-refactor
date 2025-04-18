package sqlancer.hsqldb.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPrefixOperatorNode;
import sqlancer.hsqldb.HSQLDBSchema;

public class HSQLDBUnaryPrefixOperation extends NewUnaryPrefixOperatorNode<HSQLDBExpression>
        implements HSQLDBExpression {

    public HSQLDBUnaryPrefixOperation(HSQLDBUnaryPrefixOperator operation, HSQLDBExpression expression) {
        super(expression, operation);
    }

    @Override
    public String getOperatorRepresentation() {
        return this.op.getTextRepresentation();
    }

    public enum HSQLDBUnaryPrefixOperator implements BinaryOperatorNode.Operator {
        NOT("NOT", HSQLDBSchema.HSQLDBDataType.BOOLEAN, HSQLDBSchema.HSQLDBDataType.INTEGER) {
            @Override
            public HSQLDBSchema.HSQLDBDataType getExpressionType() {
                return HSQLDBSchema.HSQLDBDataType.BOOLEAN;
            }
        },

        UNARY_PLUS("+", HSQLDBSchema.HSQLDBDataType.INTEGER) {
            @Override
            public HSQLDBSchema.HSQLDBDataType getExpressionType() {
                return HSQLDBSchema.HSQLDBDataType.INTEGER;
            }
        },
        UNARY_MINUS("-", HSQLDBSchema.HSQLDBDataType.INTEGER) {
            @Override
            public HSQLDBSchema.HSQLDBDataType getExpressionType() {
                return HSQLDBSchema.HSQLDBDataType.INTEGER;
            }
        };

        private String textRepresentation;

        HSQLDBUnaryPrefixOperator(String textRepresentation, HSQLDBSchema.HSQLDBDataType... dataTypes) {
            this.textRepresentation = textRepresentation;
        }

        public abstract HSQLDBSchema.HSQLDBDataType getExpressionType();

        @Override
        public String getTextRepresentation() {
            return this.textRepresentation;
        }
    }

}

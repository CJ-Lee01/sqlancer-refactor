package sqlancer.databend.gen;

import sqlancer.base.Randomly;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.databend.DatabendErrors;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.DatabendSchema.DatabendDataType;
import sqlancer.databend.DatabendToStringVisitor;

public final class DatabendDeleteGenerator {

    private DatabendDeleteGenerator() {
    }

    public static SQLQueryAdapter generate(DatabendGlobalState globalState) {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        ExpectedErrors errors = new ExpectedErrors();
        sb.append(globalState.getSchema().getRandomTable(t -> !t.isView()).getName());
        if (Randomly.getBoolean()) {
            sb.append(" WHERE ");
            sb.append(DatabendToStringVisitor.asString(
                    new DatabendNewExpressionGenerator(globalState).generateExpression(DatabendDataType.BOOLEAN)));
            DatabendErrors.addExpressionErrors(errors);
        }
        return new SQLQueryAdapter(sb.toString(), errors);
    }

}

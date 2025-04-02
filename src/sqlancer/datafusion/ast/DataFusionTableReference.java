package sqlancer.datafusion.ast;

import sqlancer.base.common.ast.newast.TableReferenceNode;
import sqlancer.datafusion.DataFusionSchema;

public class DataFusionTableReference extends TableReferenceNode<DataFusionExpression, DataFusionSchema.DataFusionTable>
        implements DataFusionExpression {
    public DataFusionTableReference(DataFusionSchema.DataFusionTable table) {
        super(table);
    }
}

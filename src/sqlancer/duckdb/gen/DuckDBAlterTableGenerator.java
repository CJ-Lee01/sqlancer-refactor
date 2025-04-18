package sqlancer.duckdb.gen;

import sqlancer.base.Randomly;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.duckdb.DuckDBErrors;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;
import sqlancer.duckdb.DuckDBSchema.DuckDBCompositeDataType;
import sqlancer.duckdb.DuckDBSchema.DuckDBTable;
import sqlancer.duckdb.DuckDBToStringVisitor;

public final class DuckDBAlterTableGenerator {

    private DuckDBAlterTableGenerator() {
    }

    enum Action {
        ADD_COLUMN, ALTER_COLUMN, DROP_COLUMN
    }

    public static SQLQueryAdapter getQuery(DuckDBGlobalState globalState) {
        ExpectedErrors errors = new ExpectedErrors();
        errors.add(" does not have a column with name \"rowid\"");
        errors.add("Table does not contain column rowid referenced in alter statement");
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        DuckDBTable table = globalState.getSchema().getRandomTable(t -> !t.isView());
        DuckDBExpressionGenerator gen = new DuckDBExpressionGenerator(globalState).setColumns(table.getColumns());
        sb.append(table.getName());
        sb.append(" ");
        Action action = Randomly.fromOptions(Action.values());
        switch (action) {
        case ADD_COLUMN:
            sb.append("ADD COLUMN ");
            String columnName = table.getFreeColumnName();
            sb.append(columnName);
            sb.append(" ");
            sb.append(DuckDBCompositeDataType.getRandomWithoutNull().toString());
            break;
        case ALTER_COLUMN:
            sb.append("ALTER COLUMN ");
            sb.append(table.getRandomColumn().getName());
            sb.append(" SET DATA TYPE ");
            sb.append(DuckDBCompositeDataType.getRandomWithoutNull().toString());
            if (Randomly.getBoolean()) {
                sb.append(" USING ");
                DuckDBErrors.addExpressionErrors(errors);
                sb.append(DuckDBToStringVisitor.asString(gen.generateExpression()));
            }
            errors.add("Cannot change the type of this column: an index depends on it!");
            errors.add("Cannot change the type of a column that has a UNIQUE or PRIMARY KEY constraint specified");
            errors.add("Unimplemented type for cast");
            errors.add("Conversion:");
            errors.add("Cannot change the type of a column that has a CHECK constraint specified");
            break;
        case DROP_COLUMN:
            sb.append("DROP COLUMN ");
            sb.append(table.getRandomColumn().getName());
            errors.add("named in key does not exist"); // TODO
            errors.add("Cannot drop this column:");
            errors.add("Cannot drop column: table only has one column remaining!");
            errors.add("because there is a CHECK constraint that depends on it");
            errors.add("because there is a UNIQUE constraint that depends on it");
            break;
        default:
            throw new AssertionError(action);
        }
        return new SQLQueryAdapter(sb.toString(), errors, true);
    }

}

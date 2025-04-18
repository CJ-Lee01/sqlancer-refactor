package sqlancer.cockroachdb.gen;

import sqlancer.base.Randomly;
import sqlancer.cockroachdb.CockroachDBBugs;
import sqlancer.cockroachdb.CockroachDBProvider.CockroachDBGlobalState;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;

public final class CockroachDBTruncateGenerator {

    private CockroachDBTruncateGenerator() {
    }

    // https://www.cockroachlabs.com/docs/v19.2/truncate.html
    public static SQLQueryAdapter truncate(CockroachDBGlobalState globalState) {
        ExpectedErrors errors = new ExpectedErrors();
        errors.add("is referenced by foreign key");
        if (CockroachDBBugs.bug85230) {
            errors.add("found in depended-on-by references, no such index in this relation");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("TRUNCATE");
        if (Randomly.getBoolean()) {
            sb.append(" TABLE");
        }
        sb.append(" ");
        if (Randomly.getBooleanWithRatherLowProbability()) {
            for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(globalState.getSchema().getRandomTable(t -> !t.isView()).getName());
            }
        } else {
            sb.append(globalState.getSchema().getRandomTable(t -> !t.isView()).getName());
        }
        if (Randomly.getBoolean()) {
            sb.append(" ");
            sb.append(Randomly.fromOptions("CASCADE", "RESTRICT"));
        }
        return new SQLQueryAdapter(sb.toString(), errors);
    }

}

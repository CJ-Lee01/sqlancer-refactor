package sqlancer.tidb.gen;

import sqlancer.base.IgnoreMeException;
import sqlancer.base.Randomly;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;

public final class TiDBDropViewGenerator {

    private TiDBDropViewGenerator() {
    }

    public static SQLQueryAdapter dropView(TiDBGlobalState globalState) {
        if (globalState.getSchema().getTables(t -> t.isView()).isEmpty()) {
            throw new IgnoreMeException();
        }
        StringBuilder sb = new StringBuilder("DROP VIEW ");
        if (Randomly.getBoolean()) {
            sb.append("IF EXISTS ");
        }
        sb.append(globalState.getSchema().getRandomTableOrBailout(t -> t.isView()).getName());
        return new SQLQueryAdapter(sb.toString(), null, true);
    }

}

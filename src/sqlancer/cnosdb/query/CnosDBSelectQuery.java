package sqlancer.cnosdb.query;

import sqlancer.base.GlobalState;
import sqlancer.cnosdb.client.CnosDBConnection;
import sqlancer.cnosdb.client.CnosDBResultSet;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLancerResultSet;

public class CnosDBSelectQuery extends CnosDBQueryAdapter {
    CnosDBResultSet resultSet;

    public CnosDBSelectQuery(String query, ExpectedErrors errors) {
        super(query, errors);
    }

    @Override
    public boolean couldAffectSchema() {
        return false;
    }

    @Override
    public <G extends GlobalState<?, ?, CnosDBConnection>> boolean execute(G globalState, String... fills)
            throws Exception {
        globalState.getConnection().getClient().execute(query);
        return false;
    }

    @Override
    public <G extends GlobalState<?, ?, CnosDBConnection>> SQLancerResultSet executeAndGet(G globalState,
            String... fills) throws Exception {
        resultSet = globalState.getConnection().getClient().executeQuery(query);
        return null;
    }

    public CnosDBResultSet getResultSet() {
        return resultSet;
    }
}

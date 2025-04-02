package sqlancer.cnosdb.query;

import sqlancer.cnosdb.client.CnosDBConnection;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.Query;

public abstract class CnosDBQueryAdapter extends Query<CnosDBConnection> {

    String query;
    ExpectedErrors errors;

    public CnosDBQueryAdapter(String query, ExpectedErrors errors) {
        this.query = query;
        this.errors = errors;
    }

    @Override
    public String getLogString() {
        return query;
    }

    @Override
    public String getQueryString() {
        return query;
    }

    @Override
    public String getUnterminatedQueryString() {
        return null;
    }

    @Override
    public boolean couldAffectSchema() {
        return false;
    }

    @Override
    public ExpectedErrors getExpectedErrors() {
        return errors;
    }
}

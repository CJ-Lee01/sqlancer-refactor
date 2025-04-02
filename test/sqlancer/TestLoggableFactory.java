package sqlancer;

import org.junit.jupiter.api.Test;
import sqlancer.base.common.log.SQLLoggableFactory;
import sqlancer.base.common.query.SQLQueryAdapter;

public class TestLoggableFactory {

    @Test
    public void testLogCreateTable() {
        String query = "CREATE TABLE t1 (c1 INT)";
        SQLLoggableFactory logger = new SQLLoggableFactory();
        SQLQueryAdapter queryAdapter = logger.getQueryForStateToReproduce(query);
        assert (queryAdapter.couldAffectSchema());
    }

}

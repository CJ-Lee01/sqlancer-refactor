package sqlancer.yugabyte.ycql;

import java.sql.SQLException;

import sqlancer.base.OracleFactory;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.yugabyte.ycql.test.YCQLFuzzer;

public enum YCQLOracleFactory implements OracleFactory<YCQLProvider.YCQLGlobalState> {
    FUZZER {
        @Override
        public TestOracle<YCQLProvider.YCQLGlobalState> create(YCQLProvider.YCQLGlobalState globalState)
                throws SQLException {
            return new YCQLFuzzer(globalState);
        }

    }
}

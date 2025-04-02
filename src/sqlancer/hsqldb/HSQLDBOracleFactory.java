package sqlancer.hsqldb;

import java.sql.SQLException;

import sqlancer.base.OracleFactory;
import sqlancer.base.common.oracle.NoRECOracle;
import sqlancer.base.common.oracle.TLPWhereOracle;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.hsqldb.gen.HSQLDBExpressionGenerator;

public enum HSQLDBOracleFactory implements OracleFactory<HSQLDBProvider.HSQLDBGlobalState> {
    WHERE {
        @Override
        public TestOracle<HSQLDBProvider.HSQLDBGlobalState> create(HSQLDBProvider.HSQLDBGlobalState globalState)
                throws SQLException {
            HSQLDBExpressionGenerator gen = new HSQLDBExpressionGenerator(globalState);
            ExpectedErrors expectedErrors = ExpectedErrors.newErrors().with(HSQLDBErrors.getExpressionErrors()).build();

            return new TLPWhereOracle<>(globalState, gen, expectedErrors);
        }
    },
    NOREC {
        @Override
        public TestOracle<HSQLDBProvider.HSQLDBGlobalState> create(HSQLDBProvider.HSQLDBGlobalState globalState)
                throws Exception {
            HSQLDBExpressionGenerator gen = new HSQLDBExpressionGenerator(globalState);
            ExpectedErrors errors = ExpectedErrors.newErrors().with(HSQLDBErrors.getExpressionErrors()).build();
            return new NoRECOracle<>(globalState, gen, errors);
        }
    }
}

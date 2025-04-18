package sqlancer.datafusion;

import java.sql.SQLException;

import sqlancer.base.OracleFactory;
import sqlancer.base.common.oracle.NoRECOracle;
import sqlancer.base.common.oracle.TLPWhereOracle;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.datafusion.gen.DataFusionExpressionGenerator;

public enum DataFusionOracleFactory implements OracleFactory<DataFusionProvider.DataFusionGlobalState> {
    NOREC {
        @Override
        public TestOracle<DataFusionProvider.DataFusionGlobalState> create(
                DataFusionProvider.DataFusionGlobalState globalState) throws SQLException {
            DataFusionExpressionGenerator gen = new DataFusionExpressionGenerator(globalState);
            ExpectedErrors errors = ExpectedErrors.newErrors().with(DataFusionErrors.getExpectedExecutionErrors())
                    .with("canceling statement due to statement timeout").build();
            return new NoRECOracle<>(globalState, gen, errors);
        }
    },
    QUERY_PARTITIONING_WHERE {
        @Override
        public TestOracle<DataFusionProvider.DataFusionGlobalState> create(
                DataFusionProvider.DataFusionGlobalState globalState) throws SQLException {
            DataFusionExpressionGenerator gen = new DataFusionExpressionGenerator(globalState);
            ExpectedErrors expectedErrors = ExpectedErrors.newErrors()
                    .with(DataFusionErrors.getExpectedExecutionErrors()).build();

            return new TLPWhereOracle<>(globalState, gen, expectedErrors);
        }
    }
}

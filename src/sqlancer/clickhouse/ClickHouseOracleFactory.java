package sqlancer.clickhouse;

import java.sql.SQLException;

import sqlancer.base.OracleFactory;
import sqlancer.clickhouse.ClickHouseProvider.ClickHouseGlobalState;
import sqlancer.clickhouse.gen.ClickHouseExpressionGenerator;
import sqlancer.clickhouse.oracle.tlp.ClickHouseTLPAggregateOracle;
import sqlancer.clickhouse.oracle.tlp.ClickHouseTLPDistinctOracle;
import sqlancer.clickhouse.oracle.tlp.ClickHouseTLPGroupByOracle;
import sqlancer.clickhouse.oracle.tlp.ClickHouseTLPHavingOracle;
import sqlancer.base.common.oracle.NoRECOracle;
import sqlancer.base.common.oracle.TLPWhereOracle;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.base.common.query.ExpectedErrors;

public enum ClickHouseOracleFactory implements OracleFactory<ClickHouseGlobalState> {
    TLPWhere {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            ClickHouseExpressionGenerator gen = new ClickHouseExpressionGenerator(globalState);
            ExpectedErrors expectedErrors = ExpectedErrors.newErrors()
                    .with(ClickHouseErrors.getExpectedExpressionErrors()).build();

            return new TLPWhereOracle<>(globalState, gen, expectedErrors);
        }
    },
    TLPDistinct {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPDistinctOracle(globalState);
        }
    },
    TLPGroupBy {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPGroupByOracle(globalState);
        }
    },
    TLPAggregate {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPAggregateOracle(globalState);
        }
    },
    TLPHaving {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPHavingOracle(globalState);
        }
    },
    NoREC {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            ClickHouseExpressionGenerator gen = new ClickHouseExpressionGenerator(globalState);
            ExpectedErrors errors = ExpectedErrors.newErrors().with(ClickHouseErrors.getExpectedExpressionErrors())
                    .with("canceling statement due to statement timeout").build();

            return new NoRECOracle<>(globalState, gen, errors);
        }
    }
}

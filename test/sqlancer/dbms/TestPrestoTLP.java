package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import sqlancer.base.Main;

public class TestPrestoTLP {
    @Test
    public void testPrestoTLP() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.PRESTO_ENV));
        assertEquals(0,
                Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                        "--num-threads", "4", "--num-queries", TestConfig.NUM_QUERIES, "--validate-result-size-only",
                        "true", "--canonicalize-sql-strings", "false", "presto", "--oracle", "QUERY_PARTITIONING" }));
    }
}

package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import sqlancer.base.Main;

public class TestH2 {

    @Test
    public void testH2DB() {
        assertEquals(0, Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                "--num-threads", "4", "--num-queries", TestConfig.NUM_QUERIES, "h2" }));

    }

}

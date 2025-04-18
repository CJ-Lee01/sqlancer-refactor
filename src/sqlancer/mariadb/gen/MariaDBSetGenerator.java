package sqlancer.mariadb.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import sqlancer.base.MainOptions;
import sqlancer.base.Randomly;
import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.mariadb.MariaDBBugs;
import sqlancer.mariadb.MariaDBProvider.MariaDBGlobalState;

public class MariaDBSetGenerator {

    private final Randomly r;
    private final StringBuilder sb = new StringBuilder();

    // currently, global options are only generated when a single thread is executed
    private boolean isSingleThreaded;

    public MariaDBSetGenerator(Randomly r, MainOptions options) {
        this.r = r;
        this.isSingleThreaded = options.getNumberConcurrentThreads() == 1;
    }

    public static SQLQueryAdapter set(Randomly r, MainOptions options) {
        return new MariaDBSetGenerator(r, options).get();
    }

    private enum Scope {
        GLOBAL, SESSION
    }

    private enum Action {

        AUTOCOMMIT("autocommit", (r) -> 1, Scope.GLOBAL, Scope.SESSION), //
        BIG_TABLES("big_tables", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL, Scope.SESSION), //
        COMPLETION_TYPE("completion_type",
                (r) -> Randomly.fromOptions("'NO_CHAIN'", "'CHAIN'", "'RELEASE'", "0", "1", "2"), Scope.GLOBAL), //
        // BULK_INSERT_CACHE_SIZE("bulk_insert_buffer_size", (r) -> r.getLong(0, Long.MAX_VALUE), Scope.GLOBAL,
        // Scope.SESSION),
        CONCURRENT_INSERT("concurrent_insert", (r) -> Randomly.fromOptions("NEVER", "AUTO", "ALWAYS", "0", "1", "2"),
                Scope.GLOBAL),
        DELAY_KEY_WRITE("delay_key_write", (r) -> Randomly.fromOptions("ON", "OFF", "ALL"), Scope.GLOBAL),
        EQ_RANGE_INDEX_DIVE_LIMIT("eq_range_index_dive_limit", (r) -> r.getLong(0, 4294967295L), Scope.GLOBAL),
        FLUSH("flush", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL),
        FOREIGN_KEY_CHECKS("foreign_key_checks", (r) -> Randomly.fromOptions(1, 0), Scope.GLOBAL, Scope.SESSION),
        HOST_CACHE_SIZE("host_cache_size", (r) -> r.getLong(0, 65536), Scope.GLOBAL),
        JOIN_BUFFER_SIZE("join_buffer_size", (r) -> r.getLong(128, Long.MAX_VALUE), Scope.GLOBAL, Scope.SESSION),
        /* disabled as a workaround for https://bugs.mysql.com/bug.php?id=95987 */
        // KEY_BUFFER_SIZE("key_buffer_size", (r) -> r.getLong(8, Long.MAX_VALUE), Scope.GLOBAL),
        // KEY_CACHE_AGE_THRESHOLD("key_cache_age_threshold", (r) -> r.getLong(100, Long.MAX_VALUE), Scope.GLOBAL),
        // KEY_CACHE_BLOCK_SIZE("key_cache_block_size", (r) -> r.getLong(512, 16384), Scope.GLOBAL),
        // KEY_CACHE_DIVISION_LIMIT("key_cache_division_limit", (r) -> r.getLong(1, 100), Scope.GLOBAL),
        // MAX_HEAP_TABLE_SIZE("max_heap_table_size", (r) -> r.getLong(16384, Long.MAX_VALUE), Scope.GLOBAL,
        // Scope.SESSION),
        MAX_LENGTH_FOR_SORT_DATA("max_length_for_sort_data", (r) -> r.getLong(4, 8388608), Scope.GLOBAL, Scope.SESSION),
        MAX_SEEKS_FOR_KEY("max_seeks_for_key", (r) -> r.getLong(1, Long.MAX_VALUE), Scope.GLOBAL, Scope.SESSION),
        MAX_SORT_LENGTH("max_sort_length", (r) -> r.getLong(4, 8388608), Scope.GLOBAL, Scope.SESSION),
        MAX_SP_RECURSION_DEPTH("max_sp_recursion_depth", (r) -> r.getLong(0, 255), Scope.GLOBAL, Scope.SESSION),
        MYISAM_DATA_POINTER_SIZE("myisam_data_pointer_size", (r) -> r.getLong(2, 7), Scope.GLOBAL),
        MYISAM_MAX_SORT_FILE_SIZE("myisam_max_sort_file_size", (r) -> r.getLong(0, 9223372036854775807L), Scope.GLOBAL),
        MYISAM_REPAIR_THREADS("myisam_repair_threads", (r) -> r.getLong(1, Long.MAX_VALUE), Scope.GLOBAL,
                Scope.SESSION),
        // MYISAM_SORT_BUFFER_SIZE("myisam_sort_buffer_size", (r) -> r.getLong(4096, Long.MAX_VALUE), Scope.GLOBAL,
        // Scope.SESSION),
        MYISAM_STATS_METHOD("myisam_stats_method",
                (r) -> Randomly.fromOptions("nulls_equal", "nulls_unequal", "nulls_ignored"), Scope.GLOBAL,
                Scope.SESSION),
        MYISAM_USE_MMAP("myisam_use_mmap", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL),
        OPTIMIZER_PRUNE_LEVEL("optimizer_prune_level", (r) -> Randomly.fromOptions(0, 1), Scope.GLOBAL, Scope.SESSION),
        OPTIMIZER_SEARCH_DEPTH("optimizer_search_depth", (r) -> r.getLong(0, 62), Scope.GLOBAL, Scope.SESSION),
        OPTIMIZER_SWITCH("optimizer_switch", (r) -> getOptimizerSwitchConfiguration(r), Scope.GLOBAL, Scope.SESSION),
        // PRELOAD_BUFFER_SIZE("preload_buffer_size", (r) -> r.getLong(1024, 1073741824), Scope.GLOBAL, Scope.SESSION),
        // QUERY_ALLOC_BLOCK_SIZE("query_alloc_block_size", (r) -> r.getLong(1024, 4294967295L), Scope.GLOBAL,
        // Scope.SESSION),

        // causes out of memory errors
        // QUERY_PREALLOC_SIZE("query_prealloc_size", (r) -> r.getLong(8192,
        // Long.MAX_VALUE), Scope.GLOBAL, Scope.SESSION),
        // RANGE_ALLOC_BLOCK_SIZE("range_alloc_block_size", (r) -> r.getLong(4096, Long.MAX_VALUE), Scope.GLOBAL,
        // Scope.SESSION),
        /*
         * Removed Scope.GLOBAL as a workaround for https://bugs.mysql.com/bug.php?id=95985
         */
        // READ_BUFFER_SIZE("read_buffer_size", (r) -> r.getLong(8200, 2147479552), Scope.GLOBAL, Scope.SESSION),
        // READ_RND_BUFFER_SIZE("read_rnd_buffer_size", (r) -> r.getLong(1, 2147483647), Scope.GLOBAL, Scope.SESSION),
        /*
         * sort_buffer_size is commented out as a workaround for https://bugs.mysql.com/bug.php?id=95969
         */
        // SORT_BUFFER_SIZE("sort_buffer_size", (r) -> r.getLong(32768, Long.MAX_VALUE)),
        SQL_AUTO_IS_NULL("sql_auto_is_null", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL, Scope.SESSION),
        SQL_BUFFER_RESULT("sql_buffer_result", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL, Scope.SESSION),
        SQL_LOG_OFF("sql_log_off", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL, Scope.SESSION),
        SQL_QUOTE_SHOW_CREATE("sql_quote_show_create", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL,
                Scope.SESSION),
        // SQL_REQUIRE_PRIMARY_KEY("sql_require_primary_key", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL),
        // TMP_TABLE_SIZE("tmp_table_size", (r) -> r.getLong(1024, Long.MAX_VALUE), Scope.GLOBAL, Scope.SESSION),
        UNIQUE_CHECKS("unique_checks", (r) -> Randomly.fromOptions("OFF", "ON"), Scope.GLOBAL, Scope.SESSION),
        // TODO: https://dev.mysql.com/doc/refman/8.0/en/switchable-optimizations.html

        // MariaDB-specific
        JOIN_CACHE_LEVEL("join_cache_level", (r) -> r.getInteger(1, 8), Scope.GLOBAL, Scope.SESSION);

        private String name;
        private Function<Randomly, Object> prod;
        private final Scope[] scopes;

        Action(String name, Function<Randomly, Object> prod, Scope... scopes) {
            if (scopes.length == 0) {
                throw new AssertionError(name);
            }
            this.name = name;
            this.prod = prod;
            this.scopes = scopes.clone();
        }

        private static String getOptimizerSwitchConfiguration(Randomly r) {
            StringBuilder sb = new StringBuilder();
            sb.append("'");
            String[] options = { "condition_pushdown_for_derived", "condition_pushdown_for_subquery",
                    "condition_pushdown_from_having", "derived_merge", "derived_with_keys", "exists_to_in",
                    "extended_keys", "firstmatch", "index_condition_pushdown", "hash_join_cardinality", "index_merge",
                    "index_merge_intersection", "index_merge_sort_intersection", "index_merge_sort_union",
                    "index_merge_union", "in_to_exists", "join_cache_bka", "join_cache_hashed",
                    "join_cache_incremental", "loosescan", "materialization", "mrr", "mrr_cost_based", "mrr_sort_keys",
                    "not_null_range_scan", "optimize_join_buffer_size", "orderby_uses_equalities",
                    "outer_join_with_cache", "partial_match_rowid_merge", "partial_match_table_scan", "rowid_filter",
                    "semijoin", "semijoin_with_cache", "split_materialized", "subquery_cache", "table_elimination" };
            List<String> optionSubset = Arrays.asList(Randomly.fromOptions(options));
            sb.append(optionSubset.stream().map(s -> s + "=" + Randomly.fromOptions("on", "off"))
                    .collect(Collectors.joining(",")));
            sb.append("'");
            return sb.toString();
        }

        public boolean canBeUsedInScope(Scope session) {
            for (Scope scope : scopes) {
                if (scope == session) {
                    return true;
                }
            }
            return false;
        }

        public Scope[] getScopes() {
            return scopes.clone();
        }
    }

    private SQLQueryAdapter get() {
        sb.append("SET ");
        Action a;
        if (isSingleThreaded) {
            a = Randomly.fromOptions(Action.values());
            Scope[] scopes = a.getScopes();
            Scope randomScope = Randomly.fromOptions(scopes);
            switch (randomScope) {
            case GLOBAL:
                sb.append("GLOBAL");
                break;
            case SESSION:
                sb.append("SESSION");
                break;
            default:
                throw new AssertionError(randomScope);
            }

        } else {
            do {
                a = Randomly.fromOptions(Action.values());
            } while (!a.canBeUsedInScope(Scope.SESSION));
            sb.append("SESSION");
        }
        sb.append(" ");
        sb.append(a.name);
        sb.append(" = ");
        sb.append(a.prod.apply(r));
        return new SQLQueryAdapter(sb.toString(), ExpectedErrors
                .from("At least one of the 'in_to_exists' or 'materialization' optimizer_switch flags must be 'on'"));
    }

    public static SQLQueryAdapter resetOptimizer() {
        return new SQLQueryAdapter("SET optimizer_switch='default'");
    }

    public static List<SQLQueryAdapter> getAllOptimizer(MariaDBGlobalState globalState) {
        List<SQLQueryAdapter> result = new ArrayList<>();
        String[] options = { "condition_pushdown_for_derived", "condition_pushdown_for_subquery",
                "condition_pushdown_from_having", "derived_merge", "derived_with_keys", "exists_to_in", "extended_keys",
                "firstmatch", "index_condition_pushdown", "hash_join_cardinality", "index_merge",
                "index_merge_intersection", "index_merge_sort_intersection", "index_merge_sort_union",
                "index_merge_union", "in_to_exists", "join_cache_bka", "join_cache_hashed", "join_cache_incremental",
                "loosescan", "materialization", "mrr", "mrr_cost_based", "mrr_sort_keys", "not_null_range_scan",
                "optimize_join_buffer_size", "orderby_uses_equalities", "outer_join_with_cache",
                "partial_match_rowid_merge", "partial_match_table_scan", "rowid_filter", "semijoin",
                "semijoin_with_cache", "split_materialized", "subquery_cache", "table_elimination" };
        List<String> availableOptions = new ArrayList<>(Arrays.asList(options));
        if (MariaDBBugs.bug21058) {
            availableOptions.remove("in_to_exists"); // https://jira.mariadb.org/browse/MDEV-21058
        }
        if (MariaDBBugs.bug32076) {
            availableOptions.remove("not_null_range_scan"); // https://jira.mariadb.org/browse/MDEV-32076
        }
        if (MariaDBBugs.bug32099) {
            availableOptions.remove("optimize_join_buffer_size"); // https://jira.mariadb.org/browse/MDEV-32099
        }
        if (MariaDBBugs.bug32105) {
            availableOptions.remove("join_cache_hashed"); // https://jira.mariadb.org/browse/MDEV-32105
        }
        if (MariaDBBugs.bug32106) {
            availableOptions.remove("outer_join_with_cache"); // https://jira.mariadb.org/browse/MDEV-32106
        }
        if (MariaDBBugs.bug32107) {
            availableOptions.remove("table_elimination"); // https://jira.mariadb.org/browse/MDEV-32107
        }
        if (MariaDBBugs.bug32108) {
            availableOptions.remove("join_cache_incremental"); // https://jira.mariadb.org/browse/MDEV-32108
        }
        if (MariaDBBugs.bug32143) {
            availableOptions.remove("mrr"); // https://jira.mariadb.org/browse/MDEV-32143
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SET SESSION optimizer_switch = '%s'");

        for (String option : availableOptions) {
            result.add(new SQLQueryAdapter(String.format(sb.toString(), option + "=on"), ExpectedErrors.from(
                    "At least one of the 'in_to_exists' or 'materialization' optimizer_switch flags must be 'on'")));
            result.add(new SQLQueryAdapter(String.format(sb.toString(), option + "=off"), ExpectedErrors.from(
                    "At least one of the 'in_to_exists' or 'materialization' optimizer_switch flags must be 'on'")));
            result.add(new SQLQueryAdapter(String.format(sb.toString(), option + "=default"), ExpectedErrors.from(
                    "At least one of the 'in_to_exists' or 'materialization' optimizer_switch flags must be 'on'")));
        }

        return result;
    }

}

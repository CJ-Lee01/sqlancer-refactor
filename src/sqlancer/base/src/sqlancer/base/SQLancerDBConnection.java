package sqlancer.base;

public interface SQLancerDBConnection extends AutoCloseable {

    String getDatabaseVersion() throws Exception;
}

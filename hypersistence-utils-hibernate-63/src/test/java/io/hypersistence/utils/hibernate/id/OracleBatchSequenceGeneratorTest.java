package io.hypersistence.utils.hibernate.id;

import io.hypersistence.utils.hibernate.util.providers.OracleDataSourceProvider;
import io.hypersistence.utils.test.providers.DataSourceProvider;

/**
 * @author Philippe Marschall
 */
public class OracleBatchSequenceGeneratorTest extends AbstractBatchSequenceGeneratorTest {

    @Override
    protected DataSourceProvider dataSourceProvider() {
        return new OracleDataSourceProvider();
    }

}

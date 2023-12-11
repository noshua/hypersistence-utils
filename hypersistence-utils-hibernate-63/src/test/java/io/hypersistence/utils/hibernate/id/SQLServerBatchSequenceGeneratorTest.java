package io.hypersistence.utils.hibernate.id;

import io.hypersistence.utils.hibernate.util.providers.SQLServerDataSourceProvider;
import io.hypersistence.utils.test.providers.DataSourceProvider;

/**
 * @author Philippe Marschall
 */
public class SQLServerBatchSequenceGeneratorTest extends AbstractBatchSequenceGeneratorTest {

    @Override
    protected DataSourceProvider dataSourceProvider() {
        return new SQLServerDataSourceProvider();
    }

}

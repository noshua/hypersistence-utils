package io.hypersistence.utils.hibernate.util;

import io.hypersistence.utils.hibernate.util.providers.OracleDataSourceProvider;
import io.hypersistence.utils.test.providers.DataSourceProvider;

/**
 * AbstractOracleIntegrationTest - Abstract Oracle IntegrationTest
 *
 * @author Vlad Mihalcea
 */
public abstract class AbstractOracleIntegrationTest extends AbstractTest {

    @Override
    protected DataSourceProvider dataSourceProvider() {
        return new OracleDataSourceProvider();
    }
}

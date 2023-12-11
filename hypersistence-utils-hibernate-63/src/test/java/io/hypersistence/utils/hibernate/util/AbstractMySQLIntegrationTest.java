package io.hypersistence.utils.hibernate.util;

import io.hypersistence.utils.hibernate.util.providers.MySQLDataSourceProvider;
import io.hypersistence.utils.test.providers.DataSourceProvider;

/**
 * AbstractMySQLIntegrationTest - Abstract MySQL IntegrationTest
 *
 * @author Vlad Mihalcea
 */
public abstract class AbstractMySQLIntegrationTest extends AbstractTest {

    @Override
    protected DataSourceProvider dataSourceProvider() {
        return new MySQLDataSourceProvider();
    }
}

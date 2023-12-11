package io.hypersistence.utils.hibernate.util;

import io.hypersistence.utils.hibernate.util.providers.PostgreSQLDataSourceProvider;
import io.hypersistence.utils.test.providers.DataSourceProvider;

/**
 * AbstractPostgreSQLIntegrationTest - Abstract PostgreSQL IntegrationTest
 *
 * @author Vlad Mihalcea
 */
public abstract class AbstractPostgreSQLIntegrationTest extends AbstractTest {

    @Override
    protected DataSourceProvider dataSourceProvider() {
        return new PostgreSQLDataSourceProvider();
    }
}

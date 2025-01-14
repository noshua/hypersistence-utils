package io.hypersistence.utils.hibernate.type.array;

import io.hypersistence.utils.hibernate.type.AbstractHibernateType;
import io.hypersistence.utils.hibernate.type.array.internal.ArraySqlTypeDescriptor;
import io.hypersistence.utils.hibernate.type.array.internal.DateArrayTypeDescriptor;
import io.hypersistence.utils.hibernate.type.util.Configuration;
import io.hypersistence.utils.hibernate.type.util.ParameterizedParameterType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Date;
import java.util.Properties;

/**
 * Maps an {@code Date[]} array on a PostgreSQL ARRAY type. Multidimensional arrays are supported as well, as explained in <a href="https://vladmihalcea.com/multidimensional-array-jpa-hibernate/">this article</a>.
 * <p>
 * For more details about how to use it, check out <a href="https://vladmihalcea.com/how-to-map-java-and-sql-arrays-with-jpa-and-hibernate/">this article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
 *
 * @author Guillaume Briand
 */
public class DateArrayType
        extends AbstractHibernateType<Date[]>
        implements DynamicParameterizedType {

    public static final DateArrayType INSTANCE = new DateArrayType();

    public DateArrayType() {
        super(
                ArraySqlTypeDescriptor.INSTANCE,
                new DateArrayTypeDescriptor()
        );
    }

    public DateArrayType(Configuration configuration) {
        super(
                ArraySqlTypeDescriptor.INSTANCE,
                new DateArrayTypeDescriptor(), configuration
        );
    }

    public DateArrayType(Class arrayClass) {
        this();
        Properties parameters = new Properties();
        parameters.put(DynamicParameterizedType.PARAMETER_TYPE, new ParameterizedParameterType(arrayClass));
        setParameterValues(parameters);
    }

    public String getName() {
        return "date-array";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((DateArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}

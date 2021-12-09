package com.vladmihalcea.hibernate.type.array;

import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType;
import com.vladmihalcea.hibernate.type.array.internal.LocalDateArrayTypeDescriptor;
import com.vladmihalcea.hibernate.type.util.Configuration;
import com.vladmihalcea.hibernate.type.util.ParameterizedParameterType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * Maps a {@code java.Time.LocalDate[]} array on a PostgreSQL date[] ARRAY type. Multidimensional arrays are
 * supported as well, as
 * explained in <a href="https://vladmihalcea.com/multidimensional-array-jpa-hibernate/">this article</a>.
 * <p>
 * For more details about how to use it, check out
 * <a href="https://vladmihalcea.com/how-to-map-java-and-sql-arrays-with-jpa-and-hibernate/">this
 * article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
 *
 * @author Andrew Lazarus, based on DateArrayType by Guillaume Briand
 */

public class LocalDateArrayType extends AbstractArrayType<java.time.LocalDate[]> {

    public static final LocalDateArrayType INSTANCE =
        new LocalDateArrayType();

    public LocalDateArrayType() {
        super(
            new LocalDateArrayTypeDescriptor()
        );
    }

    public LocalDateArrayType(Configuration configuration) {
        super(
            new LocalDateArrayTypeDescriptor(), configuration
        );
    }

    public LocalDateArrayType(Class arrayClass) {
        this();
        Properties parameters = new Properties();
        parameters.put(DynamicParameterizedType.PARAMETER_TYPE, new ParameterizedParameterType(arrayClass));
        setParameterValues(parameters);
    }

    public String getName() {
        return "localdate-array";
    }
}

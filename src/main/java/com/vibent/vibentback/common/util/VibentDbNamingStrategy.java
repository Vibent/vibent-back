package com.vibent.vibentback.common.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;

import static com.google.common.base.CaseFormat.*;

public class VibentDbNamingStrategy extends PhysicalNamingStrategyStandardImpl implements Serializable {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(UPPER_CAMEL.to(LOWER_UNDERSCORE, name.getText()), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(LOWER_CAMEL.to(LOWER_UNDERSCORE, name.getText()), name.isQuoted());
    }
}
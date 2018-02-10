package com.gitlab.vibent.vibentback.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;
import java.util.Locale;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_UNDERSCORE;

public class VibentPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl implements Serializable {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(UPPER_CAMEL.to(LOWER_UNDERSCORE, name.getText()), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(LOWER_CAMEL.to(LOWER_UNDERSCORE, name.getText()), name.isQuoted());
    }
}
package com.rookiesquad.excelparsing.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.rookiesquad.excelparsing.util.Snowflake;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private static final Snowflake snowflake = new Snowflake();

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

}

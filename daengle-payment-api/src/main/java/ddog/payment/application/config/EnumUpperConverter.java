package ddog.payment.application.config;

import ddog.domain.payment.enums.ServiceType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class EnumUpperConverter implements Converter<String, ServiceType> {

    @Override
    public ServiceType convert(String source) {
        return ServiceType.valueOf(source.toUpperCase());
    }
}
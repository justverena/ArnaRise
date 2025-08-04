package kz.kbtu.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EnumService {

    private final MessageSource messageSource;
    public EnumService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String  translateEnum(Enum<?> e, Locale locale) {
        String key = toMessageKeyPrefix(e.getClass().getSimpleName()) + "." + e.name();
        return messageSource.getMessage(key, null, key, locale);
    }

    private String toMessageKeyPrefix(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}

package io.mosip.esignet.core.validator;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.validator.routines.UrlValidator.ALLOW_ALL_SCHEMES;
import static org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS;

@Component
public class RedirectURLValidator implements ConstraintValidator<RedirectURL, String> {

    // Matches host.tld structure without restricting to IANA-registered TLDs,
    // so dev/test domains like *.test, *.tst, *.local etc. are accepted.
    private static final RegexValidator AUTHORITY_VALIDATOR =
            new RegexValidator("^([a-zA-Z0-9]([a-zA-Z0-9\\-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$");

    private final UrlValidator urlValidator =
            new UrlValidator(null, AUTHORITY_VALIDATOR, ALLOW_ALL_SCHEMES + ALLOW_LOCAL_URLS);

    @Override
    public boolean isValid(String redirectUrl, ConstraintValidatorContext constraintValidatorContext) {
        return urlValidator.isValid(redirectUrl);
    }
}

package cominyaa.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

import java.util.Objects;

@Slf4j
public class AuthWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    public static final String BAD_MSG = "坏的凭证";

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        // e.printStackTrace();
        OAuth2Exception oAuth2Exception;
        if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
            oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
        } else if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof RedirectMismatchException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof InvalidScopeException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else {
            oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
        }

        ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
        ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
        Objects.requireNonNull(response.getBody()).addAdditionalInformation("code", oAuth2Exception.getHttpErrorCode() + "");
        return response;
    }
}

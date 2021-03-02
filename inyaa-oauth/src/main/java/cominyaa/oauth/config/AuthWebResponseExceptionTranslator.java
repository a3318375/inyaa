package cominyaa.oauth.config;

import cominyaa.oauth.exception.InyaaOauthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

@Slf4j
public class AuthWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        String msg = "";
        if (e instanceof AccountExpiredException) {
            msg = "账户过期";
            log.error(msg, e);
        } else if (e instanceof CredentialsExpiredException) {
            msg = "证书过期";
            log.error(msg, e);
        } else if (e instanceof DisabledException) {
            msg = "账户不可用";
            log.error(msg, e);
        } else if (e instanceof LockedException) {
            msg = "账户锁定";
            log.error(msg, e);
        } else if (e instanceof InvalidGrantException) {
            msg = "凭据无效";
            log.error(msg, e);
        } else if (e instanceof InsufficientAuthenticationException) {
            msg = "凭据不信任";
            log.error(msg, e);
        } else {
            msg = "系统异常";
            log.error(msg, e);
        }
        InyaaOauthException exception = new InyaaOauthException(msg);
        return super.translate(exception);
    }
}

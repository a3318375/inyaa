package cominyaa.oauth.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@Data
public class InyaaOauthException extends OAuth2Exception {

    //异常错误编码
    private Integer code;
    //异常信息
    private String msg;
    //返回数据
    private Boolean status;

    public InyaaOauthException(String message) {
        super(message);
        this.code = 400;
        this.msg = message;
        this.status = false;
    }

    public InyaaOauthException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.msg = message;
    }

    public InyaaOauthException(HttpStatus httpStatus, String message, Throwable t) {
        super(message, t);
        this.code = httpStatus.value();
        this.msg = message;
    }
}

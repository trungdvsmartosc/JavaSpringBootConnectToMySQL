package smartosc.fresher.connectmysql.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {

    private int status;

    private String message;

    private Instant time;
}

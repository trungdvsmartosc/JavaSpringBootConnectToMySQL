package smartosc.fresher.connectmysql.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smartosc.fresher.connectmysql.security.utils.SecurityConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping(SecurityConstants.AUTHENTICATION_BASE_PATH)
public class AuthenticationController {

   
}

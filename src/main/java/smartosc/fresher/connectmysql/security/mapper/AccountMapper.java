package smartosc.fresher.connectmysql.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.security.dto.AuthenticationAccountDto;
import smartosc.fresher.connectmysql.security.dto.RegisterRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account convertToAccount(RegisterRequest accountDto);

    RegisterRequest convertToRegisterRequest(Account account);

    Account convertToAccount(AuthenticationAccountDto accountDto);

    AuthenticationAccountDto convertToAuthenticationAccountDto(Account account);
}

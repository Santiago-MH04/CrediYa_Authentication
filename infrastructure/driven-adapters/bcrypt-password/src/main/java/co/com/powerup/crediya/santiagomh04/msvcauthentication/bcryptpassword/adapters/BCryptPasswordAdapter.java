package co.com.powerup.crediya.santiagomh04.msvcauthentication.bcryptpassword.adapters;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.PasswordEncoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordAdapter implements PasswordEncoderRepository {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String hashPassword(String password) {
        return this.bCryptPasswordEncoder.encode(password);
    }

    @Override
    public Boolean matchesPassword(String plainPassword, String hashedPassword) {
        return this.bCryptPasswordEncoder.matches(plainPassword, hashedPassword);
    }
}

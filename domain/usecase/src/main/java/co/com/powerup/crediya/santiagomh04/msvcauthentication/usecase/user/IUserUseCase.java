package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways.RoleRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.validations.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IUserUseCase implements UserUseCase{
    private final UserRepository repoUser;
    private final UserValidator userValidator;

    private final RoleRepository repoRole;

    @Override
    public Mono<User> createUser(User user) {
        return this.userValidator.validateUser(user)
                // Assign ROLE_APPLICANT by default
            .then(this.repoRole.findByName(Role.RoleName.ROLE_APPLICANT.name()))
            .flatMap(role -> {
                user.setRole(role);
                return this.repoUser.save(user);
            });
    }

    @Override
    public Mono<User> findByIdentificationNumber(String identificationNumber) {
        return this.userValidator.validateUserSearch(identificationNumber);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return this.repoUser.findByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return this.repoUser.existsByEmail(email);
    }
}

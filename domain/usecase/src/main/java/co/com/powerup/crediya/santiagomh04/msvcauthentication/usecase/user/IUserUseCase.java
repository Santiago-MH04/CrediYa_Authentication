package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways.RoleRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IUserUseCase implements UserUseCase{
    private final UserRepository repoUser;
    private final RoleRepository repoRole;

    @Override
    public Mono<User> createUser(User user) {
        // 1. Find the role by its name, which returns a Mono<Role>.
        return this.repoRole.findByName(Role.RoleName.ROLE_APPLICANT.name())
            // 2. Use flatMap to concat the following operation.
            //Once the Mono<Role> is completed, the lambda will be executed.
            .flatMap(role -> {
                // 3. Assign the role to the user object.
                user.setRole(role);
                // 4. Save the user with its rol.
                //This will return a new Mono<User>.
                return this.repoUser.save(user);
            });
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return this.repoUser.existsByEmail(email);
    }
}

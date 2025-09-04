package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {
    /*ROLE_APPLICANT(1L, "ROLE_APPLICANT", "Represents a user who is applying for a loan."),
    ROLE_AGENT(2L, "ROLE_AGENT", "Represents a user who manages applicants."),
    ROLE_ADMIN(3L, "ROLE_ADMIN", "Represents a user with full administrative privileges.");*/

    public enum RoleName{
        ROLE_APPLICANT,
        ROLE_AGENT,
        ROLE_ADMIN;
    }

    private Long id;
    private RoleName name;
    private String description;
}

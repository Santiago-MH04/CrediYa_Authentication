package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("roles")
public class RoleEntity {
    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    public enum RoleName{
        ROLE_APPLICANT,
        ROLE_AGENT,
        ROLE_ADMIN;
    }
}

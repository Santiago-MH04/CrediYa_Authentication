package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("users")
public class UserEntity {
    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("last_name")
    private String lastName;

    @Column("address")
    private String address;

    @Column("telephone")
    private String telephone;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("base_salary")
    private BigInteger baseSalary;

    @Column("document_type")
    private DocumentType documentType;

    @Column("identification_number")
    private String identificationNumber;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("role_id")
    private Long roleId;

    @Column("active")
    private boolean active;

    public enum DocumentType{
        CC,
        CE,
        DNI,
        PASSPORT,
        OTHER;
    }
}

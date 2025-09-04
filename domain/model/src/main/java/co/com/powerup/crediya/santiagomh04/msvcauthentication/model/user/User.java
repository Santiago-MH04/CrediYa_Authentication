package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String name;
    private String lastName;
    private String address;
    private String telephone;
    private LocalDate dateOfBirth;
    private BigInteger baseSalary;
    private DocumentType documentType;
    private String identificationNumber;

    private String email;
    private String password;

    private Role role;

    public enum DocumentType{
        CC,
        CE,
        DNI,
        PASSPORT,
        OTHER;
    }
}

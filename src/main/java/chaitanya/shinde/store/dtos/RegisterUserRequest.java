package chaitanya.shinde.store.dtos;
import lombok.*;

@Data //Getter + Setter + ToString + HashCode
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
}

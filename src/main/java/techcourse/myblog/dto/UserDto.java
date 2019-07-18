package techcourse.myblog.dto;

import lombok.Getter;
import techcourse.myblog.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UserDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$", message = "이름은 2~10자로 제한하며 숫자나 특수문자가 포함될 수 없습니다.")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 8자 이상의 소문자, 대문자, 숫자, 특수문자의 조합입니다.")
    private String password;

    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toUser() {
        return User.to(name, email, password);
    }
}
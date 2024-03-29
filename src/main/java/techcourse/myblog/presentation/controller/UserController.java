package techcourse.myblog.presentation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.application.UserReadService;
import techcourse.myblog.application.UserWriteService;
import techcourse.myblog.application.dto.UserRequestDto;
import techcourse.myblog.domain.user.User;
import techcourse.myblog.domain.user.validation.UserInfo;

import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

@Controller
public class UserController {
    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserReadService userReadService,
                          UserWriteService userWriteService) {
        this.userReadService = userReadService;
        this.userWriteService = userWriteService;
    }

    @GetMapping("/signup")
    public String createSignupForm(Model model) {
        if (!model.containsAttribute("signUpUserDto")) {
            model.addAttribute("signUpUserDto", new UserRequestDto("", "", ""));
        }
        return "signup";
    }
    
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userReadService.findAll());
        return "user-list";
    }

    @PostMapping("/users")
    public RedirectView createUser(@ModelAttribute("signUpUserDto") @Validated({Default.class, UserInfo.class}) UserRequestDto userRequestDto) {
        log.debug("user save request data : -> {}", userRequestDto);
        log.debug("user save response data : -> {}", userWriteService.save(userRequestDto));
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String createLoginForm(Model model) {
        if (!model.containsAttribute("loginUserDto")) {
            model.addAttribute("loginUserDto", new UserRequestDto("", "", ""));
        }

        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(HttpSession session,
                              @ModelAttribute("loginUserDto") @Validated(Default.class) UserRequestDto userRequestDto) {
        log.debug("user login request data : -> {}", userRequestDto);
        User loginUser = userReadService.findByEmailAndPassword(userRequestDto);
        log.debug("user login response data : -> {}", loginUser);
        session.setAttribute("user", loginUser);
        return new RedirectView("/");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/");
    }
}
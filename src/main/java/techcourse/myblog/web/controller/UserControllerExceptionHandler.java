package techcourse.myblog.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.support.exception.InvalidCommentException;
import techcourse.myblog.support.exception.DuplicatedEmailException;
import techcourse.myblog.support.exception.MismatchAuthorException;
import techcourse.myblog.support.exception.NotFoundArticleException;

@ControllerAdvice
public class UserControllerExceptionHandler {
    @ExceptionHandler(LoginFailedException.class)
    public RedirectView handleLoginFail(LoginFailedException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return new RedirectView("/login");
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public RedirectView handleSignupFail(DuplicatedEmailException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return new RedirectView("/signup");
    }

    @ExceptionHandler(MismatchAuthorException.class)
    public RedirectView handleMismatchArticleAuthorFail(MismatchAuthorException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return new RedirectView("/");
    }

    @ExceptionHandler(NotFoundArticleException.class)
    public RedirectView handleArticleFail(NotFoundArticleException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return new RedirectView("/");
    }

    @ExceptionHandler(InvalidCommentException.class)
    public RedirectView handleCommentFail(InvalidCommentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return new RedirectView("/");
    }
}
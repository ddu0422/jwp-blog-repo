package techcourse.myblog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.Article;
import techcourse.myblog.service.ArticleReadService;
import techcourse.myblog.service.CommentService;
import techcourse.myblog.service.dto.CommentDto;
import techcourse.myblog.support.argument.LoginUser;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final ArticleReadService articleReadService;

    public CommentController(CommentService commentService, ArticleReadService articleReadService) {
        this.commentService = commentService;
        this.articleReadService = articleReadService;
    }

    @PostMapping("/articles/{articleId}/comment")
    public RedirectView createComment(@PathVariable Long articleId, CommentDto commentDto, LoginUser loginUser) {
        Article article =  articleReadService.findById(articleId);;
        commentService.save(commentDto.toComment(loginUser.getUser(), article));

        return new RedirectView("/articles/" + articleId);
    }

    @PutMapping("/articles/{articleId}/comment/{commentId}")
    public RedirectView updateComment(@PathVariable Long commentId, @PathVariable Long articleId, CommentDto commentDto, LoginUser loginUser) {
        Article article = articleReadService.findById(articleId);
        commentService.modify(commentId, commentDto.toComment(loginUser.getUser(), article));

        return new RedirectView("/articles/" + articleId);
    }

    @DeleteMapping("/articles/{articleId}/comment/{commentId}")
    public RedirectView removeComment(@PathVariable Long commentId, @PathVariable Long articleId, LoginUser loginUser) {
        commentService.findByIdAndWriter(commentId, loginUser.getUser());
        commentService.deleteById(commentId);
        return new RedirectView("/articles/" + articleId);
    }
}

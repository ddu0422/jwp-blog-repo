package techcourse.myblog.domain.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.myblog.domain.BaseEntity;
import techcourse.myblog.domain.article.Article;
import techcourse.myblog.domain.comment.exception.InvalidCommentException;
import techcourse.myblog.domain.comment.exception.MismatchCommentAuthorException;
import techcourse.myblog.domain.user.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@DynamicUpdate
public class Comment extends BaseEntity {
    private static final String USER_FK_FIELD_NAME = "writer";
    private static final String USER_FK_NAME = "fk_comment_to_user";
    private static final String ARTICLE_FK_FILED_NAME = "article";
    private static final String ARTICLE_FK_NAME = "fk_comment_to_article";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(name = USER_FK_FIELD_NAME, foreignKey = @ForeignKey(name = USER_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;

    @ManyToOne
    @JoinColumn(name = ARTICLE_FK_FILED_NAME, foreignKey = @ForeignKey(name = ARTICLE_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    public Comment(String contents, User writer, Article article) {
        validateContents(contents);
        this.contents = contents;
        this.writer = writer;
        this.article = article;
    }

    private void validateContents(String contents) {
        if (contents == null || contents.isEmpty()) {
            throw new InvalidCommentException();
        }
    }

    public Comment update(Comment comment) {
        validateComment(comment);
        validateArticleAndAuthor(comment);
        this.contents = comment.contents;
        return this;
    }
    
    private void validateComment(Comment comment) {
        if (comment == null) {
            throw new InvalidCommentException();
        }
    }

    private void validateArticleAndAuthor(Comment comment) {
        if (matchArticle(comment) && matchAuthor(comment)) {
            return;
        }
        throw new MismatchCommentAuthorException();
    }

    private boolean matchAuthor(Comment comment) {
        return comment.writer.equals(this.writer);
    }

    private boolean matchArticle(Comment comment) {
        return comment.article.equals(this.article);
    }

    public void validateAuthor(User user) {
        if (matchAuthor(user)) {
            return;
        }
        throw new MismatchCommentAuthorException();
    }

    private boolean matchAuthor(User user) {
        return writer.equals(user);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                ", article=" + article +
                ", createTimeAt=" + createTimeAt +
                ", updateTimeAt=" + updateTimeAt +
                '}';
    }
}

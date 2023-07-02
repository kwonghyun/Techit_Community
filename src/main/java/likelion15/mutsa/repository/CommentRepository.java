package likelion15.mutsa.repository;

import likelion15.mutsa.entity.Comment;
import likelion15.mutsa.entity.enums.DeletedStatus;
import likelion15.mutsa.entity.enums.YesOrNo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //userId로 작성한 게시물 전체 조회
    Page<Comment> findAllByUsernameAndIsDeleted(String username, DeletedStatus isDeleted, Pageable pageable);

    default Page<Comment> findAllByUsernameAndIsDeleted(String username, Pageable pageable) {
        return findAllByUsernameAndIsDeleted(username, DeletedStatus.NONE, pageable);
    }

    // userId로 좋아요한 게시물 전체 조회
    @Query("SELECT c " +
            "from Comment c " +
            "join fetch Likes l on c.id = l.comment.id " +
            "WHERE l.user.id = ?1 and c.isDeleted = ?2 and l.isLike = ?3")
    Page<Comment> findAllByUsernameWithLikes(Long userId, DeletedStatus isDeleted, YesOrNo isLike, Pageable pageable);

    default Page<Comment> findAllByUsernameWithLikes(Long userId, Pageable pageable) {
        return findAllByUsernameWithLikes(userId, DeletedStatus.NONE, YesOrNo.YES, pageable);
    }

    // 댓글 삭제
    @Query("update Comment c set c.isDeleted = ?2 where c.id = ?1")
    void updateIsDeletedById(Long commentId, DeletedStatus isDeleted);

    default void updateIsDeletedById(Long commentId) {
        updateIsDeletedById(commentId, DeletedStatus.DELETE);
    }

}

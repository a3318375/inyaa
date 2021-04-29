package com.inyaa.web.posts.vo;

import com.inyaa.web.posts.bean.PostComment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author byteblogs
 * @since 2019-09-03
 */
@Data
@Accessors(chain = true)
public class PostCommentVO extends PostComment {

    private String avatar;

    private String name;

    private String toUserAvatar;

    private String toUserName;

    private List<PostCommentVO> children;

}

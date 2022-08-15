package com.dzl.duanzil.bean;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

/**
 * @author 345 QQ:1831712732
 * @name CommentListItemBean
 * @package com.dzl.duanzil.bean
 * @time 2022/08/15 15:38
 * @description
 */
public class CommentListItemBean extends BaseNode {
    private Integer commentItemId;
    private Integer commentParentId;
    private CommentUserDTO commentUser;
    private String commentedNickname;
    private Integer commentedUserId;
    private String content;
    private Boolean isReplyChild;
    private Integer jokeId;
    private String timeStr;

    public Integer getCommentItemId() {
        return commentItemId;
    }

    public void setCommentItemId(Integer commentItemId) {
        this.commentItemId = commentItemId;
    }

    public Integer getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Integer commentParentId) {
        this.commentParentId = commentParentId;
    }

    public CommentUserDTO getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CommentUserDTO commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentedNickname() {
        return commentedNickname;
    }

    public void setCommentedNickname(String commentedNickname) {
        this.commentedNickname = commentedNickname;
    }

    public Integer getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(Integer commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsReplyChild() {
        return isReplyChild;
    }

    public void setIsReplyChild(Boolean isReplyChild) {
        this.isReplyChild = isReplyChild;
    }

    public Integer getJokeId() {
        return jokeId;
    }

    public void setJokeId(Integer jokeId) {
        this.jokeId = jokeId;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }

    public static class CommentUserDTO {
        private String nickname;
        private String userAvatar;
        private Integer userId;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}

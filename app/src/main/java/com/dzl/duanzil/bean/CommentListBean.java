package com.dzl.duanzil.bean;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 345 QQ:1831712732
 * @name C
 * @package com.dzl.duanzil.bean
 * @time 2022/08/12 18:51
 * @description
 */
public class CommentListBean {
    private List<Comment> comments;
    private Integer count;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static class Comment extends BaseNode implements NodeFooterImp {
        private Integer commentId;
        private CommentUser commentUser;
        private String content;
        private Boolean isLike;
        private List<CommentListItemBean> itemCommentList;
        private Integer itemCommentNum;
        private Integer jokeId;
        private Integer jokeOwnerUserId;
        private Integer likeNum;
        private String timeStr;
        private int page = 0;
        private Boolean isExpanded = false;

        public Boolean getExpanded() {
            return isExpanded;
        }

        public void setExpanded(Boolean expanded) {
            isExpanded = expanded;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            return (List)itemCommentList;
        }

        public Boolean getLike() {
            return isLike;
        }

        public void setLike(Boolean like) {
            isLike = like;
        }

        public List<CommentListItemBean> getItemCommentList() {
            if(itemCommentList == null)
                itemCommentList = new ArrayList<>();
            return itemCommentList;
        }

        public void setItemCommentList(List<CommentListItemBean> itemCommentList) {
            this.itemCommentList = itemCommentList;
        }


        @Nullable
        @Override
        public BaseNode getFooterNode() {
            if (itemCommentNum == 0 || (itemCommentList != null && itemCommentList.size() == itemCommentNum))
                return null;
            else if (itemCommentList != null && itemCommentList.size() > 0) {
                return new CommentFooterMoreBean(commentId);//显示更多回复
            }
            return new CommentFooterBean(commentId, itemCommentNum);//显示回复了多少条;
        }

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public CommentUser getCommentUser() {
            return commentUser;
        }

        public void setCommentUser(CommentUser commentUser) {
            this.commentUser = commentUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Boolean getIsLike() {
            return isLike;
        }

        public void setIsLike(Boolean isLike) {
            this.isLike = isLike;
        }


        public Integer getItemCommentNum() {
            return itemCommentNum;
        }

        public void setItemCommentNum(Integer itemCommentNum) {
            this.itemCommentNum = itemCommentNum;
        }

        public Integer getJokeId() {
            return jokeId;
        }

        public void setJokeId(Integer jokeId) {
            this.jokeId = jokeId;
        }

        public Integer getJokeOwnerUserId() {
            return jokeOwnerUserId;
        }

        public void setJokeOwnerUserId(Integer jokeOwnerUserId) {
            this.jokeOwnerUserId = jokeOwnerUserId;
        }

        public Integer getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }


        public static class CommentUser {
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
}



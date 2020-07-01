package com.justefun.ordering.entity;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.entity
 * @time 2018/12/18 9:36
 * @describe 评论实体类
 */
public class CommentData {
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

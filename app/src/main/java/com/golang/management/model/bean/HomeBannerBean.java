package com.golang.management.model.bean;
/**
 *
 * Created by dongyaoyao
 */
public class HomeBannerBean {

    private String title;
    private String content;
    private int background;

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public HomeBannerBean(int background, String title) {
        this.title = title;
//        this.content = content;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

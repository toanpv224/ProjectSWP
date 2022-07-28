package model;

import dal.AccountDAO;
import java.sql.Timestamp;
import java.util.List;

public class Post {

    private int post_id;
    private int user_id;
    private String thumbnail;
    private String title;
    private String sub_title;
    private List<Tag> tag_list;
    private Timestamp publication_date;
    private PostSubCategory postSubCategory;
    private Timestamp updated_date;
    private String post_details;
    private boolean featured;

    public Post() {
    }

    public Post(int user_id, String thumbnail, String title,PostSubCategory postSubCategory, String post_details, boolean featured) {
        this.user_id = user_id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.postSubCategory = postSubCategory;
        this.post_details = post_details;
        this.featured = featured;
    }
    
    public Post(int post_id, int user_id, String thumbnail, String title, String sub_title, List<Tag> tag_list, Timestamp publication_date, PostSubCategory postSubCategory, Timestamp updated_date, String post_details, boolean featured) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.sub_title = sub_title;
        this.tag_list = tag_list;
        this.publication_date = publication_date;
        this.postSubCategory = postSubCategory;
        this.updated_date = updated_date;
        this.post_details = post_details;
        this.featured = featured;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public List<Tag> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<Tag> tag_list) {
        this.tag_list = tag_list;
    }

    public Timestamp getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Timestamp publication_date) {
        this.publication_date = publication_date;
    }

    public PostSubCategory getPostSubCategory() {
        return postSubCategory;
    }

    public void setPostSubCategory(PostSubCategory postSubCategory) {
        this.postSubCategory = postSubCategory;
    }

    public Timestamp getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Timestamp updated_date) {
        this.updated_date = updated_date;
    }

    public String getPost_details() {
        return post_details;
    }

    public void setPost_details(String post_details) {
        this.post_details = post_details;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    
    public String getAuthor() {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByID(user_id);
        return account.getUsername();
    }
    
    @Override
    public String toString() {
        return "Post{" + "post_id=" + post_id + ", user_id=" + user_id + ", thumbnail=" + thumbnail + ", title=" + title + ", sub_title=" + sub_title + ", tag_list=" + tag_list + ", publication_date=" + publication_date + ", updated_date=" + updated_date  + ", post_details=" + post_details + ", featured=" + featured + '}';
    }

}

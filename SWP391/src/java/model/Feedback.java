
package model;

import java.sql.Timestamp;

/**
 *
 * @author Duy Phuong
 */
public class Feedback {
    private int id;
    private Account user;
    private Product product;
    private int star;
    private String content;
    private String fullname;
    private String phone;
    private boolean gender;
    private String email;
    private String image_url;
    private Timestamp feedbackDate;//
    private int status;

    public Feedback() {
    }

    public Feedback(int id, int star, String content, String fullname, String phone, boolean gender, String email, String image_url, Timestamp feedbackDate, int status) {
        this.id = id;
        this.star = star;
        this.content = content;
        this.fullname = fullname;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.image_url = image_url;
        this.feedbackDate = feedbackDate;
        this.status = status;
    }
    
    public Feedback(int id, Account user, Product product, int star, String content, String fullname, String phone, boolean gender, String email, String image_url, Timestamp feedbackDate, int status) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.star = star;
        this.content = content;
        this.fullname = fullname;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.image_url = image_url;
        this.feedbackDate = feedbackDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}

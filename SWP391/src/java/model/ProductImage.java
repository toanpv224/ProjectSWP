
package model;

/**
 *
 * @author Duy Phuong
 */
public class ProductImage {
    private int image_id;
    private String path;
    private String description;

    public ProductImage() {
    }

    public ProductImage(int image_id, String path, String description) {
        this.image_id = image_id;
        this.path = path;
        this.description = description;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

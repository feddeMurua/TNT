package murua.fedde.prueba;

public class ItemData {

    private String marca;
    private int imageUrl;

    public ItemData(String marca,int imageUrl){

        this.marca = marca;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return marca;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.marca = title;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}

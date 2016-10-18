import java.awt.image.BufferedImage;

public class NamedImage {

    private String name;
    private BufferedImage image;

    public NamedImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }
}

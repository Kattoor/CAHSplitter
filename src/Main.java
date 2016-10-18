import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        long time = System.currentTimeMillis();
        new File("outputfiles").mkdir();
        IntStream.range(3, 31).parallel().boxed().map(Main::getImage).flatMap(Main::cutImage).forEach(Main::saveCardImage);
        System.out.println("Time " + (System.currentTimeMillis() - time));
    }

    private static NamedImage getImage(int index) {

        try {
            return new NamedImage(
                    String.format("CAH-%d", index),
                    ImageIO.read(new File(String.format("files/CAH_MainGame.%d-1.png", index)))
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Stream<NamedImage> cutImage(NamedImage namedImage) {

        final int PADDING_LEFT = 54;
        final int PADDING_TOP = 105;
        final int CARD_SIZE = 398;

        Stream.Builder<NamedImage> stream = Stream.builder();

        for (int x = 0; x < 4; x++) {

            for (int y = 0; y < 5; y++) {

                int left = PADDING_LEFT + CARD_SIZE * x;
                int top = PADDING_TOP + CARD_SIZE * y;

                BufferedImage cardImage = namedImage.getImage().getSubimage(left, top, CARD_SIZE, CARD_SIZE);

                stream.accept(new NamedImage(namedImage.getName() + String.format("-%d-%d.gif", x, y), cardImage));
            }
        }

        return stream.build();
    }

    private static void saveCardImage(NamedImage namedImage) {

        try {
            ImageIO.write(namedImage.getImage(), "gif", new File(String.format("outputfiles/%s", namedImage.getName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

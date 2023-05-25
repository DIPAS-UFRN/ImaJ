package dipas.eaj.ufrn;

public class App {
    public static void main( String[] args ){
        int[][][] im = Image.imRead("src/examples/image.jpeg");
        int[][] imgray = Image.rgb2gray(im);
        Image.imWrite(imgray, "src/examples/result.jpeg");
    }
}
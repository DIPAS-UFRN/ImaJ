package proseed.services.image;

/**
 * Esta classe representa uma propriedade de um determinado objeto.
 * 
 * @author Nome do Autor
 * @version 1.0
 */
public class Properties {
    /**
     * Área total da propriedade
     */
    public int area;
    /**
     * Array contendo o tamanho da caixa delimitadora da propriedade
     */
    public int[] boundingBox;
    /**
     * Matriz contendo a imagem da propriedade
     */
    public boolean[][] image;

    public Properties() {
        boundingBox = new int[4];
    }
}
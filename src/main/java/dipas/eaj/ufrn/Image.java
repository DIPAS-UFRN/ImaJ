package dipas.eaj.ufrn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public abstract class Image {
    /**
     * Classe abstrata Image, que fornece métodos para análise de imagens.
     * Esta classe depende do Java 11 e da biblioteca Jgrapht.
     * 
     * @author Luan Magioli e Yuri Felipe
     * @version 1.0
     */

     /**
     * O método imRead é usado para ler uma imagem de um arquivo e retornar um array tridimensional de inteiros.
     *
     * @param path O caminho para o arquivo de imagem.
     * @return Retorna o array tridimensional de inteiros representando uma imagem colorida.
     */
    public static int[][][] imRead(String path) {
        try {
            System.out.println(path);
            BufferedImage im = ImageIO.read(new File(path));
            
            int[][][] imMatrix = bufferedImage2Image(im);

            System.out.println("Image '"+path+"' loaded successfully!");
            return imMatrix;
        } catch (IOException e) {
            System.out.println("Loading error: Please verify that the specified path exists and try again.");
        }
        return null;
    }

    
    /**
     * O método bufferedImage2Image é usado para converter um objeto BufferedImage em um array tridimensional de inteiros.
     *
     * @param im Objeto BufferedImage para converter.
     * @return Retorna o array tridimensional de inteiros representando a imagem colorida.
     */
    public static int[][][] bufferedImage2Image(BufferedImage im) {
        int[][][] imMatrix = new int[im.getHeight()][im.getWidth()][3];
        for (int i = 0; i < im.getHeight() - 1; i++) {
            for (int j = 0; j < im.getWidth() - 1; j++) {
                int rgb = im.getRGB(j, i);
                imMatrix[i][j][0] = (rgb & 0x00FF0000) >>> 16; // R
                imMatrix[i][j][1] = (rgb & 0x0000FF00) >>> 8; // G
                imMatrix[i][j][2] = rgb & 0x000000FF; // B
            }
        }

        return imMatrix;
    }

    /**
     * O método image2BufferedImage é usado para converter um array tridimensional de inteiros em um objeto BufferedImage.
     *
     * @param im Array tridimensional de inteiros para converter.
     * @return Retorna o objeto BufferedImage representando a imagem colorida.
     */
    public static BufferedImage image2BufferedImage(int[][][] im) {
        BufferedImage buff = new BufferedImage(im[0].length, im.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                Color color = new Color(im[i][j][0], im[i][j][1], im[i][j][2]);
                buff.setRGB(j, i, color.getRGB());
            }
        }
        return buff;
    }

    /**
     * O método image2BufferedImage é usado para converter um array bidimensional de inteiros em um objeto BufferedImage.
     *
     * @param im Array bidimensional de inteiros para converter.
     * @return Retorna o objeto BufferedImage representando a imagem em escala de cinza.
     */
    public static BufferedImage image2BufferedImage(int[][] im) {
        BufferedImage buff = new BufferedImage(im[0].length, im.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                if (im[i][j] < 0)
                    im[i][j] = 0;
                if (im[i][j] > 255)
                    im[i][j] = 255;

                Color color = new Color(im[i][j], im[i][j], im[i][j]);
                buff.setRGB(j, i, color.getRGB());
            }
        }
        return buff;
    }

    /**
     * O método image2BufferedImage é usado para converter um array bidimensional de valores lógicos em um objeto BufferedImage.
     *
     * @param im Array bidimensional de booleans para converter.
     * @return Retorna o objeto BufferedImage representando a imagem binária.
     */
    public static BufferedImage image2BufferedImage(boolean[][] im) {
        BufferedImage buff = new BufferedImage(im[0].length, im.length, BufferedImage.TYPE_INT_RGB);
        int val;
        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                if (im[i][j])
                    val = 255;
                else
                    val = 0;

                Color color = new Color(val, val, val);
                buff.setRGB(j, i, color.getRGB());
            }
        }
        return buff;
    }

    /**
     * O método imWrite é usado para salvar uma imagem de um objeto BufferedImage para um arquivo. 
     *
     * @param path O caminho para o arquivo de imagem.
     * @param bufferedImage O objeto BufferedImage para salvar.
     */
    private static void imWrite(String path, BufferedImage bufferedImage) {
        try {
            BufferedImage buff = bufferedImage;
            String[] type = path.split("\\.");
            ImageIO.write(buff, type[type.length - 1], new File(path));
            System.out.println("Image '"+path+"' successfully written!");
        } catch (IOException e) {
            System.out.println("Writing error: Please verify that the specified path exists and try again.");
        } catch (Exception e) {
            System.out.println("Writing error: " + e.getMessage());
        }
    }

    /**
     * O método imWrite é usado para salvar uma imagem de um array tridimensional de inteiros para um arquivo. 
     *
     * @param im O array tridimensional de inteiros para salvar.
     * @param path O caminho para o arquivo de imagem.
     */
    public static void imWrite(int[][][] im, String path) {
        imWrite(path, image2BufferedImage(im));
    }

    /**
     * O método imWrite é usado para salvar uma imagem de um array bidimensional de inteiros para um arquivo. 
     *
     * @param im O array bidimensional de inteiros para salvar.
     * @param path O caminho para o arquivo de imagem.
     */
    public static void imWrite(int[][] im, String path) {
        imWrite(path, image2BufferedImage(im));
    }

    /**
     * O método imWrite é usado para salvar uma imagem de um array bidimensional de valores lógicos para um arquivo. 
     *
     * @param im O array bidimensional de valores lógicos para salvar.
     * @param path O caminho para o arquivo de imagem.
     */
    public static void imWrite(boolean[][] im, String path) {
        imWrite(path, image2BufferedImage(im));
    }

    /**
     * Exibe uma imagem na janela de exibição.
     *
     * @param name nome da imagem a ser exibida
     * @param buff imagem a ser exibida
     */
    private static void imShow(String name, BufferedImage buff) {
        ImageIcon icon = new ImageIcon(buff);
        JFrame frame = new JFrame(name);
        frame.setLayout(new FlowLayout());
        frame.setSize((int)(buff.getHeight()*1.1), (int)(buff.getWidth()*1.1));
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Exibe uma imagem na janela de exibição.
     *
     * @param name nome da imagem a ser exibida
     * @param im imagem a ser exibida
     */
    public static void imShow(int[][][] im, String name) {
        imShow(name, image2BufferedImage(im));
    }

    /**
     * Exibe uma imagem na janela de exibição.
     *
     * @param name nome da imagem a ser exibida
     * @param im imagem a ser exibida
     */
    public static void imShow(int[][] im, String name) {
        imShow(name, image2BufferedImage(im));
    }

    /**
     * Exibe uma imagem na janela de exibição.
     *
     * @param name nome da imagem a ser exibida
     * @param im imagem a ser exibida
     */
    public static void imShow(boolean[][] im, String name) {
        imShow(name, image2BufferedImage(im));
    }

    /**
    * Calcula o histograma de uma imagem.
    *
    * @param im A imagem tridimensional.
    * @return O histograma da imagem, com 256 posições.
    */
    public static int[][] imHist(int[][][] im) {
        int[][] hist = new int[3][256];
        int v = im[0][0].length;

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                for (int c = 0; c < v; c++) {
                    hist[c][im[i][j][c]]++;
                }
            }
        }

        return hist;
    }

    /**
    * Calcula o histograma de uma imagem.
    *
    * @param im A imagem bidimensional.
    * @return O histograma da imagem, com 256 posições.
    */
    public static int[] imHist(int[][] im) {
        int[] hist = new int[256];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                hist[im[i][j]]++;
            }
        }

        return hist;
    }

    /**
     * Esta função divide uma imagem de três canais em um único canal.
     * 
     * @param im Uma imagem de três canais.
     * @param channel O canal a ser extraído.
     * @return Uma imagem com um único canal.
     */
    public static int[][] splitChannel(int[][][] im, int channel) {
        int[][] image = new int[im.length][im[0].length];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                image[i][j] = im[i][j][channel];
            }
        }

        return image;
    }

    /**
     * Esta função divide uma imagem de três canais em um único canal.
     * 
     * @param im Uma imagem de três canais.
     * @param channel O canal a ser extraído.
     * @return Uma imagem com um único canal.
     */
    public static double[][] splitChannel(double[][][] im, int channel) {
        double[][] image = new double[im.length][im[0].length];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                image[i][j] = im[i][j][channel];
            }
        }

        return image;
    }

    /**
     * Esta função converte uma imagem de três canais para uma imagem em tons de cinza.
     * 
     * @param im Uma imagem de três canais.
     * @return Uma imagem em tons de cinza.
     */
    public static int[][] rgb2gray(int[][][] im) {
        int[][] image = new int[im.length][im[0].length];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                image[i][j] = (int) (0.299 * im[i][j][0] + 0.587 * im[i][j][1] + 0.114 * im[i][j][2]);
            }
        }

        return image;
    }

    /**
     * Converte a imagem RGB fornecida para um espaço de cor CMYK.
     * 
     * @param im A imagem RGB a ser convertida.
     * @return A imagem convertida para CMYK.
     */    
    public static int[][][] rgb2cmyk(int[][][] im) {
        int[][][] image = new int[im.length][im[0].length][4];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                float R = 1 - (im[i][j][0] / (float) 255);
                float G = 1 - (im[i][j][1] / (float) 255);
                float B = 1 - (im[i][j][2] / (float) 255);

                float K = Math.min(Math.min(R, G), B);

                image[i][j][0] = (int) ((R - K) * 255);
                image[i][j][1] = (int) ((G - K) * 255);
                image[i][j][2] = (int) ((B - K) * 255);
                image[i][j][3] = (int) (K * 255);
            }
        }

        return image;
    }

    /**
     * Converte a imagem RGB fornecida para um espaço de cor HSV.
     * 
     * @param im A imagem RGB a ser convertida.
     * @return A imagem convertida para HSV.
     */    
    public static double[][][] rgb2hsv(int[][][] im) {
        double[][][] image = new double[im.length][im[0].length][3];
        double[] rgb = new double[3], vet = new double[3];
        double h = 0, s = 0, v = 0;
        boolean gray;

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                vet[0] = rgb[0] = im[i][j][0] / 255.0;
                vet[1] = rgb[1] = im[i][j][1] / 255.0;
                vet[2] = rgb[2] = im[i][j][2] / 255.0;

                Arrays.sort(vet);

                h = 0;
                s = vet[0];
                v = vet[2];

                gray = (s == v);

                //Hue Azul
                if (v == rgb[2] && !gray)
                    h = (2 / (double) 3) + (1 / (double) 6) * ((rgb[0] - rgb[1]) / (v - s));
                //Hue Verde
                if (v == rgb[1] && !gray)
                    h = 1 / (double) 3 + 1 / (double) 6 * ((rgb[1] - rgb[0]) / (v - s));
                //Hue Vermelho
                if (v == rgb[0] && !gray)
                    h = 1 / (double) 6 * ((rgb[1] - rgb[2]) / (v - s));
                if (h < 0)
                    h++;

                if (gray)
                    s = 0;
                else
                    s = 1 - s / v;

                image[i][j][0] = h;
                image[i][j][1] = s;
                image[i][j][2] = v;
            }
        }

        return image;
    }

    /**
     * Converte a imagem RGB fornecida para um espaço de cor YCbCr.
     * 
     * @param im A imagem RGB a ser convertida.
     * @return A imagem convertida para YCbCr.
     */  
    public static int[][][] rgb2ycbcr(int[][][] im) {
        int[][][] image = new int[im.length][im[0].length][3];

        double[][] origT = new double[][]{{0.2568, 0.5041, 0.0979}, {-0.1482, -0.2910, 0.4392},
                {0.4392, -0.3678, -0.0714}};
        int[] origOffset = new int[]{16, 128, 128};

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                for (int k = 0; k < 3; k++) {
                    image[i][j][k] = Math.abs((int) ((origT[k][0] * im[i][j][0]) + (origT[k][1] * im[i][j][1])
                            + (origT[k][2] * im[i][j][2]) + origOffset[k]));
                }
            }
        }

        return image;
    }

    /**
     * Esta função faz o recorte de uma imagem colorida, passando como parâmetro a imagem, seguido dos parâmetros x1, y1, x2 e y2.
     *
     * @param im Imagem colorida a ser recortada.
     * @param x1 Coordenada x do primeiro ponto.
     * @param y1 Coordenada y do primeiro ponto.
     * @param x2 Coordenada x do segundo ponto.
     * @param y2 Coordenada y do segundo ponto.
     * @return Retorna uma imagem colorida recortada.
     */
    public static int[][][] imCrop(int[][][] im, int x1, int y1, int x2, int y2) {
        int height = Math.abs(x1 - x2);
        int width = Math.abs(y1 - y2);
        int minX, minY;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);

        int[][][] imCropped = new int[height][width][3];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imCropped[i][j][0] = im[minX + i][minY + j][0];
                imCropped[i][j][1] = im[minX + i][minY + j][1];
                imCropped[i][j][2] = im[minX + i][minY + j][2];
            }
        }
        return imCropped;
    }

    /**
     * Esta função faz o recorte de uma imagem em escalas de cinza, passando como parâmetro a imagem, seguido dos parâmetros x1, y1, x2 e y2.
     *
     * @param im Imagem colorida a ser recortada.
     * @param x1 Coordenada x do primeiro ponto.
     * @param y1 Coordenada y do primeiro ponto.
     * @param x2 Coordenada x do segundo ponto.
     * @param y2 Coordenada y do segundo ponto.
     * @return Retorna uma imagem em escalas de cinza recortada.
     */
    public static int[][] imCrop(int[][] im, int x1, int y1, int x2, int y2) {
        int height = Math.abs(x1 - x2);
        int width = Math.abs(y1 - y2);
        int minX, minY;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);

        int[][] imCropped = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imCropped[i][j] = im[minX + i][minY + j];
                imCropped[i][j] = im[minX + i][minY + j];
                imCropped[i][j] = im[minX + i][minY + j];
            }
        }
        return imCropped;
    }

    /**
     * Esta função faz o recorte de uma imagem binária, passando como parâmetro a imagem, seguido dos parâmetros x1, y1, x2 e y2.
     *
     * @param im Imagem colorida a ser recortada.
     * @param x1 Coordenada x do primeiro ponto.
     * @param y1 Coordenada y do primeiro ponto.
     * @param x2 Coordenada x do segundo ponto.
     * @param y2 Coordenada y do segundo ponto.
     * @return Retorna uma imagem binária recortada.
     */
    public static boolean[][] imCrop(boolean[][] im, int x1, int y1, int x2, int y2) {
        int height = Math.abs(x1 - x2);
        int width = Math.abs(y1 - y2);
        int minX, minY;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);


        boolean[][] imCropped = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imCropped[i][j] = im[minX + i][minY + j];
                imCropped[i][j] = im[minX + i][minY + j];
                imCropped[i][j] = im[minX + i][minY + j];
            }
        }

        return imCropped;
    }

    /**
     * Aplica um filtro Gaussiano a uma imagem de 3 dimensões.
     * 
     * @param im Uma matriz tridimensional que representa a imagem.
     * @param tam O tamanho do filtro Gaussiano.
     * @return Uma matriz tridimensional com o resultado do filtro aplicado.
     */
    public static int[][][] imGaussian(int[][][] im, int tam) {
        int[][][] filter = new int[im.length][im[0].length][im[0][0].length];

        int sum, count;

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                for (int c = 0; c < 3; c++) {
                    sum = 0;
                    count = 0;
                    for (int x = ((tam / 2)) * -1; x <= (tam / 2); x++) {
                        for (int y = ((tam / 2)) * -1; y <= (tam / 2); y++) {
                            if (i + x >= 0 && j + y >= 0 && i + x < im.length && j + y < im[0].length) {
                                sum += im[i + x][j + y][c];
                                count++;
                            }
                        }
                    }

                    filter[i][j][c] = sum / count;
                }
            }
        }

        return filter;
    }

    /**
     * Converte uma imagem em tons de cinza em uma imagem em preto e branco usando o limiar fixo 126.
     * 
     * @param im Uma matriz de inteiros representando uma imagem em tons de cinza.
     * @return Uma matriz de booleans representando uma imagem em preto e branco.
     */
    public static boolean[][] im2bw(int[][] im) {
        boolean[][] bw = new boolean[im.length][im[0].length];

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                bw[i][j] = (im[i][j] > 126);
            }
        }

        return bw;
    }

    /**
     * Esta função bwLabel é usada para rotular componentes conexos binários.
     * 
     * @param im A imagem binária de entrada
     * @return Uma matriz de inteiros que contém as etiquetas dos componentes conexos
     */
    public static int[][] bwLabel(boolean[][] im) {
        int[][] imLabel = zeros(im.length, im[0].length);

        List<HashSet<Integer>> regionError = new ArrayList<>();
        List<Integer> regionAll = new ArrayList<>();

        int label = 0;
        boolean flag, flag2;
        int upperLabel, nextLabel, leftLabel, rightLabel;
        boolean upperNeighbour, nextNeighbour, leftNeighbour, rightNeighbour;

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                if (im[i][j]) { //se for região
                    upperNeighbour = false;
                    nextNeighbour = false;
                    leftNeighbour = false;
                    rightNeighbour = false;
                    upperLabel = 0;
                    nextLabel = 0;
                    leftLabel = 0;
                    rightLabel = 0;

                    if (i != 0) {
                        upperNeighbour = im[i - 1][j];
                        upperLabel = imLabel[i - 1][j];
                    }
                    if (j != 0) {
                        nextNeighbour = im[i][j - 1];
                        nextLabel = imLabel[i][j - 1];
                    }
                    if (j != 0 && i != 0) {
                        leftNeighbour = im[i - 1][j - 1];
                        leftLabel = imLabel[i - 1][j - 1];
                    }
                    if (j != im[0].length - 1 && i != 0) {
                        rightNeighbour = im[i - 1][j + 1];
                        rightLabel = imLabel[i - 1][j + 1];
                    }

                    if (!upperNeighbour && !nextNeighbour && !leftNeighbour && !rightNeighbour) { //Nova região
                        label += 1;
                        imLabel[i][j] = label;
                        regionAll.add(label);
                    } else {
                        List<Integer> labels = new ArrayList<>();
                        if (upperLabel != 0) labels.add(upperLabel);
                        if (nextLabel != 0) labels.add(nextLabel);
                        if (leftLabel != 0) labels.add(leftLabel);
                        if (rightLabel != 0) labels.add(rightLabel);
                        Collections.sort(labels);
                        int lbMin = labels.get(0);
                        imLabel[i][j] = lbMin;
                        flag = false;
                        for (int lb : labels) {//Para cada Label
                            for (HashSet<Integer> list : regionError) { //Procure nas regiões com erro
                                for (Integer num : list) { //Para cada erro de cada regiõo
                                    if (lb == num) {     //Se encontrar, adicione todos
                                        list.addAll(labels);
                                        labels.remove(0);
                                        //regionAll.removeAll(labels);
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag)
                                    break;
                            }
                            if (flag)
                                break;
                        }
                        if (!flag) {
                            regionError.add(new HashSet<>());
                            regionError.get(regionError.size() - 1).addAll(labels);
                        }
                    }
                }
            }
        }


        List<HashSet<Integer>> regionNew = new ArrayList<>();
        HashSet<Integer> auxList = null;
        //Adicionar elementos sem erros
        for (Integer num : regionAll) {
            flag2 = false;
            for (HashSet<Integer> list : regionNew) {
                if (list.contains(num)) {
                    flag2 = true;
                    break;
                }
            }
            if (!flag2) {
                flag = false;
                for (HashSet<Integer> lista : regionError) {
                    if (lista.contains(num)) {
                        auxList = (HashSet<Integer>) lista.clone();
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    regionNew.add(auxList);
                } else {
                    regionNew.add(new HashSet<>());
                    regionNew.get(regionNew.size() - 1).add(num);
                }
            }
        }

        //Correção de erros

        for (int i = 0; i < im.length; i++) {
            for (int j = 0; j < im[0].length; j++) {
                if (im[i][j]) { //se for região
                    for (int c = 0; c < regionNew.size(); c++) {
                        HashSet<Integer> set = regionNew.get(c);
                        if (set.contains(imLabel[i][j])) {
                            imLabel[i][j] = c + 1;
                            break;
                        }
                    }
                }
            }
        }
        return imLabel;
    }

    /**
     * Esta função executa uma operação de erosão binária em uma matriz de booleanos.
     *
     * @param bw A matriz de booleanos a ser erodida.
     * @param tam O tamanho da matriz.
     * @return A matriz erodida.
     */
    public static boolean[][] bwErode(boolean[][] bw, int tam) {
        boolean[][] erosion = new boolean[bw.length][bw[0].length];
        erosion = Image.copy(bw);
        boolean flag;
        for (int i = 0; i < bw.length; i++) {
            for (int j = 0; j < bw[0].length; j++) {
                if (bw[i][j]) {
                    flag = false;
                    for (int x = ((tam / 2)) * -1; x <= (tam / 2); x++) {
                        for (int y = ((tam / 2)) * -1; y <= (tam / 2); y++) {
                            if (x != 0 || y != 0) {
                                if (i + x >= 0 && j + y >= 0 && i + x < bw.length && j + y < bw[0].length) {
                                    if (!bw[i + x][j + y]) {
                                        erosion[i][j] = false;
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag) break;
                    }
                }
            }
        }

        return erosion;
    }

    /**
     * Esta função executa uma operação de dilatação binária em uma matriz de booleanos.
     *
     * @param bw A matriz de booleanos a ser dilatada.
     * @param tam O tamanho da matriz.
     * @return A matriz dilatada.
     */
    public static boolean[][] bwDilate(boolean[][] bw, int tam) {
        boolean[][] dilatation = new boolean[bw.length][bw[0].length];
        dilatation = Image.copy(bw);

        boolean flag;
        for (int i = 0; i < bw.length; i++) {
            for (int j = 0; j < bw[0].length; j++) {
                if (!bw[i][j]) {
                    flag = false;
                    for (int x = ((tam / 2)) * -1; x <= (tam / 2); x++) {
                        for (int y = ((tam / 2)) * -1; y <= (tam / 2); y++) {
                            if (x != 0 || y != 0) {
                                if (i + x >= 0 && j + y >= 0 && i + x < bw.length && j + y < bw[0].length) {
                                    if (bw[i + x][j + y]) {
                                        dilatation[i][j] = true;
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag) break;
                    }
                }
            }
        }

        return dilatation;
    }

     /**
     * Aplica a operação de abertura em uma imagem binária.
    *
    * @param im Um array bidimensional de booleanos representando a imagem binária.
    * @param n O tamanho do elemento estruturante.
    * @return Um array bidimensional de booleanos representando a imagem binária resultante da abertura.
    */
    public static boolean[][] bwOpen(boolean[][] im, int n) {
        return Image.bwDilate(Image.bwErode(im, n), n);
    }

    /**
     * Aplica a operação de fechamento em uma imagem binária.
     *
     * @param im Um array bidimensional de booleanos representando a imagem binária.
     * @param n O tamanho do elemento estruturante.
     * @return Um array bidimensional de booleanos representando a imagem binária resultante do fechamento.
     */
    public static boolean[][] bwClose(boolean[][] im, int n) {
        return Image.bwErode(Image.bwDilate(im, n), n);
    }


    /**
     * Esta função retorna uma imagem preenchida com uma linha preta entre os pontos especificados.
     *
     * @param im Uma matriz de booleanos representando a imagem.
     * @param dots Uma matriz de inteiros especificando os pontos de início e fim.
     * @return A imagem com a linha preta entre os pontos especificados.
     */
    public static boolean[][] bwLine(boolean[][] im, int[] dots) {
        im = Image.copy(im);
        int i = dots[0], j = dots[1];

        double dNorth, dSouth, dEast, dWest;
        while (i != dots[2] && j != dots[3]) {
            dNorth = Math.sqrt(Math.pow((i - 1) - dots[2], 2) + Math.pow((j) - dots[3], 2));
            dSouth = Math.sqrt(Math.pow((i + 1) - dots[2], 2) + Math.pow((j) - dots[3], 2));
            dEast = Math.sqrt(Math.pow((i) - dots[2], 2) + Math.pow((j + 1) - dots[3], 2));
            dWest = Math.sqrt(Math.pow((i) - dots[2], 2) + Math.pow((j - 1) - dots[3], 2));

            if (dNorth <= dSouth && dNorth <= dEast && dNorth <= dWest) {
                i--;
            } else if (dSouth <= dNorth && dSouth <= dEast && dSouth <= dWest) {
                i++;
            } else if (dEast <= dSouth && dEast <= dNorth && dEast <= dWest) {
                j++;
            } else if (dWest <= dSouth && dWest <= dNorth && dWest <= dEast) {
                j--;
            }
            im[i][j] = true;
        }

        return im;
    }

     /**
     * Aplica o algoritmo de esqueletização de um array de booleanos bidimensionais.
     * 
     * @param bw O array de booleanos bidimensionais.
     * @return O array de booleanos bidimensionais esqueletizado.
     */
    public static boolean[][] skeletonize(boolean[][] bw) {
        //Loockup arrays
        int[][] a = {
                //Phase 0
                {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56, 60,
                62, 63, 96, 112, 120, 124, 126, 127, 129, 131, 135,
                143, 159, 191, 192, 193, 195, 199, 207, 223, 224,
                225, 227, 231, 239, 240, 241, 243, 247, 248, 249,
                251, 252, 253, 254},

                //Phase 1
                {7, 14, 28, 56, 112, 131, 193, 224},
                //Phase 2
                {7, 14, 15, 28, 30, 56, 60, 112, 120, 131, 135,
                193, 195, 224, 225, 240},
                //Phase 3
                {7, 14, 15, 28, 30, 31, 56, 60, 62, 112, 120,
                124, 131, 135, 143, 193, 195, 199, 224, 225, 227,
                240, 241, 248},
                //Phase 4
                {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120,
                124, 126, 131, 135, 143, 159, 193, 195, 199, 207,
                224, 225, 227, 231, 240, 241, 243, 248, 249, 252},
                //Phase 5
                {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120,
                124, 126, 131, 135, 143, 159, 191, 193, 195, 199,
                207, 224, 225, 227, 231, 239, 240, 241, 243, 248,
                249, 251, 252, 254}
        };

        boolean[][] border;
        boolean[][] output = copy(bw);

        boolean modified = true;
        do {
            modified = false;

            //Phase 0 - Border
            border = new boolean[output.length][output[0].length];

            for (int i = 1; i < output.length - 1; i++) {
                for (int j = 10; j < output[0].length - 1; j++) {
                    if (output[i][j]) {
                        boolean[][] neighbours = Image.neighbours(output, i, j);
                        if (Arrays.stream(a[0]).anyMatch(x -> x == neighbourWeight(neighbours))) {
                            border[i][j] = true;
                        }
                    }
                }
            }

            //Phase 1 - 5
            for (int k = 1; k <= 5; k++) {
                for (int i = 0; i < output.length; i++) {
                    for (int j = 0; j < output[0].length; j++) {
                        if(border[i][j]){
                            boolean[][] neighbours = Image.neighbours(output, i, j);
                            if (Arrays.stream(a[k]).anyMatch(x -> x == neighbourWeight(neighbours))) {
                                output[i][j] = false;
                                border[i][j] = false;
                                modified = true;
                            }
                        }
                    }
                }
            }

            //Phase 6 - unmarking remaining borders
            output = sum(output, border);
        } while (modified);
        return output;
    }

    /**
    * Calcula os vizinhos de um pixel dado (x,y) em uma imagem
    * @param im boolean[][] Imagem a ser analisada
    * @param x int Coordenada x do pixel
    * @param y int Coordenada y do pixel
    * @return boolean[][] Vizinhos do pixel (x,y)
    */
    public static boolean[][] neighbours(boolean[][] im, int x, int y){
        boolean[][] neighbours = new boolean[3][3];

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                neighbours[i+1][j+1] = im[x+i][y+j];
            }
        }

        return neighbours;
    }
    
    /**
    * Esta função calcula o peso dos vizinhos.
    *
    * @param neighbours a matriz de vizinhança 
    * @return o peso dos vizinhos
    */
    public static int neighbourWeight(boolean[][] neighbours) {
        int[][] neighborhood = {
                {128, 1, 2},
                {64,  0, 4},
                {32, 16, 8}
        };

        int sum = 0;
        for (int i = 0; i < neighbours.length; i++) {
            for (int j = 0; j < neighbours[0].length; j++) {
                if (neighbours[i][j])
                    sum += neighborhood[i][j];
            }
        }

        return sum;
    }

    /*public static boolean[][] bwThin(boolean[][] bw) {
        boolean[][] thin = new boolean[bw.length][bw[0].length];
        int viz;

        for (int i = 0; i < bw.length; i++) {
            for (int j = 0; j < bw[0].length; j++) {
                for (int x = -1; x <= 1; x++) {
                    if (bw[i][j]) {
                        viz = 0;
                        for (int y = -1; y <= 1; y++) {
                            if (bw[i + x][j + y] && (x != 0 && y != 0))
                                viz++;
                        }

                        //endpts[i][j] = (viz == 1);
                    }
                }
            }
        }

        return null;
    }*/
    
    /**
    * Esta função encontra os pontos finais de um 'esqueleto' a partir de uma matriz binária.
    *
    * @param bw Matriz binária
    * @return Pontos finais
    */
    public static boolean[][] bwEndPoints(boolean[][] bw) {
        boolean[][] endpoints = new boolean[bw.length][bw[0].length];

        int[] lookup = {1, 2, 4, 8, 16, 32, 64, 128, 3, 6, 12, 24, 48, 96, 192, 129};

        for (int i = 1; i < bw.length-1; i++) {
            for (int j = 1; j < bw[0].length-1; j++) {
                if (bw[i][j]) {
                    boolean[][] neighbours = neighbours(bw, i, j);
                    endpoints[i][j] = (Arrays.stream(lookup).anyMatch(x -> x == neighbourWeight(neighbours)));
                }
            }
        }

        return endpoints;
    }

    /**
    * Esta função encontra os pontos finais em um ArrayList de um 'esqueleto' a partir de uma matriz binária.
    *
    * @param bw Matriz binária
    * @return Pontos finais em um ArrayList
    */
    public static ArrayList<int[]> bwEndPointsArray(boolean[][] bw){
        boolean[][] endpointsimage = bwEndPoints(bw);
        ArrayList<int[]> endpoints = new ArrayList<int[]>();
        for(int i=0; i<endpointsimage.length; i++){
            for(int j=0; j< endpointsimage[0].length; j++){
                if(endpointsimage[i][j])
                    endpoints.add(new int[]{i, j});
            }
        }
        return endpoints;
    }

    /**
     * Esta função recebe um array de booleanos bidimensional e retorna uma lista de propriedades contendo informações sobre as regiões identificadas na imagem.
     * @param im O array de boolean bidimensional contendo a imagem.
     * @return Uma lista de propriedades contendo informações sobre as regiões identificadas na imagem.
     */
    public static ArrayList<Properties> regionProps(boolean[][] im) {
        int[][] lb = Image.bwLabel(im);
        ArrayList<Properties> prop = new ArrayList<>();

        int qtd = Image.max(lb);

        int minRow, minCol;
        int maxRow, maxCol;
        int area;

        for (int r = 1; r <= qtd; r++) {
            boolean[][] imRegion = new boolean[im.length][im[0].length];

            Properties p = new Properties();
            minRow = im.length;
            minCol = im[0].length;
            maxRow = 0;
            maxCol = 0;
            area = 0;
            for (int i = 0; i < im.length; i++) {
                for (int j = 0; j < im[0].length; j++) {
                    if (lb[i][j] == r) {
                        imRegion[i][j] = true;
                        area++;
                        if (i < minRow)
                            minRow = i;
                        if (j < minCol)
                            minCol = j;
                        if (i > maxRow)
                            maxRow = i;
                        if (j > maxCol)
                            maxCol = j;
                    }
                }
            }

            p.image = Image.imCrop(imRegion, minRow, minCol, maxRow, maxCol);

            p.boundingBox[0] = minRow;
            p.boundingBox[1] = minCol;
            p.boundingBox[2] = maxRow;
            p.boundingBox[3] = maxCol;

            p.area = area;
            if (area > 0)
                if(area == 1)
                    p.image = new boolean[1][1];
                prop.add(p);
        }

        //amo você, tenha um bom dia de trabalho
        return prop;
    }

    /**
    * Cria uma matriz de zeros do tamanho especificado
    * 
    * @param height Altura da matriz
    * @param width Largura da matriz
    * 
    * @return Uma matriz de inteiros de zeros do tamanho especificado
    */
    public static int[][] zeros(int height, int width) {
        return new int[height][width];
    }

    /**
    * Calcula o valor mínimo de uma matriz de inteiros.
    * 
    * @param im Matriz de inteiros.
    * @return O menor valor da matriz.
    */
    public static int min(int[][] im) {
        im = Image.copy(im);
        for (int[] ints : im) Arrays.sort(ints);

        int[] v = new int[im.length];

        for (int i = 0; i < im.length; i++)
            v[i] = im[i][0];

        Arrays.sort(v);

        return v[0];
    }

    /**
     * Recebe um array bidimensional de inteiros e retorna o maior valor contido nele.
     * 
     * @param im O array bidimensional de inteiros a ser analisado
     * @return O maior valor contido no array
     */
    public static int max(int[][] im) {
        im = Image.copy(im);
        for (int[] ints : im) Arrays.sort(ints);

        int[] v = new int[im.length];

        for (int i = 0; i < im.length; i++)
            v[i] = im[i][im[0].length - 1];

        Arrays.sort(v);

        return v[v.length - 1];
    }

    /**
     * Esta função copia uma matriz tridimensional.
     * 
     * @param im A matriz tridimensional a ser copiada.
     * @return Uma nova matriz tridimensional contendo uma cópia da matriz de entrada.
     */
    public static int[][][] copy(int[][][] im) {
        int[][][] newImage = new int[im.length][im[0].length][3];
        for (int i = 0; i < im.length; i++)
            for (int j = 0; j < im[0].length; j++)
                System.arraycopy(im[i][j], 0, newImage[i][j], 0, im[0][0].length);
        return newImage;
    }

    /**
     * Esta função copia uma matriz bidimensional.
     * 
     * @param im A matriz bidimensional a ser copiada.
     * @return Uma nova matriz bidimensional contendo uma cópia da matriz de entrada.
     */
    public static int[][] copy(int[][] im) {
        int[][] newImage = new int[im.length][im[0].length];
        for (int i = 0; i < im.length; i++)
            System.arraycopy(im[i], 0, newImage[i], 0, im[0].length);
        return newImage;
    }

    /**
     * Esta função copia uma matriz binária.
     * 
     * @param im A matriz binária a ser copiada.
     * @return Uma nova matriz binária contendo uma cópia da matriz de entrada.
     */
    public static boolean[][] copy(boolean[][] im) {
        boolean[][] newImage = new boolean[im.length][im[0].length];
        for (int i = 0; i < im.length; i++)
            System.arraycopy(im[i], 0, newImage[i], 0, im[0].length);
        return newImage;
    }


    /**
     * Esta função realiza a soma de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser somada.
     * @param im2 A segunda matriz de inteiros a ser somada.
     * @return Uma nova matriz contendo a soma das duas matrizes de inteiros.
     */
    public static int[][] sum(int[][] im1, int[][] im2) {
        int[][] result = new int[im1.length][im1[0].length];
        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                result[i][j] = im1[i][j] + im2[i][j];
        return result;
    }

    /**
     * Esta função realiza a soma de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser somada.
     * @param im2 A segunda matriz de inteiros a ser somada.
     * @return Uma nova matriz contendo a soma das duas matrizes de inteiros.
     */
    public static int[][][] sum(int[][][] im1, int[][][] im2) {
        int[][][] result = new int[im1.length][im1[0].length][3];
        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                for (int c = 0; c < im1[0][0].length; c++)
                    result[i][j][c] = im1[i][j][c] + im2[i][j][c];
        return result;
    }

    /**
     * Esta função realiza a soma de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser somada.
     * @param im2 A segunda matriz de inteiros a ser somada.
     * @return Uma nova matriz contendo a soma das duas matrizes de inteiros.
     */
    public static boolean[][] sum(boolean[][] im1, boolean[][] im2) {
        boolean[][] result = new boolean[im1.length][im1[0].length];

        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                result[i][j] = (im1[i][j] || im2[i][j]);
        return result;
    }

    /**
     * Esta função realiza a subtração de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser subtraida.
     * @param im2 A segunda matriz de inteiros para subtrair.
     * @return Uma nova matriz contendo a subtração das duas matrizes de inteiros.
     */
    public static int[][] sub(int[][] im1, int[][] im2) {
        int[][] result = Image.copy(im1);
        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                result[i][j] = im1[i][j] - im2[i][j];
        return result;
    }

    /**
     * Esta função realiza a subtração de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser subtraida.
     * @param im2 A segunda matriz de inteiros para subtrair.
     * @return Uma nova matriz contendo a subtração das duas matrizes de inteiros.
     */
    public static int[][][] sub(int[][][] im1, int[][][] im2) {
        int[][][] result = Image.copy(im1);
        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                for (int c = 0; c < im1[0][0].length; c++)
                    result[i][j][c] = im1[i][j][c] - im2[i][j][c];
        return result;
    }

    /**
     * Esta função realiza a subtração de duas matrizes de inteiros.
     * 
     * @param im1 A primeira matriz de inteiros a ser subtraida.
     * @param im2 A segunda matriz de inteiros para subtrair.
     * @return Uma nova matriz contendo a subtração das duas matrizes de inteiros.
     */
    public static boolean[][] sub(boolean[][] im1, boolean[][] im2) {
        boolean[][] result = Image.copy(im1);
        for (int i = 0; i < im1.length; i++)
            for (int j = 0; j < im1[0].length; j++)
                if ((im2[i][j])) result[i][j] = false;
        return result;
    }

	/*public static int[][] _bwLabel(boolean[][] im) {
		int[][] imRotulo = zeros(im.length, im[0].length);
		
		List<HashSet<Integer>> regioes = new ArrayList<>();
		List<Integer> todasRegioes = new ArrayList<>();
		int rotulo = 40, e1, e2;
		boolean flag;
		
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[0].length; j++) {
				if (im[i][j]) { //se for regi�o
					
					boolean vizCima;
					boolean vizLado;
					
					if(i == 0)
						vizCima = false;
					else
						vizCima = im[i - 1][j];
							
					if(j == 0)
						vizLado = false;
					else
						vizLado = im[i][j - 1];
					
					if (!vizCima && !vizLado) { //se for nova
						rotulo += 5;
						imRotulo[i][j] = rotulo;
						todasRegioes.add(rotulo);
					} else if (vizCima && !vizLado) //R�tulo de esquerda
						imRotulo[i][j] = imRotulo[i - 1][j];
					else if (!vizCima && vizLado) //R�tulo de cima
						imRotulo[i][j] = imRotulo[i][j - 1];
					else if ((vizCima && vizLado) && (imRotulo[i - 1][j] == imRotulo[i][j - 1])) //R�tulos iguais
						imRotulo[i][j] = imRotulo[i - 1][j];
					else if ((vizCima && vizLado ) && (imRotulo[i - 1][j] != imRotulo[i][j - 1])) { //R�tulos diferentes
						imRotulo[i][j] = imRotulo[i][j - 1];
						//Guardar erro
						e1 = imRotulo[i - 1][j];
						e2 = imRotulo[i][j - 1];
						
						flag = false;
						for(HashSet<Integer> lista : regioes) {
							for(Integer num : lista) {
								if(num == e1) {
									lista.add(e2);
									while(todasRegioes.remove(Integer.valueOf(e2))) {};
									flag = true;
									break;
								}else if(num == e2) {
									lista.add(e1);
									while(todasRegioes.remove(Integer.valueOf(e1))) {};
									flag = true;
									break;
								}
							}
							if(flag)
								break;
						}
						
						if(!flag) {
							regioes.add(new HashSet<>());
							regioes.get(regioes.size()-1).add(e1);
							regioes.get(regioes.size()-1).add(e2);
						}
					}
				}
			}
		}
		
		
		List<HashSet<Integer>> novasRegioes = new ArrayList<>();
		HashSet<Integer> listaAux = null;
		//Adicionar elementos sem erros
		for(Integer num : todasRegioes) {
			flag = false;
			for(HashSet<Integer> lista : regioes) {
				for(Integer num2 : lista) {
					if(num.equals(num2)) {
						flag = true;
						listaAux = (HashSet<Integer>) lista.clone();
						break;
					}
				}
				if(flag)
					break;
			}
			if(flag) {
				novasRegioes.add(listaAux);
			}else {
				novasRegioes.add(new HashSet<>());
				novasRegioes.get(novasRegioes.size()-1).add(num);				
			}
		}
		
		//Corre��o de erros
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[0].length; j++) {
				if (im[i][j]) { //se for regi�o
					for(int c = 0; c < novasRegioes.size(); c++) {
						HashSet<Integer> conjunto = novasRegioes.get(c);
						if(conjunto.contains(imRotulo[i][j])) {
							imRotulo[i][j] = (c+1);
							break;
						}
					}
				}
			}
		}
		
		return imRotulo;
	}*/

    /**
   * Método que redimensiona uma imagem.
   * 
   * @param im A imagem a ser redimensionada.
   * @return A imagem redimensionada.
   */
    public static int[][][] _imResize(int[][][] im) {
        int[][][] image = new int[im.length / 2][im[0].length / 2][3];
        int x = 0;
        int y;

        for (int i = 0; i < image.length; i ++) {
            y = 0;
            for (int j = 0; j < image[0].length; j++) {
                if(x >= im.length)
                    x = im.length-1;
                if(y >= im[0].length)
                    y = im[0].length-1;

                image[i][j] = im[x][y];
                y+=2;
            }
            x+=2;
        }

        return image;
    }
}



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.*;

public class ImageEditor {
    /*
     * Function to convert an image to grayscale.
     */
    public static BufferedImage convertToGrayScale(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outputImage.setRGB(j, i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }

    /*
     * Function to rotate an image by right by 90 degrees.
     */
    public static BufferedImage rotateImageRight(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width,BufferedImage.TYPE_INT_RGB);
        int x=height-1;
        int y;
        for (int i = 0; i < height; i++) {
            y=0;
            for (int j = 0; j < width; j++) {
                outputImage.setRGB(x, y, inputImage.getRGB(j, i));
                y++;
            }
            x--;
        }
        return outputImage;
    }

    /*
     * Function to rotate an image by left by 90 degrees.
     */

    public static BufferedImage rotateImageLeft(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width,BufferedImage.TYPE_INT_RGB);
        int x=0;
        int y;
        for(int i=0;i<height;i++){
            y=width-1;
            for(int j=0;j<width;j++){
                outputImage.setRGB(x, y, inputImage.getRGB(j, i));
                y--;
            }
            x++;
        }
        return outputImage;
    }

    /*
     * Function to flip an image horizontally.
     */

    public static BufferedImage flipImageHorizontal(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        int x;
        for(int i=0;i<height;i++){
            x=width-1;
            for(int j=0;j<width;j++){
                outputImage.setRGB(x, i, inputImage.getRGB(j, i));
                x--;
            }
        }
        return outputImage;
    }

    /*
     * Function to flip an image vertically.
     */
    
    public static BufferedImage flipImageVertical(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        int y=height - 1;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                outputImage.setRGB(j, y, inputImage.getRGB(j, i));
            }
            y--;
        }
        return outputImage;
    }
    
    /*
     * Function to increase/decrease the brightness of an image by a provided value
     */

    public static BufferedImage changeBrightness(BufferedImage inputImage, int increase) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen();
                red = red + (increase * red / 100);
                blue = blue + (increase * blue) / 100;
                green = green + (increase * green) / 100;
                if (red > 255)
                    red = 255;
                if (blue > 255)
                    blue = 255;
                if (green > 255)
                    green = 255;
                if (red < 0)
                    red = 0;
                if (blue < 0)
                    blue = 0;
                if (green < 0)
                    green = 0;
                Color newPixel = new Color(red, green, blue);
                outputImage.setRGB(j, i, newPixel.getRGB());
            }
        }
        return outputImage;
    }

    /*
     * Function to blur an image by a provided value
     */

    private static BufferedImage blurImage(BufferedImage inputImage, int value) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < height / value; i++) {
            for (int j = 0; j < width / value; j++) {
                ArrayList<Color> pixels = new ArrayList<>();
                for (int k = i * value; k < (i * value) + value; k++) {
                    for (int l = j * value; l < (j * value) + value; l++) {
                        pixels.add(new Color(inputImage.getRGB(l, k)));
                    }
                }
                int[] rgb = { 0, 0, 0 };
                for (Color pixel : pixels) {
                    rgb[0] += pixel.getRed();
                    rgb[1] += pixel.getGreen();
                    rgb[2] += pixel.getBlue();
                }
                rgb[0] /= pixels.size();
                rgb[1] /= pixels.size();
                rgb[2] /= pixels.size();
                Color newPixel = new Color(rgb[0], rgb[1], rgb[2]);
                for (int k = i * value; k < (i * value) + value; k++) {
                    for (int l = j * value; l < (j * value) + value; l++) {
                        outputImage.setRGB(l, k, newPixel.getRGB());
                    }
                }
            }
        }

        return outputImage;
    }

    public static void printPixelValues(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // System.out.print(inputImage.getRGB(j, i) + " ");
                Color pixel = new Color(inputImage.getRGB(j, i));
                System.out.print("(" + pixel.getRed() + " " + pixel.getBlue() + " "
                        + pixel.getGreen() + ")");
                // pixel.getGreen());
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your filename with extension else type unsplash.jpg");
        String filename=sc.nextLine();
        File inputFile = new File(filename);
        
        try {
            BufferedImage inputImage = ImageIO.read(inputFile);
            
            System.out.println("Enter 1 to grayscale");
            System.out.println("Enter 2 to change brightness");
            System.out.println("Enter 3 to rotate right");
            System.out.println("Enter 4 to rotate left");
            System.out.println("Enter 5 to flip horizontal");
            System.out.println("Enter 6 to flip vertical");
            System.out.println("Enter 7 to print pixel values");
            System.out.println("Enter 8 to blur image");
            int x=sc.nextInt();
            switch(x){
                case 1:
                    BufferedImage grayScale = convertToGrayScale(inputImage);
                    File graScaleImage = new File("grayscaleImage.jpg");
                    ImageIO.write(grayScale, "jpg", graScaleImage);
                    break;
                case 2:
                    System.out.println("Enter the value to increase/decrease brightness");
                    int increase=sc.nextInt();
                    BufferedImage changedBrightness = changeBrightness(inputImage, increase);
                    File changedBrightnessImage = new File("changedBrightnessImage.jpg");
                    ImageIO.write(changedBrightness, "jpg", changedBrightnessImage);
                    break;
                case 3:
                    BufferedImage rotateRight = rotateImageRight(inputImage);
                    File rotateRightImage = new File("rotateRightImage.jpg");
                    ImageIO.write(rotateRight, "jpg", rotateRightImage);
                    break;
                case 4:
                    BufferedImage rotateLeft = rotateImageLeft(inputImage);
                    File rotateLeftImage = new File("rotateLeftImage.jpg");
                    ImageIO.write(rotateLeft, "jpg", rotateLeftImage);
                    break;
                case 5:
                    BufferedImage flipHorizontal = flipImageHorizontal(inputImage);
                    File flipHorizontalImage = new File("flipHorizontalImage.jpg");
                    ImageIO.write(flipHorizontal, "jpg", flipHorizontalImage);
                    break;
                case 6:
                    BufferedImage flipVertical = flipImageVertical(inputImage);
                    File flipVerticalImage = new File("flipVerticalImage.jpg");
                    ImageIO.write(flipVertical, "jpg", flipVerticalImage);
                    break;
                case 7:
                    printPixelValues(inputImage);
                    break;
                case 8:
                    System.out.println("Enter the value to blur image");
                    int value=sc.nextInt();
                    BufferedImage blur = blurImage(inputImage, value);
                    File blurImage = new File("blurImage.jpg");
                    ImageIO.write(blur, "jpg", blurImage);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
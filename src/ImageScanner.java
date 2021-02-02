import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class ImageScanner{
		BufferedImage image;
		int[] colorValues;
		public ImageScanner(BufferedImage image){
				this.image = image;
				colorValues = new int[256];
		}	
		public int[] scan(){
				for(int y =0; y < image.getHeight(); y++){
						for(int x = 0; x < image.getWidth(); x++){
								Color color  = new Color(image.getRGB(x,y));
								colorValues[color.getRed()]++;
								colorValues[color.getBlue()]++;
								colorValues[color.getGreen()]++;
								colorValues[color.getAlpha()]++;
						}
				}
				return colorValues;
		}
		public BufferedImage getImage(){
				return image;
		}
}
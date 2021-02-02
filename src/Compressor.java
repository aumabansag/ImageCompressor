import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Compressor{
		private File file;
		private BufferedImage image;
		private ImageScanner imageScanner;
		private TreeGenerator treeGenerator;		
		private String[] encoding;
		public Compressor(File file, String treeName){
				try{
						this.file = file;
						image = ImageIO.read(file);
						imageScanner = new ImageScanner(image);
						treeGenerator = new TreeGenerator();
				}catch(IOException e){
						e.printStackTrace();
				}
				Node huffTree = treeGenerator.generateTree(imageScanner.scan(),treeName);
				encoding = treeGenerator.getEncoding();
		}
		public void compress(String destination){
				String compressed = "";
				for(int y =0; y < image.getHeight(); y++){
						for(int x = 0; x < image.getWidth(); x++){
								Color color  = new Color(image.getRGB(x,y));

								compressed+=encoding[color.getAlpha()];
								compressed+=encoding[color.getRed()];
								compressed+=encoding[color.getGreen()];
								compressed+=encoding[color.getBlue()];
								//System.out.println(""+color.getRed()+"  "+color.getGreen()+"  "+color.getBlue()+"  "+color.getAlpha());
						}
				}
				for(int x =0; x < compressed.length() % 8; x++){
							compressed+="0";
				}
				//check if file exists then create a newfile
				//
				//
				//System.out.println(compressed);
				byte[] tokens = new byte[compressed.length()/8];


				try{
						FileOutputStream fos = new FileOutputStream(new File("src/compressedFiles/"+destination),false);


						int height = image.getHeight();
						int width = image.getWidth();

						byte[] heightBytePair = turnToBytePair(height);
						byte[] widthBytePair = turnToBytePair(width);

						byte[] dimensions = {widthBytePair[0], widthBytePair[1],heightBytePair[0], heightBytePair[1]};
						fos.write(dimensions);

						int counter =0;
						while(compressed.length() > 0){
								String currByteString = compressed.substring(0,8);								
								int byteToBe = Integer.parseInt(currByteString,2)-128;

								byte yeet = (byte)byteToBe;
								tokens[counter] = yeet;


								compressed = compressed.substring(8, compressed.length());
								counter++;
						}
						fos.write(tokens);
						fos.close();
				}catch(IOException e){
						e.printStackTrace();
				}
		}
		private byte[] turnToBytePair(int x){
				byte[] bytePair = new byte[2];

				int upper = x/128;
				int lower = x % 128;
				int twoMultiple = 64; 


				String upperBinary = "";
				String lowerBinary = "";
				for(twoMultiple = 64; twoMultiple > 0; twoMultiple/=2){
						if(upper >= twoMultiple){
								upperBinary +="1";
								upper -= twoMultiple;
						}
						else{
								upperBinary += "0";
						}
				}

				for(twoMultiple = 64; twoMultiple > 0; twoMultiple/=2){
						if(lower >= twoMultiple){
								lowerBinary +="1";
								lower -= twoMultiple;
						}
						else{
								lowerBinary += "0";
						}
				}


				bytePair[0] = (byte)Integer.parseInt(upperBinary,2);
				bytePair[1] = (byte)Integer.parseInt(lowerBinary,2);


				return bytePair;
		}

}
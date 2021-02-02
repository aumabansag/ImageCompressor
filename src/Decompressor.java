import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
public class Decompressor{
		private int width;
		private int height;
		private BufferedImage decompressedImage;
		private File compressedFile;
		private File tree;
		private int[] huffTree;		
		public Decompressor(File compressedFile){
						this.compressedFile =compressedFile;
		}
		public BufferedImage decompress(File treeFile){

				try{

						/********get dimensions********/
						FileInputStream fr = new FileInputStream(compressedFile);
						byte[] byteArr = new byte[4];
						fr.read(byteArr);
						width = byteArr[0]*128+byteArr[1];
						height = byteArr[2]* 128+byteArr[3];
						//System.out.println(width+" ----  "+height);
						//fr.close();
						//System.out.println("height" + height);
						//System.out.println("width" + width);
						decompressedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
						
						/*******reconstruct tree*********/
						reconstructTree(treeFile);


						//rebuild bitString
						byte[] currByte = new byte[1];
						String bitString = "";
						int curr;
						while(fr.read(currByte) != -1){
								curr = currByte[0] + 128;
								bitString+=getBitString(curr);
						}
						//System.out.println(bitString.length());
						int imageX = 0;
						int imageY = 0;
						int currentIndex = 0; // index in the huffTree
						int currStringIndex = 0;// index in the bitString
						
						int [] colorValues = new int[4];
						while(true){//iterate through all pixels
								
								if(imageX >= width){
										imageX = 0;
										imageY++;
								}
								if(imageY >= height){
										break;
								}

								//get the four values for current pixel;
								for(int x =0; x< 4; x++){
										currentIndex = 0;
										while(true){
												if(bitString.charAt(currStringIndex) == '0'){
														currentIndex = currentIndex*2+1;
												}
												else if(bitString.charAt(currStringIndex) == '1'){
														currentIndex = currentIndex*2+2;
												}
												//System.out.println("currIndex: "+ currentIndex + " bit: "+bitString.charAt(currStringIndex)+" stringIndex: "+currStringIndex);
												if(huffTree[currentIndex] != -1){
														colorValues[x] = huffTree[currentIndex];
														//System.out.println(colorValues[x]+"SET!!");
														currStringIndex++;
														break;
												}
												currStringIndex++;
										}
								}
								Color color = new Color(colorValues[1], colorValues[2], colorValues[3], colorValues[0]);
								decompressedImage.setRGB(imageX, imageY, color.getRGB());


								//System.out.println(imageX+","+imageY+" PixelPrinted!");
								imageX++;
						}
						//System.out.println("RGB:"+decompressedImage.getRGB(1,1));
				}catch(IOException e){
					e.printStackTrace();
				}
				return decompressedImage;
		}
		private void reconstructTree(File fileName){
				try{
						FileReader fr = new FileReader(fileName);
						BufferedReader br = new BufferedReader(fr);

						//copy encodings into a string array
						String[] readTree = new String[256];
						for(int x =0; x < 256; x++){
								readTree[x]  = br.readLine();
						}
						br.close();
						fr.close();

						for(int x = 0; x < readTree.length; x++){
						}

						//find tree height
						int height = 0;
						for(int x = 0; x < 256; x++){
								if(!readTree[x].equals("x") && readTree[x].length() > height){
										height = readTree[x].length();
								}
						}

						//initialize tree represented as an int array
						huffTree = new int[(int)Math.pow(2, height+1)-1];

						for(int x =0; x < huffTree.length; x++){
								huffTree[x] = -1;
						}
						for(int x = 0; x < 256; x++){
								if(!readTree[x].equals("x")){
										huffTree[getHuffIndex(readTree[x])] = x;
								}
						}




				}catch(IOException e){
						e.printStackTrace();
				}

		}
		private int getHuffIndex(String encoding){
				int huffIndex = 0;
				for(int x = 0; x < encoding.length(); x++){
						if(encoding.charAt(x) == '0'){
								huffIndex = huffIndex*2+1;
						}
						else if(encoding.charAt(x) == '1'){
								huffIndex = huffIndex*2+2;
						}
				}
				return huffIndex;
		}
		private String getBitString(int num){
				String bitString = "";
				for(int x = 128; x > 0; x /=2){
						if(num >= x){
								bitString+= "1";
								num -=x;
						}
						else{
								bitString += "0";
						}
						
				}
				return bitString;
		}
}
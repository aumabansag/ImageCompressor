import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
public class GUIHandler extends JFrame{
		private File imgChosen;
		private File cmpChosen;
		private File treeChosen;
		public GUIHandler(){}
		public void display(){
				JFrame frame = new JFrame("IMAGE COMPRESSION");
				frame.setBounds(1,1,1010,700);
				frame.setResizable(false);
				frame.setLayout(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel compressPanel = new JPanel();
				compressPanel.setLayout(null);
				compressPanel.setBounds(1,100,500,500);

				final JLabel original = new JLabel("");
				original.setBounds(1,1,500,300);
				original.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
				
				final JLabel imgSelected = new JLabel("No Image Selected");
				
				JButton chooserA = new JButton("Choose Image To Compress");
				chooserA.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JFileChooser jfc = new JFileChooser("src/uncompressedFiles");
						  FileNameExtensionFilter filter = new FileNameExtensionFilter(
							        "Image files", "jpg", "bmp", "png", "jpeg");
						  jfc.setFileFilter(filter);
						int returnVal = jfc.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION){
							imgChosen = jfc.getSelectedFile();
							imgSelected.setText("Selected "+imgChosen.getName());
						}
					}
				});
				
				//JTextField originalImageAddress = new JTextField("Original picture");
				final JTextField compressedFileDestination = new JTextField("Compressed File Name");
				final JTextField treeDestination = new JTextField(" Tree File Name");

				//originalImageAddress.setBounds(1,301,200,50);

				chooserA.setBounds(1,301,200,50);
				imgSelected.setBounds(1,351,200,50);
				compressedFileDestination.setBounds(1,401,200,50);
				treeDestination.setBounds(1,451,200,50);
				
				JButton compressButton = new JButton("Compress");
				compressButton.setBounds(250,301,200,50);

				compressButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(imgChosen == null){
								JOptionPane.showMessageDialog(null,"You have not chosen a file yet", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
							String imgName = imgChosen.getName();
							String extension =imgName.substring(imgName.length()-4, imgName.length());
							if(!(extension.equals(".BMP") ||extension.equals(".jpg") || extension.equals(".png"))){
								JOptionPane.showMessageDialog(null,"Unrecognized file type!", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
								try{
										BufferedImage buffIMG = ImageIO.read(imgChosen);
										original.setIcon(new ImageIcon(buffIMG));

								}catch(IOException exception){
									exception.printStackTrace();
								}
								Compressor compressor = new Compressor(imgChosen, treeDestination.getText()+".alm");
								compressor.compress(compressedFileDestination.getText()+".gbc");
						}
				});





				JPanel decompressPanel = new JPanel();
				decompressPanel.setLayout(null);
				decompressPanel.setBounds(501,100,500,500);
				
				final JLabel decompressed = new JLabel("");
				decompressed.setBounds(1,1,500,300);
				decompressed.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

				final JLabel compressedFileAddress = new JLabel("No File Selected");
				final JLabel treeAddress = new JLabel("No Tree File Selected");
				
				JButton chooserB = new JButton("Choose file to decompress");
				chooserB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JFileChooser jfc = new JFileChooser("src/compressedFiles");
						int returnVal = jfc.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION){
							cmpChosen = jfc.getSelectedFile();
							 FileNameExtensionFilter filter = new FileNameExtensionFilter(
								        "", "gbc");
							 jfc.setFileFilter(filter);
							compressedFileAddress.setText("Selected "+cmpChosen.getName());
						}
					}
				});
				
				JButton chooserC = new JButton("Choose file to decompress");
				chooserC.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JFileChooser jfc = new JFileChooser("src/treeFiles");
						int returnVal = jfc.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION){
							treeChosen = jfc.getSelectedFile();
							FileNameExtensionFilter filter = new FileNameExtensionFilter(
							        "", "alm");
						 jfc.setFileFilter(filter);
							treeAddress.setText("Selected "+treeChosen.getName());
						}
					}
				});
				
				chooserB.setBounds(1,301,200,50);
				compressedFileAddress.setBounds(1,351,200,50);
				chooserC.setBounds(1,401,200,50);
				treeAddress.setBounds(1,451,200,50);

				JButton decompressButton = new JButton("Decompress");
				decompressButton.setBounds(250,301,200,50);
				decompressButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(cmpChosen == null){
								JOptionPane.showMessageDialog(null,"You have not chosen a file yet", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
							else if(treeChosen == null){
								JOptionPane.showMessageDialog(null,"You have not chosen a tree file yet", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
							String cmpName = cmpChosen.getName();
							String extension =cmpName.substring(cmpName.length()-4, cmpName.length());
							if(!(extension.equals(".gbc"))){
								JOptionPane.showMessageDialog(null,"Only .gbc files can be decompressed!", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
							String treeName = treeChosen.getName();
							String extension2 =treeName.substring(treeName.length()-4, treeName.length());
							if(!(extension2.equals(".alm"))){
								JOptionPane.showMessageDialog(null,"Only .alm files are valid tree files!", "ERROR", JOptionPane.ERROR_MESSAGE );
								return;
							}
								Decompressor decompressor = new Decompressor(cmpChosen);
								BufferedImage expandedImg = decompressor.decompress(treeChosen);
								decompressed.setIcon(new ImageIcon(expandedImg));

						}
				});

				//decompressPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
				//decompressPanel.setBackground(Color.RED);


				JPanel titlePanel = new JPanel();
				titlePanel.setLayout(null);
				titlePanel.setBackground(Color.PINK);
				titlePanel.setBounds(1,1,1020,100);
				JLabel titleA = new JLabel("COMPRESSOR");
				titleA.setBounds(170,50,500,50);
				JLabel titleB = new JLabel("DECOMPRESSOR");
				titleB.setBounds(681,50,500,50);
				JLabel nameLabel = new JLabel("MABANSAG, CALVO IMAGE DE/COMPRESSOR");
				nameLabel.setBounds(100,1,1010,50);
				Font font = new Font("Rockwell", Font.PLAIN, 24);
				Font font2 = new Font("Rockwell", Font.BOLD, 36);
				titleA.setFont(font);
				titleB.setFont(font);
				nameLabel.setFont(font2);


				titlePanel.add(titleA);
				titlePanel.add(titleB);
				titlePanel.add(nameLabel);
				compressPanel.add(original);
				compressPanel.add(imgSelected);
				compressPanel.add(compressedFileDestination);
				compressPanel.add(treeDestination);
				compressPanel.add(compressButton);
				compressPanel.add(chooserA);


				decompressPanel.add(decompressed);
				decompressPanel.add(compressedFileAddress);
				decompressPanel.add(treeAddress);
				decompressPanel.add(decompressButton);
				decompressPanel.add(chooserB);
				decompressPanel.add(chooserC);


				frame.add(compressPanel);
				frame.add(decompressPanel);
				frame.add(titlePanel);


				titlePanel.setVisible(true);
				compressPanel.setVisible(true);
				decompressPanel.setVisible(true);
				frame.setVisible(true);
		}
}
import java.io.*;
public class TreeGenerator{
		HeapQueue heapQ;
		String[] encoding;
		public TreeGenerator(){
				heapQ = new HeapQueue(256);
				encoding = new String[256];
		}
		private void initializeQueue(int[] arr){
				 Node[] nodeArr = generateNodes(arr);
				for(int x = 0; x < nodeArr.length; x++){
						if(nodeArr[x] != null){
								heapQ.enqueue(new Node(nodeArr[x]));
						}
				}
		}
		private Node[] generateNodes(int[] arr){
				Node[] nodeArr = new Node[arr.length];
				for(int x = 0; x< arr.length; x++){
						if(arr[x] != 0){
								nodeArr[x] = new Node(x, arr[x]);
						}
				}
				return nodeArr;
		}	
		public Node generateTree(int[] arr, String fileName){
				initializeQueue(arr);
				Node nodeA = heapQ.dequeue();
				Node nodeB = heapQ.dequeue();
				while(nodeB != null){
						Node newBaby = new Node(-1, nodeA.data+nodeB.data);
						newBaby.left = nodeA.baby;
						newBaby.right = nodeB.baby;
						heapQ.enqueue(new Node(newBaby));
						nodeA = heapQ.dequeue();
						nodeB = heapQ.dequeue();
				}
				setEncoding(nodeA.baby, "");

				try{
						FileWriter fw = new FileWriter(new File("src/treeFiles/"+fileName),false);
						BufferedWriter bw = new BufferedWriter(fw);

						for(int x =0; x < encoding.length; x++){
								if(encoding[x] == null){
										bw.write("x");
								}
								else{
										bw.write(encoding[x]);
								}
								bw.newLine();
								bw.flush();
						}
						bw.close();
						fw.close();
				}catch(IOException e){
						e.printStackTrace();
				}
				//writeTree(fileName, nodeA);
				return nodeA;

		}
		private void writeTree(String fileName,Node root){
				try{
						File file = new File(fileName);
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);

				}catch(IOException e){
						e.printStackTrace();
				}

		}
		public String[] getEncoding(){
				return encoding;
		}	
		private void setEncoding(Node x, String huffCode){
				if(x == null){
					return;
				} 
				if(x.name != -1){
						encoding[x.name] = huffCode;
				}
				else{
						if(x.left != null){
								setEncoding(x.left, huffCode+"0");
						}
						if(x.right != null){
								setEncoding(x.right, huffCode+"1");
						}
				}
		}
}
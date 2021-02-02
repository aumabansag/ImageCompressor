public class BinaryHeap{
		Node[] nodeArr;
		private int currIndex;
		private int maxSize;
		public BinaryHeap(int maxSize){
				this.maxSize = maxSize;
				currIndex = 0;
				nodeArr = new Node[maxSize];
		}
		public void insert(Node x){
				if(currIndex == maxSize){
						return;
				}
				nodeArr[currIndex] = x;				
				fixUpward();
				currIndex++;
		}
		private void fixUpward(){
				int curr = currIndex;
				while(curr > 0){
						if(nodeArr[curr].data < nodeArr[curr/2].data){
								swap(curr, curr/2);
								curr = curr/2;
						}
						else{
							break;
						}
				}
		}
		public Node getMin(){
				if(currIndex == 0){
						return null;
				}
				Node output = nodeArr[0];
				swap(0,currIndex-1);
				currIndex--;
				fixDownward();
				return output;
		}
		private void fixDownward(){
				int curr = 0;
				while(curr < currIndex){
						if(inBounds(curr*2+2)){
								if(nodeArr[curr].data > nodeArr[curr*2+1].data 
									|| nodeArr[curr].data > nodeArr[curr*2+2].data){

										if(nodeArr[curr*2+2].data > nodeArr[curr*2+1].data){
												swap(curr, curr*2+1);
												curr = curr*2+1;
										}
										else{
												swap(curr, curr*2+2);
												curr = curr*2+2;
										}
								}
								else{
										break;
								}
						}
						else if(inBounds(curr*2+1)){
								if(nodeArr[curr].data > nodeArr[curr*2+1].data){

										swap(curr, curr*2+1);
										curr = curr*2+1;
								}
								else{
										break;
								}
						}
						else{
								break;
						}
				}
		}
		private boolean inBounds(int x){
				if(x < 0 || x >= currIndex){
						return false;
				}
				else{
						return true;
				}
		}
		private void swap(int source, int destination){
				Node temp = nodeArr[source];
				nodeArr[source] = nodeArr[destination];
				nodeArr[destination] = temp;
		}
}
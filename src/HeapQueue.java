public class HeapQueue{
		BinaryHeap heap;
		public HeapQueue(int maxSize){
				heap = new BinaryHeap(maxSize);
		}
		public void enqueue(Node x){
				heap.insert(x);
		}
		public Node dequeue(){
				Node output = heap.getMin();
				return output;
		}
}
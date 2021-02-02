public class Node{
		int data;
		int name;
		Node left;
		Node right;
		Node baby;
		public Node(int name, int data){
				left = null;
				right = null;
				this.name = name;
				this.data = data;
				baby = null;
		}
		public Node(Node baby){
				this.baby = baby;
				data = baby.data;
				name = -1;
				left = null;
				right = null;
		}
}
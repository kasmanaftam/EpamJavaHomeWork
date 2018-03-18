package HackerRank;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SolutionDay23 {


    static void levelOrder(NodeTree root) {
        //Write your code here
        Queue<NodeTree> nodes = new LinkedList<>();
        nodes.offer(root);
        while (true) {
            NodeTree buf = nodes.poll();
            if (buf == null) break;
            System.out.print(buf.data + " ");
            if (buf.left != null) {
                nodes.offer(buf.left);
            }
            if (buf.right != null) {
                nodes.offer(buf.right);
            }
        }
    }

    public static NodeTree insert(NodeTree root, int data) {
        if (root == null) {
            return new NodeTree(data);
        } else {
            NodeTree cur;
            if (data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        NodeTree root = null;
        while (T-- > 0) {
            int data = sc.nextInt();
            root = insert(root, data);
        }
        levelOrder(root);
    }
}


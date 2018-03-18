package HackerRank;

import java.util.Scanner;

public class SolutionDay22 {
    public static int getHeight(NodeTree root) {
        //Write your code here
        if (root == null) {
            return -1;
        }
        int heightRight = getHeight(root.right);
        int heightLeft = getHeight(root.left);
        return Math.max(heightLeft, heightRight) + 1;
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
        int height = getHeight(root);
        System.out.println(height);
    }
}


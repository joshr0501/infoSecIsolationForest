
public class TestBST
{
    public static void main(String [] args)
    {
        BinarySearchTree testTree = new BinarySearchTree();

        testTree.insert(testTree.getRoot(), 5);
        testTree.insert(testTree.getRoot(), 3);
        testTree.insert(testTree.getRoot(), 7);
        testTree.insert(testTree.getRoot(), 1);
        testTree.insert(testTree.getRoot(), 10);

        int distTo5 = testTree.search(testTree.getRoot(), 5);
        int distTo3 = testTree.search(testTree.getRoot(), 3);
        int distTo7 = testTree.search(testTree.getRoot(), 7);
        int distTo1 = testTree.search(testTree.getRoot(), 1);
        int distTo10 = testTree.search(testTree.getRoot(), 10);

        System.out.println(distTo5);
        System.out.println(distTo3);
        System.out.println(distTo7);
        System.out.println(distTo1);
        System.out.println(distTo10);


    }
}

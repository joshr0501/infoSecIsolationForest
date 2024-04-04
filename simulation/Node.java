public class Node
{
    int data;
    Node left, right;

    public Node(int givenData)
    {
        data  = givenData;
        left = right = null;
    }

    Node getLeft()
    {
        return left;
    }

    Node getRight()
    {
        return right;
    }

    void setLeft(Node newLeft)
    {
        left = newLeft;
    }

    void setRight(Node newRight)
    {
        right = newRight;
    }

    int getData()
    {
        return data;
    }
}

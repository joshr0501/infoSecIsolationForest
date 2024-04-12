public class BinarySearchTree {
    Node root;
    //Constructor
    BinarySearchTree()
    {
        root = null;
    }

    Node getRoot()
    {
        return root;
    }

    //Function to insert data, should call this and feed in the root as startingNode
    void insert(Node startingNode, int data)
    {
        //If the node we're at is null, we feed in the new node
        if(root == null)
        {
            root = new Node(data);
        }
        //Otherwise, we gotta start sorting
        else
        {
            if(data < startingNode.getData())
            {
                //If the data we're inserting is less than the data stored in the node,
                //try to place it left
                if(startingNode.getLeft() == null)
                {
                    startingNode.setLeft(new Node(data));
                }
                else
                {
                    insert(startingNode.getLeft(), data);
                }
            }
            else if(data > startingNode.getData())
            {
                //If the data we're inserting is greater than the data stored in the node,
                //try to place it right
                if(startingNode.getRight() == null)
                {
                    startingNode.setRight(new Node(data));
                }
                else
                {
                    insert(startingNode.getRight(), data);
                }
            }
            //If the data point is equal to an already-existing node, its placement should be skipped
        }
    }

    //Function to search for data, feed in count = 0 when calling this method
    int search(Node startingNode, int searchData)
    {
        //If root is null, return error value
        if(startingNode == null)
        {
            return -1;
        }

        //If we've reached the data point
        if(startingNode.getData() == searchData)
        {
            return 0;
        }

        //Otherwise, have to branch left or right
        else if(startingNode.getData() > searchData)
        {
            int leftDistance = search(startingNode.getLeft(), searchData);
            return (leftDistance == -1) ? -1 : leftDistance + 1;
        }
        else
        {
            int rightDistance = search(startingNode.getRight(), searchData);
            return (rightDistance == -1) ? -1 : rightDistance + 1;
        }
    }
}

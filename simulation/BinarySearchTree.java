public class BinarySearchTree {
    Node root;
    //Constructor
    BinarySearchTree()
    {
        root = null;
    }

    //Function to insert data, should call this and feed in the root as startingNode
    void insert(Node startingNode, int data)
    {
        //If the node we're at is null, we feed in the new node
        if(startingNode == null)
        {
            startingNode = new Node(data);
        }
        //Otherwise, we gotta start sorting
        else
        {
            if(data < startingNode.getData())
            {
                //If the data we're inserting is less than the data stored in the node,
                //try to place it left
                insert(startingNode.getLeft(), data);
            }
            else if(data > startingNode.getData())
            {
                //If the data we're inserting is greater than the data stored in the node,
                //try to place it right
                insert(startingNode.getRight(), data);
            }
            //If the data point is equal to an already-existing node, its placement should be skipped
        }
    }

    //Function to search for data, feed in count = 0 when calling this method
    int search(Node startingNode, int searchData, int count)
    {
        //If root is null, return error value
        if(root == null)
        {
            return -1;
        }

        //If we've reached the data point
        if(startingNode.getData() == searchData)
        {
            return count;
        }

        //Otherwise, have to branch left or right
        else if(startingNode.getData() > searchData)
        {
            count++;
            return search(startingNode.getLeft(), searchData, count);
        }
        else
        {
            count++;
            return search(startingNode.getRight(), searchData, count);
        }
    }
}

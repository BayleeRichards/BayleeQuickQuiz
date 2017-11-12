package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

public class BinaryTree 
{
    btNode root;
    String data = "";
    
    public void addNode(int _qnNum, String _topic)
    {
        //Create a new Node and initialize it
        btNode newNode = new btNode(_qnNum, _topic);
        
        //if there is no root, this becomes the root
        if (root == null)
        {
            root = newNode;
        }
        else
        {
            //set root as the Node we will start with as we traverse the tree
            btNode focusNode = root;
            //future parent for our new Node
            btNode parent;
            while (true)
            {
                //root is the top parent so we start there
                parent = focusNode;
                
                //check if the new node should go on the left side of the parent node
                if (_qnNum < focusNode.qnNum)
                {
                    //switch focus to the left child
                    focusNode = focusNode.leftChild;
                    
                    //if the left child has no children
                    if (focusNode == null)
                    {
                        //then place the new node on the left of it
                        parent.leftChild = newNode;
                        return;
                    }
                }
                else
                {
                    //if we get here, put the node on the right
                    focusNode = focusNode.rightChild;
                    
                    //if the right child has no children
                    if (focusNode == null)
                    {
                        //then plae the new node on the right of it
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }
    
    //all nodes are visited in ascending order
    //recursion is used to go to one node and then to go to its child nodes and so forth
    
    public void inOrderTraverseTree(btNode focusNode)
    {
        if (focusNode != null)
        {
            //traverse the left node
            inOrderTraverseTree(focusNode.leftChild);
            //visit the currently focused on node
            data += focusNode.qnNum + "-" + focusNode.topic + ", ";
            //traverse the right node
            inOrderTraverseTree(focusNode.rightChild);
        }
    }
    
//    public void preOrderTraverseTree(btNode focusNode, String data)
//    {
//        if (focusNode != null)
//        {
//            data += focusNode.qnNum + "-" + focusNode.topic + ", ";
//            
//            preOrderTraverseTree(focusNode.leftChild, data);
//            preOrderTraverseTree(focusNode.rightChild, data);
//        }
//    }
    
    public void preOrderTraverseTree(btNode focusNode)
    {
        if (focusNode != null)
        {
            data += focusNode.qnNum + "-" + focusNode.topic + ", ";
            
            preOrderTraverseTree(focusNode.leftChild);
            preOrderTraverseTree(focusNode.rightChild);
        }
    }
    
//    public String treeText()
//    {
//        
//    }
    
    public void postOrderTraverseTree(btNode focusNode)
    {
        if (focusNode != null)
        {
            postOrderTraverseTree(focusNode.leftChild);
            postOrderTraverseTree(focusNode.rightChild);
            
            data += focusNode.qnNum + "-" + focusNode.topic + ", ";
        }
    }
    
    public btNode findNode(int _qnNum)
    {
        //start at the top of the tree
        btNode focusNode = root;
        
        //while we havent found the node, keeping looking
        while (focusNode.qnNum != _qnNum)
        {
            //if we should search to the left
            if (_qnNum < focusNode.qnNum)
            {
                //shift the focus node to the left child
                focusNode = focusNode.leftChild;
            }
            else
            {
                //shift the focus node to the right child
                focusNode = focusNode.rightChild;
            }
            //the node wasnt found
            if (focusNode == null)
                return null;
        }
        return focusNode;
    }
}

class btNode
{
    int qnNum;
    String topic;
    
    btNode leftChild;
    btNode rightChild;
    
    btNode(int _qnNum, String _topic)
    {
        qnNum = _qnNum;
        topic = _topic;
    }
    
    public String toString()
    {
        return qnNum + "-" + topic + ", ";
    }
    
}

package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/
public class DLinkedList 
{
    DLLNode head;
    
    public DLinkedList(String _topic, int _qnNum, int _incorrect)
    {
        head = new DLLNode(_topic, _qnNum, _incorrect);
    }
    
    public DLLNode find(int _qnNum)
    {
        for (DLLNode current = head.next; current != head; current = current.next)
        {
            if (current.qnNum == _qnNum)
            {
                return current;
            }
        }
        return null;
    }
    
    public DLLNode get(int i)
    {
        DLLNode current = this.head;
        if (i < 0 || current == null)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        while (i > 0)
        {
            i--;
            current = current.next;
            if (current == null)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current;
    }
    
    public void update(DLLNode existingNode, String _topic, int _qnNum, int _incorrect)
    {
        //existingNode.topic = _topic;
        //existingNode.qnNum = _qnNum;
        existingNode.incorrect = _incorrect;
    }
    
    public String toString()
    {
        String str = "";
        if (head.next == head)
        {
            return "List Empty";
        }
        str = "List content = ";
        for (DLLNode current = head.next; current != head && current != null; current = current.next)
        {
            str = str + current.topic;
        }
        return str;
    }
    
    public String print()
    {
        String str = "";
//        if (head.next == head)
//        {
//            System.out.println("list empty");
//            return "";
//        }
        //System.out.print("list content = ");
        if (head.incorrect != 0)
        {
            str = head.topic + ", Qn " + head.qnNum + ", " + head.incorrect + " Students <--> ";
        }
        if (head.next != null)
        {
            for (DLLNode current = head.next; current != null; current = current.next)
            {
                if (current.incorrect != 0)
                {
                    str += current.topic + ", Qn " + current.qnNum + ", " + current.incorrect + " Students <--> ";
                }
            }
        }
        return str;
    }
}

class DLLNode
{
    DLLNode prev;
    DLLNode next;
    String topic;
    int qnNum;
    int incorrect;
    
    DLLNode()
    {
        prev = this;
        next = this;
        topic = "";
        qnNum = 0;
        incorrect = 0;
    }
    
    DLLNode(String _topic, int _qnNum, int _incorrect)
    {
        prev = null;
        next = null;
        topic = _topic;
        qnNum = _qnNum;
        incorrect = _incorrect;
    }
    
    public void append(DLLNode newNode)
    {
        //attach newNode after this Node
        newNode.prev = this;
        newNode.next = next;
        if (next != null)
        {
            next.prev = newNode;
        }
        next = newNode;
        //System.out.println("Node with data " + newNode.topic);
    }
    
    public void insert(DLLNode newNode)
    {
        //attach newNode before this Node
        newNode.prev = prev;
        newNode.next = this;
        prev.next = newNode;
        prev = newNode;
    }
    
    public void remove()
    {
        next.prev = prev;
        prev.next = next;
    }
    
    public String toString()
    {
        //MAY NEED TO ALTER
        return this.topic;
    }
}
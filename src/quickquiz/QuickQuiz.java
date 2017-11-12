package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.*;

//CHAT RELATED ---------------------------
import java.net.*;
import java.io.*;
import java.util.HashMap;
//----------------------------------------

public class QuickQuiz extends JFrame implements ActionListener, WindowListener
{
    private static final JLibrary library = new JLibrary();
    //initialise UI elements
    JLabel lblTitle, lblTopic, lblQn, lblQnNumber, lblA, lblB, lblC, lblD, lblCorrectAns, lblPreOrder, lblInOrder, lblPostOrder, 
           lblLinkedList, lblBinaryTree, lblQuestionsHeader, lblQnNumHeader, lblTopicHeader, lblQnHeader, lblSortBy;
    JTextField txtTopic;
    JTextArea txtQn, txtA, txtB, txtC, txtD, txtLinkedList, txtBinaryTree;
    JComboBox cboAnswerOptions;
    JButton btnSend, btnCreate, btnExit, btnSortByQn, btnSortByTopic, btnSortByAnswer, btnDisplayBinTree, 
            btnDisplayPreOrder, btnDisplayInOrder, btnDisplayPostOrder, btnSavePreOrder, btnSaveInOrder, btnSavePostOrder;
    JPanel pnlTitle, pnlBorder, pnlCreate, pnlQuestions, pnlPreOrder, pnlInOrder, pnlPostOrder;
    JTable tblQuestions;
    JScrollPane scrQuestions;
    
    String[] answerOptions = {"A", "B", "C", "D"};
    SortMethods sort = new SortMethods();
    QuestionManager manager = new QuestionManager();
    QuestionModel questionModel;
    
    DLinkedList dLinkedList;
    BinaryTree binaryTree;
    String treeData = "";
    String head = "HEAD <--> ";
    String tail = "TAIL";
    
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private InstructorThread client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    
    int incorrectCounter = 0;
    boolean firstQuestion = true;
    boolean lastQuestion = false;
    boolean firstNode = true;
    String linkedTopic = "";
    String linkedQnNum = "";
    
    public static void main(String[] args)
    {
        //initialise the main form
        JFrame frmMain = new QuickQuiz();
        frmMain.setSize(635, 580);
        frmMain.setLocation(600, 200);
        frmMain.setResizable(false);
        frmMain.setVisible(true);
    }
    
    public QuickQuiz()
    {
        //Set frame title and layout
        setTitle("Quick Quiz");
        SpringLayout mainLayout = new SpringLayout();
        setLayout(mainLayout);
        
        //place all objects using layout and specifics
        //INSERT PLACING METHODS BELOW
        PlaceJPanels(mainLayout);
        PlaceJLabels(mainLayout);
        PlaceJTextFields(mainLayout);
        PlaceJTextAreas(mainLayout);
        PlaceJComboBoxes(mainLayout);
        PlaceJButtons(mainLayout);
        PlaceJTables(mainLayout);
        binaryTree = new BinaryTree();
        
        ColourUI();
        this.addWindowListener(this);
        
        //CHAT RELATED ---------------------------
        getParameters();
        connect(serverName, serverPort);
        //----------------------------------------
    }
    
    public void PlaceJLabels(SpringLayout myLayout)
    {
        lblTitle = new JLabel("Quick Quiz", SwingConstants.CENTER);
        pnlTitle.add(lblTitle, BorderLayout.CENTER);
        lblPreOrder = new JLabel("Pre-Order", SwingConstants.CENTER);
        pnlPreOrder.add(lblPreOrder, BorderLayout.CENTER);
        lblInOrder = new JLabel("In-Order", SwingConstants.CENTER);
        pnlInOrder.add(lblInOrder, BorderLayout.NORTH);
        lblPostOrder = new JLabel("Post-Order", SwingConstants.CENTER);
        pnlPostOrder.add(lblPostOrder, BorderLayout.CENTER);
        
        lblTopic = library.PlaceAJLabel(this, myLayout, lblTopic, "Topic:", 375, 65);
        lblQn = library.PlaceAJLabel(this, myLayout, lblQn, "Qn:", 375, 90);
        lblQnNumber = library.PlaceAJLabel(this, myLayout, lblQnNumber, "0", 375, 107);
        lblA = library.PlaceAJLabel(this, myLayout, lblA, "A:", 375, 147);
        lblB = library.PlaceAJLabel(this, myLayout, lblB, "B:", 375, 183);
        lblC = library.PlaceAJLabel(this, myLayout, lblC, "C:", 375, 219);
        lblD = library.PlaceAJLabel(this, myLayout, lblD, "D:", 375, 255);
        lblCorrectAns = library.PlaceAJLabel(this, myLayout, lblCorrectAns, "Correct Ans:", 368, 313);
        lblLinkedList = library.PlaceAJLabel(this, myLayout, lblLinkedList, "Linked List:", 10, 340);
        lblBinaryTree = library.PlaceAJLabel(this, myLayout, lblBinaryTree, "Binary Tree:", 10, 420);
        
        lblQuestionsHeader = new JLabel("Quick Quiz Questions");
        this.add(lblQuestionsHeader, 0);
        myLayout.putConstraint(SpringLayout.WEST, lblQuestionsHeader, 126, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, lblQuestionsHeader, 57, SpringLayout.NORTH, this);
        
        lblQnNumHeader = new JLabel("#");
        this.add(lblQnNumHeader, 0);
        myLayout.putConstraint(SpringLayout.WEST, lblQnNumHeader, 13, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, lblQnNumHeader, 75, SpringLayout.NORTH, this);
        
        lblTopicHeader = new JLabel("Topic");
        this.add(lblTopicHeader, 0);
        myLayout.putConstraint(SpringLayout.WEST, lblTopicHeader, 35, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, lblTopicHeader, 75, SpringLayout.NORTH, this);
        
        lblQnHeader = new JLabel("Question");
        this.add(lblQnHeader, 0);
        myLayout.putConstraint(SpringLayout.WEST, lblQnHeader, 126, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, lblQnHeader, 75, SpringLayout.NORTH, this);
        
        lblSortBy = new JLabel("Sort By:");
        this.add(lblSortBy, 0);
        myLayout.putConstraint(SpringLayout.WEST, lblSortBy, 35, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, lblSortBy, 283, SpringLayout.NORTH, this);
    }
    
    public void PlaceJTextFields(SpringLayout myLayout)
    {
        txtTopic = library.PlaceATextField(this, myLayout, txtTopic, 420, 65, 17);
    }
    
    public void PlaceJTextAreas(SpringLayout myLayout)
    {
        Border textBorder = txtTopic.getBorder();
        txtQn = library.PlaceATextArea(this, myLayout, txtQn, 420, 90, 3, 17);
        txtA = library.PlaceATextArea(this, myLayout, txtA, 420, 147, 2, 17);
        txtB = library.PlaceATextArea(this, myLayout, txtA, 420, 183, 2, 17);
        txtC = library.PlaceATextArea(this, myLayout, txtC, 420, 219, 2, 17);
        txtD = library.PlaceATextArea(this, myLayout, txtD, 420, 255, 2, 17);
        txtLinkedList = library.PlaceATextArea(this, myLayout, txtLinkedList, 10, 355, 3, 55);
        txtBinaryTree = library.PlaceATextArea(this, myLayout, txtBinaryTree, 10, 435, 3, 55);
        
        txtQn.setBorder(textBorder);
        txtA.setBorder(textBorder);
        txtB.setBorder(textBorder);
        txtC.setBorder(textBorder);
        txtD.setBorder(textBorder);
        txtLinkedList.setBorder(textBorder);
        txtBinaryTree.setBorder(textBorder);
        
        txtQn.setLineWrap(true);
        txtA.setLineWrap(true);
        txtB.setLineWrap(true);
        txtC.setLineWrap(true);
        txtD.setLineWrap(true);
        txtLinkedList.setLineWrap(true);
        txtBinaryTree.setLineWrap(true);
        
        txtLinkedList.setEditable(false);
        txtBinaryTree.setEditable(false);
    }
    
    public void PlaceJComboBoxes(SpringLayout myLayout)
    {
        cboAnswerOptions = library.PlaceAComboBox(this, myLayout, cboAnswerOptions, answerOptions, 445, 310, 0, this);
    }
    
    public void PlaceJButtons(SpringLayout myLayout)
    {
        btnSend = library.PlaceAJButton(this, myLayout, btnSend, "Send", 488, 310, 130, 25, this);
        btnExit = library.PlaceAJButton(this, myLayout, btnExit, "Exit", 10, 310, 180, 25, this);
        btnCreate = library.PlaceAJButton(this, myLayout, btnCreate, "Create Qn", 260, 310, 100, 25, this);
        
        btnDisplayPreOrder = library.PlaceAJButton(this, myLayout, btnDisplayPreOrder, "Display", 25, 514, 60, 22, this);
        btnSavePreOrder = library.PlaceAJButton(this, myLayout, btnSavePreOrder, "Save", 85, 514, 60, 22, this);
        btnDisplayInOrder = library.PlaceAJButton(this, myLayout, btnDisplayInOrder, "Display", 258, 514, 60, 22, this);
        btnSaveInOrder = library.PlaceAJButton(this, myLayout, btnSaveInOrder, "Save", 318, 514, 60, 22, this);
        btnDisplayPostOrder = library.PlaceAJButton(this, myLayout, btnDisplayPostOrder, "Display", 484, 514, 60, 22, this);
        btnSavePostOrder = library.PlaceAJButton(this, myLayout, btnSavePostOrder, "Save", 544, 514, 60, 22, this);
        
        //PLACE SORTING BUTTONS OVER PANEL (REFER TO JLABELS PLACED FOR REFERENCE)
        btnSortByQn = new JButton("Qn #");
        this.add(btnSortByQn, 1);
        btnSortByQn.addActionListener(this);
        myLayout.putConstraint(SpringLayout.WEST, btnSortByQn, 90, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, btnSortByQn, 282, SpringLayout.NORTH, this);
        btnSortByQn.setPreferredSize(new Dimension(80, 20));
        
        btnSortByTopic = new JButton("Topic");
        this.add(btnSortByTopic, 1);
        btnSortByTopic.addActionListener(this);
        myLayout.putConstraint(SpringLayout.WEST, btnSortByTopic, 170, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, btnSortByTopic, 282, SpringLayout.NORTH, this);
        btnSortByTopic.setPreferredSize(new Dimension(80, 20));
        
        btnSortByAnswer = new JButton("Answer");
        this.add(btnSortByAnswer, 1);
        btnSortByAnswer.addActionListener(this);
        myLayout.putConstraint(SpringLayout.WEST, btnSortByAnswer, 250, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, btnSortByAnswer, 282, SpringLayout.NORTH, this);
        btnSortByAnswer.setPreferredSize(new Dimension(80, 20));
        
        Insets marginFix = new Insets(0,0,0,0);
        btnDisplayPreOrder.setMargin(marginFix);
        btnSavePreOrder.setMargin(marginFix);
        btnDisplayInOrder.setMargin(marginFix);
        btnSaveInOrder.setMargin(marginFix);
        btnDisplayPostOrder.setMargin(marginFix);
        btnSavePostOrder.setMargin(marginFix);
        btnSortByQn.setMargin(marginFix);
        btnSortByTopic.setMargin(marginFix);
        btnSortByAnswer.setMargin(marginFix);
        
        
        
    }
    
    public void PlaceJPanels(SpringLayout myLayout)
    {
        pnlBorder = library.PlaceAJPanel(this, myLayout, pnlBorder, 0, 45, 629, 507);
        pnlBorder.setOpaque(false);
        
        pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setPreferredSize(new Dimension(635, 45));
        this.add(pnlTitle);
                    
        pnlPreOrder = library.PlaceAJPanel(this, myLayout, pnlPreOrder, 25, 495, 120, 19);
        pnlInOrder = library.PlaceAJPanel(this, myLayout, pnlInOrder, 258, 495, 120, 19);
        pnlPostOrder = library.PlaceAJPanel(this, myLayout, pnlPostOrder, 484, 495, 120, 19);
                
        pnlQuestions = library.PlaceAJPanel(this, myLayout, pnlQuestions, 10, 55, 350, 250);
        pnlCreate = library.PlaceAJPanel(this, myLayout, pnlCreate, 368, 55, 250, 250);
        pnlCreate.setOpaque(false);
    }
    
    public void PlaceJTables(SpringLayout myLayout)
    {
        String[] columnNames = {"#", "Topic", "Question"};
        manager.LoadQuestions();
        questionModel = new QuestionModel(manager.questions);
        tblQuestions = new JTable(questionModel);
        tblQuestions.setTableHeader(null);
        
        
        
        scrQuestions = new JScrollPane(tblQuestions);
        scrQuestions.setPreferredSize(new Dimension(346, 190));
        this.add(scrQuestions, 1);
        myLayout.putConstraint(SpringLayout.WEST, scrQuestions, 12, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, scrQuestions, 91, SpringLayout.NORTH, this);
        
        tblQuestions.getColumnModel().getColumn(0).setPreferredWidth(1);
        tblQuestions.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblQuestions.getColumnModel().getColumn(2).setPreferredWidth(220);
        
        tblQuestions.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                if (me.getClickCount() == 2 && me.getButton() == MouseEvent.BUTTON1)
                {
                    JTable target = (JTable)me.getSource();
                    int row = target.getSelectedRow();
                    String id = target.getValueAt(row, 0).toString();
                    //System.out.println(id);
                    DisplayData(id);
                }
            }
        });

        // System.out.println(lblQuestionsHeader.getPreferredSize().width);
    }
    
    public void ColourUI()
    {
        Color excel = new Color(0, 102, 0);
        Border standardBorder = new LineBorder(excel, 2);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Serif", Font.PLAIN, 28));
        lblTopic.setForeground(excel);
        lblQn.setForeground(excel);
        lblQnNumber.setForeground(Color.LIGHT_GRAY);
        lblA.setForeground(excel);
        lblB.setForeground(excel);
        lblC.setForeground(excel);
        lblD.setForeground(excel);
        lblCorrectAns.setForeground(excel);
        lblLinkedList.setForeground(excel);
        lblBinaryTree.setForeground(excel);
        lblPreOrder.setForeground(Color.WHITE);
        lblInOrder.setForeground(Color.WHITE);
        lblPostOrder.setForeground(Color.WHITE);
        lblQuestionsHeader.setForeground(excel);
        lblQnNumHeader.setForeground(excel);
        lblTopicHeader.setForeground(excel);
        lblQnHeader.setForeground(excel);
        lblSortBy.setForeground(new Color(46, 85, 190));
        
        
        pnlTitle.setBackground(excel);
        pnlPreOrder.setBackground(excel);
        pnlInOrder.setBackground(excel);
        pnlPostOrder.setBackground(excel);
        pnlBorder.setBorder(standardBorder);
        pnlQuestions.setBorder(standardBorder);
        pnlCreate.setBorder((standardBorder));
        
        //scrQuestions.setBorder(new LineBorder(Color.BLACK, 2));
        scrQuestions.setBorder(BorderFactory.createEmptyBorder());
        tblQuestions.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btnSortByQn)
        {
            sort.SortByQn(manager.questions);
            tblQuestions.repaint();
        }
        
        if(e.getSource() == btnSortByTopic)
        {
            sort.SortByTopic(manager.questions);
            tblQuestions.repaint();
        }
        
        if(e.getSource() == btnSortByAnswer)
        {
            sort.SortByAnswer(manager.questions);
            tblQuestions.repaint();
        }
        
        if(e.getSource() == btnSend)
        {
            if (Integer.parseInt(lblQnNumber.getText()) != 0)
            {
                if (firstQuestion)
                {
                    dLinkedList = new DLinkedList(txtTopic.getText(), Integer.parseInt(lblQnNumber.getText()), 0);
                    txtLinkedList.setText(head);
                    firstQuestion = false;
                }
                else
                {
                    if (firstNode)
                    {
                        dLinkedList.update(dLinkedList.head, txtTopic.getText(), Integer.parseInt(lblQnNumber.getText()), incorrectCounter);
                        txtLinkedList.setText(head + dLinkedList.print() + tail);
                        dLinkedList.head.append(new DLLNode(txtTopic.getText(), Integer.parseInt(lblQnNumber.getText()), 0));
                        firstNode = false;
                    }
                    else
                    {
                        //replace linkedTopic and linkedQnNum with node.topic and node.question
                        //update old DLLNode BEFORE creating new node
                        dLinkedList.update(dLinkedList.head.next, txtTopic.getText(), Integer.parseInt(lblQnNumber.getText()), incorrectCounter);
                        txtLinkedList.setText(head + dLinkedList.print() + tail);
                        dLinkedList.head.append(new DLLNode(txtTopic.getText(), Integer.parseInt(lblQnNumber.getText()), 0));
                        
                        //txtLinkedList.append(linkedTopic + ", Qn " + linkedQnNum + ", " + incorrectCounter + " Students <--> ");
                    }
                }
                incorrectCounter = 0;
                //send question over the network
                send();
                
                //add to binary tree
                binaryTree.addNode(Integer.parseInt(lblQnNumber.getText()), txtTopic.getText());
                
                //then clear fields
                ClearFields();
            }
        }
        
        if(e.getSource() == btnDisplayPreOrder)
        {
            treeData = "PRE-ORDER: ";
            preOrderTraverse();
            txtBinaryTree.setText(treeData + binaryTree.data);
        }
        
        if(e.getSource() == btnDisplayInOrder)
        {
            treeData = "IN-ORDER: ";
            inOrderTraverse();
            txtBinaryTree.setText(treeData + binaryTree.data);
        }
        
        if(e.getSource() == btnDisplayPostOrder)
        {
            treeData = "POST-ORDER: ";
            postOrderTraverse();
            txtBinaryTree.setText(treeData + binaryTree.data);
        }
        
        if(e.getSource() == btnSavePreOrder)
        {
            hashPreOrder();
        }
        
        if(e.getSource() == btnSaveInOrder)
        {
            hashInOrder();
        }
        
        if(e.getSource() == btnSavePostOrder)
        {
            hashPostOrder();
        }
        
        if(e.getSource() == btnCreate)
        {
            Question question = new Question();
            question.setTopic(txtTopic.getText());
            question.setQn(txtQn.getText());
            question.setQnNum(manager.GetAQnNum());
            question.setA(txtA.getText());
            question.setB(txtB.getText());
            question.setC(txtC.getText());
            question.setD(txtD.getText());
            question.setCorrectAns(cboAnswerOptions.getSelectedItem().toString());     
            
            manager.questions.add(question);
            manager.SaveQuestions(manager.questions);
            questionModel.fireTableDataChanged();
            ClearFields();
        }
        
        if(e.getSource() == btnExit)
        {
            try
            {
                close();
            }
            catch (Exception ex)
            {
            
            }
            System.exit(0);
        }
    }
    
    public void hashPreOrder()
    {           
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        preOrderTraverse();
        String[] splitData = binaryTree.data.split(",");

        for (int i = 0; i < splitData.length - 1; i++)
        {
            int hkey = splitData[i].hashCode();
            hm.put(hkey, splitData[i]);
        }
        
//        for (String key : initialDataSplit)
//        {
//            int hkey = key.hashCode();
//            hm.put(hkey, key);
//        }
        writeHashedFile(hm, "preOrder.txt");
    }
    
    public void hashInOrder()
    {           
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        inOrderTraverse();
        String[] splitData = binaryTree.data.split(",");

        for (int i = 0; i < splitData.length - 1; i++)
        {
            int hkey = splitData[i].hashCode();
            hm.put(hkey, splitData[i]);
        }
        writeHashedFile(hm, "inOrder.txt");
    }
    
    public void hashPostOrder()
    {           
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        postOrderTraverse();
        String[] splitData = binaryTree.data.split(",");

        for (int i = 0; i < splitData.length - 1; i++)
        {
            int hkey = splitData[i].hashCode();
            hm.put(hkey, splitData[i]);
        }
        writeHashedFile(hm, "postOrder.txt");
    }
    
    public void writeHashedFile(HashMap hm, String file)
    {
        try
        {
            PrintWriter printFile = new PrintWriter(file);
            printFile.println(hm);
            printFile.close();
        }
        catch (Exception e)
        {
            System.err.println("Error Writing File: " + e.getMessage());
        }
    }
    
    public void connect(String serverName, int serverPort)
    {
        System.out.println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            System.out.println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
    }
    
    private void send()
    {
        try
        {
            String question = "#" + "i" + "#" + txtTopic.getText() + "#" + txtQn.getText() + "#" + txtA.getText() + "#" + txtB.getText() + "#" + txtC.getText() + "#" + txtD.getText() + "#" + lblQnNumber.getText() + "#" + cboAnswerOptions.getSelectedItem();
            
            streamOut.writeUTF(question);
            //streamOut.writeUTF(txtWord1.getText());
            streamOut.flush();
            //txtWord1.setText("");
        }
        catch (IOException ioe)
        {
            System.out.println("Sending error: " + ioe.getMessage());
            close();
        }
    }
    
    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            System.out.println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            String[] tempQuestion = new String[5];
            tempQuestion = msg.split("#");
            if (tempQuestion[1].equals("s"))
            {
                linkedTopic = tempQuestion[2];
                linkedQnNum = tempQuestion[3];
                if (tempQuestion[4].equals(tempQuestion[5]))
                {
                    System.out.println("Correct");
                }
                else
                {
                    System.out.println("Incorrect");
                    incorrectCounter++;
                }
            }
            else
            {
                //ignore it
            }
        }
    }
    
    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client = new InstructorThread(this, socket);
        }
        catch (IOException ioe)
        {
            System.out.println("Error opening output stream: " + ioe);
        }
    }
    
    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing...");
        }
        client.close();
        client.stop();
    }
    
    public void getParameters()
    {
        //serverName = getParameter("host");
        //serverPort = Integer.parseInt(getParameter("port"));
        
        serverName = "localhost";
        serverPort = 4444;        
    }
    
    public void ClearFields()
    {
        txtTopic.setText("");
        txtQn.setText("");
        lblQnNumber.setText("0");
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        txtD.setText("");
        cboAnswerOptions.setSelectedItem("A");
    }
    
    public void DisplayData(String id)
    {
        for (int i = 0; i < manager.questions.size(); i++)
        {
            if (manager.questions.get(i).getQnNum() == Integer.parseInt(id))
            {
                txtTopic.setText(manager.questions.get(i).getTopic());
                txtQn.setText(manager.questions.get(i).getQn());
                lblQnNumber.setText(id);
                txtA.setText(manager.questions.get(i).getA());
                txtB.setText(manager.questions.get(i).getB());
                txtC.setText(manager.questions.get(i).getC());
                txtD.setText(manager.questions.get(i).getD());
                cboAnswerOptions.setSelectedItem(manager.questions.get(i).getCorrectAns());
            }
        }
    }
    
    public void preOrderTraverse()
    {
        binaryTree.data = "";
        binaryTree.preOrderTraverseTree(binaryTree.root);
    }
    
    public void inOrderTraverse()
    {
        binaryTree.data = "";
        binaryTree.inOrderTraverseTree(binaryTree.root);
    }
    
    public void postOrderTraverse()
    {
        binaryTree.data = "";
        binaryTree.postOrderTraverseTree(binaryTree.root);
    }
    
    public void windowClosing(WindowEvent we)
    {
        // Exit the application
        try
        {
            close();
        }
        catch (Exception ex)
        {
            
        }
        System.exit(0);
    }

    public void windowOpened(WindowEvent we)
    {
        
    }

    //perform the specfic window actions
    public void windowIconified(WindowEvent we)  { }
    public void windowClosed(WindowEvent we)  { }
    public void windowDeiconified(WindowEvent we)  { }
    public void windowActivated(WindowEvent we)  { }
    public void windowDeactivated(WindowEvent we)  { }
}


package quickquiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*
@Author: Baylee Richards
@Date: July 2017
*/

public class QuickQuizStudent extends JFrame implements ActionListener, WindowListener
{
    private static final JLibrary library = new JLibrary();
    
    JLabel lblTitle, lblStudentName, lblDesc, lblTopic, lblQn, lblA, lblB, lblC, lblD, lblYourAns;
    JTextField txtStudentName, txtTopic;
    JTextArea txtQn, txtA, txtB, txtC, txtD;
    JComboBox cboAnswerOptions;
    JButton btnSubmit, btnExit;
    JPanel pnlTitle, pnlDesc, pnlDescBorder, pnlBorder;
    
    String[] answerOptions = {"A", "B", "C", "D"};
    
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private StudentThread studentThread = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    //----------------------------------------
    
    int qnNum = 0;
    String correctAnswer = "";
    
    public static void main(String[] args)
    {
        //initialise the main form
        JFrame frmMain = new QuickQuizStudent();
        frmMain.setSize(450, 495);
        frmMain.setLocation(600, 200);
        frmMain.setResizable(false);
        frmMain.setVisible(true);
    }
    
    public QuickQuizStudent()
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
        //PlaceJTables(mainLayout);
        //binaryTree = new BinaryTree();
        
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
        
        lblStudentName = library.PlaceAJLabel(this, myLayout, lblStudentName, "Student Name:", 25, 60);
        lblDesc = new JLabel("Select your answer and click Submit", SwingConstants.CENTER);
        pnlDesc.add(lblDesc, BorderLayout.CENTER);
        
        lblTopic = library.PlaceAJLabel(this, myLayout, lblTopic, "Topic:", 35, 125);
        lblQn = library.PlaceAJLabel(this, myLayout, lblQn, "Question:", 35, 150);
        lblA = library.PlaceAJLabel(this, myLayout, lblA, "A:", 35, 207);
        lblB = library.PlaceAJLabel(this, myLayout, lblB, "B:", 35, 243);
        lblC = library.PlaceAJLabel(this, myLayout, lblC, "C:", 35, 279);
        lblD = library.PlaceAJLabel(this, myLayout, lblD, "D:", 35, 315);
        lblYourAns = library.PlaceAJLabel(this, myLayout, lblYourAns, "Your Answer:", 20, 400);
    }
    
    public void PlaceJTextFields(SpringLayout myLayout)
    {
        txtStudentName = library.PlaceATextField(this, myLayout, txtStudentName, 180, 60, 17);
        txtTopic = library.PlaceATextField(this, myLayout, txtTopic, 180, 125, 17);
    }
    
    public void PlaceJTextAreas(SpringLayout myLayout)
    {
        Border textBorder = txtStudentName.getBorder();
        txtQn = library.PlaceATextArea(this, myLayout, txtQn, 180, 150, 3, 17);
        txtA = library.PlaceATextArea(this, myLayout, txtA, 180, 207, 2, 17);
        txtB = library.PlaceATextArea(this, myLayout, txtA, 180, 243, 2, 17);
        txtC = library.PlaceATextArea(this, myLayout, txtC, 180, 279, 2, 17);
        txtD = library.PlaceATextArea(this, myLayout, txtD, 180, 315, 2, 17);
        
        txtTopic.setEditable(false);
        txtQn.setEditable(false);
        txtA.setEditable(false);
        txtB.setEditable(false);
        txtC.setEditable(false);
        txtD.setEditable(false);
        
        txtQn.setBorder(textBorder);
        txtA.setBorder(textBorder);
        txtB.setBorder(textBorder);
        txtC.setBorder(textBorder);
        txtD.setBorder(textBorder);
        
        txtQn.setLineWrap(true);
        txtA.setLineWrap(true);
        txtB.setLineWrap(true);
        txtC.setLineWrap(true);
        txtD.setLineWrap(true);
        
        txtTopic.setBackground(Color.WHITE);
    }
    
    public void PlaceJComboBoxes(SpringLayout myLayout)
    {
        cboAnswerOptions = library.PlaceAComboBox(this, myLayout, cboAnswerOptions, answerOptions, 100, 397, 0, this);
    }
    
    public void PlaceJButtons(SpringLayout myLayout)
    {
        btnSubmit = library.PlaceAJButton(this, myLayout, btnSubmit, "Submit", 20, 430, 116, 25, this);
        btnExit = library.PlaceAJButton(this, myLayout, btnExit, "Exit", 307, 430, 116, 25, this);
    }
    
    public void PlaceJPanels(SpringLayout myLayout)
    {
        pnlDescBorder = library.PlaceAJPanel(this, myLayout, pnlDescBorder, 20, 115, 405, 275);
        pnlDescBorder.setOpaque(false);
        
        pnlBorder = library.PlaceAJPanel(this, myLayout, pnlBorder, 0, 45, 444, 422);
        pnlBorder.setOpaque(false);
        
        pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setPreferredSize(new Dimension(450, 45));
        this.add(pnlTitle);
        
        pnlDesc = library.PlaceAJPanel(this, myLayout, pnlDesc, 20, 90, 405, 25);
    }
    
    public void ColourUI()
    {
        Color excel = new Color(0, 102, 0);
        Border standardBorder = new LineBorder(excel, 2);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Serif", Font.PLAIN, 28));
        lblDesc.setForeground(Color.WHITE);
        
        lblStudentName.setForeground(excel);
        lblTopic.setForeground(excel);
        lblQn.setForeground(excel);
        lblA.setForeground(excel);
        lblB.setForeground(excel);
        lblC.setForeground(excel);
        lblD.setForeground(excel);
        lblYourAns.setForeground(excel);
        
        pnlTitle.setBackground(excel);
        pnlDesc.setBackground(excel);
        pnlDescBorder.setBorder(standardBorder);
        pnlBorder.setBorder(standardBorder);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSubmit)
        {
            send();
            //txtWord1.requestFocus();
        }

        if (e.getSource() == btnExit)
        {
            //txtWord1.setText(".bye");
            //txtWord2.setText("");
            close();
            System.exit(0);
        }

//        if (e.getSource() == btnConnect)
//        {
//            connect(serverName, serverPort);
//        }
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
            String answer = "#" + "s" + "#" + txtTopic.getText() + "#" + qnNum + "#" + cboAnswerOptions.getSelectedItem().toString() + "#" + correctAnswer;
            streamOut.writeUTF(answer);
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
            String[] tempQuestion = new String[9];
            tempQuestion = msg.split("#");
            if (tempQuestion[1].equals("i"))
            {
                txtTopic.setText(tempQuestion[2]);
                txtQn.setText(tempQuestion[3]);
                txtA.setText(tempQuestion[4]);
                txtB.setText(tempQuestion[5]);
                txtC.setText(tempQuestion[6]);
                txtD.setText(tempQuestion[7]);
                qnNum = Integer.parseInt(tempQuestion[8]);
                correctAnswer = tempQuestion[9];
            }
            else
            {
                //do nothing
            }
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            studentThread = new StudentThread(this, socket);
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
            System.out.println("Error closing ...");
        }
        studentThread.close();
        studentThread.stop();
    }

    public void getParameters()
    {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));
        
        serverName = "localhost";
        serverPort = 4444;        
    }
    
    public void windowClosing(WindowEvent we)
    {
        // Exit the application
        close();
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

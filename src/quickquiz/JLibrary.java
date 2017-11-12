package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

//Class to hold instantiation methods for a number of JObjects
public class JLibrary 
{
    public JLabel PlaceAJLabel(JFrame myFrame, SpringLayout myLabelLayout, JLabel myLabel, String myLabelCaption, int x, int y)
    {
        myLabel = new JLabel(myLabelCaption);
        myFrame.add(myLabel);
        myLabelLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, myFrame);
        myLabelLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, myFrame);
        return myLabel;
    }
    
    public JPanel PlaceAJPanel(JFrame myFrame, SpringLayout myPanelLayout, JPanel myPanel, int x, int y, int w, int h)
    {
        myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(w, h));
        myFrame.add(myPanel);
        myPanelLayout.putConstraint(SpringLayout.WEST, myPanel, x, SpringLayout.WEST, myFrame);
        myPanelLayout.putConstraint(SpringLayout.NORTH, myPanel, y, SpringLayout.NORTH, myFrame);
        return myPanel;
    }
    
    public JTextField PlaceATextField(JFrame myFrame, SpringLayout myTextLayout, JTextField myTextField, int x, int y, int w)
    {
        myTextField = new JTextField(w);
        myFrame.add(myTextField);
        myTextLayout.putConstraint(SpringLayout.WEST, myTextField, x, SpringLayout.WEST, myFrame);
        myTextLayout.putConstraint(SpringLayout.NORTH, myTextField, y, SpringLayout.NORTH, myFrame);
        return myTextField;
    }
    
    public JTextArea PlaceATextArea(JFrame myFrame, SpringLayout myTextLayout, JTextArea myTextArea, int x, int y, int w, int h)
    {
        myTextArea = new JTextArea(w, h);
        myFrame.add(myTextArea);
        myTextLayout.putConstraint(SpringLayout.WEST, myTextArea, x, SpringLayout.WEST, myFrame);
        myTextLayout.putConstraint(SpringLayout.NORTH, myTextArea, y, SpringLayout.NORTH, myFrame);
        return myTextArea;
    }
    
    public JComboBox PlaceAComboBox(JFrame myFrame, SpringLayout myComboBoxLayout, JComboBox myComboBox, String[] listItems, int x, int y, int i, ActionListener listener)
    {
        myComboBox = new JComboBox(listItems);
        myFrame.add(myComboBox);
        myComboBox.addActionListener(listener);
        myComboBoxLayout.putConstraint(SpringLayout.WEST, myComboBox, x, SpringLayout.WEST, myFrame);
        myComboBoxLayout.putConstraint(SpringLayout.NORTH, myComboBox, y, SpringLayout.NORTH, myFrame);
        myComboBox.setSelectedIndex(i);
        return myComboBox;
    }
    
    public JButton PlaceAJButton(JFrame myFrame, SpringLayout myButtonLayout, JButton myButton, String buttonCaption, int x, int y, int w, int h, ActionListener listener)
    {
        myButton = new JButton(buttonCaption);
        myFrame.add(myButton);
        myButton.addActionListener(listener);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, myFrame);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, myFrame);
        myButton.setPreferredSize(new Dimension(w, h));
        return myButton;
    }
}

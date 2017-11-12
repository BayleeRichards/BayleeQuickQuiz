package quickquiz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

/*
@Author: Baylee Richards
@Date: July 2017
*/

public class QuizTableHeader extends JTableHeader
{
    private JLabel header;
    
    public QuizTableHeader()
    {
        //setBackground(Color.WHITE);
        header = new JLabel("#");
        header.setBorder(new EmptyBorder(0, 0, 0, 0));
    }
    
    @Override
    public Dimension getPreferredSize() 
    {
        return header.getPreferredSize();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        FontMetrics fm = g2d.getFontMetrics();

        Insets insets = getInsets();
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);

        header.setBounds(insets.left, insets.top, width, height);
        header.paint(g2d);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(insets.left, insets.top + height - 1, insets.left + width, insets.top + height - 1);

        g2d.dispose();
    }
}

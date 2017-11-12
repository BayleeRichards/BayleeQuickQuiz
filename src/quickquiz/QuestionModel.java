package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class QuestionModel extends AbstractTableModel
{
    ArrayList<Question> questions;
    
    public QuestionModel(ArrayList<Question> _questions)
    {
        questions = _questions;
    }
    
    @Override
    public int getRowCount()
    {
        return questions.size();
    }
    
    @Override    
    public int getColumnCount()
    {
        return 3;
    }
    
    @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Object value = null;
            //Question question = manager.questions.get(rowIndex);
            Question question = questions.get(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    value = question.getQnNum();
                    break;
                case 1:
                    value = question.getTopic();
                    break;
                case 2:
                    value = question.getQn();
                    break;
            }
            return value;
        }
}

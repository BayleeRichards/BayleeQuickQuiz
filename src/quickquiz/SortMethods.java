package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

import java.util.ArrayList;

/*
@Author: Baylee Richards
@Date: July 2017
*/

public class SortMethods 
{
    public ArrayList<Question> SortByQn(ArrayList<Question> questions)
    {
        //Bubble sort
        for (int j = 0; j < questions.size(); j++)
        {
            for (int i = j+1; i < questions.size(); i++)
            {
                if (questions.get(i).getQnNum() < questions.get(j).getQnNum())
                {
                    Question question = questions.get(j);
                    questions.set(j, questions.get(i));
                    questions.set(i, question);
                }
            }
        }
        return questions;
    }
    
//    public ArrayList<Question> SortByTopic(ArrayList<Question> questions)
//    {
//        for (int j = 0; j < questions.size(); j++)
//        {
//            for (int i = j+1; i < questions.size(); i++)
//            {
//                if (questions.get(i).getTopic().compareToIgnoreCase(questions.get(j).getTopic())<0)
//                {
//                    Question question = questions.get(j);
//                    questions.set(j, questions.get(i));
//                    questions.set(i, question);
//                }
//            }
//        }
//        return questions;
//    }
    
    public ArrayList<Question> SortByTopic(ArrayList<Question> questions)
    {
        //Insertion sort
        int j; //the number of questions sorted so far
        Question question; //the question to be inserted
        int i; //counter
        
        for (j = 1; j < questions.size(); j++)
        {
            question = questions.get(j);
            for (i = j - 1; (i >= 0) && (questions.get(i).getTopic().compareToIgnoreCase(question.getTopic())<0); i--)
            {
                questions.set(i + 1, questions.get(i));
            }
            questions.set(i + 1, question);
        }
        return questions;
    }
    
//    public ArrayList<Question> SortByAnswer(ArrayList<Question> questions)
//    {
//        for (int j = 0; j < questions.size(); j++)
//        {
//            for (int i = j+1; i < questions.size(); i++)
//            {
//                if (questions.get(i).getCorrectAns().compareToIgnoreCase(questions.get(j).getCorrectAns())<0)
//                {
//                    Question question = questions.get(j);
//                    questions.set(j, questions.get(i));
//                    questions.set(i, question);
//                }
//            }
//        }
//        return questions;
//    }
    
    public ArrayList<Question> SortByAnswer(ArrayList<Question> questions)
    {
        //Selection sort
        int i, j, first; //various counters
        Question question; //question to add
        for (i = questions.size() - 1; i > 0; i--)
        {
            first = 0; //initialise first element
            for (j = 1; j <= i; j++) //locate smallest element between 1 and i
            {
                if (questions.get(j).getCorrectAns().compareToIgnoreCase(questions.get(first).getCorrectAns())<0)
                {
                    first = j;
                }
            }
            question = questions.get(first); //swap smallest found with element in position i
            questions.set(first, questions.get(i));
            questions.set(i, question);
        }
        return questions;
    }
}

//public static void BubbleSort( int [ ] num )
//{
//  int j;
//  boolean flag = true; // set flag to true to begin first pass
//  int temp; //holding variable
//  while ( flag )
//  {
//      flag= false; //set flag to false awaiting a possible swap
//      for( j=0; j < num.length -1; j++ )
//      {
//          if ( num[ j ] < num[j+1] ) // change to > for ascending sort
//          {
//              temp = num[ j ]; //swap elements
//              num[ j ] = num[ j+1 ];
//              num[ j+1 ] = temp;
//              flag = true; //shows a swap occurred
//          }
//      }
//  }
//}

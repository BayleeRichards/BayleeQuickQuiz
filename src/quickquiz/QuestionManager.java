package quickquiz;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/*
@Author: Baylee Richards
@Date: July 2017
*/

public class QuestionManager 
{
    public ArrayList<Question> questions;
    private String file = "questions.csv";
    static int qnNum;
    
    public QuestionManager()
    {
        
    }
    
    public void LoadQuestions()
    {
        questions = new ArrayList<Question>();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(dataInputStream));
            
            String line;
            
            while ((line = br.readLine()) != null)
            {
                String[] temp = line.split("\\|");
                
                questions.add(new Question(temp[0], temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7]));
            }
            
            br.close();
            dataInputStream.close();
            fileInputStream.close();
        }
        catch (Exception e)
        {
            System.err.println("Error Reading File: " + e.getMessage());
        }
    }
    
    public void SaveQuestions(ArrayList<Question> questionsToSave)
    {
        try
        {
            PrintWriter printQuestions = new PrintWriter(file);
            
            for (int i = 0; i < questionsToSave.size(); i++)
            {
                printQuestions.println(questionsToSave.get(i).getTopic() + "|" + questionsToSave.get(i).getQn() + "|" + questionsToSave.get(i).getQnNum() 
                                    + "|" + questionsToSave.get(i).getA() + "|" + questionsToSave.get(i).getB() + "|" + questionsToSave.get(i).getC()
                                    + "|" + questionsToSave.get(i).getD() + "|" + questionsToSave.get(i).getCorrectAns());
            }
            printQuestions.close();
        }
        catch (Exception e)
        {
            System.err.println("Error Writing File: " + e.getMessage());
        }
    }
    
    public int GetAQnNum()
    {
        qnNum = questions.size()+1;
        return qnNum;
//        Random random = new Random();
//        int qnNum = random.nextInt(900) + 100;
//        return qnNum;
    }
}

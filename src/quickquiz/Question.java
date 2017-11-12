package quickquiz;
/*
@Author: Baylee Richards
@Date: July 2017
*/

public class Question 
{
    String topic;
    String qn;
    int qnNum;
    String a;
    String b;
    String c;
    String d;
    String correctAns;
    
    public Question()
    {
        //Create an empty instance of Question
        topic = "";
        qn = "";
        qnNum = 0;
        a = "";
        b = "";
        c = "";
        d = "";
        correctAns = "";
    }
    
    public Question(String _topic, String _qn, int _qnNum, String _a, String _b, String _c, String _d, String _correctAns)
    {
        topic = _topic;
        qn = _qn;
        qnNum = _qnNum;
        a = _a;
        b = _b;
        c = _c;
        d = _d;
        correctAns = _correctAns;
    }
    
    public void setTopic(String _topic){topic = _topic;}
    public void setQn(String _qn){qn = _qn;}
    public void setQnNum(int _qnNum){qnNum = _qnNum;}
    public void setA(String _a){a = _a;}
    public void setB(String _b){b = _b;}
    public void setC(String _c){c = _c;}
    public void setD(String _d){d = _d;}
    public void setCorrectAns(String _correctAns){correctAns = _correctAns;}
    
    public String getTopic(){return topic;}
    public String getQn(){return qn;}
    public int getQnNum(){return qnNum;}
    public String getA(){return a;}
    public String getB(){return b;}
    public String getC(){return c;}
    public String getD(){return d;}
    public String getCorrectAns(){return correctAns;}
}

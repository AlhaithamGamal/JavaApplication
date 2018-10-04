/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HaithamGamal
 */
public class ReadingF {

    private String fileName;
    String line = "";
     int j = 0;
     int regcommandNested;
    Matcher m2; int comm=0;
    Pattern[] p;
    Matcher m;
    String declarationDataType="int|float|double|string|char";
    String declaratinArithmaticPracet="(\\(|\\)|\\+|\\*|\\/|\\%|\\*\\*|\\-)*";
    String arrayDeclaration="array";
    String arrayDeclarationSymbol="\\$array[A-Za-z]+(\\d{1,})*_";
    String regexBeginEndOne="BEGIN([!@#$%^&*()_+~\\.?\\s/\\*-])*([A-Za-z])*(\\d{1,})*(.)*(\\s)*END";
     String regexBeginEndTwo="[A-Za-z]*[!@#$%^&*()_+~?\\s/\\*-]*(\\d{1,})*BEGIN|[!@#$%^&*()_+~?\\s/\\*-]*[A-Za-z]*(\\d{1,})*(\\s)*END";
    String regexOneDeclaration = "([a-zA-Z])+|equal";
    String regexTwoDeclarationNested = "&([a-zA-Z])+(equal)|&([a-zA-Z])+|(equal)";
    String regexSpaceBeginEnd="BEGIN(\\s)+END";
    String regexEqualDeclaration="equal";
   // String regexThreeNumbersNested = "\\d{1,}(dot)\\d{1,}|\\d{1,}|(\\+|\\-)\\d{1,}|(\\+|\\-)\\d{1,}(dot)\\d{1,}|(\\+|\\-)\\d{1,}(e|E)(\\+|\\-)\\d{1,}|\\d{1,}(e|E)(\\+|\\-)\\d{1,}|\\d{1,}(dot)\\d{1,}(\\+|\\-)\\d{1,}(e|E)|(\\-|\\+)\\d{1,}(dot)\\d{1,}(e|E)(\\+|\\-)\\d{1,}";
    String regexThreeNumbersNested="\\d{1,}(\\.)\\d{1,}";
    String regexThreeNumbers = "\\d{1,}";
    String regexThreeNumbersDot="dot";
    String regexSymbolsRejected = "[#@=]+";
    // String regexReservedWords="if|array";
    String regexReservedWordsRejected = "if=|array=|for|while|do";
    String regexComment = "!\\*\\*!";
    String regexCommentNested = "!\\*\\*!([A-Za-z])*(\\d{0,})([!@#$%^&*()_+=~><\\*\\s])+";
    String[] regexCompile = {regexOneDeclaration, regexTwoDeclarationNested, regexThreeNumbers, regexThreeNumbersNested, regexSymbolsRejected, regexReservedWordsRejected, regexComment, regexCommentNested,regexThreeNumbersDot,regexEqualDeclaration,regexBeginEndOne,regexBeginEndTwo,declarationDataType,arrayDeclaration,arrayDeclarationSymbol,declaratinArithmaticPracet,regexSpaceBeginEnd};

    ReadingF() throws FileNotFoundException {
        regexCompilation();

        readingFile();
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void readingFile() throws FileNotFoundException {
        File txt = new File("C:\\Users\\Alhaitham Gamal\\Documents\\CompilerProject\\src\\compilerproject\\testcompile.txt");
        Scanner sc = new Scanner(txt);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            System.out.println(line);
           regexSemantic(line);
          //  initRegex(line);

        }

    }

    private void initRegex(String recieved) {
        // String to be scanned to find the pattern.

        int sumMatch = 0; Matcher mCommentrej; Matcher beginEnd;
       Matcher mdotNumber; Matcher mEqual;int prac=0;int beginSpace=0;
        // Create a Pattern object
        for (int i = 0; i < regexCompile.length; i++) {
            int comm=0; int dotCount=0; int equalCount=0;int begCount=0;int arrCount=0;
            switch (i) {

                case 0:
                    m = p[0].matcher(recieved);
                    int c = 0;
                    j=0;
                    boolean flag1 = false;
                    while (m.find(j)) {
                    //    c=0;
                        flag1 = true;
                        m2 = p[1].matcher(recieved);
                       mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                          mdotNumber=p[8].matcher(recieved);
                         while(mdotNumber.find(j)){
                         
                         dotCount++;
                         j++;
                         
                         }
                         mEqual=p[9].matcher(recieved);
                         while(mEqual.find(j)){
                         equalCount++;
                         j++;
                         
                         }
                        beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                        Matcher arrayDec=p[13].matcher(recieved);
                        while(arrayDec.find(j)){
                        
                        arrCount++;
                        j++;
                        
                        }
                         
                       Matcher mPracit=p[15].matcher(recieved);
                        while (m2.find(j)) {
                              j++;
                             c++;
                           
                        }
                        while(mPracit.find()){
                        prac++;   //for not print error to sem in variables
                        }
                        Matcher mBeginSpace=p[16].matcher(recieved);
                        while(mBeginSpace.find(j)){
                        beginSpace++;
                        
                        }
                    j++;
                    }

                    if (c == 0 && flag1&&!(comm>0)&&!(dotCount>0)&&!(equalCount>0)&&(begCount>0)&&!(arrCount>0)&&!(beginSpace>0)&&!(prac>0)) {//DONE
                        System.out.println("Checks the variable declaration should be like this &v");

                    }
                    break;
                case 2:
                    m = p[2].matcher(recieved);
                    boolean flag2 = false;
                    int c2 = 0;begCount=0;
                    j=0;
                    while (m.find(j)) {
                      //  c2=0;
                        flag2 = true;
                        m2 = p[3].matcher(recieved);
                         mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                           beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }

                        while (m2.find(j)) {
                            c2++;
                            j++; //to move forward

                        }
                        j++;

                    }
                    if (c2 > 0 && flag2&&!(comm>0)&&(begCount>0)) {
                        System.out.println("Checks the number syntax for real numberdotnumber for numbers should be from 1 to 100");
//done
                    }
                    break;

                case 4:
                    m = p[4].matcher(recieved);
                    j=0;
                    boolean flag3 = false; //flag for not printing the statement each time
                    int c3 = 0;
                    while (m.find()) {
                        flag3 = true;           
                        mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                             beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                          
                          c3++;
                          j++;
                    }
                    if (c3 > 0 && flag3&&!(comm>0)&&(begCount>0)) {

                        System.out.println("Checks the symbols symbols should be like all symbols exept \\'#\\' and \\'@\\'");
//done
                    }
                    break;
                case 5:
                    m = p[5].matcher(recieved);
                    j=0;
                    boolean flag4 = false;
                    int c4 = 0;
                    while (m.find()) {
                        flag4 = true;
                        
                        
                        mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                           mdotNumber=p[8].matcher(recieved);
                         while(mdotNumber.find(j)){
                         
                         dotCount++;
                         j++;
                         
                         }
                            beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                        c4++;
                        j++;

                    }
                    if (c4 > 0 && flag4&&!(comm>0)&&!(dotCount>0)&&begCount>0) {

                        System.out.println("Checks the reserved words if and array can't be assigned");
//done
                    }
                    break;

                case 6:
                    m = p[6].matcher(recieved);
                    j=0;
                    boolean flag5 = false;
                    begCount=0;
                    int c5 = 0;//flag علشان ممكن يكون ال فمسيكتبش الرسالة  regex مش موجود اصلا 
                    while (m.find(j)) {
                        flag5 = true;
                           beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                        m = p[7].matcher(recieved);
                        while (m.find(j)) {
                            c5++;
                            j++;

                        }
                       
                        
                     j++;
                    }

                    if (c5 == 0&&(begCount>0)) {

                        System.out.println("Checking the comment comment is easy to understand syntax if you want comment make like this !**! hello world!");

                    }
                    break;
                case 10:
                    m=p[10].matcher(recieved);
                    j=0;
                    boolean flag6=false;
                    int c9=0;
                    while(m.find(j)){
                    flag6=true;
//                    beginEnd=p[11].matcher(recieved);
//                    while(beginEnd.find(j)){   
//                    c9++;
//                    j++;
//                    
//                    }
                  
                    
                    
                    c9++;
                    j++;
                    }
                     if(c9==0&&flag6){
                   
                       System.out.println("Beginner and End oof program error ");
                   
                   }
                    break;
                case 12:
                     m=p[12].matcher(recieved);
                    j=0;comm=0;
                    boolean flag12=false;
                    int c12=0;
                    while(m.find(j)){
                    flag12=true;
                        beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                         
                        mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                    
                    
                    c12++;
                    j++;
                    
                    
                    
                    
                    }
                    if(c12>0&&flag12&&!(comm>0)&&(begCount>0)){
                    
                        System.out.println("NO DATA TYPE DECLARATION");
                    
                    }
                    break;
                case 13:
                      m=p[13].matcher(recieved);
                      Matcher arrm=p[14].matcher(recieved);
                    j=0;comm=0;
                    boolean flag13=false;
                    int c13=0;
                    while(m.find(j)){
                    flag13=true;
                        beginEnd=p[10].matcher(recieved);
                        while(beginEnd.find(j)){
                        begCount++;
                        j++;
                        
                        }
                         
                        mCommentrej=p[6].matcher(recieved);
                          while(mCommentrej.find(j)){
                            comm++;
                            j++;
                          
                            }
                          while(arrm.find()){
                          c13++;
                          j++;
                          }
                          
                    
                    
              
                j++;

            }
                    if(c13==0&&begCount>0&&flag13&&!(comm>0)){
                        System.out.println("error in declaring array");
                    
                    }

        }

        }
    }

    private void regexCompilation() {
        p = new Pattern[regexCompile.length];
        for (int i = 0; i < regexCompile.length; i++) {
            p[i] = Pattern.compile(regexCompile[i]);

        }
    }
    
    private void regexSemantic(String recieved){
        
        
          m=p[10].matcher(recieved);
                    j=0;
                    boolean flag6=false;
                    int c9=0;
                    while(m.find(j)){
                    flag6=true;
//                    beginEnd=p[11].matcher(recieved);
//                    while(beginEnd.find(j)){   
//                    c9++;
//                    j++;
//                    
//                    }
                  
                    
                    
                    c9++;
                    j++;
                    }
                     if(c9>0&&flag6){
                   
                      System.out.println(EvaluateSementic.evaluate(recieved));
                   
                   }
    
       
    
 
                    
    }
}

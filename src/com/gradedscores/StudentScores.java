package com.gradedscores;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
//Student Class
 
class Student
{
    String firstname;
    String lastname;
    int marks;
     
    public Student(String firstname, String lastname, int marks) 
    {
        this.firstname = firstname;

        this.lastname = lastname;
         
        this.marks = marks;
    }
}
 
//nameCompare Class to compare the names
 
class nameCompare implements Comparator<Student>
{
    @Override
    public int compare(Student s1, Student s2)
    {
    	if(s1.firstname.compareToIgnoreCase(s2.firstname) == 0)
    		return s1.lastname.compareToIgnoreCase(s2.lastname);

        return s1.firstname.compareToIgnoreCase(s2.firstname);
    }
}
 
//marksCompare Class to compare the marks
 
class marksCompare implements Comparator<Student>
{
    @Override
    public int compare(Student s1, Student s2)
    {
    	if(s2.marks - s1.marks == 0)
    		return new nameCompare().compare(s1, s2);
    	
        return s2.marks - s1.marks;
    }
}
 
public class StudentScores
{
    public static void main(String[] args)throws IOException
    {
    	//Get input file path specified in the main argument.
       	String filePath = args.length > 0 ? args[0] : "" ;
       	
       	if (filePath.isEmpty()){

           	System.out.println("Input file path is not specified.");
           	System.exit(0);
       	}
       		
        //Creating BufferedReader object to read the input text file
       	BufferedReader reader = null;
       	try{
        
       		reader = new BufferedReader(new FileReader(filePath));
        
       	}catch(FileNotFoundException e){
       		
       		System.out.println(e.getMessage());
           	System.exit(0);
       	}
         
        //Creating ArrayList to hold Student objects
         
        ArrayList<Student> studentRecords = new ArrayList<Student>();
         
        //Reading Student records one by one
         
        String currentLine = reader.readLine();
         
        while (currentLine != null)
        {
            String[] studentDetail = currentLine.split(",");
             
            String firstname = studentDetail[0].trim();
            
            String lastname = studentDetail[1].trim();
             
            int marks = Integer.valueOf(studentDetail[2].trim());
         
            //Creating Student object for every student record and adding it to ArrayList
             
            studentRecords.add(new Student(firstname, lastname, marks));
             
            currentLine = reader.readLine();
        }
         
        //Sorting ArrayList studentRecords based on marks
         
        Collections.sort(studentRecords, new marksCompare());
         
        //Creating BufferedWriter object to write into output text file
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(FilenameUtils.getFullPath(filePath) + FilenameUtils.getBaseName(filePath) + "-graded.txt"));
         
        //Writing every studentRecords into output text file
         
        for (Student student : studentRecords) 
        {
            writer.write(student.firstname);

            writer.write(", " + student.lastname);
             
            writer.write(", " + student.marks);
             
            writer.newLine();
            
            System.out.print("\n"+ student.firstname + ", " + student.lastname + ", " + student.marks + "\n");
        }

		System.out.print("\n" + "Finished: created " + FilenameUtils.getBaseName(filePath) + "-graded.txt");
        
        //Closing the resources
         
        reader.close();
         
        writer.close();
    }
}
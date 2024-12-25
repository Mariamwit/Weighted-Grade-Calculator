package project_2program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Project_2program {

    final static int NUM_LABS = 10;
    final static int NUM_PROJECTS = 4;
    final static int NUM_EXAMS = 2;
    final static double POINT_VALUE_LAB = 10;
    final static double LAB_WEIGHT = .25;
    final static double POINT_VALUE_PROJECTS = 50;
    final static double PROJECT_WEIGHT = .30;
    final static double POINT_VALUE_EXAM = 100;
    final static double EXAM_WEIGHT = 0.45;

    public static void main(String[] args) throws FileNotFoundException {

        //Open the input file and read the first line, which indicates the number of students
        File myFile = new File("scores.txt");
        if (!(myFile.exists())) {
            System.out.println("Unable to open file. ");
            return;
        }
        Scanner inputFile = new Scanner(myFile);
        PrintWriter outputFile = new PrintWriter("out.txt");
        int numStudents = inputFile.nextInt();
        inputFile.nextLine();
        //declare arrays
        char courseGradeArray[] = new char[numStudents];
        double averagesArray[][] = new double[numStudents][4];
        double averageLab[] = new double[numStudents * NUM_LABS];
        double averageProject[] = new double[numStudents * NUM_PROJECTS];
        double averageExam[] = new double[numStudents * NUM_EXAMS];
        double averageCourse[] = new double[numStudents * NUM_EXAMS * NUM_PROJECTS * NUM_LABS];
        String namesArray[] = new String[numStudents];
        double labGradesArray[][] = new double[numStudents][NUM_LABS];
        double projectGradesArray[][] = new double[numStudents][NUM_PROJECTS];
        double examGradesArray[][] = new double[numStudents][NUM_EXAMS];

        
        
        //call a method to correctly read the appropriate number of scores into the array.
        for (int i = 0; i < numStudents; i++) {
            namesArray[i] = inputFile.nextLine();
            getScores(inputFile, i, labGradesArray, NUM_LABS);
            getScores(inputFile, i, projectGradesArray, NUM_PROJECTS);
            getScores(inputFile, i, examGradesArray, NUM_EXAMS);
            inputFile.nextLine(); //discard newline after reading last score
        }
        //call a method to calculate the averages for each student.
        //call a void  method tocorrectly computes the lab, project, exam, and course averages 
        for (int i = 0; i < numStudents; i++) {
            String name = namesArray[i];
            calculateAverages( averageLab, i, labGradesArray, NUM_LABS);
            calculateAverages( averageProject, i, projectGradesArray, NUM_PROJECTS);
            calculateAverages(averageExam, i, examGradesArray, NUM_EXAMS);
            averageCourse[i] = ((averageLab[i] * 10) * LAB_WEIGHT) + ((averageProject[i] * 2) * PROJECT_WEIGHT) + (averageExam[i] * EXAM_WEIGHT);
            // A method to determine the course grade for all students.
            determineCoursegrade(averageCourse, i, courseGradeArray, numStudents);
         
        }
        

        //Call a method to store the averages into the correct positions of the averagesArray.
        outputFile.printf("%20s", "Student name");
        outputFile.printf("%20s", "Labs");
        outputFile.printf("%20s", "Projects");
        outputFile.printf("%20s", "Exams");
        outputFile.printf("%20s", "CourseAvg");
        outputFile.printf("%20s", "Grade");
        outputFile.println();
        for (int i = 0; i < numStudents; i++) {
            outputFile.printf("%20s", namesArray[i]);
            displayaveragesandGrades(outputFile, i, averagesArray, averageLab, averageProject, averageExam, averageCourse, courseGradeArray);
        }

        outputFile.println();
        outputFile.println("Details: ");
        outputFile.println();

        //call a method that outputs all information to a file in tabular form,
        for (int i = 0; i < numStudents; i++) {
            outputFile.println();
            outputFile.println(namesArray[i]);
            outputFile.print("Lab scores: ");
            displayScores(outputFile, i, labGradesArray, NUM_LABS);
            outputFile.print("Project scores: ");
            displayScores(outputFile, i, projectGradesArray, NUM_PROJECTS);
            outputFile.print("Exam scores: ");
            displayScores(outputFile, i, examGradesArray, NUM_EXAMS);

        }

        //Output a line to the screen stating that the program is finished indicating where the results can be viewed.   
        System.out.println("Program complete.");
        System.out.println("Results have been written to the file 'output.txt'.");

        inputFile.close();
        outputFile.close();
    }//end main

    public static void getScores(Scanner input, int currentRow, double[][] scoresArray, int numScores) {
        for (int j = 0; j < numScores; j++) {
            scoresArray[currentRow][j] = input.nextDouble();
        }
    }

    public static void displayScores(PrintWriter out, int currentRow, double[][] scoresArray, int numScores) {

        for (numScores = 0; numScores < scoresArray[currentRow].length; numScores++) {
            out.print(scoresArray[currentRow][numScores] + " ");
        }
        out.println();
    }

    //void method to calculate the average lab score, average project score, average exam score, and thecourse average for all students
    public static void calculateAverages( double[] Average, int row, double[][] scoresArray, int numScores) {
        double total1 = 0;
        for (int j = 0; j < numScores; j++) {
            //scoresArray[row][j] = input.nextDouble();
            total1 = total1 + scoresArray[row][j];
            Average[row] = (total1 / numScores);
        }
    }

    public static void determineCoursegrade( double[] averageCourse, int i, char[] courseGradeArray, int numStudents) {
        for (i = 0; i < numStudents; i++) {
            //[90-100] A; [80-90) B; [70-80) C; [60-70) D; < 60 F
            if (averageCourse[i] >= 90 && averageCourse[i] <= 100) {
                courseGradeArray[i] = 'A';
            } else if (averageCourse[i] >= 80 && averageCourse[i] < 90) {
                courseGradeArray[i] = 'B';
            } else if (averageCourse[i] >= 70 && averageCourse[i] < 80) {
                courseGradeArray[i] = 'C';
            } else if (averageCourse[i] >= 60 && averageCourse[i] < 70) {
                courseGradeArray[i] = 'D';
            } else if (averageCourse[i] < 60) {
                courseGradeArray[i] = 'F';
            }

        }
    }

    public static void displayaveragesandGrades(PrintWriter output, int row, double[][] arrayAverage, double[] averageLab, double[] averageProject, double[] averageExam, double[] averageCourse, char[] courseGradeArray) {

        for (int column = 0; column < arrayAverage[row].length; column++) {

        }
        output.printf("%20s ", (averageLab[row] * 10));
        output.printf("%20s ", (averageProject[row] * 2));
        output.printf("%20s ", averageExam[row]);
        output.printf("%20s", averageCourse[row]);
        output.printf("%20s ", courseGradeArray[row]);
        output.println();
    }

}//end class

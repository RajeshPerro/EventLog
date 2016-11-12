/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventlog;

/**
 *
 * @author rajesh
 */
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


/**
 *
 * @author rajesh
 */
public class EventLog{
    public static int id;
    public static String Email;
	//Implements a singleton logger instance
	private static final EventLog instance = new EventLog();

	//Retrieve the execution directory. Note that this is whereever this process was launched
	public String logname = "log_rajesh";
	protected String env = System.getProperty("user.dir");
	private static File logFile;

	public static EventLog getInstance(){
		return instance;
	}

	public static EventLog getInstance(String withName){
		instance.logname = withName;
		instance.createLogFile();
		return instance;
	}

	public void createLogFile(){
		//Determine if a logs directory exists or not.
		File logsFolder = new File(env + '/' + "logs");
		if(!logsFolder.exists()){
			//Create the directory 
			System.err.println("INFO: Creating new logs directory in " + env);
			logsFolder.mkdir();
			
		}

		//Get the current date and time
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   	Calendar cal = Calendar.getInstance();
	   	
	   	//Create the name of the file from the path and current time
		logname =  logname + '-' +  dateFormat.format(cal.getTime()) + ".log";
		EventLog.logFile = new File(logsFolder.getName(),logname);
		try{
			if(logFile.createNewFile()){
				//New file made
				System.err.println("INFO: Creating new log file");	
			}
		}catch(IOException e){
			System.err.println("ERROR: Cannot create log file");
			System.exit(1);
		}
	}

	private EventLog(){
		if (instance != null){
			//Prevent Reflection
			throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
		}
		this.createLogFile();
	}

	public static void log(String message){
		try{
			FileWriter out = new FileWriter(EventLog.logFile, true);
			out.write(message.toCharArray());
			out.close();
		}catch(IOException e){
			System.err.println("ERROR: Could not write to log file");
		}
	}
       public static void setWarningMsg(String text){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
            JDialog dialog = optionPane.createDialog("Warning.!!");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }
        //This is for just checking email address is vaild or not so I can write in log.
        public static boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
        }
        public static void StudentInfoSave()
        {
            String Name;
              
            try {
			
                        FileWriter writer = new FileWriter("StudentInfo.txt", true);
			Scanner sc = new Scanner(System.in);
                        System.out.println("Enter the Name of the Studet : ");
                        Name = sc.nextLine();
                        System.out.println("Enter the EMail of the Student : ");
                        Email = sc.nextLine();
                        try
                        {
                          //System.out.println("Email validate fucntion check: "+isValidEmailAddress(Email));
                          /* Here I am cheking if the email address is not valid then I am giving a alert and writing in log file*/  
                          if(isValidEmailAddress(Email) == false)
                          {
                          //System.out.println("!!!.......Alert.......!!!\nYou did something wrong.!! Please cehck the log file.");
                          setWarningMsg("You did something wrong.!! Please cehck the log file.");
                          EventLog.log("The Email address You have entered is not Valid.!!\nEmail should be like : somename@example.com\n");
                          }
                            
                        }
                        catch(InputMismatchException exception)
                        {
                          //Print "This is not an integer"
                          //when user put other than integer
                          
                        }
                        
                        
                        System.out.println("\nEnter the ID# (Please input only Number.!!)of the Student : ");
                        try
                        {
                          
                          //nextInt will throw InputMismatchException
                          //if the next token does not match the Integer
                          //regular expression, or is out of range
                           id=sc.nextInt();
                        }
                        catch(InputMismatchException exception)
                        {
                          //Print "This is not an integer"
                          //when user put other than integer
                          //System.out.println("!!!.......Alert.......!!!\nYou did something wrong.!! Please cehck the log file.");
                          setWarningMsg("You did something wrong.!! Please cehck the log file.");
                          EventLog.log("\nYou have entered text instead of Number.!!\n");
                          exit(0);
                        }
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);
                        bufferedWriter.write("#####.Student Information For :"+id+".#####");
                        bufferedWriter.newLine();
                        bufferedWriter.write("Name : "+Name);
			bufferedWriter.newLine();
                        bufferedWriter.write("E-Mail : "+Email);
			bufferedWriter.newLine();
                        bufferedWriter.write("ID : "+id);
			bufferedWriter.newLine();
                        bufferedWriter.write("#####.END of Student Information.#####");
                        bufferedWriter.newLine();
                        bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        StudentInfoSave();
    }
    
}

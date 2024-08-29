# SavorScope Tracker  
## Author  
***Taha Malik*** 


## Class Information 
The main entry point of the SavorScope Tracker application is defined in the `TrackerStart.java` class. This class is responsible for launching the JavaFX application and setting up the initial scene. 
All the files are within the src folder and are there for you to see the insides of the work I have done, all documented and commented!

## Program Description 
SavorScope Tracker is a comprehensive dietary and activity tracking application designed to help users manage their nutritional intake and track their physical activity levels. The application allows users to log daily meals, track nutritional values, and calculate key health metrics like BMI and daily caloric needs. 

## Execution Instructions 
To run the SavorScope Tracker program, you will need to have Java installed on your computer. Once you have Java installed, it is recommended you use the `.exe` file, however, you can also execute the program using a command line interface (CLI).

Note that if you are running the program without the jar file, you must keep the src folder intact completely without changing its contents, as the .png must be in the resources section for the program to function. If you are running the program with the jar, this does not apply to you.

### Running the Program(Using Exe File)
1. Download the `.exe` file for the SavorScope Tracker. It has the cool logo!
2. Double click it and it should work! (Hopefully :) )

### Running the Program  (Using Jar File)

1. Download the `.jar` file for the SavorScope Tracker. 
2. Open your command line interface. 
3. Navigate to the directory where the `.jar` file is located. 
4. Enter the following command to run the program:

Now execute this in command line, but change the javafx-sdk-22 according to your needs and the shown path to your path:

	java --module-path "C:\Program Files\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml -jar SavorScope.jar

-   “**C:\Program Files\Java\javafx-sdk-22\lib**” is what you need to alter to your specific computer!
- You must change this path to the specific path to your JavaFX library on your computer!

### Run with Loaded file

- Before attempting to run with the loaded file, ensure you have the file in existence, this can be done by simply opening the program and creating a save.
- Then simply rerun the previous code but with the name of the load file as an argument, it will always be **Saved_Log_Data.csv** if you created it from the program. 
	
		java --module-path "C:\Program Files\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml -jar SavorScope.jar Saved_Log_Data.csv
		
	- If you have a valid format file of a different name, that will work too, just ensure its of proper formatting, or the program will recognize the invalid format and not upload any data.
	
			java --module-path "C:\Program Files\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml -jar SavorScope.jar <INSERT FILE NAME HERE>
		
- Again, make sure you replace the path with your computers particular path!

## Contact Information 
If you have any questions or need support, feel free  to contact any  of us: 
- Taha Malik, UCID: 30204169, Email: taha.malik2@ucalgary.ca 

# *Thank you for using SavorScope Tracker!*

	



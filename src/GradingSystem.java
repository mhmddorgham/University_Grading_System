import java.util.Arrays;
import javax.swing.JOptionPane;

public class GradingSystem {

	public static void main(String[] args) {

		// Asking the users whether they are a teacher of a student
		String type = JOptionPane.showInputDialog(null, "Are you a student or a teacher? Write 'Student'/'Teacher'");

		// checking of the type of user to execute the appropriate mode for them
		if (type.equalsIgnoreCase("Teacher")) {

			// Asking the user to enter how many modules they teach then passing the answer
			// to a method that asks the teacher to enter the name of each module and save
			// the return array in modules array
			int modulesNum = Integer.parseInt(JOptionPane.showInputDialog(null, "How many modules do you have?"));
			String[] modules = modules(modulesNum);

			// Asking the teacher how many assessments in each module besides how many
			// students in each module and save the answer in an integer form
			int assessmentNum = Integer
					.parseInt(JOptionPane.showInputDialog(null, "How many assessments in each module?"));
			int studentsNum = Integer.parseInt(JOptionPane.showInputDialog(null, "How many students in each module?"));

			/*
			 * Declaring arrays with different types of dimensions to hold the data and save
			 * it somewhere else. The 3D arrays are going to hold the whole data modules,
			 * students, and assessments. The 2D arrays are going to hold the data for each
			 * iteration of a module and save it in the 3D arrays. The dimensions of arrays
			 * was build descending from modules level to students level to assessments
			 * level. The data that are going to be saved in the 3D arrays are names of
			 * students, names of assessments, grades of students, weighted grades. 2D
			 * arrays will hold grades for students and their weight in each module and then
			 * it will save them all in the 3D array The String one dimension arrays are
			 * responsible of holding names of students and assessments to save it in the
			 * appropriate 3D, double one dimension arrays are responsible of saving the
			 * weight of each assessment and the average of grades in each module.
			 */
			String[][][] allNames = new String[modulesNum][studentsNum][assessmentNum];
			String[][][] allassessment = new String[modulesNum][studentsNum][assessmentNum];
			String[] assessment = new String[assessmentNum];
			String[] names = new String[studentsNum];
			double[][][] numData = new double[modulesNum][studentsNum][assessmentNum];
			double[][][] weightedData = new double[modulesNum][studentsNum][assessmentNum];
			double[][] assessmentInput = new double[studentsNum][assessmentNum];
			double[][] weightedInput = new double[studentsNum][assessmentNum];
			double[] marksWeight = new double[assessmentNum];
			double[] average = new double[modulesNum];

			/*
			 * The first loop will iterate over the modules to collect data for each and
			 * save it in 3D array The data that will be hold in each iteration for the
			 * first loop are name of assessments, marks weight name of students, grades of
			 * students and their weight then the average
			 */
			for (int i = 0; i < numData.length; i++) {
				assessment = assessment(assessmentNum, modules, i);
				marksWeight = marksWeight(assessmentNum, assessment, modules, i);
				names = names(studentsNum, modules, i);
				assessmentInput = assessmentInput(names, assessment, modules, i);
				weightedInput = weightedInput(marksWeight, assessmentInput, names, assessment);
				average[i] = average(weightedInput, studentsNum, studentsNum);

				/*
				 * The second and the third loop are going to access the indexes of all 3D
				 * arrays to store the data that have been collected above
				 */
				for (int j = 0; j < numData[i].length; j++) {
					for (int k = 0; k < numData[i][j].length; k++) {
						allassessment[i][j][k] = assessment[k];
						numData[i][j][k] = assessmentInput[j][k];
						weightedData[i][j][k] = weightedInput[j][k];
						allNames[i][j][k] = names[j];
					}
				}
			}

			// Declaring a variable to save the user choice on it
			int chooseFrom;

			/*
			 * Initializing a do-while loop to keep showing choices to the user whether
			 * student mode or showing all data and keep running until the user write exit
			 */
			do {

				// saving the choice in variable called chooseFrom
				chooseFrom = Integer.parseInt(JOptionPane.showInputDialog(null,
						"1. Student Mode \n 2. Show All Data\n 3. Exit\n Your Choice:"));

				// Using switch-case to execute the appropriate condition depends on user choice
				switch (chooseFrom) {

				// case 1 calling student method and passing the appropriate arguments to it
				case 1:
					student(allNames, modules, numData, weightedData, allassessment, marksWeight, average, modulesNum,
							assessmentNum);
					break;
				// case 2 calling data method and passing the appropriate arguments to it
				case 2:
					data(allNames, allassessment, modules, numData);
					break;
				// case 3 when user write exit the program will show greetings to them
				case 3:
					JOptionPane.showMessageDialog(null, "Greetings.");
					break;
				default:
					JOptionPane.showMessageDialog(null, "Invalid Choice");
					break;
				}

			} while (chooseFrom != 3);

		}
		/*
		 * If the user enters the requester from the beginning, the program will execute
		 * the student method which contains a message that no data was found
		 */

		else if (type.equalsIgnoreCase("Student")) {
			student();
		}
		// If the user was not either a student or a teacher the program will not give
		// them access
		else {
			JOptionPane.showMessageDialog(null, "Not Allowed Access!");
		}

	}

	// Method of type double called average responsible of calculating the average
	// of all grades in each module
	public static double average(double[][] weightedInput, int studentsNum, int assessmentNum) {

		// initializing a variable called average to be equal to zero
		int average = 0;
		// Nested loop to add the weighted grades to variable average
		for (int i = 0; i < weightedInput.length; i++) {
			for (int j = 0; j < weightedInput[i].length; j++) {
				average += weightedInput[i][j];
			}
		}
		// return the average after dividing the by student number*assessment number
		return average = average / (studentsNum * assessmentNum);
	}

	// Method returns 2D double array containing the weight of students' marks
	public static double[][] weightedInput(double[] marksWeight, double[][] assessmentInput, String[] names,
			String[] assessment) {

		// Initializing a 2D array with a dimension of names length and assessment
		// length and calculating the weight of each assessment
		double[][] weightedInput = new double[names.length][assessment.length];
		for (int i = 0; i < weightedInput.length; i++) {
			for (int j = 0; j < weightedInput[i].length; j++) {
				weightedInput[i][j] = assessmentInput[i][j] * marksWeight[j];
			}
		}
		return weightedInput;
	}

	public static void student(String[][][] allNames, String[] modules, double[][][] numData, double[][][] weightedData,
			String[][][] allassessment, double[] marksWeight, double[] averagenum, int modulesNum, int assessmentNum) {

		// showing student mode and a space for the user to enter their name
		String name = JOptionPane.showInputDialog(null,
				"---------------Student Mode--------------- \n Enter you name: \n");

		// calling a method called contains to check which modules that student is
		// enrolled in
		int[] contains = contains(allNames, name, modulesNum);

		// Initializing variables
		double idaverage = 0;
		double sum = 0;
		double average = 0;
		// bloolean variable responsible of checking the user will choose the module
		// appear to them
		boolean isChoice = false;

		// variables will hold information to show them all leather in a one frame
		String message;
		String output;
		String main = "";
		String temp = "";

		// do-while loop
		do {
			message = "";
			output = name + " here are the module/s you are enrolled in: \n";

			// showing the modules for the user
			for (int i = 0; i < contains.length; i++) {
				if (contains[i] == 1) {
					message = (i + 1) + ". " + modules[i];
					output += message + "\n";
				}
			}
			// store the choice of modules in choice varaible
			int choice = Integer.parseInt(JOptionPane.showInputDialog(null, output + "\nChoose one:"));

			// checking the user choose the correct module for them and assign the isChoice
			// to be equal to ture
			if (contains[choice - 1] == 1) {
				isChoice = true;

				// first loop will keep iterating until it stop in an appropriate index of i
				for (int i = 0; i < allNames.length; i++) {
					if ((choice - 1) == i) {
						main = "";
						temp = "";

						// calling identifier method to get an index of j which contains the name of
						// student
						int identefier = identefier(allNames, name, i);

						// second loop will choose only one value of j
						for (int j = 0; j < allNames[i].length; j++) {
							if ((j == identefier)) {
								main = name + " : \n";

								// Printing the data for the student
								for (int k = 0; k < allNames[i][j].length; k++) {
									temp = (k + 1) + ". " + allassessment[i][j][k] + " = " + numData[i][j][k]
											+ " equal to " + weightedData[i][j][k] + " out of 100.0 ("
											+ allassessment[i][j][k] + " weight is " + marksWeight[k] * 100 + "%)";
									sum += weightedData[i][j][k];
									idaverage += weightedData[i][j][k];
									average += averagenum[i];
									main += temp + "\n";
								}
							}
						}
					}
				}
			}
			// if the user enter invalid choice of modules
			else {
				JOptionPane.showMessageDialog(null, "Invalid Choice Of Modules " + name + " Choose Again \n");
			}

		} while (!isChoice);

		// showing the sum of his/her grades
		main += "Total Grade = " + sum + " out of 100.0 \n";
		idaverage = idaverage / assessmentNum;

		// showing the average
		if (idaverage > average) {
			main += "Your marks average is " + idaverage + " wich is above your class average " + average
					+ " in this moduel \n";
		} else {
			main += "Your marks average is " + idaverage + " wich is down from your class average " + average
					+ " in this moduel \n";
		}

		// showing the module grade, his/her GPA and where pass or fail
		if (sum >= 70 && sum <= 100) {
			main += "Module Grage: A, GPA = 4.00, Pass";
		} else if (sum >= 67 && sum <= 69) {
			main += "Module Grage: A-, GPA = 3.70, Pass";
		} else if (sum >= 64 && sum <= 66) {
			main += "Module Grage: B+, GPA = 3.30, Pass";
		} else if (sum >= 60 && sum <= 63) {
			main += "Module Grage: B, GPA = 3.00, Pass";
		} else if (sum >= 57 && sum <= 59) {
			main += "Module Grage: B-, GPA = 2.70, Pass";
		} else if (sum >= 54 && sum <= 56) {
			main += "Module Grage: C+, GPA = 2.30, Pass";
		} else if (sum >= 50 && sum <= 53) {
			main += "Module Grage: C, GPA = 2.00, Pass";
		} else if (sum >= 47 && sum <= 49) {
			main += "Module Grage: C-, GPA = 1.70, Pass";
		} else if (sum >= 44 && sum <= 46) {
			main += "Module Grage: D+, GPA = 1.30, Pass";
		} else if (sum >= 40 && sum <= 43) {
			main += "Module Grage: D, GPA = 1.00, Pass";
		} else {
			main += "Module Grage: F, GPA = 0, Fail";
		}
		JOptionPane.showMessageDialog(null, main);

	}

	// void method called student
	public static void student() {

		String name = JOptionPane.showInputDialog(null,
				"---------------Student Mode--------------- \n Enter you name: \n");
		JOptionPane.showMessageDialog(null, name + " No Data Found Related To Your Name!");
	}

	// Method of type double array holds the weight of each assessment
	public static double[] marksWeight(int length, String[] assessment, String[] modules, int var) {

		double[] percentage = new double[length];
		for (int i = 0; i < percentage.length; i++) {
			percentage[i] = Double.parseDouble(JOptionPane.showInputDialog(null,
					"Enter the weight of " + assessment[i] + " in " + modules[var] + " (if it is 35% write 35)"));
			percentage[i] = (percentage[i] / 100);
		}
		return percentage;
	}

	// method of type string array holds the name of each module
	public static String[] modules(int length) {

		String[] modules = new String[length];
		for (int i = 0; i < modules.length; i++) {
			modules[i] = JOptionPane.showInputDialog(null, "Enter the name of module No." + (i + 1));
		}
		return modules;
	}

	// method of type string array holds name of studnes
	public static String[] names(int length, String[] modules, int var) {

		String[] names = new String[length];
		for (int i = 0; i < names.length; i++) {
			names[i] = JOptionPane.showInputDialog(null,
					"Enter the name of student No." + (i + 1) + " in module " + modules[var]);
		}
		Arrays.sort(names);
		return names;
	}

	// method of type string array holds names of assessments
	public static String[] assessment(int length, String[] modules, int var) {

		String[] assessment = new String[length];
		for (int i = 0; i < assessment.length; i++) {
			assessment[i] = JOptionPane.showInputDialog(null,
					"Enter the name of assessment No." + (i + 1) + " in module " + modules[var]);
		}
		return assessment;
	}

	// method of students grades
	public static double[][] assessmentInput(String[] names, String[] assessment, String[] modules, int var) {

		double[][] assessmentInput = new double[names.length][assessment.length];
		for (int i = 0; i < names.length; i++) {
			for (int j = 0; j < assessment.length; j++) {
				assessmentInput[i][j] = Double.parseDouble(JOptionPane.showInputDialog(null,
						"Enter the grade of student " + names[i] + " in " + assessment[j] + " in " + modules[var]));
			}
		}
		return assessmentInput;
	}

	// method of checking user modules
	public static int[] contains(String[][][] allNames, String key, int modulesNum) {

		int[] found = new int[modulesNum];
		for (int i = 0; i < allNames.length; i++) {
			for (int j = 0; j < allNames[i].length; j++) {
				for (int k = 0; k < allNames[i][j].length; k++) {
					if (allNames[i][j][k].equalsIgnoreCase(key)) {
						found[i] = 1;
					}
				}
			}
		}
		return found;
	}

	// method returning the index of student in student mode
	public static int identefier(String[][][] allNames, String name, int var) {

		int num = 0;
		for (int j = 0; j < allNames[var].length; j++) {
			for (int k = 0; k < allNames[var][j].length; k++) {
				if (allNames[var][j][k].equalsIgnoreCase(name)) {
					num = j;
				}
			}
		}
		return num;
	}

	// method showing all data to the user
	public static void data(String[][][] AllData, String[][][] allassessment, String[] modules, double[][][] numData) {

		String main = "---------------All Data--------------- \n";
		String temp = "";
		for (int i = 0; i < AllData.length; i++) {
			for (int j = 0; j < AllData[i].length; j++) {
				for (int k = 0; k < AllData[i][j].length; k++) {
					temp = AllData[i][j][k] + " : " + allassessment[i][j][k] + " = " + numData[i][j][k] + " in "
							+ modules[i] + "\n";
					main += temp;
				}
			}
		}
		JOptionPane.showMessageDialog(null, main);
	}
}

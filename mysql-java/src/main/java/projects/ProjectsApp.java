package projects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.dao.DbConnection;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	private ProjectService projectService = new ProjectService();
	static Project curProject = new Project();
	// @formatter:off
	private List<String> operations = new ArrayList<>(List.of(   // list of operations in menu
					"1) Add a project",
					"2) List projects",
					"3) Select a project",
					"4) Update project details",
					"5) Delete a project"
					) );
			// @formatter:on
			
	private Scanner scanner = new Scanner(System.in);  //initialize scanner to take user's input

	
	public static void main(String[] args) {
		
		DbConnection.getConnection();
		curProject = null;

		new ProjectsApp().processUserSelections();
	}

	//method displays menu selections, gets selection from the user and acts on selection
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
					case -1:
						done = exitMenu();
						break;
					case 1:
						createProject();
						break;
					
					case 2:
						listProjects();
						break;
						
					case 3:
						selectProject();
						break;
						
					case 4:
						updateProjectDetails();
						break;
						
					case 5:
						deleteProject();
						break;
						
					default:
						System.out.println("\n" + selection + " is not a valid selection. Try again.");
						break;
				}
			} catch(Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}
		
	}

	private void deleteProject() {
		listProjects();
		Integer projectId = getIntInput("\nEnter project ID you would like to delete ");
		
		projectService.deleteProject(projectId);
		if(Objects.isNull(projectId)) {
			System.out.println("You successfully deleted project " + projectId);
		} 
		
		if(Objects.nonNull(curProject)&& curProject.getProjectId().equals(projectId)) {
			curProject = null;
		}
		
	}

	private void updateProjectDetails() {
		if(Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project");
			return;
		}
		String projectName = getStringInput("Enter the project name [" + (curProject).getProjectName() + "]");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours [" + curProject.getActualHours() + "]");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
		
		Project project = new Project();
		project.setProjectId(curProject.getProjectId());
		project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
		project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
		project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
		project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
		project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		projectService.modifyProjectDetails(project);
		curProject = (Project) projectService.fetchProjectById(curProject.getProjectId());
	}

	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		curProject = null;
		curProject = (Project) projectService.fetchProjectById(projectId);
		
	}

	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		System.out.println("\nProjects:");
		
		projects.forEach(project -> System.out.println("   " + project.getProjectId() + " : " + project.getProjectName()));
		
	}

	//get user's input for the project
	private void createProject() {
		String projectName = getStringInput("\nEnter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project DbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + DbProject);
	}

	// convert user's input into BigDecimal
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		} catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
		
	}

	private boolean exitMenu() {
		System.out.println("\nExiting the menu.");
		return true;
	}

	//method prints operations and takes user's input as int
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection ");
		return Objects.isNull(input) ? -1 : input;
	}

	//convert user's input into int
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		} catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}

	//convert user's input into String
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		return input.isBlank() ? null : input.trim();
	}

	//method prints list of operations from menu
	private void printOperations() {
		
		System.out.println("\nThese are the available selections. Press the Enter key to quit: ");
		operations.forEach(line -> System.out.println("   " + line));
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
		
	}

}

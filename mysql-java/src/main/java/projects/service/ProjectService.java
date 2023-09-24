/**
 * 
 */
package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

/**
 * 
 */
public class ProjectService {

	private ProjectDao projectDao = new ProjectDao();
	
	
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects()
				.stream()
				.sorted((p1, p2) -> p1.getProjectId() - p2.getProjectId())
				.collect(Collectors.toList());
	}

	public Object fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectsById(projectId).orElseThrow(() -> new NoSuchElementException(
				"Project with project ID=" + projectId + " does not exist."));
	}

	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project ID=" + project.getProjectId() + " does not exist.");
		}
		
	}

	public void deleteProject(Integer projectId) {
		if(projectDao.deleteProject(projectId)) {
			System.out.println("You successfully deleted project " + projectId);
		
		}
		else {
			throw new DbException("Project ID= " + projectId + " does not exist.");
		}
		
	}

}

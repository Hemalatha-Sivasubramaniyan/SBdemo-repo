package com.cg.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.exception.ProjectIDException;
import com.cg.ppmtoolapi.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdate(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch (Exception e) {
			throw new ProjectIDException("ProjectIdentifier "+project.getProjectIdentifier()+" already available");
		}
	}
	/*
	 * Find project by projectIdentifier
	 */
	public Project findProjectByProjectIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {
			throw new ProjectIDException("ProjectIdentifier " + projectIdentifier + " not available");
		}
		return project;
	}
	/*
	 * to get all project as list in postman
	 */
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
 
	/*
	 * delete data by projectIdentifier
	 */
	public void deleteProjectByProjectIdentifier(String projectIdentifier) {
		Project project=findProjectByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project==null) {
			throw new ProjectIDException("ProjectIdentifier " + projectIdentifier + " not available");
		}
		projectRepository.delete(project);
	}

}

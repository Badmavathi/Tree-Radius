package com.holidu.interview.assignment.controller;

import java.net.URISyntaxException;
import java.util.Map;

import com.holidu.interview.assignment.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.holidu.interview.assignment.model.SearchParam;
import com.holidu.interview.assignment.service.TreeDataService;

@RestController
public class TreeDataController {

	TreeDataService treeDataService;
	@Autowired
	public void setTreeDataService(TreeDataService treeDataService){
		this.treeDataService = treeDataService;
	}

	@GetMapping(path = "/trees")
	@ResponseBody
	public  ResponseEntity<Map<String, Integer>> getTreeDataForGetRequest(SearchParam params) {
		return fetchTrees(params);
	}

	
	@PostMapping(path = "/trees", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public  ResponseEntity<Map<String, Integer>> getTreeDataForPostRequest(@RequestBody SearchParam params) {
		return fetchTrees(params);
	}
	
	private ResponseEntity<Map<String, Integer>> fetchTrees(SearchParam params){
		normalizeParameters(params);
		Map<String, Integer> output = null;
		try{
			output = treeDataService.fetchTreeData(params, new Utils());
		}catch (URISyntaxException ex){
			ex.printStackTrace();
		}
		if (output == null) {
			return ResponseEntity.status(HttpStatus.resolve(500)).body(null);
        }else{
			return ResponseEntity.status(HttpStatus.OK).body(output);
		}
	}
	
	private void normalizeParameters(SearchParam params) {
		double radiusInFeet = params.getRadius() * 3.2;
		params.setRadius(radiusInFeet);
	}
}
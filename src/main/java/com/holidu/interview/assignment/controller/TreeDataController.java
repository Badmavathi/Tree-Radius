package com.holidu.interview.assignment.controller;

import java.util.Map;
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
		TreeDataService service = new TreeDataService();
		Map<String, Integer> output = service.fetchTreeData(params);
		if (output == null) {
			return ResponseEntity.status(HttpStatus.resolve(500)).body(null);
        }
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	private void normalizeParameters(SearchParam params) {
		double radiusinFeet = params.getRadius() * 3.2;
		params.setRadius(radiusinFeet);
	}
}
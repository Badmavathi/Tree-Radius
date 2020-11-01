package com.holidu.interview.assignment.service;

import com.holidu.interview.assignment.model.SearchParam;
import com.holidu.interview.assignment.request.HttpProxy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TreeDataService {

	private final String ENDPOINT = "https://data.cityofnewyork.us/resource/nwxe-4ae8.json";

	public Map<String, Integer> fetchTreeData(SearchParam searchParams) throws URISyntaxException{
		URI uri = new URI(ENDPOINT);
		HttpGet getRequest = prepareGetRequest(searchParams, uri);
		String result = new HttpProxy(HttpClientBuilder.create().build()).getData(getRequest);
		return parseResult(searchParams, result);
	}

	private Map<String, Integer> parseResult(SearchParam searchParams, String result) {

		Map<String, Integer> trees = new HashMap<String, Integer>();
		JSONArray tempArray = null;

		try {
			tempArray = result != null ? new JSONArray(result) : new JSONArray();

			for (int i = 0; i < tempArray.length(); i++) {
				JSONObject treeEntry = tempArray.getJSONObject(i);
				double x_sp = Double.parseDouble(treeEntry.getString("x_sp"));
				double y_sp = Double.parseDouble(treeEntry.getString("y_sp"));
				if (isWithinRadius(searchParams.getX_coord(), searchParams.getY_coord(), x_sp, y_sp,
						searchParams.getRadius())) {

					if (!treeEntry.isNull("spc_common") && !treeEntry.getString("spc_common").isEmpty()) {
						trees.merge(treeEntry.getString("spc_common"), 1, Integer::sum);
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return trees;

	}

	public boolean isWithinRadius(double x, double y, double targetX, double targetY, double radius) {
		return Math.sqrt(Math.pow((targetX - x), 2) + Math.pow((targetY - y), 2)) <= radius;
	}

	private HttpGet prepareGetRequest(SearchParam searchParams, URI uri) {
		String fields = "spc_common, x_sp, y_sp";
		UriBuilder builder = UriComponentsBuilder.fromUri(uri);

		double x = searchParams.getX_coord();
		double y = searchParams.getY_coord();
		double radius = searchParams.getRadius();
		String treeName = searchParams.getCommon_name();

		String whereClause = "x_sp <= " + Double.toString(x + radius) + " AND " + "x_sp >= "
				+ Double.toString(x - radius) + " AND " + "y_sp <= " + Double.toString(y + radius) + " AND "
				+ "y_sp >= " + Double.toString(y - radius);

		if (treeName != null && treeName.length() > 0) {
			whereClause = whereClause + " AND spc_common LIKE '%" + treeName + "%'";
		}

		builder.queryParam("$select", fields).queryParam("$where", whereClause);

		return new HttpGet(builder.build());
	}
}

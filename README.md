# Tree Radius Interview Assignment

Your task is to implement a specific feature as a REST service that uses some 3rd party API.
A service should make an aggregated search of trees (plants, not binary trees) in the circle.

Input:
  - X and Y of the cartesian center point
  - A search radius in meters

Output:
  - Aggregation by "common name" (please see in the documentation on the same page above which field to refer to)

Example of the expected output:
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```

The service should use the data from the 3rd party API (https://data.cityofnewyork.us/Environment/2015-Street-Tree-Census-Tree-Data/uvpi-gqnh): `https://data.cityofnewyork.us/resource/nwxe-4ae8.json`

If you happen to have any questions, please send an email to your HR contact at Holidu.

Good luck and happy coding!


## Instructions to run the application.

> Run App.java as JAVA Application

> App is running on localhost:8080. 
	
	For Sample get request :

>	http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000&common_name=Sophora

>	http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000
    
	To Get results from UI :
	
	http://localhost:8080/

    Enter the sample co-ordinate values	as below in the appropriate textboxes
    	
    	X-Coordinate - 913368
    	Y-Coordinate - 124270
    	Radius (in meters) - 1000 
    	Filter by common name (optional) - Sophora
    
> Filter by common name is optional can be used to check the count of particular tree by entering it's common name.


## Attached Package
Within the attached zip, there are two packages attached:

1. Source Code package - this contains the java application source code and unit tests.
2. JaCoCo package - this zip folder contains the jacoco library generated files.  
 
 
Unit Tests coverage : 98% -> Report generated using Jacoco. 
 	
## Advantages
1. Code can be manually tested through UI by giving required input values.
		Ex : http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000
2. Common name filtering has been added to the code which is optional to check the count of particular tree.
		Ex : http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000&common_name=Sophora

## Disadvantages
1. Third party API returns random 1000 values and is inconsistent. We have to batch process it to get the complete result (like elastic search..)    


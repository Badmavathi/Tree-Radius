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


##

x = 913368
y = 124270
radius = 1000
Sample get request :

http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000&common_name=Sophora

http://localhost:8080/trees?x_coord=913368&y_coord=124270&radius=1000

Get results from UI :
http://localhost:8080/

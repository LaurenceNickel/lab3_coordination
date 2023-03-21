# Cooperative Agents - Predator-Prey Scenario
## Laurence Nickel: i6257119, Dino Pasic: i6250955
##### Intelligent Systems - Department of Data Science and Knowledge Engineering of Maastricht University (Maastricht, The Netherlands)

This zip file contains the code for submission of the assignment choice of lab 3.

# Executing
To execute the code, the 'WolvesApp.java' file can be run.

When the variable 'grid_predefined' is set to true within the 'Wolves' constructor,
one of the predefined grids will be used of which the code is presented towards the 
bottom of the 'Wolves.java' file. To change the grid used and therefore also the 
number of wolves and the number of preys used, the call to the constructor within the
'WolvesApp.java' file can be edited which automatically changes the selection of the
desired grid.

To change the wolf agents used for the game, the 'Wolves' constructor can be altered.
The implementations of the different wolf agents can be found in the following files:
* BaselineWolf.java
* FollowCoordinationWolf.java
* PackofWolves.java
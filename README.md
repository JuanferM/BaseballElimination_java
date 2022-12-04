# BaseballElimination_java
Ford-Fulkerson flow network algorithm to solve Baseball Elimination Problem in Java

# Requirements
JDK 1.7 (Java compiler ```javac``` and interpreter ```java``` versions should be 1.7)

# How to compile
	javac Baseball.java FlowNetwork.java

# How to run
	java Baseball path_to_file

Where _path_to_file_ is the path to the instance to run.

# Instance format
An instance (a file) should have the following format.
* on the first line : the number _n_ of teams
* on the following _n_ lines : for every team, its number _i_ (with _i_ an integer from 1 to _n_), its name
  (without spaces), the number of games won by the team, the remaining number of games left for the
  team to play and, for every team _j_ the number of games left for team _i_ to play against _j_ (with 
  _j_ an integer from 1 to _n_, also when _i_=_j_ then the number of games left is -1 by convention)
  
## Example of instance (didactic.txt)
```
4
1 New-York-Yankees  93 8 -1 1 6 1
2 Boston-Red-Sox    89 4 1 -1 0 3
3 Toronto-Blue-Jays 88 7 6 0 -1 1
4 Baltimore-Orioles 86 5 1 3 1 -1
```

## Example of execution
	java Baseball didactic.txt

```
Noms des équipes :
	New York Yankees
	Boston Red Sox
	Toronto Blue Jays
	Baltimore Orioles

Début de la méthode...

Les Boston Red Sox sont éliminés.

Les Baltimore Orioles sont éliminés.

Fin de la méthode.

Équipes restantes : 
	New York Yankees
	Toronto Blue Jays

```

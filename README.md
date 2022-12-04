# BaseballElimination_java
Ford-Fulkerson flow network algorithm to solve Baseball Elimination Problem in Java

# Requirements
JDK 1.7 (Java compiler ```javac``` and interpreter ```java``` versions should be 1.7)

# How to compile
```bash
javac Baseball.java FlowNetwork.java
```

# How to run
```bash
java Baseball <instance>
```
Where _<instance>_ is a filename.

# Instance format
An instance (a file) should have the following format.
* on the first line : the number _n_ of teams
* on the following _n_ lines : for every team, its number _i_ (with _i_ an integer from 1 to _n_), its name
  (without spaces), the number of games won by the team, the remaining number of games left for the
  team to play and, for every team _j_ the number of games left for team _i_ to play against _j_ (with 
  _j_ an integer from 1 to _n_, also when _i_=_j_ then the number of games left is -1 by convention)
  
## Example of instance
```
4
1 New-York-Yankees 93 8 -1 1 6 1
2 Boston-Red-Sox 89 4 1 -1 0 3
3 Toronto-Blue-Jays 88 7 6 0 -1 1
4 Baltimore-Orioles 86 5 1 3 1 -1
```

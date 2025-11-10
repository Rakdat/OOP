#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/AdjacencyList.java src/main/java/ru/nsu/tokarev4/AdjacencyMatrix.java \
                               src/main/java/ru/nsu/tokarev4/Graph.java src/main/java/ru/nsu/tokarev4/GraphManager.java \
                               src/main/java/ru/nsu/tokarev4/IncidenceMatrix.java src/main/java/ru/nsu/tokarev4/TopologocalSort.java
java -cp build ru.nsu.tokarev4.GraphManager
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/AdjacencyList.java src/main/java/ru/nsu/tokarev4/AdjacencyMatrix.java \
                                src/main/java/ru/nsu/tokarev4/Graph.java src/main/java/ru/nsu/tokarev4/GraphManager.java \
                                src/main/java/ru/nsu/tokarev4/IncidenceMatrix.java src/main/java/ru/nsu/tokarev4/TopologocalSort.java
read -p "Нажмите любую кнопку"
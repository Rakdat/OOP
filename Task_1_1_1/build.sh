#!/bin/bash
javac -d build src/main/java/ru/nsu/tokarev4/HeapSort.java
java -cp build ru.nsu.tokarev4.HeapSort
javadoc -d docs src/main/java/ru/nsu/tokarev4/HeapSort.java
read -p "Нажмите любую кнопку"
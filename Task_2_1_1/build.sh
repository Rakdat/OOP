#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/SimpleOrNot.java src/main/java/ru/nsu/tokarev4/SimpleNumbers.java \
                               src/main/java/ru/nsu/tokarev4/ParallelStream.java src/main/java/ru/nsu/tokarev4/MultiThreads.java \
                               src/main/java/ru/nsu/tokarev4/InOneLine.java
java -cp build ru.nsu.tokarev4.Blackjack
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/SimpleOrNot.java src/main/java/ru/nsu/tokarev4/SimpleNumbers.java \
                                src/main/java/ru/nsu/tokarev4/ParallelStream.java src/main/java/ru/nsu/tokarev4/MultiThreads.java \
                                src/main/java/ru/nsu/tokarev4/InOneLine.java
read -p "Нажмите любую кнопку"
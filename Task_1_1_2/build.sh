#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/Card.java src/main/java/ru/nsu/tokarev4/Blackjack.java
java -cp build ru.nsu.tokarev4.Blackjack
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/Card.java src/main/java/ru/nsu/tokarev4/Blackjack.java
read -p "Нажмите любую кнопку"
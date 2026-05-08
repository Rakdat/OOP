#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/Baker.java src/main/java/ru/nsu/tokarev4/Courier.java \
                               src/main/java/ru/nsu/tokarev4/CustomQueue.java src/main/java/ru/nsu/tokarev4/Order.java \
                               src/main/java/ru/nsu/tokarev4/StartPiz.java
java -cp build ru.nsu.tokarev4.Blackjack
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/Baker.java src/main/java/ru/nsu/tokarev4/Courier.java \
                                src/main/java/ru/nsu/tokarev4/CustomQueue.java src/main/java/ru/nsu/tokarev4/Order.java \
                                src/main/java/ru/nsu/tokarev4/StartPiz.java
read -p "Нажмите любую кнопку"
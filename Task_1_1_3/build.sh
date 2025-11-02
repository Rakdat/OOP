#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/Add.java src/main/java/ru/nsu/tokarev4/Div.java \
                               src/main/java/ru/nsu/tokarev4/Expression.java src/main/java/ru/nsu/tokarev4/ExpressionParser.java \
                               src/main/java/ru/nsu/tokarev4/Main.java src/main/java/ru/nsu/tokarev4/Mul.java \
                               src/main/java/ru/nsu/tokarev4/Number.java src/main/java/ru/nsu/tokarev4/Sub.java \
                               src/main/java/ru/nsu/tokarev4/Variable.java
java -cp build ru.nsu.tokarev4.Main
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/Add.java src/main/java/ru/nsu/tokarev4/Div.java \
                                src/main/java/ru/nsu/tokarev4/Expression.java src/main/java/ru/nsu/tokarev4/ExpressionParser.java \
                                src/main/java/ru/nsu/tokarev4/Main.java src/main/java/ru/nsu/tokarev4/Mul.java \
                                src/main/java/ru/nsu/tokarev4/Number.java src/main/java/ru/nsu/tokarev4/Sub.java \
                                src/main/java/ru/nsu/tokarev4/Variable.java
read -p "Нажмите любую кнопку"
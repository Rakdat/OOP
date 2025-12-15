#!/bin/bash
javac -encoding UTF-8 -d build src/main/java/ru/nsu/tokarev4/Blockquote.java src/main/java/ru/nsu/tokarev4/CodeBlock.java \
                               src/main/java/ru/nsu/tokarev4/Element.java src/main/java/ru/nsu/tokarev4/Header.java \
                               src/main/java/ru/nsu/tokarev4/Image.java src/main/java/ru/nsu/tokarev4/Link.java \
                               src/main/java/ru/nsu/tokarev4/ListElement.java src/main/java/ru/nsu/tokarev4/ListItem.java \
                               src/main/java/ru/nsu/tokarev4/Main.java src/main/java/ru/nsu/tokarev4/Table.java \
                               src/main/java/ru/nsu/tokarev4/Task.java src/main/java/ru/nsu/tokarev4/Text.java

java -cp build ru.nsu.tokarev4.Main
javadoc -encoding UTF-8 -d docs src/main/java/ru/nsu/tokarev4/Blockquote.java src/main/java/ru/nsu/tokarev4/CodeBlock.java \
                               src/main/java/ru/nsu/tokarev4/Element.java src/main/java/ru/nsu/tokarev4/Header.java \
                               src/main/java/ru/nsu/tokarev4/Image.java src/main/java/ru/nsu/tokarev4/Link.java \
                               src/main/java/ru/nsu/tokarev4/ListElement.java src/main/java/ru/nsu/tokarev4/ListItem.java \
                               src/main/java/ru/nsu/tokarev4/Main.java src/main/java/ru/nsu/tokarev4/Table.java \
                               src/main/java/ru/nsu/tokarev4/Task.java src/main/java/ru/nsu/tokarev4/Text.java
read -p "Нажмите любую кнопку"
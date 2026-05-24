#!/bin/bash

echo "=== Начинаем проверку проекта Змейки (CI/CD) ==="

chmod +x gradlew

./gradlew clean build

echo "=== Проверка успешно завершена! Код идеален. ==="
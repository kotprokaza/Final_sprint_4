#!/bin/bash
echo "=== Запуск тестов в Chrome ==="
mvn test -Pchrome

echo "=== Запуск тестов в Firefox ==="
mvn test -Pfirefox

echo "=== Все тесты завершены ==="

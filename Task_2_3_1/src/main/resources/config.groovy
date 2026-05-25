server {
    port = 8080
    tickRateMs = 150 // Скорость игры (чем меньше, тем быстрее змея)
}

game {
    logic {
        ticksBeforeChange = 67  // Тиков до смены камней (10 сек)
        transitionTicks = 20    // Тиков на мигание камней (3 сек)
        blinkTicks = 5          // Тиков на мигание оторванного хвоста
    }
}

colors {
    background = "#1e1e1e"      // Цвет фона (темно-серый)
    grid = "#ffffff"            // Цвет сетки (белый, будет полупрозрачным)
    snakeHead = "#27ae60"       // Цвет головы змеи
    snakeBody = "#2ecc71"       // Цвет тела змеи
    food = "#e74c3c"            // Цвет еды (красный)
    obstacle = "#7f8c8d"        // Цвет обычного камня (серый)
    obstacleWarning = "#f39c12" // Цвет мигающего будущего камня (оранжевый)
    tailWarning1 = "#ff0000"    // Цвет мигания оторванного хвоста 1 (красный)
    tailWarning2 = "#2ecc71"    // Цвет мигания оторванного хвоста 2 (зеленый)
}

ui {
    title = "Змейка"
    buttonStart = "СТАРТ"
    buttonRestart = "ИГРАТЬ ЗАНОВО"
    textSettings = "НАСТРОЙКИ"
    textGameOver = "ИГРА ОКОНЧЕНА! Счет:"
}
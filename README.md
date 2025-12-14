# UI Testing Framework

Автоматизированное тестирование Wikipedia на веб и мобильных платформах с использованием Selenium и Appium.

## Требования

- Java 11 или выше
- Maven 3.6+
- Для веб-тестов: Chrome/Firefox/Edge браузер
- Для мобильных тестов:
  - Appium Server (версия 2.x или 3.x)
  - Android Emulator или физическое устройство
  - Wikipedia приложение установлено на устройстве

## Быстрый старт

### 1. Компиляция проекта

```bash
mvn clean compile
```

### 2. Запуск всех тестов

```bash
mvn test
```

### 3. Запуск только веб-тестов

```bash
mvn test -Dtest=WikipediaWebTests
```

Или через TestNG группы:

```bash
mvn test -Dgroups=web
```

### 4. Запуск только мобильных тестов

**Важно:** Перед запуском убедитесь, что:
- Appium сервер запущен (`appium`)
- Android эмулятор/устройство подключено
- Wikipedia приложение установлено

```bash
mvn test -Dtest=WikipediaMobileTests
```

Или через TestNG группы:

```bash
mvn test -Dgroups=mobile
```

## Настройка параметров

### Веб-тесты

Выбор браузера:
```bash
mvn test -Dbrowser=chrome      # По умолчанию
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

Headless режим:
```bash
mvn test -Dheadless=true
```

Базовый URL:
```bash
mvn test -DwebBaseUrl=https://ru.wikipedia.org
```

### Мобильные тесты

Appium сервер URL:
```bash
mvn test -DappiumServerUrl=http://127.0.0.1:4723
```

Имя устройства:
```bash
mvn test -DdeviceName=Android_Emulator
```

Версия платформы:
```bash
mvn test -DplatformVersion=11.0
```

## Примеры запуска

### Полный запуск веб-тестов в headless режиме
```bash
mvn test -Dgroups=web -Dheadless=true -Dbrowser=chrome
```

### Запуск конкретного теста
```bash
mvn test -Dtest=WikipediaWebTests#searchOpensExactArticle
```

### Запуск с подробным логированием
```bash
mvn test -X
```

## Структура проекта

```
src/test/java/com/testing/
├── config/              # Конфигурация (TestConfig, Timeouts)
├── driver/              # Фабрики драйверов (WebDriver, MobileDriver)
├── pages/               # Page Object модели
│   ├── web/            # Веб страницы
│   └── mobile/         # Мобильные страницы
└── tests/              # Тестовые классы
    ├── web/           # Веб тесты
    └── mobile/        # Мобильные тесты
```

## Результаты тестов

- **Логи:** `logs/test.log`
- **Скриншоты при падении:** `screenshots/`
- **TestNG отчеты:** `target/surefire-reports/`

## Устранение проблем

### Веб-тесты не запускаются
- Проверьте, что браузер установлен
- WebDriverManager автоматически скачает драйверы

### Мобильные тесты пропускаются
- Убедитесь, что Appium сервер запущен: `appium --version`
- Проверьте подключение устройства: `adb devices`
- Убедитесь, что Wikipedia приложение установлено

### Проблемы с компиляцией
```bash
mvn clean install
```

## Дополнительная информация

Все таймауты и константы находятся в `Timeouts.java`.
Конфигурация логирования в `src/test/resources/logback.xml`.


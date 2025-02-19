# ERP-система для Московского зоопарка

## Описание проекта

Данное веб-приложение предназначено для автоматизации учета объектов Московского зоопарка. Система ведет учет животных, инвентарных предметов (вещей) и обеспечивает своевременное проведение медосмотров, согласно внутреннему регламенту.

## Функциональные возможности

### 1. Управление животными
- **Добавление нового животного:**  
  Перед добавлением животного в зоопарк система проводит проверку его здоровья через модуль ветеринарной клиники (VetClinic). Если животное признано здоровым, ему присваивается уникальный инвентаризационный номер и оно добавляется на баланс.
  
- **Учет потребления еды:**  
  Для каждого животного хранится информация о количестве килограммов еды, которое оно потребляет ежедневно (свойство `Food`).

- **Контактный зоопарк:**  
  Для животных, являющихся травоядными (реализовано через класс `Herbo`) и имеющих уровень доброты (`Kindness`) выше 5 баллов, формируется список, пригодный для интерактивного взаимодействия с посетителями.

- **Инвентаризация животных:**  
  Каждому животному присваивается уникальный инвентаризационный номер (свойство `Number`), который используется при ведении учета.

### 2. Управление вещами
- **Добавление инвентарных предметов:**  
  Система позволяет добавить на баланс зоопарка вещи (например, `Table` или `Computer`). Каждая вещь имеет наименование (`Name`) и уникальный инвентаризационный номер.

- **Инвентаризация вещей:**  
  Все предметы, находящиеся на балансе, учитываются вместе с животными и отображаются в общем списке инвентаризации.

### 3. Формирование отчетов
- **Отчет по потребляемой еде:**  
  Система рассчитывает общее количество килограммов еды, потребляемой всеми животными зоопарка.

- **Список животных для контактного зоопарка:**  
  Выводится перечень всех животных, подходящих для интерактивного контакта (травоядные с добротой выше 5).

- **Общий список инвентаризации:**  
  Отображается список всех объектов (животных и вещей) с указанием их наименования и инвентаризационных номеров.

## Веб-интерфейс и REST API

Веб-приложение реализовано с использованием Java (например, на базе Spring Boot) и предоставляет следующие REST эндпоинты:

- **POST `/animals`**  
  Добавление нового животного. В теле запроса передаются параметры:
  - `species` – вид животного (например, "Обезьяна", "Кролик", "Тигр", "Волк")
  - `Food` – количество еды в сутки
  - (Для травоядных) `Kindness` – уровень доброты

- **GET `/animals`**  
  Получение списка всех животных с их инвентаризационными номерами и информацией о потребляемой еде.

- **GET `/animals/contact`**  
  Получение списка животных, подходящих для контактного зоопарка (травоядных с `Kindness > 5`).

- **POST `/things`**  
  Добавление нового инвентарного предмета. В теле запроса указывается:
  - `Name` – наименование вещи
  - Тип (например, "Table" или "Computer")

- **GET `/inventory`**  
  Получение общего списка инвентаризационных объектов (животные и вещи) с указанием их наименований и номеров.

- **GET `/reports/food`**  
  Получение отчета с общим количеством еды, потребляемой животными.

## Технологии и архитектура

- **Язык программирования:** Java
- **Фреймворк для веб-приложения:** Spring Boot (рекомендуется, но можно использовать и другой Java веб-фреймворк)
- **Паттерны проектирования:** Принципы SOLID:
  - **SRP:** Каждый класс отвечает за свою задачу (например, `VetClinic` для проверки здоровья, `Zoo` для учета объектов).
  - **OCP:** Возможность расширения функционала без модификации существующего кода (новые типы животных и вещей).
  - **LSP:** Подклассы (например, `Monkey`, `Rabbit`) могут заменять базовый класс `Animal`.
  - **ISP:** Интерфейсы `IAlive` и `IInventory` разделяют ответственность.
  - **DIP:** Зависимости внедряются через абстракции (например, `Zoo` зависит от `VetClinic`).
- **Внедрение зависимостей (DI):** Реализовано вручную или с использованием встроенных возможностей Spring Boot.

## Запуск приложения

1. **Клонирование репозитория:**  
   ```bash
   git clone https://github.com/Euggenius/java-hw-1
   cd java-hw-1
   ```
2. **Сборка и запуск:**
   ```bash
   javac ZooApp.java
   java ZooApp
   ```
3. **Необходимые библиотеки и инструменты:**
- Java JDK 11 или выше

# Lab 1 – Spring Boot + H2 + JPA

Проект демонстрирует:

* **OneToOne** – `User` ⇄ `UserProfile`
* **ManyToOne** – `User` → `Role`
* **ManyToMany** – `User` ⇄ `Project`
* Таблицу **Signature** с требуемыми полями

## Сборка и запуск

```bash
./mvnw spring-boot:run          # через wrapper
# или
mvn clean package && java -jar target/lab1-demo-0.0.1-SNAPSHOT.jar
```

H2‑консоль: <http://localhost:8080/h2-console>  
JDBC URL: `jdbc:h2:mem:lab1db`

## Работа из Postman

| Операция | Метод и URL |
|----------|-------------|
| Список пользователей | `GET /api/users` |
| Создать пользователя | `POST /api/users` |
| Обновить пользователя | `PUT /api/users/{id}` |
| Удалить пользователя | `DELETE /api/users/{id}` |
| Аналогично для: roles (`/api/roles`), projects (`/api/projects`), signatures (`/api/signatures`) |

Примеры JSON см. в каталоге `/postman_examples` (можете создать коллекцию сами).

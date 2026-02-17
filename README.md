Untuk technical test BE, sebagai syarat seleksi perekrutan karyawan PT Mega Giga Solusindo.

**Prerequisites:**

- Install and start MySQL service
- Install and start Redis service
- Install and start RabbitMQ service (optional)

**Setup:**

1. Clone
2. Import DB from this folder project (bookstore.sql)
3. Change MySQL and Redis credentials in the (src > main > resources > application.properties) if needed
4. run "mvn clean install"
5. run "mvn spring-boot:run"
6. Open http://localhost:8080/swagger-ui/index.html
7. Register/Login
8. Available credentials: [Admin] admin@gmail.com / admin123456 || [User] donny@gmail.com / donny123456 || [User] user@gmail.com / user123456
9. Input token in the Swagger's Authorize section
10. Use the app as desired

**Other Features:**

[Unit Test]
- To perform a unit test, run "mvn test"

[Web Socket]
- Open HTML page example for WebSocket listener in /src/main/java/com/github/donnyk22/project/docs/
- WebsocketBroadcastTest.html for broadcast WebSocket (hit with /api/experimental/ws endpoint)
- WebsocketDirectUserTest.html for specific user (hit with /api/experimental/ws/users endpoint)
- /api/experimental/ws/users/online to check all connected users

[Message Broker]
- Make sure you have already run the RabbitMQ service
- You can change the RabbitMQ credentials in the application.properties
- /api/experimental/ms-broker/topic/text to send a message for text topic
- /api/experimental/ms-broker/topic/object to send a message for object topic
- Check the log for the message printed
- There are three listeners: 1. For text, 2. for object, 3. Listening to all topics
- So, at least there will be two messages printed

**Security**
- JWT
- CORS Configuration
- Password hashing
- Brute force login protection
- Limiting Sign Up action
- XSS (Cross-Site Scripting) Protection
- Clickjacking Protection (iFrame)
- MIME-Sniffing Protection
- Referrer Policy (STRICT_ORIGIN_WHEN_CROSS_ORIGIN)

[Rate Limiting]
- You can change the setting in the configurations/RateLimitFilter.java
- Change the value of MAX_REQUEST and MAX_REQ_DURATION

[Other]
- API Caching
- Async (simple Async & with RabbitMQ implementation with max worker & max queue)

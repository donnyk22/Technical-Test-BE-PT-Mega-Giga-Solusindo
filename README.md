Untuk technical test BE, sebagai syarat seleksi perekrutan karyawan PT Mega Giga Solusindo.

Prerequisites:

- Install and start MySQL service
- Install and start Redis service

Setup:

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

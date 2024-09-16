Приложение поднимается с помошью docker-compose.
Для входа в базу данных используется PgAdmin:
-URL: http://localhost:5050
-login: admin@admin.com
-password: root

Данные для связи с сервером БД в PgAdmin:
-Hostname / address: service-db
-Port: 5432
-Maintenance: store_db
-Username: username
-Password: password

Http-запросы клиента:
-Для регистрации:
  -URL: http://localhost:8183/auth/registration
  -Body: 
    "username" : "usernameUser",
    "password" : "passwordUser",
    "email": "emailUser",
    "phoneNumber": phoneUser
-Для получения токена по логину и паролю:
  -URL: http://localhost:8183/auth/token
  -Body: 
    "login" : "usernameUser",
    "password" : "passwordUser"
  -Responce:
    -"token": "generatedToken"
-Для добавления покупки по userId:
  -URL: http://localhost:8183/purchase/{userId}/add
  -Header:
    -Authorization: "Bearer {generatedToken}"
  -Body: 
    "name" : "namePurchase",
    "value" : valuePurchase
-Для получения покупки по userId:
  -URL: http://localhost:8183/purchase/{userId}/all
  -Header:
    -Authorization: "Bearer {generatedToken}"
  
  
    
    

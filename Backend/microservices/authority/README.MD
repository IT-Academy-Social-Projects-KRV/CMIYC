> ### Installation guide:
>1. Download MySQL 8.0.26 from https://dev.mysql.com/downloads/installer/
>2. Install MySQL and create user with admin privileges
>3. Add environment variables:<br>
>**spring.datasource.username=<YOUR_USERNAME>;**<br>
>**spring.datasource.password=<YOUR_PASSWORD>;**<br>
>4. Run AuthorityApplication.
 
>### Change email api key in Run/Debug Configurations:
> 1.Choose AuthorityApplication in left bar <br>
> 2.In line **"Environment variables"** add **"spring.mail.password=somepasswordhere"**<br>
> 3.You must now the password ! 
>![img_1.png](img_1.png)

> ### WARNING
> DO NOT CHANGE FILES UNDER **/resources/db/migration** DIRECTORY AS IT WILL CHANGE MIGRATIONS CHECKSUM
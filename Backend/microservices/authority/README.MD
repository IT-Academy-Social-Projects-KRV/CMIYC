> ### Installation guide:
>1. Download MySQL 8.0.26 from https://dev.mysql.com/downloads/installer/
>2. Install MySQL and create user with admin privileges
>3. Add environment variables:<br>
>**spring.datasource.username=<YOUR_USERNAME>;**<br>
>**spring.datasource.password=<YOUR_PASSWORD>;**<br>
>4. Run AuthorityApplication.

> ### WARNING
> DO NOT CHANGE FILES UNDER **/resources/db/migration** DIRECTORY AS IT WILL CHANGE MIGRATIONS CHECKSUM
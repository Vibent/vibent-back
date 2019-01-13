CREATE USER 'vibent_dev'@'%' IDENTIFIED BY '9L%b85u^WGLEin7D';
GRANT INSERT, DELETE, UPDATE, SELECT ON vibent_back_dev.* TO 'vibent_dev'@'%';

CREATE USER 'vibent_prod'@'%' IDENTIFIED BY 'N6^1kr4OwItUoK4#';
GRANT INSERT, DELETE, UPDATE, SELECT ON vibent_back_prod.* TO 'vibent_prod'@'%';

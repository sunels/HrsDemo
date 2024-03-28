CREATE TABLE guest (
                       id                 bigint(20) NOT NULL AUTO_INCREMENT,
                       created_at         datetime(6) NULL,
                       email              varchar(255) NOT NULL,
                       full_name          varchar(255) NOT NULL,
                       last_modified_date datetime(6) NULL,
                       version            bigint(20),
                       PRIMARY KEY (id))  Engine=InnoDB;
CREATE TABLE parsel (
                        id                 bigint(20) NOT NULL AUTO_INCREMENT,
                        accept_date        datetime(6) NOT NULL,
                        created_at         datetime(6) NULL,
                        delivery_date      datetime(6) NULL,
                        description        varchar(1024) NOT NULL,
                        is_delivered       tinyint(4) NOT NULL,
                        last_modified_date datetime(6) NULL,
                        version            bigint(20),
                        guestid            bigint(20) NOT NULL,
                        PRIMARY KEY (id)) Engine=InnoDB;
CREATE TABLE reservation (
                             id                 bigint(20) NOT NULL AUTO_INCREMENT,
                             created_at         datetime(6) NULL,
                             date_in            datetime(6) NOT NULL,
                             date_out           datetime(6) NULL,
                             last_modified_date datetime(6) NULL,
                             version            bigint(20),
                             guestid            bigint(20) NOT NULL,
                             roomid             bigint(20),
                             PRIMARY KEY (id)) Engine=InnoDB;
CREATE TABLE room (
                      id                 bigint(20) NOT NULL AUTO_INCREMENT,
                      created_at         datetime(6) NULL,
                      door_number        int(11) NOT NULL,
                      is_available       tinyint(4) NOT NULL,
                      last_modified_date datetime(6) NULL,
                      version            bigint(20),
                      PRIMARY KEY (id)) Engine=InnoDB;
CREATE UNIQUE INDEX UK_l30f0fvs78rfwtjbim6nqo2cp ON guest (email);
CREATE UNIQUE INDEX UK_gkhar34wjkgh2g02sjjkbkbr0 ON room (door_number);
ALTER TABLE reservation ADD CONSTRAINT FKe6tnoh1ucce3otqrry6gu47xe FOREIGN KEY (roomid) REFERENCES room (id) ON UPDATE Restrict ON DELETE Restrict;
ALTER TABLE parsel ADD CONSTRAINT FKflvk0wvwasdctvbre9fn0h7mq FOREIGN KEY (guestid) REFERENCES guest (id) ON UPDATE Restrict ON DELETE Restrict;
ALTER TABLE reservation ADD CONSTRAINT FKs20fqsqmoo0hf4rp5evjctja9 FOREIGN KEY (guestid) REFERENCES guest (id) ON UPDATE Restrict ON DELETE Restrict;

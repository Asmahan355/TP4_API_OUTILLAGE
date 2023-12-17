CREATE TABLE IF NOT EXISTS ADDRESS (
                                       id INT PRIMARY KEY,
                                       creation TIMESTAMP,
                                       content VARCHAR(255),
                                       auteur VARCHAR(255)
    );



INSERT INTO ADDRESS (id, creation, content, auteur) VALUES (1, CURRENT_TIMESTAMP(), '57 boulevard demorieux','Asmahan El Mhassani');
INSERT INTO ADDRESS (id, creation, content, auteur) VALUES (2, CURRENT_TIMESTAMP(), '51 allee du gamay, 34080 montpellier','Ema Watson');
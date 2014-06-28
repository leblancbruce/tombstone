USE $(database_name);
GO

PRINT 'Populating the cemetery table';
GO

SET NOCOUNT ON;

INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (1, 1, 'Cheticamp Cemetery', '123 Main St', 'Cheticamp', 23, 1, 1898, 'IN_USE', 'INVERNESS');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (1, 1, 'Margaree Cemetery', '13456 Cabot Trail', 'Margaree', NULL, NULL, 1899, 'IN_USE', 'INVERNESS');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (1, 3, 'Inverness Cemetery', '634646 Cabot Trail', 'Inverness', NULL, 11, 1701, 'IN_USE', 'INVERNESS');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (1, 1, 'Inverness Presbyterian Cemetery', '634333 Cabot Trail', 'Inverness', NULL, NULL, NULL, 'DISUSED', 'INVERNESS');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (2, 1, 'Lillian O''reilley Cemetery', '23 St. Mark''s rd.', 'Ile Madame', 12, 9, 1918, 'IN_USE', 'RICHMOND');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (3, 3, 'St. Anthony Cemetery', NULL, 'Annapolis Royal', NULL, NULL, 1946, 'IN_USE', 'ANNAPOLIS');
INSERT INTO cemetery (created_by_application_user_id, updated_by_application_user_id, name, address, city, established_day, established_month, established_year, cemetery_status, county) VALUES (2, 2, 'Fairview Cemetery', '33 Main St.', 'Halifax', 11, 11, 1922, 'IN_USE', 'HALIFAX');

DECLARE @cemeteryId BIGINT;
DECLARE @plotId BIGINT;

SET @cemeteryId = (SELECT id FROM cemetery where name = 'Cheticamp Cemetery');

INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'PT100', @cemeteryId, 'SINGLE');
INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'PT101', @cemeteryId, 'SINGLE');
INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'PT102', @cemeteryId, 'MASS');
INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'PT103', @cemeteryId, 'SINGLE');

SET @plotId = (SELECT id FROM plot where name = 'PT100');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'John', 'Adams', 45, 'MALE', @cemeteryId, @plotId, NEWID());

SET @plotId = (SELECT id FROM plot where name = 'PT101');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Margaret', 'McKeown', 21, 'FEMALE', @cemeteryId, @plotId, NEWID());

SET @plotId = (SELECT id FROM plot where name = 'PT102');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Laurie', 'Jones', 89, 'FEMALE', @cemeteryId, @plotId, NEWID());
INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Robin', 'Jones', NULL, 'UNKNOWN', @cemeteryId, @plotId, NEWID());
INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Ron', 'Jones', 15, 'MALE', @cemeteryId, @plotId, NEWID());

SET @plotId = (SELECT id FROM plot where name = 'PT103');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Louis', 'Deveau', 20, 'MALE', @cemeteryId, @plotId, NEWID());

SET @cemeteryId = (SELECT id FROM cemetery where name = 'Margaree Cemetery');

INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'MC100', @cemeteryId, 'MULTIPLE');
INSERT INTO plot (created_by_application_user_id, updated_by_application_user_id, name, cemetery_id, plot_type) VALUES (2, 2, 'MC101', @cemeteryId, 'MULTIPLE');

SET @plotId = (SELECT id FROM plot where name = 'MC100');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Leo', 'Thurmann', 59, 'MALE', @cemeteryId, @plotId, NEWID());
INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Josette', 'Thurmann', 82, 'FEMALE', @cemeteryId, @plotId, NEWID());

SET @plotId = (SELECT id FROM plot where name = 'MC101');

INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Hank', 'Hill', 68, 'MALE', @cemeteryId, @plotId, NEWID());
INSERT INTO person (created_by_application_user_id, updated_by_application_user_id, firstname, lastname, age_years, gender, cemetery_id, plot_id, person_uuid) VALUES (1, 1, 'Wendy', 'Hill', 81, 'FEMALE', @cemeteryId, @plotId, NEWID());

GO
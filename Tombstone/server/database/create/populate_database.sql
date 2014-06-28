USE $(database_name);
GO

PRINT 'Populating the user table';
GO

SET NOCOUNT ON;

INSERT INTO application_user (firstname, lastname, username, password, active) VALUES ('Bruce', 'LeBlanc', 'bruce', 'tombstone12', 1);
INSERT INTO application_user (firstname, lastname, username, password, active) VALUES ('Barry', 'Coolen', 'barry', 'tombstone12', 1);
INSERT INTO application_user (firstname, lastname, username, password, active) VALUES ('Cynthia', 'Cormier-Simpson', 'cynthia', 'tombstone12', 1);

GO
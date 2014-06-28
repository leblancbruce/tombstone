USE $(database_name);
GO

PRINT 'Creating indexes on the application_user table'
GO

CREATE INDEX application_user_firstname_idx ON application_user(firstname);
CREATE INDEX application_user_lastname_idx ON application_user(lastname);
CREATE UNIQUE INDEX application_user_username_idx ON application_user(username);
GO

PRINT 'Creating indexes on the cemetery table'
GO

CREATE UNIQUE INDEX cemetery_name_idx ON cemetery(name);
GO

PRINT 'Creating indexes on the plot table'
GO

CREATE UNIQUE INDEX plot_name_idx ON plot(name);
GO

PRINT 'Creating indexes on the person table'
GO

CREATE INDEX person_firstname_idx ON person(firstname);
CREATE INDEX person_lastname_idx ON person(lastname);
CREATE INDEX person_maiden_name_idx ON person(maiden_name);
GO

PRINT 'Creating indexes on the next_of_kin table'
GO

CREATE INDEX next_of_kin_name_idx ON next_of_kin(name);
GO

USE $(database_name);
GO

-- #######################################################################
-- # functions                                                           #
-- #######################################################################

CREATE FUNCTION createWhereClause(@where_clause NVARCHAR(256))
RETURNS NVARCHAR(256)
AS
BEGIN
  DECLARE @actual_where_clause NVARCHAR(256);
  
  IF(@where_clause IS NULL)
    BEGIN
	  SET @actual_where_clause = ''; 
	END
  ELSE
    BEGIN
	  SET @actual_where_clause = 'WHERE ' + @where_clause; 
	END;

  RETURN(@actual_where_clause);
END;
GO


-- #######################################################################
-- # image                                                               #
-- #######################################################################

PRINT 'Creating the image stored procedures'
GO

CREATE PROCEDURE getImage
  @where_clause NVARCHAR(256)
AS
  SET NOCOUNT ON;

  DECLARE @sql NVARCHAR(512);

  SET @sql = 'SELECT
      id,
      version,
      created_on,
      updated_on,
      thumbnail,
      image,
      default_image,
      application_user_id,
      cemetery_id,
      plot_id,
      person_id
    FROM image '
    + (SELECT dbo.createWhereClause(@where_clause))
    + ' ORDER BY id'; 

  EXEC(@sql);
GO

CREATE PROCEDURE getImageById
  @id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'id = ' + CAST(@id AS NVARCHAR(32));

  EXEC getImage @where_clause;
GO

CREATE PROCEDURE getImageByUuid
  @image_uuid UNIQUEIDENTIFIER
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'image_uuid = ''' + convert(NVARCHAR(36), @image_uuid) + '''';

  EXEC getImage @where_clause;
GO

CREATE PROCEDURE getImagesByApplicationId
  @application_user_id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'application_user_id = ' + CAST(@application_user_id AS NVARCHAR(32));

  EXEC getImage @where_clause;
GO

CREATE PROCEDURE saveImage
  @id BIGINT,
  @version BIGINT,
  @thumbnail VARBINARY(MAX),
  @image VARBINARY(MAX),
  @default_image TINYINT,
  @application_user_id BIGINT,
  @cemetery_id BIGINT,
  @plot_id BIGINT,
  @person_id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @image_uuid UNIQUEIDENTIFIER;

  SET @image_uuid = NEWID();

  IF(@id IS NULL)
     BEGIN
       INSERT INTO image (
         image_uuid,
         thumbnail,
         image,
         default_image,
         application_user_id,
         cemetery_id,
         plot_id,
         person_id)
       VALUES (
         @image_uuid,
         @thumbnail,
         @image,
         @default_image,
         @application_user_id,
         @cemetery_id,
         @plot_id,
         @person_id);

       EXEC getImageByUuid @image_uuid;
     END
  ELSE
     BEGIN
       UPDATE image SET
         version=@version,
         thumbnail=@thumbnail,
         image=@image,
         default_image=@default_image,
         application_user_id=@application_user_id,
         cemetery_id=@cemetery_id,
         plot_id=@plot_id,
         person_id=@person_id
       WHERE id = @id;

       EXEC getImageById @id;
     END
GO

-- #######################################################################
-- # application_user                                                    #
-- #######################################################################

PRINT 'Creating the application_user stored procedures'
GO

CREATE PROCEDURE getApplicationUser
  @where_clause NVARCHAR(256)
AS
  SET NOCOUNT ON;
  
  DECLARE @sql NVARCHAR(512);
  
  SET @sql = 'SELECT
      id,
      version,
      created_on,
      updated_on,
      firstname,
      lastname,
      username,
      password,
      active
    FROM application_user '
    + (SELECT dbo.createWhereClause(@where_clause))
	+ ' ORDER BY id'; 

  EXEC(@sql);
GO

CREATE PROCEDURE getAllApplicationUsers
AS
  SET NOCOUNT ON;

  EXEC getApplicationUser NULL;
GO

CREATE PROCEDURE getApplicationUserById
  @id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'id = ' + CAST(@id AS NVARCHAR(32));

  EXEC getApplicationUser @where_clause;
GO

CREATE PROCEDURE getApplicationUserByUsername
  @username NVARCHAR(32)
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'username = ''' + @username + '''';

  EXEC getApplicationUser @where_clause;
GO

CREATE PROCEDURE saveApplicationUser
  @id BIGINT,
  @version BIGINT,
  @firstname NVARCHAR(32),
  @lastname NVARCHAR(32),
  @username NVARCHAR(32),
  @password NVARCHAR(64),
  @active TINYINT
AS
  SET NOCOUNT ON;

  IF(@id IS NULL)
     BEGIN
       INSERT INTO application_user (
         firstname,
         lastname,
         username,
         password,
         active)
       VALUES (
         @firstname,
         @lastname,
         @username,
         @password,
         @active);

       EXEC getApplicationUserByUsername @username;
     END
  ELSE
     BEGIN
       UPDATE application_user SET
	     version=@version,
         firstname=@firstname,
         lastname=@lastname,
         username=@username,
         password=@password,
         active=@active
       WHERE id = @id;

       EXEC getApplicationUserById @id;
     END
GO

CREATE PROCEDURE disableApplicationUser
  @id BIGINT
AS
  SET NOCOUNT ON;
  
  UPDATE application_user SET active = 0 WHERE id = @id;

  EXEC getApplicationUserById @id;
GO

CREATE PROCEDURE getApplicationUserSummaries
    @start INT,
	@count INT
AS
  SELECT
    au.id,
	au.firstname,
	au.lastname
  FROM
    application_user au
  ORDER BY 
    au.id;
GO
-- #######################################################################
-- # cemetery                                                            #
-- #######################################################################


PRINT 'Creating the cemetery stored procedures'
GO

CREATE PROCEDURE getCemetery
  @where_clause NVARCHAR(256)
AS
  SET NOCOUNT ON;
  
  DECLARE @sql NVARCHAR(512);

  SET @sql = 'SELECT
      id,
      version,
      created_by_application_user_id,
      created_on,
      updated_by_application_user_id,
      updated_on,
      name,
      address,
      city,
      established_day,
      established_month,
      established_year,
      cemetery_status,
      county
    FROM cemetery '
    + (SELECT dbo.createWhereClause(@where_clause))
	+ ' ORDER BY id'; 
	
  EXEC(@sql);
GO

CREATE PROCEDURE getAllCemeteries
AS
  SET NOCOUNT ON;

  EXEC getCemetery NULL;
GO

CREATE PROCEDURE getCemeteryById
  @id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'id = ' + CAST(@id AS NVARCHAR(32));

  EXEC getCemetery @where_clause;
GO

CREATE PROCEDURE getCemeteryByName
  @name NVARCHAR(128)
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'name = ''' + @name + '''';

  EXEC getCemetery @where_clause;
GO

CREATE PROCEDURE saveCemetery
  @logged_in_user_id BIGINT,
  @id BIGINT,
  @version BIGINT,
  @name NVARCHAR(128),
  @address NVARCHAR(512),
  @city NVARCHAR(128),
  @established_day TINYINT,
  @established_month TINYINT,
  @established_year SMALLINT,
  @cemetery_status NVARCHAR(32),
  @county NVARCHAR(32)
AS
  SET NOCOUNT ON;

  IF(@id IS NULL)
     BEGIN
       INSERT INTO cemetery (
         created_by_application_user_id,
         updated_by_application_user_id,
         name,
         address,
         city,
         established_day,
         established_month,
         established_year,
         cemetery_status,
         county)
       VALUES (
         @logged_in_user_id,
         @logged_in_user_id,
         @name,
         @address,
         @city,
         @established_day,
         @established_month,
         @established_year,
         @cemetery_status,
         @county);

       EXEC getCemeteryByName @name;
     END
  ELSE
     BEGIN
       UPDATE cemetery SET
         version = @version,
         updated_by_application_user_id = @logged_in_user_id,
         name = @name,
         address = @address,
         city = @city,
         established_day = @established_day,
         established_month = @established_month,
         established_year = @established_year,
         cemetery_status = @cemetery_status,
         county = @county

       WHERE id = @id;

       EXEC getCemeteryById @id;
     END
GO

CREATE PROCEDURE getCemeterySummaries
    @start INT,
	@count INT
AS
  SELECT
    c.id,
	c.name,
	c.established_year,
	c.cemetery_status,
	COUNT(DISTINCT(pt.id)),
	COUNT(DISTINCT(pr.id)),
	c.updated_on,
	au.username
  FROM
    cemetery c
	LEFT JOIN plot pt ON c.id = pt.cemetery_id
	LEFT JOIN person pr ON c.id = pr.cemetery_id
	LEFT JOIN application_user au ON c.updated_by_application_user_id = au.id
  GROUP BY
    c.id,
    c.name,
    c.established_year,
    c.cemetery_status,
    c.updated_on,
    au.username
  ORDER BY 
    c.id;
GO

-- #######################################################################
-- # plot                                                                #
-- #######################################################################


PRINT 'Creating the plot stored procedures'
GO

CREATE PROCEDURE getPlot
  @where_clause NVARCHAR(256)
AS
  SET NOCOUNT ON;
    
  DECLARE @sql NVARCHAR(512);
  
  SET @sql = 'SELECT
      id,
      version,
      created_by_application_user_id,
      created_on,
      updated_by_application_user_id,
      updated_on,
      name,
      cemetery_id,
	  plot_type
    FROM plot '
    + (SELECT dbo.createWhereClause(@where_clause)) 
	+ ' ORDER BY id'; 

  EXEC(@sql);
GO

CREATE PROCEDURE getPlotById
  @id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'id = ' + CAST(@id AS NVARCHAR(32));

  EXEC getPlot @where_clause;
GO

CREATE PROCEDURE getPlotByName
  @name NVARCHAR(32)
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'name = ''' + @name + '''';

  EXEC getPlot @where_clause;
GO

CREATE PROCEDURE savePlot
  @logged_in_user_id BIGINT,
  @id BIGINT,
  @version BIGINT,
  @name NVARCHAR(32),
  @cemetery_id BIGINT,
  @plot_type NVARCHAR(32)
AS
  SET NOCOUNT ON;

  IF(@id IS NULL)
     BEGIN
       INSERT INTO plot (
         created_by_application_user_id,
         updated_by_application_user_id,
         name,
         cemetery_id,
         plot_type)
       VALUES (
         @logged_in_user_id,
         @logged_in_user_id,
         @name,
         @cemetery_id,
         @plot_type);

       EXEC getPlotByName @name;
     END
  ELSE
     BEGIN
       UPDATE plot SET
	     version = @version,
         updated_by_application_user_id = @logged_in_user_id,
         name = @name,
         cemetery_id = @cemetery_id,
		 plot_type = @plot_type
       WHERE id = @id;

       EXEC getPlotById @id;
     END
GO

-- #######################################################################
-- # person                                                              #
-- #######################################################################


PRINT 'Creating the person stored procedures'
GO

CREATE PROCEDURE getPerson
  @where_clause NVARCHAR(256)
AS
  SET NOCOUNT ON;
  
  DECLARE @sql NVARCHAR(512);
  
  SET @sql = 'SELECT
      id,
      version,
      created_by_application_user_id,
      created_on,
      updated_by_application_user_id,
      updated_on,
      firstname,
      middlenames,
      lastname,
      maiden_name,
      age_days,
      age_months,
      age_years,
      birth_day,
      birth_month,
      birth_year,
      death_day,
      death_month,
      death_year,
      gender,
      cemetery_id,
      plot_id
    FROM person '
    + (SELECT dbo.createWhereClause(@where_clause))
	+ ' ORDER BY id'; 

  EXEC(@sql);
GO

CREATE PROCEDURE getPersonById
  @id BIGINT
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'id = ' + CAST(@id AS NVARCHAR(32));

  EXEC getPerson @where_clause;
GO

CREATE PROCEDURE getPersonByPersonUuid
  @person_uuid UNIQUEIDENTIFIER
AS
  SET NOCOUNT ON;

  DECLARE @where_clause NVARCHAR(256);

  SET @where_clause = 'person_uuid = ''' + convert(NVARCHAR(36), @person_uuid) + '''';

  EXEC getPerson @where_clause;
GO

CREATE PROCEDURE savePerson
  @logged_in_user_id BIGINT,
  @id BIGINT,
  @version BIGINT,
  @firstname NVARCHAR(32),
  @middlenames NVARCHAR(128),
  @lastname NVARCHAR(32),
  @maiden_name NVARCHAR(32),
  @age_days TINYINT,
  @age_months TINYINT,
  @age_years TINYINT,
  @birth_day TINYINT,
  @birth_month TINYINT,
  @birth_year SMALLINT,
  @death_day TINYINT,
  @death_month TINYINT,
  @death_year SMALLINT,
  @gender NVARCHAR(32),
  @cemetery_id BIGINT,
  @plot_id BIGINT
AS
  SET NOCOUNT ON;

  IF(@id IS NULL)
     BEGIN
	   DECLARE @uuid UNIQUEIDENTIFIER;
	   SET @uuid = NEWID();

       INSERT INTO person (
         created_by_application_user_id,
         updated_by_application_user_id,
         firstname,
         middlenames,
         lastname,
         maiden_name,
         age_days,
         age_months,
         age_years,
         birth_day,
         birth_month,
         birth_year,
         death_day,
         death_month,
         death_year,
         gender,
         cemetery_id,
         plot_id,
         person_uuid)
       VALUES (
         @logged_in_user_id,
         @logged_in_user_id,
         @firstname,
         @middlenames,
         @lastname,
         @maiden_name,
         @age_days,
         @age_months,
         @age_years,
         @birth_day,
         @birth_month,
         @birth_year,
         @death_day,
         @death_month,
         @death_year,
         @gender,
         @cemetery_id,
         @plot_id,
         @uuid);

       EXEC getPersonByPersonUuid @uuid;
     END
  ELSE
     BEGIN
       UPDATE person SET
	     version = @version,
         updated_by_application_user_id = @logged_in_user_id,
         firstname = @firstname,
         middlenames = @middlenames,
         lastname = @lastname,
         maiden_name = @maiden_name,
         age_days = @age_days,
         age_months = @age_months,
         age_years = @age_years,
         birth_day = @birth_day,
         birth_month = @birth_month,
         birth_year = @birth_year,
         death_day = @death_day,
         death_month = @death_month,
         death_year = @death_year,
         gender = @gender,
         cemetery_id = @cemetery_id,
         plot_id = @plot_id
       WHERE id = @id;

       EXEC getPersonById @id;
     END
GO

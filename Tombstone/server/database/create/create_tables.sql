USE $(database_name);
GO

PRINT 'Creating the image table';
GO

CREATE TABLE image
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  image_uuid UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL UNIQUE,
  thumbnail VARBINARY(MAX) FILESTREAM NOT NULL,
  image VARBINARY(MAX) FILESTREAM NOT NULL,
  default_image TINYINT NOT NULL DEFAULT(0) CHECK(default_image = 0 OR default_image = 1),
  application_user_id BIGINT NULL,
  cemetery_id BIGINT NULL,
  plot_id BIGINT NULL,
  person_id BIGINT NULL
);
GO

PRINT 'Creating the application_user table';
GO

CREATE TABLE application_user
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  firstname NVARCHAR(32) NOT NULL,
  lastname NVARCHAR(32) NOT NULL,
  username NVARCHAR(32) NOT NULL UNIQUE,
  password NVARCHAR(64) NOT NULL,
  active TINYINT NOT NULL DEFAULT(0) CHECK(active = 0 OR active = 1)
);
GO

PRINT 'Creating the cemetery table';
GO

CREATE TABLE cemetery
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  created_by_application_user_id BIGINT NOT NULL,
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_by_application_user_id BIGINT NOT NULL,
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  name NVARCHAR(128) NOT NULL UNIQUE,
  address NVARCHAR(512) NULL,
  city NVARCHAR(128) NULL,
  established_day TINYINT NULL CHECK(established_day > 0 AND established_day < 32),
  established_month TINYINT NULL CHECK(established_month > 0 AND established_month < 13),
  established_year SMALLINT NULL CHECK(established_year > 1600),
  cemetery_status NVARCHAR(32) NOT NULL DEFAULT('UNKNOWN'),
  county NVARCHAR(32) NOT NULL
);
GO

PRINT 'Creating the plot table';
GO

CREATE TABLE plot
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  name NVARCHAR(32) NOT NULL UNIQUE,
  created_by_application_user_id BIGINT NOT NULL,
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_by_application_user_id BIGINT NOT NULL,
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  cemetery_id BIGINT NOT NULL,
  plot_type NVARCHAR(32) NOT NULL DEFAULT('UNKNOWN')
);
GO

PRINT 'Creating the person table';
GO

CREATE TABLE person
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  created_by_application_user_id BIGINT NOT NULL,
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_by_application_user_id BIGINT NOT NULL,
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  firstname NVARCHAR(32) NULL,
  middlenames NVARCHAR(128) NULL,
  lastname NVARCHAR(32) NULL,
  maiden_name NVARCHAR(128) NULL,
  age_days TINYINT NULL CHECK(age_days > 0),
  age_months TINYINT NULL CHECK(age_months > 0),
  age_years TINYINT NULL CHECK(age_years > 0),
  birth_day TINYINT NULL CHECK(birth_day > 0 AND birth_day < 32),
  birth_month TINYINT NULL CHECK(birth_month > 0 AND birth_month < 13),
  birth_year SMALLINT NULL CHECK(birth_year > 1600),
  death_day TINYINT NULL CHECK(death_day > 0 AND death_day < 32),
  death_month TINYINT NULL CHECK(death_month > 0 AND death_month < 13),
  death_year SMALLINT NULL CHECK(death_year > 1600),
  gender NVARCHAR(32) NOT NULL,
  cemetery_id BIGINT NOT NULL,
  plot_id BIGINT NOT NULL,
  person_uuid UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL UNIQUE
);
GO

PRINT 'Creating the next_of_kin table';
GO

CREATE TABLE next_of_kin
(
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  version BIGINT NOT NULL DEFAULT(0),
  created_by_application_user_id BIGINT NOT NULL,
  created_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  updated_by_application_user_id BIGINT NOT NULL,
  updated_on DATETIMEOFFSET NOT NULL DEFAULT (SYSDATETIMEOFFSET()),
  person_id BIGINT NOT NULL,
  next_of_kin_person_id BIGINT NULL,
  name NVARCHAR(64) NULL,
  relationship NVARCHAR(16) NOT NULL DEFAULT('UNKNOWN')
);
GO

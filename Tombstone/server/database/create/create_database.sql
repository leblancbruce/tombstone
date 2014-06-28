USE master;
GO

IF(EXISTS (SELECT name FROM sysdatabases WHERE '[' + name + ']' = '$(database_name)' OR name = '$(database_name)'))
BEGIN
  PRINT 'The ' + '$(database_name)' + ' database already exists.  Dropping it.';

  DROP DATABASE $(database_name);
END

PRINT 'Creating the ' + '$(database_name)' + ' database.';

CREATE DATABASE $(database_name) ON PRIMARY
  ( NAME = '$(database_name)_primary',
    FILENAME = '$(sql_server_home)\$(database_name)_primary.mdf'),
FILEGROUP $(database_name)_image_filegroup CONTAINS FILESTREAM
  (NAME = '$(database_name)_image_filegroup',
   FILENAME = '$(sql_server_home)\$(database_name)_image_filegroup.ndf')
LOG ON
  (NAME = '$(database_name)_log',
   FILENAME = '$(sql_server_home)\$(database_name)_log.ldf');

GO

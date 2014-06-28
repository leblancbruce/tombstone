USE $(database_name);
GO

PRINT 'Adding user-defined error messages';
GO

EXEC sp_addmessage @msgnum=50001, @severity=16, @msgtext=N'Concurrent modification error.', @replace = 'REPLACE'
GO

CREATE PROCEDURE createUpdateTrigger
  @table_name VARCHAR(50)
AS
    EXEC ('CREATE TRIGGER update_' + @table_name + ' ON [' + @table_name + '] FOR UPDATE AS '
	  + 'BEGIN'
	  + '  SET NOCOUNT ON'
	  + '  DECLARE @id BIGINT;'
	  + '  DECLARE @old_version BIGINT;'
	  + '  DECLARE @current_database_version BIGINT;'
	  + '  SELECT @id=id, @old_version=version FROM deleted;'
	  + '  SET @current_database_version = (SELECT version FROM application_user WHERE id = @id);'
	  + '  IF(@old_version <> @current_database_version)'
	  + '  BEGIN'
	  + '    RAISERROR(50001, 16, 2);'
	  + '  END;'
	  + '  DECLARE @new_version BIGINT;'
	  + '  SET @new_version = @old_version + 1;'
	  + '  UPDATE application_user SET version = @new_version, updated_on = SYSDATETIMEOFFSET() WHERE id = @id;'
	  + 'END;');
GO

PRINT 'Creating the triggers on the application_user table';
GO

EXEC createUpdateTrigger 'application_user';
GO

PRINT 'Creating the triggers on the application_user_image table';
GO

EXEC createUpdateTrigger 'application_user_image';
GO

PRINT 'Creating the triggers on the cemetery table';
GO

EXEC createUpdateTrigger 'cemetery';
GO

PRINT 'Creating the triggers on the cemetery_image table';
GO

EXEC createUpdateTrigger 'cemetery_image';
GO

PRINT 'Creating the triggers on the plot table';
GO

EXEC createUpdateTrigger 'plot';
GO

PRINT 'Creating the triggers on the plot_image table';
GO

EXEC createUpdateTrigger 'plot_image';
GO

PRINT 'Creating the triggers on the person table';
GO

EXEC createUpdateTrigger 'person';
GO

PRINT 'Creating the triggers on the person_image table';
GO

EXEC createUpdateTrigger 'person_image';
GO

PRINT 'Creating the triggers on the next_of_kin table';
GO

EXEC createUpdateTrigger 'next_of_kin';
GO

DROP PROCEDURE createUpdateTrigger;
GO

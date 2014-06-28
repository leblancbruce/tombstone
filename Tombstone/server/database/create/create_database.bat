@ECHO OFF

REM ######################################################################################################################################
REM Before you can use this script, you need to configure SQL Server properly first.
REM 
REM 1. File streams are disabled by default.  To enable them, follow these instructions 
REM    http://msdn.microsoft.com/en-us/library/cc645923.aspx
REM 2. Load the SQL Server Configuration Manager (not the SQL Server Management Studio).
REM    a. Click on 'SQL Server Network Configuration' in the left-hand pane.
REM    b. Double-click on 'Protocols for SQLEXPRESS' in the right-hand pane (or whatever relevant SQL SERVER instance referenced).
REM    c. Double-click on 'TCP/IP' and switch 'Enabled' from 'No' to 'Yes'.
REM    d. Before Applying, click on the 'IP Addresses' tab.
REM    e. Change the 'Enabled' value for all entries.
REM    f. Delete the value contained within 'TCP Dynamic Ports' in the 'IPAll' section.
REM    g. Set the 'TCP Port' value to 1433 in the 'IPAll' section.
REM    d. Apply the changes
REM    e. Click on 'SQL Server Services' in the left-hand pane.
REM    f. Right-click on 'SQL Server (SQLEXPRESS)' in the right-hand pane (or whatever relevant SQL SERVER instance referenced).
REM    g. Click 'restart'.
REM    f. Right-click on 'SQL Server (SQLEXPRESS)' in the right-hand pane (or whatever relevant SQL SERVER instance referenced).
REM    g. Click 'restart'.
REM 3. Update the environment variables declared within this batch file to the appropriate values for the system hosting SQL Server.
REM
REM ######################################################################################################################################

SET HOSTNAME=COLOSSUS
SET INSTANCE=SQLExpress
SET USERNAME=sa
SET PASSWORD=Password12
SET DATABASE_NAME=tombstone
SET SQL_SERVER_HOME="C:\Program Files\Microsoft SQL Server\MSSQL10_50.SQLEXPRESS\MSSQL\DATA"

sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_database.sql -v database_name=%DATABASE_NAME% sql_server_home=%SQL_SERVER_HOME% 
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_tables.sql -v database_name=%DATABASE_NAME%
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_foreign_keys.sql -v database_name=%DATABASE_NAME%
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_triggers.sql -v database_name=%DATABASE_NAME%
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_indexes.sql -v database_name=%DATABASE_NAME%
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\create_stored_procedures.sql -v database_name=%DATABASE_NAME%
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\populate_database.sql -v database_name=%DATABASE_NAME%

IF "%1"=="test" GOTO :TEST

GOTO :END

:TEST
sqlcmd -S %HOSTNAME%\%INSTANCE% -U %USERNAME% -P %PASSWORD% -i .\load_test_data.sql -v database_name=%DATABASE_NAME%

:END
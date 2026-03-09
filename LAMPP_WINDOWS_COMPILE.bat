@echo off
cd %USERPROFILE%\Lampp
REM Show Java version
echo "Java version should be 22 or higher. Version 22 is best for now."
java -version
echo "JavaFX version should be 22.0.2 for gif animation to work and path to directory's lib subdirectory given in system variable PATH_TO_FX."
echo PATH_TO_FX=%PATH_TO_FX%
REM First unpack png images into html directory for program access and inclusion in jar file
REM Define variables for the archive and destination
REM SET ARCHIVE_PATH="%USERPROFILE%\Lampp\src\img.tar.gz"
REM SET DESTINATION_PATH="%USERPROFILE%\Lampp\src\main\resources\html"
REM Extract the tar.gz file using the built-in tar command
REM tar -xvzf %ARCHIVE_PATH% -C %DESTINATION_PATH%
REM echo Extraction complete.
echo running Maven to create jar file.
mvn clean package
echo Lampp can be run using:
echo java  --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web  --module-path %PATH_TO_FX% --add-modules javafx.graphics,javafx.web -jar target/Lampp-0.0.1-SNAPSHOT.jar
echo or using:
echo java --module-path %PATH_TO_FX% --add-modules javafx.graphics,javafx.web -jar target\Lampp-0.0.1-SNAPSHOT.jar
echo or using:
echo LAMPP_WINDOWS_RUN.bat
pause

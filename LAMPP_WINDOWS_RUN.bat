@echo off
cd %USERPROFILE%\Lampp
java --module-path %PATH_TO_FX% --add-modules javafx.graphics,javafx.web -jar target\Lampp-0.0.1-SNAPSHOT.jar
pause

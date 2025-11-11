@echo off
@REM REM Compile et package le projet
@REM mvn clean package;

REM Vérifie si le build a réussi
IF EXIST target\testapp-1.0.0.war (
    echo Copie du WAR vers Tomcat...
    copy /Y target\testapp-1.0.0.war C:\xampp\tomcat\webapps
    echo Deploiement terminé.
) ELSE (
    echo Le fichier WAR n'a pas été généré. Vérifiez les erreurs Maven.
)
pause
@echo off
:: Déclaration des variables
set "name_jar= framework.jar"
set "lib_test=F:\S4\WEB-DYNAMIQUE\Framework\test-framework\lib"
jar cvf %name_jar% -C bin .
xcopy /s /y %name_jar% %lib_test%
xcopy /s /y  "lib\*" %lib_test% 
echo "Jarification et copy terminé"
pause > nul
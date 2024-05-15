@echo off
set jdk=%PROGRAMFILES(X86)%\BlueJ\jdk\bin
"%jdk%\jar" cfe build.jar Main *.class *.txt
pause
@echo off
set jdk=%PROGRAMFILES(X86)%\BlueJ\jdk\bin
"%jdk%\javac" *.java
"%jdk%\java" Main
pause
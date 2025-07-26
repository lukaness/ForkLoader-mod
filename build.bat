@echo off
REM Navigate to project root
cd /d "%~dp0"

REM Use Gradle Wrapper to clean and build
gradlew.bat clean build

REM Copy the generated JAR to output folder
if not exist output mkdir output
copy build\libs\*.jar output\ForkLoader.jar

echo Build finished!
pause

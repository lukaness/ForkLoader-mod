@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIR=%~dp0
@rem Find java.exe
set JAVA_EXE=

if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\java.exe" (
        set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
    )
)

if not defined JAVA_EXE (
    set JAVA_EXE=java
)

@rem Get command-line arguments, handling Windows quoting
set CMD_LINE_ARGS=

:setupArgs
if "%1"=="" goto doneArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setupArgs
:doneArgs

@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% -Dorg.gradle.appname=%~nx0 -classpath "%DIR%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%

:end
if "%OS%"=="Windows_NT" endlocal

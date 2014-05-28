@echo off
setlocal

set JAVA_OPTS=%JAVA_OPTS% -Xmx2g
set CP=
set PRG_ROOT=%~dp0
set LIB=%PRG_ROOT%target\scala-2.10\lib\
for /f %%i in ('dir /b %LIB%') do call :concat %%i
java -cp "%CP%;%PRG_ROOT%target/scala-2.10/beacon_2.10-1.0.jar" Beacon %1 %2 %3 %4 %5 %6 %7 %8 %9
endlocal
goto :eof

:concat
set CP=%CP%%LIB%%1;
goto :eof

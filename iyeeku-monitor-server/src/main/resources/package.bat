@echo off

set one=%1
set panTmp=E:\auto_Oracle\tmp\iyeekuMonitor
set srcTmp=C:\iyeeku\iyeeku-monitor-server

echo.
echo *************************************************************
echo ?????????,??Iyeeku-Monitor-Server??????????
echo *************************************************************
echo.

if "%one%"=="-n" (
	echo.
) else (
	c:
	cd %srcTmp%

	echo ******************????*********************
	echo.

	rem ?????????????mvn ????????pause???????????mvn?????bat??????exit??????????????????call??????
	call mvn clean

	echo.
	echo ******************???????????class??*********************
	echo.

	call mvn package

	echo.
	echo ******************??????????????????*********************
	echo.
)

if exist %panTmp% (
	rem ??E:\auto_Oracle\tmp\iyeekuMonitor????????.
	e:
	cd E:\auto_Oracle\utils
	java DeleteDirectory E:\auto_Oracle\tmp\iyeekuMonitor

	rem ????
	call md %panTmp%
) else (
	call md %panTmp%
)

echo.
echo ???????????????????
echo.
xcopy %srcTmp%\target\classes %panTmp% /s
md %panTmp%\lib
xcopy E:\auto_Oracle\resource %panTmp%\lib

echo.
echo ?????????
echo.

e:
cd E:\auto_Oracle\utils
java -classpath .;.\lib\commons-compress-1.18.jar GZIPUtil %panTmp%


echo.
echo ****************????iyeekuMonitor.tar.gz?*******************
echo.

echo open xxxxxxxx >ftp.up
echo xxxxxxxx>>ftp.up
echo xxxxxxxx>>ftp.up
echo binary>>ftp.up
echo put E:\auto_Oracle\tmp\iyeekuMonitor.tar.gz>>ftp.up
echo bye>>ftp.up
FTP -s:ftp.up

echo.
echo ******************????*********************

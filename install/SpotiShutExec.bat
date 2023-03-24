@echo off
cd "%appdata%\Spotify\" 
start Spotify.exe
timeout 0.2
cd "%appdata%"
start SpotiShut.jar
exit
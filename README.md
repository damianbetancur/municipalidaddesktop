# municipalidaddesktop

trello: https://trello.com/b/SusCqqtP/mas-team-scrum-final


Docker: Crea una instancia de MYSQL con una DB "municipalidaddesktop"

docker pull mysql:8.0.18
docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=municipalidaddesktop -p 3306:3306 mysql:8.0.18

ejecutar App: creara las tablas necesarias en la DB "municipalidaddesktop"

Script de carga
script.txt

ingresar
user: damian
password: 12345
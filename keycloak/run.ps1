$Version = "18.0.0"

Invoke-WebRequest -Uri https://github.com/keycloak/keycloak/releases/download/$Version/keycloak-$Version.zip -OutFile keycloak-$Version.zip
Expand-Archive -Path keycloak-$Version.zip -DestinationPath .

#Set-Location keycloak-$Version\bin
#cmd.exe /c "add-user-keycloak.bat -r master -u admin -p admin"
#cmd.exe /c "add-user-keycloak.bat -r microservices -u admin -p admin"
#cmd.exe /c "add-user-keycloak.bat -r microservices -u user -p user"

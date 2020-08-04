Invoke-WebRequest -Uri https://downloads.jboss.org/keycloak/11.0.0/keycloak-11.0.0.zip -OutFile keycloak-11.0.0.zip
Expand-Archive -Path keycloak-11.0.0.zip

Set-Location keycloak-11.0.0\bin
cmd.exe /c "add-user-keycloak.bat -r master -u admin -p admin"
cmd.exe /c "add-user-keycloak.bat -r microservices -u admin -p admin"
cmd.exe /c "add-user-keycloak.bat -r microservices -u user -p user"
cmd.exe /c "standalone.bat -Djboss.socket.binding.port-offset=100 -Dkeycloak.profile.feature.upload_scripts=enabled"
#cmd.exe /c "standalone.bat -Djboss.socket.binding.port-offset=100 -Dkeycloak.profile.feature.upload_scripts=enabled -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=microservices-realm.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING"

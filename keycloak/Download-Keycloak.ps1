$Version = "21.1.2"

$ProgressPreference = 'SilentlyContinue'
Invoke-WebRequest -Uri https://github.com/keycloak/keycloak/releases/download/$Version/keycloak-$Version.zip -OutFile keycloak-$Version.zip
Expand-Archive -Path keycloak-$Version.zip -DestinationPath .

#!/bin/bash

# https://stackoverflow.com/a/47181236/1098564
wget -c https://downloads.jboss.org/keycloak/11.0.0/keycloak-11.0.0.zip

# https://askubuntu.com/a/994965/109301
unzip -n keycloak-11.0.0.zip

cd keycloak-11.0.0/bin || exit
./add-user-keycloak.sh -r master -u admin -p admin
./add-user-keycloak.sh -r microservices -u admin -p admin
./add-user-keycloak.sh -r microservices -u user -p user
./standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.profile.feature.upload_scripts=enabled
#./standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.profile.feature.upload_scripts=enabled -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file="microservices-realm.json" -Dkeycloak.migration.strategy=OVERWRITE_EXISTING

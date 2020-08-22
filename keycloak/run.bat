@echo off
pushd keycloak-11.0.1\bin
standalone.bat -Djboss.socket.binding.port-offset=100 -Dkeycloak.profile.feature.upload_scripts=enabled
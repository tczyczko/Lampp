#!/bin/bash
# Execute Lampp java program -- after using LAMPP_LINUX_COMPILE.sh
# Can be used directly in the BASH terminal
# Can also be called by the desktop GUI launcher Lampp.desktop

cd ~/Lampp

#java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

java  --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.graphics,javafx.web -jar ./target/Lampp-0.0.1-SNAPSHOT.jar

#java -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
# Any javafx higher than 22.0.2 and up to 27 (so far) will NOT allow animated gifs in webview. Possibly this will NEVER be fixed as webkit is being deprecated in favor of forcing developers to access the machine's (phone's) default browser.

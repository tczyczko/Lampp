#!/bin/bash
# Execute Lampp java program
# Can be used directly in the BASH terminal
# Is also called by the desktop GUI launcher Lampp.desktop
cd /home/tom/Lampp
/home/tom/NetBeansJDKs/graalvm-jdk-25.0.2+10.1/bin/java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path /home/tom/NetBeansJDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

#/home/tom/NetBeansJDKs/graalvm-svm-java17-linux-gluon-22.1.0.1-Final/bin/java -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path /home/tom/NetBeansJDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
# Any javafx higher than 22.0.2 and up to 27 (so far) will NOT allow animated gifs in webview. Possibly this will NEVER be fixed as webkit is being deprecated in favor of forcing developers to access the machine's (phone's) default browser.



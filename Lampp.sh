#!/bin/bash
# Execute Lampp java program
# Can be used directly in the BASH terminal
# Is also called by the desktop GUI launcher Lampp.desktop
cd $HOME/Lampp
# The following line added as new Cosmic desktop for Pop OS displays Java app too large
export GDK_SCALE=2.0
#export GDK_DPI_SCALE=0.5
#$HOME/JDKs/graalvm-jdk-25.0.2+10.1/bin/java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $HOME/JDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

#$HOME/JDKs/graalvm-svm-java17-linux-gluon-22.1.0.1-Final/bin/java -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $HOME/JDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

#Use system java and PATH_TO_FX variable assigned and exported in .bashrc file in home directory
java  -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

# Any javafx higher than 22.0.2 and up to 27 (so far) will NOT allow animated gifs in webview. Possibly this will NEVER be fixed as webkit is being deprecated in favor of forcing developers to access the machine's (phone's) default browser.



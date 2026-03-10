#!/bin/bash
cd ~/Lampp
# Show Java version
echo "Java version should be 22 or higher. Version 22 is best for now."
java -version
echo "JavaFX version should be 22.0.2 for gif animation to work and path to directory's lib subdirectory given in system variable PATH_TO_FX."
echo "PATH_TO_FX="$PATH_TO_FX
echo "PATH_TO_FX_MODS="$PATH_TO_FX_MODS
# First unpack png images into html directory for program access and inclusion in jar file
# Define variables for the archive and destination
# ARCHIVE_PATH="~/Lampp/src/img.tar.gz"
# DESTINATION_PATH="~\Lampp\src\main\resources\html"
# Extract the tar.gz file using the built-in tar command
# tar -xvzf $ARCHIVE_PATH -C $DESTINATION_PATH
# echo Extraction complete.
echo "running Maven to create jar file."
mvn clean package
echo "Lampp can be run using:"
echo "java  --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web  --module-path \$PATH_TO_FX --add-modules javafx.graphics,javafx.web -jar target/Lampp-0.0.1-SNAPSHOT.jar"
echo or using:
echo "java --module-path \$PATH_TO_FX --add-modules javafx.graphics,javafx.web -jar target/Lampp-0.0.1-SNAPSHOT.jar"
echo or using:
echo "java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path \$PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp"
echo or using:
echo "LAMPP_LINUX_RUN.sh"
read -n 1 -s -r -p "Press any key to continue..."



#!/bin/bash
# Execute Lampp java program
# Can be used directly in the BASH terminal
# Is also called by the desktop GUI launcher Lampp.desktop
#   cd ~/Lampp
#java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path /home/tom/JDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
#   java  --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.graphics,javafx.web -jar ./target/Lampp-0.0.1-SNAPSHOT.jar
#java -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
# Any javafx higher than 22.0.2 and up to 27 (so far) will NOT allow animated gifs in webview. Possibly this will NEVER be fixed as webkit is being deprecated in favor of forcing developers to access the machine's (phone's) default browser.

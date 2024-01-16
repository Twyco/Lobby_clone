#!/bin/bash
pom_file_path="pom.xml"
ftpServer=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:ftpServer[1]" $pom_file_path)
ftpUser=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:ftpUser[1]" $pom_file_path)
ftpPassword=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:ftpPassword[1]" $pom_file_path)
artifact_id=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:artifactId[1]" $pom_file_path)
version=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:version[1]" $pom_file_path)
serverPath1=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:serverPath1[1]" $pom_file_path)
serverPath2=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:serverPath2[1]" $pom_file_path)
serverPath3=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:serverPath3[1]" $pom_file_path)
target="target/${artifact_id}-${version}.jar"
jar1="${serverPath1}${artifact_id}-${version}.jar"
jar2="${serverPath2}${artifact_id}-${version}.jar"
jar3="${serverPath3}${artifact_id}-${version}.jar"

#uploadFTP
ftp -n $ftpServer <<EOF
user $ftpUser $ftpPassword
put $target $jar1
put $target $jar2
put $target $jar3
bye
EOF

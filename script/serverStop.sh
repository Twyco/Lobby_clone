pom_file_path="pom.xml"
rconHost=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:rconHost[1]" $pom_file_path)
rconPort1=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:rconPort1[1]" $pom_file_path)
rconPort2=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:rconPort2[1]" $pom_file_path)
rconPort3=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:rconPort3[1]" $pom_file_path)
rconPassword=$(xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -v "//ns:project/ns:properties/ns:rconPassword[1]" $pom_file_path)
rconCommand="stop"

mcrcon -H $rconHost -P $rconPort1 -p $rconPassword -c $rconCommand
mcrcon -H $rconHost -P $rconPort2 -p $rconPassword -c $rconCommand
mcrcon -H $rconHost -P $rconPort3 -p $rconPassword -c $rconCommand
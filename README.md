# Expander

## Description
Tested on JDK 17.

## Help (java -jar expander.jar -h)
```

```

## Installation
```
mvn clean install -DskipTests=true
```
Resulting build file can be found in `target/expander-{{version}}-jar-with-dependencies.jar`

## Usage
**Files structure**
```
tmp/
    input-map.csv
    
server-files/
    server-a.xlsx
    server-b.xlsx
    other-servers/
        other-server-a.xls
        other-server-b.xlsm
    lost.csv
    README.md

role-files/
```
**Content of arbitrary.properties file**
```

```
### Expand action
Following command will process and edit these files: server-a.xlsx, server-b.xlsx, other-server-a.xls, other-serve-b.xlsm; 
replacing all dynamically generated oids with excel formula to static values set by input map.
```
java -jar excel-oid-transformation-1.0-jar-with-dependencies.jar replaceOid -m /tmp/input-map.csv -t server -w /server-files
```

Input map should be a *.csv file with two columns: old oid, new oid; separated with semicolon as a delimiter.

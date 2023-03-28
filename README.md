# Expander

## Description
Tested on JDK 17.

## Help (java -jar expander.jar -h)
```
Usage: java -jar expander.jar [options] [command] [command options]
  Options:
    -c, --charset

      Default: utf-8
    -h, --help

    -s, --silent

      Default: false
    -v, --verbose

      Default: false
    -V, --version

      Default: false
  Commands:
    expand
      Usage: expand [options]
        Options:
        * -e, --extension

        * -p, --properties-file

        * -w, --working-directory

```

## Installation
```
mvn clean install -DskipTests=true
```
Resulting build file can be found in `target/expander-{{version}}-jar-with-dependencies.jar`

## Usage
**Files structure**
```
workfiles/
    working-directory/
        resource-ad.xml
        resource-crm-core.xml
    arbitrary.properties
```
**Content of arbitrary.properties file**
```
crmcore.virtualPageSize=1
crmcore.allowToRenameFunctions=false
crmcore.attribute.state.strength=weak
```
### Expand action
Following command will process and edit these files: resource-ad.xml, resource-crm-core.xml; 
replacing all keys specified as $(key) based on specified properties in arbitrary.properties file.
```
java -jar expander-1.0-jar-with-dependencies.jar expand -e xml -w /working-directory -p arbitrary.properties
```


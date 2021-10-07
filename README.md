# xerces-sparse-CMStateSet

See https://issues.apache.org/jira/browse/XERCESJ-1227.

The JARs in the [lib](lib) folder are from [Xerces-J-bin.2.12.1-xml-schema-1.1.zip](https://dlcdn.apache.org//xerces/j/binaries/Xerces-J-bin.2.12.1-xml-schema-1.1.zip). See https://xerces.apache.org/mirrors.cgi#binary

## Run with make

```
$ make
...
With original CMStateSet:
java -cp "lib/*:bin" Run
loading CMStateSet.class from jar:file:/.../lib/xercesImpl-2.12.1-xml-schema-1.1.jar!/org/apache/xerces/impl/dtd/models/CMStateSet.class
validating test.xml with maxOccurs.xsd
7903.043619 millis
With sparse CMStateSet:
java -cp "bin:lib/*" Run
loading CMStateSet.class from file:/.../bin/org/apache/xerces/impl/dtd/models/CMStateSet.class
validating test.xml with maxOccurs.xsd
1639.523698 millis
```

## Run with Ant

```
$ ant
...
run-original:
     [java] loading CMStateSet.class from jar:file:/.../lib/xercesImpl-2.12.1-xml-schema-1.1.jar!/org/apache/xerces/impl/dtd/models/CMStateSet.class
     [java] validating test.xml with maxOccurs.xsd
     [java] 7580.454783 millis

run-sparse:
     [java] loading CMStateSet.class from file:/.../bin/org/apache/xerces/impl/dtd/models/CMStateSet.class
     [java] validating test.xml with maxOccurs.xsd
     [java] 1714.335978 millis
```

# xerces-sparse-CMStateSet

See https://issues.apache.org/jira/browse/XERCESJ-1227.

The JARs in the [lib](lib) folder are from [Xerces-J-bin.2.12.1-xml-schema-1.1.zip](https://dlcdn.apache.org//xerces/j/binaries/Xerces-J-bin.2.12.1-xml-schema-1.1.zip). See https://xerces.apache.org/mirrors.cgi#binary

## Run with make

```
$ make
...
java -cp "lib/*:bin" Run
loading CMStateSet.class from lib/xercesImpl-2.12.1-xml-schema-1.1.jar
validating test.xml with test-choice-assert.xsd
181.231399 millis
validating test.xml with test-choice.xsd
7002.531686 millis
validating test.xml with test-sequence.xsd
584.140115 millis
java -cp "bin:lib/*" Run
loading CMStateSet.class from bin/
validating test.xml with test-choice-assert.xsd
186.559359 millis
validating test.xml with test-choice.xsd
1637.605094 millis
validating test.xml with test-sequence.xsd
7996.99605 millis
```

## Run with Ant

```
$ ant
...
run-original:
     [java] loading CMStateSet.class from lib/xercesImpl-2.12.1-xml-schema-1.1.jar
     [java] validating test.xml with test-choice-assert.xsd
     [java] 184.118067 millis
     [java] validating test.xml with test-choice.xsd
     [java] 6943.449682 millis
     [java] validating test.xml with test-sequence.xsd
     [java] 582.964042 millis

run-sparse:
     [java] loading CMStateSet.class from bin/
     [java] validating test.xml with test-choice-assert.xsd
     [java] 192.949404 millis
     [java] validating test.xml with test-choice.xsd
     [java] 1618.323769 millis
     [java] validating test.xml with test-sequence.xsd
     [java] 7896.70078 millis
```

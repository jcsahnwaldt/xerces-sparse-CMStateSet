# xerces-sparse-CMStateSet

See https://issues.apache.org/jira/browse/XERCESJ-1227.

The JARs in the [lib](lib) folder are from [Xerces-J-bin.2.12.1-xml-schema-1.1.zip](https://dlcdn.apache.org//xerces/j/binaries/Xerces-J-bin.2.12.1-xml-schema-1.1.zip). See https://xerces.apache.org/mirrors.cgi#binary

## Run with make

```
$ make
java -cp "lib/*:bin" Run
loading CMStateSet.class from lib/xercesImpl-2.12.1-xml-schema-1.1.jar
validating test.xml with test-choice-assert.xsd
181.172242 millis
validating test.xml with test-choice.xsd
3503.313348 millis
validating test.xml with test-sequence.xsd
600.648175 millis
java -cp "bin:lib/*" Run
loading CMStateSet.class from bin/
validating test.xml with test-choice-assert.xsd
183.619571 millis
validating test.xml with test-choice.xsd
1145.443214 millis
validating test.xml with test-sequence.xsd
3319.136173 millis
```

## Run specific XSD file with make

```
$ make test-sequence.xsd 
java -cp "lib/*:bin" Run test-sequence.xsd
loading CMStateSet.class from lib/xercesImpl-2.12.1-xml-schema-1.1.jar
validating test.xml with test-sequence.xsd
506.817063 millis
java -cp "bin:lib/*" Run test-sequence.xsd
loading CMStateSet.class from bin/
validating test.xml with test-sequence.xsd
3178.244942 millis
```

## Run with Ant

```
$ ant
run-original:
     [java] loading CMStateSet.class from lib/xercesImpl-2.12.1-xml-schema-1.1.jar
     [java] validating test.xml with test-choice-assert.xsd
     [java] 178.647753 millis
     [java] validating test.xml with test-choice.xsd
     [java] 3475.774853 millis
     [java] validating test.xml with test-sequence.xsd
     [java] 623.929355 millis

run-sparse:
     [java] loading CMStateSet.class from bin/
     [java] validating test.xml with test-choice-assert.xsd
     [java] 185.44768 millis
     [java] validating test.xml with test-choice.xsd
     [java] 1070.91905 millis
     [java] validating test.xml with test-sequence.xsd
     [java] 3505.011146 millis
```

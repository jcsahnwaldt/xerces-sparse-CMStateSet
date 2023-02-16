import static java.lang.Math.pow;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.impl.dtd.models.CMStateSet;
import org.xml.sax.InputSource;

public class Run
{
  public static void main(String[] args) throws Exception
  {
    String[] xsdVersions = {"http://www.w3.org/2001/XMLSchema", "http://www.w3.org/XML/XMLSchema/v1.1"};

    String xml = "test.xml";

    String[] xsds = args;
    if (xsds.length == 0) {
      xsds = new File(".").list((d, n) -> n.endsWith(".xsd"));
      Arrays.sort(xsds);
    }

    URI path = CMStateSet.class.getProtectionDomain().getCodeSource().getLocation().toURI();
    System.out.println("loading CMStateSet.class from "+new File(".").toURI().relativize(path));

    for (String xsd: xsds) {
      SchemaFactory factory = SchemaFactory.newInstance(xsdVersions[1]);
      Schema schema = factory.newSchema(new StreamSource(xsd));
      Validator validator = schema.newValidator();
      System.out.println("validating "+xml+" with "+xsd);
      long nanos = System.nanoTime();
      validator.validate(new StreamSource(xml));
      nanos = System.nanoTime() - nanos;
      printPerformance(xsd, nanos);
    }
  }

  private static void printPerformance(String xsd, long nanos) throws XPathException {
    System.out.println(nanos / 1E6 + " millis");
    if (xsd.equals("test-choice.xsd")) {
      int max = getInteger(xsd, "/xs:schema/xs:element/xs:complexType/xs:choice/@maxOccurs");
      System.out.println("choice maxOccurs: "+max);
      System.out.println("ns/choiceMax^2: "+nanos/pow(max,2));
    }
    else if (xsd.equals("test-choice-assert.xsd")) {
      int max = getInteger(xsd, "/xs:schema/xs:element/xs:complexType/xs:assert/@test");
      System.out.println("assert max count: " + max);
    }
    else if (xsd.equals("test-sequence.xsd")) {
      int sequenceMax = getInteger(xsd, "/xs:schema/xs:element/xs:complexType/xs:sequence/@maxOccurs");
      int elementMax = getInteger(xsd, "/xs:schema/xs:element/xs:complexType/xs:sequence/xs:element[@name='b']/@maxOccurs");
      System.out.println("sequence maxOccurs: " + sequenceMax);
      System.out.println("element maxOccurs: " + elementMax);
      System.out.println("ns/(sequenceMax^2*elementMax^2): "+nanos/pow(sequenceMax,2)/pow(elementMax,2));
      System.out.println("ns/(sequenceMax^3*elementMax^3): "+nanos/pow(sequenceMax,3)/pow(elementMax,3));
      System.out.println("ns/(sequenceMax^4*elementMax^2): "+nanos/pow(sequenceMax,4)/pow(elementMax,2));
    }
  }

  private static int getInteger(String xsd, String expr) throws XPathException {
    XPath xpath = XPathFactory.newInstance().newXPath();
    xpath.setNamespaceContext(new XsdNamespaceContext());
    String value = xpath.evaluateExpression(expr, new InputSource(xsd), String.class);
    Matcher matcher = Pattern.compile("\\d+").matcher(value);
    matcher.find();
    return Integer.parseInt(matcher.group());
  }
}

class XsdNamespaceContext implements NamespaceContext {
  @Override
  public String getNamespaceURI(String prefix) {
    return XMLConstants.W3C_XML_SCHEMA_NS_URI;
  }

  @Override
  public String getPrefix(String namespaceURI) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<String> getPrefixes(String namespaceURI) {
    throw new UnsupportedOperationException();
  }
}

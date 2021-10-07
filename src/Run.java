import java.io.File;
import java.net.URI;
import java.util.Arrays;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.xerces.impl.dtd.models.CMStateSet;

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
      System.out.println(nanos / 1E6 + " millis");
    }
  }
}

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Run
{
  public static void main(String[] args) throws Exception
  {
    String[] xsds = {"maxOccurs.xsd", "assert.xsd"};
    String[] xsdVersions = {"http://www.w3.org/2001/XMLSchema", "http://www.w3.org/XML/XMLSchema/v1.1"};
    String[] xmls = {"test.xml"};

    String xsd = args.length > 0 ? args[0] : xsds[0];
    String xml = args.length > 1 ? args[1] : xmls[0];

    System.out.println(org.apache.xerces.impl.dtd.models.CMStateSet.class.getResource("CMStateSet.class"));

    SchemaFactory factory = SchemaFactory.newInstance(xsdVersions[0]);
    Schema schema = factory.newSchema(new StreamSource(xsd));
    Validator validator = schema.newValidator();

    long nanos = System.nanoTime();
    validator.validate(new StreamSource(xml));
    nanos = System.nanoTime() - nanos;
    System.out.println(nanos / 1E6 + " millis");
  }
}

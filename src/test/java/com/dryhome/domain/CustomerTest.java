package com.dryhome.domain;

import com.powtechconsulting.mailmerge.DocControlMerger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CustomerTest {
    @Test
    public void testFullNameAddress() {
        Customer customer = customer();

        CustomerView customerView = customer.toView();

        assertEquals("Mr John Jones", customerView.getFullNameAddressLine1());
        assertEquals(customer.getName(), customerView.getFullNameAddressLine2());
        assertEquals(customer.getAddress1(), customerView.getFullNameAddressLine3());
        assertEquals(customer.getAddress2(), customerView.getFullNameAddressLine4());
        assertEquals(customer.getAddress3(), customerView.getFullNameAddressLine5());
        assertEquals(customer.getTown(), customerView.getFullNameAddressLine6());
        assertEquals(customer.getPostCode(), customerView.getFullNameAddressLine7());
    }

    @Test
    public void testFullNameAddressMissingLines() {
        Customer customer = customer();
        customer.setAddress2(null);
        customer.setAddress3(null);

        CustomerView customerView = customer.toView();
        assertEquals(" ", customerView.getFullNameAddressLine1());
        assertEquals(" ", customerView.getFullNameAddressLine2());
        assertEquals("Mr John Jones", customerView.getFullNameAddressLine3());
        assertEquals(customer.getName(), customerView.getFullNameAddressLine4());
        assertEquals(customer.getAddress1(), customerView.getFullNameAddressLine5());
        assertEquals(customer.getTown(), customerView.getFullNameAddressLine6());
        assertEquals(customer.getPostCode(), customerView.getFullNameAddressLine7());
    }

    @Test
    public void testFullAddress() {
        Customer customer = customer();

        CustomerView customerView = customer.toView();

        assertEquals("myAdd1\n" +
            "myAdd2\n" +
            "myAdd3\n" +
            "theTown\n" +
            "WD17 2QH", customerView.getFullAddress());
    }

    @Test
    public void testFullAddressMissingLines() {
        Customer customer = customer();
        customer.setAddress3(null);

        CustomerView customerView = customer.toView();

        assertEquals("myAdd1\n" +
            "myAdd2\n" +
            "theTown\n" +
            "WD17 2QH", customerView.getFullAddress());
    }

    @Test
    public void testFullAddressLines() {
        Customer customer = customer();

        CustomerView customerView = customer.toView();

        List<String> fullAddressLines = customerView.getFullAddressLines();
        assertEquals(5, fullAddressLines.size());
        assertEquals(customer.getAddress1(), fullAddressLines.get(0));
        assertEquals(customer.getAddress2(), fullAddressLines.get(1));
        assertEquals(customer.getAddress3(), fullAddressLines.get(2));
        assertEquals(customer.getTown(), fullAddressLines.get(3));
        assertEquals(customer.getPostCode(), fullAddressLines.get(4));
    }

    @Test
    public void testFullAddressLinesMissingLines() {
        Customer customer = customer();
        customer.setAddress3(null);

        CustomerView customerView = customer.toView();

        List<String> fullAddressLines = customerView.getFullAddressLines();
        assertEquals(5, fullAddressLines.size());
        assertEquals(customer.getAddress1(), fullAddressLines.get(0));
        assertEquals(customer.getAddress2(), fullAddressLines.get(1));
        assertEquals(customer.getTown(), fullAddressLines.get(2));
        assertEquals(customer.getPostCode(), fullAddressLines.get(3));
        assertEquals(" ", fullAddressLines.get(4));
    }

    @Test
    public void testDate() {
        assertNotNull(customer().toView().getCurrentDateTime());
    }

    @Ignore("This is only for manual testing")
    @Test
    public void testMerge() throws Exception {
        String templateFile = "dp_labels.docx";
        URL resource = this.getClass().getClassLoader().getResource("merge-docs/" + templateFile);
        String filename = resource.getFile();

        Customer customer = customer();
        customer.setAddress3(null);
        String xml = customer.marshallToXml();
        System.out.println("xml: " + xml);
        byte[] bytes = new DocControlMerger().merge(filename, xml);
        FileOutputStream fos = new FileOutputStream(templateFile.replace(".", "_test_" + System.currentTimeMillis() + "."));
        fos.write(bytes);
        fos.close();
        System.out.println("wrote to file");
    }

    static Customer customer() {
        Customer customer = new Customer();
        customer.setId(99L);
        customer.setContactTitle("Mr");
        customer.setContactFirst("John");
        customer.setContactSurname("Jones");
        customer.setName("myComp");
        customer.setAddress1("myAdd1");
        customer.setAddress2("myAdd2");
        customer.setAddress3("myAdd3");
        customer.setTown("theTown");
        customer.setPostCode("WD17 2QH");
        customer.setTel("01269 841313");
        customer.setMob("07971 433131");
        return customer;
    }
}

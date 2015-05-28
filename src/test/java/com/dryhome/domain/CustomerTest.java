package com.dryhome.domain;

import com.powtechconsulting.mailmerge.WordMerger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CustomerTest {
    @Test
    public void testFullAddress() {
        Customer customer = customer();

        Map<String, String> map = customer.toMergeMap();
        assertEquals("Mr James Powell", map.get("fullNameAddressLine1"));
        assertEquals(customer.getName(), map.get("fullNameAddressLine2"));
        assertEquals(customer.getAddress1(), map.get("fullNameAddressLine3"));
        assertEquals(customer.getAddress2(), map.get("fullNameAddressLine4"));
        assertEquals(customer.getAddress3(), map.get("fullNameAddressLine5"));
        assertEquals(customer.getTown(), map.get("fullNameAddressLine6"));
        assertEquals(customer.getPostCode(), map.get("fullNameAddressLine7"));
    }

    @Test
    public void testFullAddressMissingLines() {
        Customer customer = customer();
        customer.setAddress2(null);
        customer.setAddress3(null);

        Map<String, String> map = customer.toMergeMap();
        assertEquals("", map.get("fullNameAddressLine1"));
        assertEquals("", map.get("fullNameAddressLine2"));
        assertEquals("Mr James Powell", map.get("fullNameAddressLine3"));
        assertEquals(customer.getName(), map.get("fullNameAddressLine4"));
        assertEquals(customer.getAddress1(), map.get("fullNameAddressLine5"));
        assertEquals(customer.getTown(), map.get("fullNameAddressLine6"));
        assertEquals(customer.getPostCode(), map.get("fullNameAddressLine7"));
    }

    @Test
    public void testDate() {
        assertNotNull("", customer().toMergeMap().get("currentDateTime"));
    }


    @Ignore
    @Test
    public void testMerge() throws IOException {
        String templateFile = "dp_remcon_prod_lit.docx";
        URL resource = this.getClass().getClassLoader().getResource("merge-docs/" + templateFile);
        String filename = resource.getFile();
        byte[] bytes = new WordMerger().merge(filename, customer().toMergeMap());
        FileOutputStream fos = new FileOutputStream(templateFile.replace(".", "_test_" + System.currentTimeMillis() + "."));
        fos.write(bytes);
        fos.close();
    }

    private Customer customer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setContactTitle("Mr");
        customer.setContactFirst("James");
        customer.setContactSurname("Powell");
        customer.setName("powtech");
        customer.setAddress1("myAdd1");
        customer.setAddress2("myAdd2");
        customer.setAddress3("myAdd3");
        customer.setTown("theTown");
        customer.setPostCode("SA14 7NF");
        return customer;
    }
}

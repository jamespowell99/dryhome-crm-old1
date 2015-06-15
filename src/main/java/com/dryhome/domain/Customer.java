package com.dryhome.domain;

import com.google.common.base.Joiner;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.beans.Statement;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dryhome.utils.StringPrintUtils.valueOrEmpty;

/**
 * A Customer.
 */
@Entity
@Table(name = "T_CUSTOMER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable, MergeableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_title")
    private String contactTitle;

    @Column(name = "contact_first")
    private String contactFirst;

    @Column(name = "contact_surname")
    private String contactSurname;

    @Column(name = "tel")
    private String tel;

    @Column(name = "mob")
    private String mob;

    @Column(name = "email")
    private String email;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "town")
    private String town;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "products")
    private String products;

    @Column(name = "interested")
    private String interested;

    @Column(name = "paid", precision = 10, scale = 2, nullable = false)
    private BigDecimal paid;

    @Column(name = "notes")
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactFirst() {
        return contactFirst;
    }

    public void setContactFirst(String contactFirst) {
        this.contactFirst = contactFirst;
    }

    public String getContactSurname() {
        return contactSurname;
    }

    public void setContactSurname(String contactSurname) {
        this.contactSurname = contactSurname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Customer customer = (Customer) o;

        if (!Objects.equals(id, customer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", contactTitle='" + contactTitle + "'" +
            ", contactFirst='" + contactFirst + "'" +
            ", contactSurname='" + contactSurname + "'" +
            ", tel='" + tel + "'" +
            ", mob='" + mob + "'" +
            ", email='" + email + "'" +
            ", address1='" + address1 + "'" +
            ", address2='" + address2 + "'" +
            ", address3='" + address3 + "'" +
            ", town='" + town + "'" +
            ", postCode='" + postCode + "'" +
            ", products='" + products + "'" +
            ", interested='" + interested + "'" +
            ", paid='" + paid + "'" +
            ", notes='" + notes + "'" +
            '}';
    }

    public CustomerView toView() {
        CustomerView customerView = new CustomerView();

        //Simple fields
        customerView.setId(id.toString());
        customerView.setName(name);
        customerView.setTel(valueOrEmpty(tel));
        customerView.setMob(valueOrEmpty(mob));
        customerView.setPaid(Optional.ofNullable(paid).orElse(BigDecimal.ZERO).toString());
        customerView.setProd(valueOrEmpty(products));
        customerView.setNotes(valueOrEmpty(notes));
        customerView.setContact(contactTitle + " " + contactFirst + " " + contactSurname);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        customerView.setCurrentDateTime(sdf.format(new Date()));

        try {
            List<String> fullNameAddressList = new ArrayList<>(Arrays.asList(postCode, town, address3, address2, address1, name, customerView.getContact()));
            fullNameAddressList.removeAll(Arrays.asList(null, "", " "));
            //TODO refactor this to use a list
            int i = 7;
            for (String item : fullNameAddressList) {
                Statement statement = new Statement(customerView, "setFullNameAddressLine" + i, new String[]{item});
                statement.execute();
                i--;
            }

            while (i >= 1) {
                Statement statement = new Statement(customerView, "setFullNameAddressLine" + i, new String[]{" "});
                statement.execute();
                i--;
            }

            List<String> addressList = new ArrayList<>(Arrays.asList(address1, address2, address3, town, postCode));
            addressList.removeAll(Arrays.asList(null, "", " "));
            String fullAddressString = Joiner.on("\n").join(addressList);
            customerView.setFullAddress(fullAddressString);
            customerView.setFullAddressLines(addressList);
            for (i = customerView.getFullAddressLines().size(); i < 5; i++) {
                customerView.getFullAddressLines().add(i, " ");
            }

            Collections.reverse(fullNameAddressList);
            String fullNameAddressString = Joiner.on("\n").join(fullNameAddressList);
            customerView.setFullNameAddress(fullNameAddressString);
            return customerView;
        } catch (Exception e) {
            throw new IllegalStateException("failed to create view: " + id, e);
        }
    }

    //TODO scope to refactor this into a shared service?
    @Override
    public String marshallToXml() {
        try {
            CustomerView customerView = toView();
            JAXBContext context = JAXBContext.newInstance(CustomerView.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            m.marshal(customerView, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new IllegalStateException("failed to marshal customer: " + id, e);
        }
    }
}

package com.dryhome.domain;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "T_CUSTOMER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

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

    public Map<String, String> toMergeMap() {
        HashMap<String, String> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        map.put("currentDateTime", sdf.format(new Date()));
        map.put("companyId", Long.toString(id));
        map.put("companyName", name);
        map.put("contact", contactTitle + " " + contactFirst + " " + contactSurname);
        map.put("tel", tel);
        map.put("mob", mob);
        if (paid != null) {
            map.put("paid", paid.toPlainString());
        }
        map.put("prod", products);
        map.put("address", Joiner.on(", ").skipNulls().join(address1, address2, address3, town, postCode));

        //TODO use streams!!
        List<String> addressList = new ArrayList<>(Arrays.asList(postCode, town, address3, address2, address1, name, map.get("contact")));
        addressList.removeAll(Arrays.asList(null, "", " "));
        int i = 7;
        for(String element : addressList) {
            map.put("fullNameAddressLine" + i, element);
            i--;
        }

        while( i >= 1) {
            map.put("fullNameAddressLine" + i, "");
            i--;
        }

        return map;
    }
}

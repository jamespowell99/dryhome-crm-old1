package com.dryhome.service;

import com.dryhome.domain.Customer;
import com.dryhome.domain.MergeableObject;
import com.google.common.base.Preconditions;
import com.powtechconsulting.mailmerge.DocControlMerger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MergeService {
    public void performMerge(MergeableObject object, String templateName, HttpServletResponse response) {
        Preconditions.checkArgument(object != null && templateName != null);
        String localName = templateName + ".docx";
        URL resource = this.getClass().getClassLoader().getResource("merge-docs/" + localName);
        Preconditions.checkState(resource != null, "template not found: " + localName);
        String filename = resource.getFile();
        byte[] mergedDoc = new DocControlMerger().merge(filename, object.marshallToXml());
        try {
            String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String replacement = new StringBuilder().append("_").append(object.getId()).append("_").append(formattedDate).append(".").toString();
            String mergedDocName = localName.replace(".", replacement);
            response.setHeader("Content-Disposition", "attachment; filename=" + mergedDocName);
            InputStream inputStream = new ByteArrayInputStream(mergedDoc);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.io.IOException e) {
            throw new RuntimeException("problem merging file: " + localName);
        }
    }
}

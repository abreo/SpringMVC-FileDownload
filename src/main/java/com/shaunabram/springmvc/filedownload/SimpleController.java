package com.shaunabram.springmvc.filedownload;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@Controller
public class SimpleController {

    @RequestMapping(value = "/")
    public String home() {
        System.out.println("SimpleController: Passing through...");
        //without view resolver
        // return "WEB-INF/views/index.jsp";
        return "index";
    }

    @RequestMapping(value = "/Excel", method = RequestMethod.GET)
    public void handleFileDownload(HttpServletResponse res) {
        try {
            String fn = "/Test.xls";
            URL url = getClass().getResource(fn);
            File f = new File(url.toURI());
            System.out.println("Loading file "+fn+"("+f.getAbsolutePath()+")");
            if (f.exists()) {
                res.setContentType("application/xls");
                res.setContentLength(new Long(f.length()).intValue());
                res.setHeader("Content-Disposition", "attachment; filename=Test.xls");
                FileCopyUtils.copy(new FileInputStream(f), res.getOutputStream());
            } else {
                System.out.println("File"+fn+"("+f.getAbsolutePath()+") does not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

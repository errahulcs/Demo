/**
 * 
 */
package com.cookedspecially.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController  {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
				byte[] bytes = file.getBytes();
				String fileName = file.getName();
				System.out.println(fileName);
				System.out.println(file.getOriginalFilename());
				System.out.println(file.getContentType());
				File outfile = new File("webapps/static/" + file.getOriginalFilename());
				System.out.println(outfile.getAbsolutePath());
				FileOutputStream fos = new FileOutputStream(outfile);
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // store the bytes somewhere
           return "uploadSuccess";
       } else {
           return "uploadFailure";
       }
    }
}

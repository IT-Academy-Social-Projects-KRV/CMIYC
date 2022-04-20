package com.ms.data.service;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Value;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.StorageObject;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.ms.data.model.XmlObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static org.mockito.Mockito.never;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
@RequestMapping("/gcp")
public class XmlService {

    @Autowired
    private Storage storage;
    
    @Value("${test_bucket_for_cmiyc}")
    private String bucketName;

    
    public XmlObject getData(String xmldata) throws JAXBException {
        StringReader reader = new StringReader(xmldata);
        JAXBContext context = JAXBContext.newInstance(XmlObject.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlObject xmlObject = (XmlObject) unmarshaller.unmarshal(reader);


        return xmlObject;

    }

    public XmlService() throws JAXBException {

    }
    /*
    public void uploadFile(MultipartFile file) throws IllegalStateException, IOException{
      String buckePath = System.getProperty("user.dir") + "/Backend/microservices/target/bucket/";
      File bucket = new File(buckePath);
      if (!bucket.exists()){
          bucket.mkdirs();
      }
      file.transferTo(new File(buckePath + file.getOriginalFilename()));
  }*/

  @GetMapping("/send-data")
  public Boolean uploadFile(MultipartFile file) throws IOException{
    StorageObject object = new StorageObject();
    object.setName(file.getOriginalFilename());
    InputStream targetStream = new ByteArrayInputStream(file.getBytes());
    storage.objects().insert(bucketName, object, new AbstractInputStreamContent(file.getOriginalFilename()){
      @Override
      public long getLength() throws IOException {
          return file.getSize();
      }

      @Override
      public boolean retrySupported() {
          return false;
      }

      @Override
      public InputStream getInputStream() throws IOException {
          return targetStream;
      }
  }).execute();
    return true;
    }
    
    public StorageObject download(String fileName) throws IOException {
      StorageObject object = storage.objects().get(bucketName, fileName).execute();
      File file = new File("./" + fileName);
      FileOutputStream os = new FileOutputStream(file);

      storage.getRequestFactory()
              .buildGetRequest(new GenericUrl(object.getMediaLink()))
              .execute()
              .download(os);
      object.set("file", file);
      return object;
  }
   /* BlobId id = BlobId.of("test_bucket_for_cmiyc", "demoFile.txt");
    BlobInfo info = BlobInfo.newBuilder(id).build();
    File file = new File("D:", "demoFile.txt");
    byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
    storage.create(info, arr);
    
    return "File uploaded successfully";
*/
  
}

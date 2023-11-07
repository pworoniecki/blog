package pl.woroniecki.restlargefileszip;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class LargeFilesController {

  // Links retrieved from https://testfile.org/
  private static final String LARGE_FILE_1GB_LINK = "https://bit.ly/1GB-testfile";
  private static final String LARGE_FILE_5GB_LINK = "https://bit.ly/5GB-TESTFILE-ORG";

  private static final List<String> ALL_FILES_TO_DOWNLOAD = List.of(
      LARGE_FILE_1GB_LINK,
      LARGE_FILE_5GB_LINK
  );

  @GetMapping("/download-large-files")
  public void downloadAndZipLargeFiles(HttpServletResponse response) throws IOException {
    response.setContentType("application/zip");
    response.setHeader("Content-Disposition", "attachment; filename=large-files.zip");

    try (var zipOut = new ZipOutputStream(response.getOutputStream())) {
      for (var fileLink : ALL_FILES_TO_DOWNLOAD) {
        try (var inputStream = new URL(fileLink).openStream()) {

          var filename = fileLink.substring(fileLink.lastIndexOf("/") + 1);
          zipOut.putNextEntry(new ZipEntry(filename));

          byte[] buffer = new byte[1024];
          int len;
          while ((len = inputStream.read(buffer)) > 0) {
            zipOut.write(buffer, 0, len);
          }
        }

        zipOut.closeEntry();
      }
    }
  }
}

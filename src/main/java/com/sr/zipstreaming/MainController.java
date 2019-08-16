package com.sr.zipstreaming;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController("/main")
public class MainController {

    private static List<String> files = Arrays.asList("Central_Tayside_Fife.jpg",
            "Dumfries_Galloway_Lothian_Borders.jpg",
            "East Midlands.jpg",
            "East_England.jpg",
            "Grampian.jpg",
            "Highlands_Eilean_Siar.jpg",
            "London_South_East_England.jpg",
            "Northern_Ireland.jpg",
            "North_East_England.jpg",
            "North_West_England.jpg",
            "Orkney_Shetland.jpg",
            "South West England.jpg",
            "Strathclyde.jpg",
            "Wales.jpg",
            "West Midlands.jpg",
            "Yorkshire_Humber.jpg");
    //private static final String BASE_URL = "https://sean-zip-streaming-test-bucket.s3.eu-west-2.amazonaws.com/";
    private static final String BASE_URL = "http://localhost:8887/";
    final RestTemplateBuilder restTemplateBuilder;


    public MainController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping()
    public HttpEntity doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        final OutputStream reponseOutputStream = response.getOutputStream();
        final ZipOutputStream zipOut = new ZipOutputStream(reponseOutputStream);

        files.forEach(file -> {
            final String filePath = BASE_URL + file;
            restTemplateBuilder.build().execute(filePath, HttpMethod.GET, null, clientHttpResponse -> {
                zipOut.putNextEntry(new ZipEntry(file));
                StreamUtils.copy(clientHttpResponse.getBody(), zipOut);
                zipOut.closeEntry();
                return null;
            });
        });
        zipOut.finish();
        return new HttpEntity<>("This is a test message");
    }
}

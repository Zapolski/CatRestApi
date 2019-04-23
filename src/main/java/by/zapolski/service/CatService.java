package by.zapolski.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.zapolski.model.CatImage;
import by.zapolski.model.DownloadResult;

@Component
public class CatService {

    public static final String TARGET_PATH = "c:/tempImages/";
    public static final String REQUEST = "https://api.thecatapi.com/v1/images/search?limit=";
    public static final int LIMIT_IMAGES = 100;
    public static final String REQUEST_PROPERTY_KEY = "User-Agent";
    public static final String REQUEST_PROPERTY_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)";

    public List<DownloadResult> doGet(int count) throws IOException{

        String request = REQUEST+count;
        
        ObjectMapper mapper = new ObjectMapper();
        CatImage[] catImages = mapper.readValue(new URL(request), CatImage[].class);

        List<DownloadResult> resultList = new ArrayList<>();
        for (CatImage catImage : catImages) {
            DownloadResult downloadResult = downloadImage(catImage);
            resultList.add(downloadResult);
        }

        return resultList;
    }

    private DownloadResult downloadImage(CatImage catImage) throws IOException {

        String url = catImage.getUrl();
        String imageName = url.substring(url.lastIndexOf("/")+1);
        
		File dir = new File(TARGET_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}
        
        URL link = new URL(url);
		URLConnection tc = link.openConnection();
		tc.addRequestProperty(REQUEST_PROPERTY_KEY,	REQUEST_PROPERTY_VALUE);
		tc.connect();

		try(InputStream in = tc.getInputStream()){
            Path target = Paths.get(TARGET_PATH+imageName);
            if (!Files.exists(target)){
                Files.copy(in, Paths.get(TARGET_PATH+imageName));
            }
        }

		DownloadResult downloadResult = new DownloadResult();
		downloadResult.setLocation(TARGET_PATH);
        downloadResult.setName(imageName);
        
        return downloadResult;
    }

}

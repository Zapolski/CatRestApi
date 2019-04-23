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

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import by.zapolski.model.CatImage;
import by.zapolski.model.DownloadResult;

public class CatService {

    public static final String TARGET_PATH = "c:/tempImages/";
    public static final String REQUEST = "https://api.thecatapi.com/v1/images/search?limit=";

    public static void main(String[] args) throws IOException {
    	CatService catService = new CatService();
        System.out.println(catService.doGet(10));
    }

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

        DownloadResult downloadResult = new DownloadResult();

        String url = catImage.getUrl();
        String imageName = url.substring(url.lastIndexOf("/")+1);
        URL link = new URL(url);
        
		URLConnection tc = link.openConnection();
		
		tc.addRequestProperty("User-Agent",	"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		tc.connect();
		
		File dir = new File(TARGET_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try(InputStream in = tc.getInputStream()){
            Path target = Paths.get(TARGET_PATH+imageName);
            if (!Files.exists(target)){
                Files.copy(in, Paths.get(TARGET_PATH+imageName));
            }
        }

        downloadResult.setLocation(TARGET_PATH);
        
        downloadResult.setName(imageName);
        
        return downloadResult;
    }

}

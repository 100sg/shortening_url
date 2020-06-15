package skbaek.shorteningurl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.shorteningurl.entity.ShorteningUrl;
import skbaek.shorteningurl.repository.ShorteningUrlRepository;
import skbaek.shorteningurl.util.Base62;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
public class ShorteningService extends GenericCRUDService<ShorteningUrl> {

    public ShorteningService(JpaRepository<ShorteningUrl, Long> repository) {
        super(repository);
    }

    @Transactional
    public ShorteningUrl encodingToUrl(ShorteningUrl data) throws Exception {
        String longUrl = data.getLongUrl();
        ShorteningUrl shorteningUrl = null;

        checkConnectionUrl(longUrl);

        String resultUrl = urlProcess(longUrl);

        ShorteningUrl findData = ((ShorteningUrlRepository) repository).findByLongUrl(resultUrl);

        if (findData != null) {
            shorteningUrl = findData;
        } else {
            ShorteningUrl entity = new ShorteningUrl();
            entity.setLongUrl(resultUrl);
            entity.setShortUrl(Base62.encodeToLong(repository.count() + 1L));
            shorteningUrl = super.save(entity);
        }

//        log.info("service : {}", shorteningUrl.toString());
        return shorteningUrl;
    }

    public ShorteningUrl decodeToUrl(String shortUrl) throws Exception {
        Optional<ShorteningUrl> shorteningUrl = Optional.ofNullable(((ShorteningUrlRepository) repository).findByShortUrl(shortUrl));
        if (!shorteningUrl.isPresent()) {
            throw new Exception("알수 없는 단축 URL입니다.");
        } else {
            ShorteningUrl tmpData = shorteningUrl.get();
            tmpData.setCallCnt(tmpData.getCallCnt() + 1);
            super.save(tmpData);
            return tmpData;
        }
    }

    private void checkConnectionUrl(String longUrl) throws Exception {

        URL tempUrl = new URL(longUrl);
        HttpURLConnection connection = (HttpURLConnection) tempUrl.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/4.0");
        connection.connect();

        if (404 == connection.getResponseCode()) {
            throw new Exception(connection.getResponseCode()+"");
        }

    }

    private String urlProcess(String longUrl) {
        String result = null;
        if (longUrl.indexOf("http://") > -1) {
            result = longUrl.replaceAll("http://", "");
        } else if (longUrl.indexOf("https://") > -1) {
            result = longUrl.replaceAll("https://", "");
        }

        int strLength = result.length();
        if (result.subSequence(strLength - 1, strLength).equals("/")) {
            result = result.substring(0, strLength - 1);
        }

        return result;
    }


}

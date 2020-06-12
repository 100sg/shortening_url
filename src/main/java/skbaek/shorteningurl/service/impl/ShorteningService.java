package skbaek.shorteningurl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
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

    public ShorteningUrl encodingToUrl(ShorteningUrl data) throws Exception {
        String longUrl = data.getLongUrl();
        ShorteningUrl shorteningUrl = null;

        checkUrlConnection(longUrl);

        if (longUrl.indexOf("http://") > -1) {
            longUrl = longUrl.replaceAll("http://", "");
        } else if (longUrl.indexOf("https://") > -1) {
            longUrl = longUrl.replaceAll("https://", "");
        }

        int strLength = longUrl.length();
        if (longUrl.subSequence(strLength - 1, strLength).equals("/")) {
            longUrl = longUrl.substring(0, strLength - 1);
        }

        log.info("최종 url : {}", longUrl);
        Optional<ShorteningUrl> su
                = Optional.ofNullable(((ShorteningUrlRepository) repository).findByLongUrl(longUrl));
        log.info("data : {}", data);

        if (su.isPresent()) {
            log.info("data msg : url exist !");
            shorteningUrl = su.get();
        } else {
            ShorteningUrl entity = new ShorteningUrl();
            entity.setLongUrl(longUrl.trim());
            entity.setShortUrl(Base62.encodeToLong(repository.count() + 1L));
            shorteningUrl = super.save(entity);
        }

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

    public void checkUrlConnection(String longUrl) throws IOException {
        boolean check = false;

        URL tempUrl = new URL(longUrl);
        HttpURLConnection connection = (HttpURLConnection) tempUrl.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/4.0");
        connection.connect();

        log.info("url response code : {}", connection.getResponseCode());
        if (200 == connection.getResponseCode()) {
            check = true;
        }

    }

}

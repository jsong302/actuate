package com.actuate.tinyurl.model;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class URL {

    private @Id String hash;
    private String originalURL;
    private int clickCount;

    public URL() {

    }

    public URL(String originalURL) {
        this.hash = DigestUtils.md5Hex(originalURL).substring(0, 9);
        this.originalURL = originalURL;
        this.clickCount = 0;
    }

    public String getHash() {
        return this.hash;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.hash = DigestUtils.md5Hex(originalURL).substring(0, 9);
        this.originalURL = originalURL;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof URL))
            return false;
        URL url = (URL) o;
        return Objects.equals(this.hash, url.hash) && Objects.equals(this.originalURL, url.originalURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hash, this.originalURL);
    }

    @Override
    public String toString() {
        return "URL{" + "hash=" + this.hash + ", original URL='" + this.originalURL + '}';
    }
}
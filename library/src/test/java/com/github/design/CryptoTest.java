package com.github.design;

import com.github.utils.CryptoUtils;

import org.junit.Test;

import java.util.Arrays;

import javax.crypto.SecretKey;

/**
 * encryption
 *
 * @author  Ivan on 2018-12-07 23:49.
 * @version v0.1
 * @since   v1.0
 */
public class CryptoTest {

    private String key = "1234567890ABCDEF";
    private String context = "It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness, it was the epoch of belief, it was the epoch of incredulity, it was the season of Light, it was the season of Darkness, it was the spring of hope, it was the winter of despair, we had everything before us, we had nothing before us, we were all going direct to Heaven, we were all going direct the other way – in short, the period was so far like the present period, that some of its noisiest authorities insisted on its being received, for good or for evil, in the superlative degree of comparison only.";
//    private String encryptedText = "cuO3uu5k/+Mi1ThzROuravWoGm9R2xWNjEY4bpBbJBmFBnp+JMO7kbS5AJcXVQTpeI7HrP6CCfK1mztwgO3QnYaLcJNT/8ojSfpU9bVgeh49qKIlRQ5GnYOJr9pdlEtxNZzglXVU7xohzqcFH7dtaO9JJKPWRION7lARkiJ5CyyLKym2XhNlLVKqUsXmbdiADB2LKlYVdTH22bxuj8Gkt349H4ttlW8Yqkh2PGmSo3b3N+1ErTYj1BmHBYntwQYtgb2vCbqBu/Qt8EQom+BGVBKcbirnAyUyU8SH6+seT8aSnvuZnd5CHBby61HaPqJrLaOvpvdXFxPOje8/yxahhjMfynT5MoMdIwETTgPVMjX9PYL1BjL+wtzrO+JML3sfFsnFn3fNxmcOOgvzfRB5/XAQhz5Cb6UiB3pA6oEAmLO9uyemmLgwQjmF3ZOHByjNzyQgM8BxP2FDdA/FPFjZNzdDZUlT3/iBOcOnVpWvQi8y5zitpPB7yKyEn+xRTnMNkJFTMfpPvv/+APi/V6ps6P6JT+PAFcTs+bAUKcmYqkZNrU8cXgaL/929jOGI5JUZcwvMqzUYfetaw2TCHMUnoWkB9Jbk8fGheOH3UXS6ktBjHr5XmV3J6k4cv5N7AmQnSUCuloPd1hvmJT95B6MuzaQ0R6AHotKiONNzClEjkkpb/82yRKqgfv+tI0sEoZjEU19/3VLxJFs0nlDc0nAU2Qb3V0I67ywqOQ8nThf+e+EiwJ/7Jhc5QM7f27ujtTeZlWFz+D9sn72m+tizDTmEvyYLAcmUjczy+3dgI5OazaNDhu6ksqwLjy1T1+hyXFDn";

    @Test
    public void testGenSecretKey() {
        SecretKey secretKey = CryptoUtils.genSecretKey(key);
        String key = "Algorithm: " + secretKey.getAlgorithm() + '\n' +
                "Encoded: " + Arrays.toString(secretKey.getEncoded()) + '\n' +
                "Format: " + secretKey.getFormat() + '\n' +
                "isDestroyed: " + secretKey.isDestroyed() + '\n';
        System.out.println(key);
    }

    @Test
    public void testEncode() {
        String text = CryptoUtils.AESEncrypt(key, context);
        System.out.println(text);
        String decryptedText = CryptoUtils.AESDecrypt(key, text);
        System.out.print(decryptedText);

    }

    @Test
    public void testDecode() {
//        String decryptedText = CryptoUtils.AESDecrypt(key, encryptedText);
//        System.out.print(decryptedText);

    }

}

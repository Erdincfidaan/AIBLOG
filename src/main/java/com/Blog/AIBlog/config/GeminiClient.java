package com.Blog.AIBlog.config;

import com.Blog.AIBlog.Dto.response.GeminiResponse;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GeminiClient {
    public GeminiClient(@Value(("${gemini.api.key}")) String api_key)
    {
        this.client=Client.builder().apiKey(api_key).build();
    }


    private final Client client;

    public List<String> generateContent(String category) throws Exception{

        String prompt = "Sen profesyonel bir içerik üreticisisin.\n" +
                "Lütfen \"" + category + "\" kategorisi hakkında SEO uyumlu, 500-800 kelimelik bir blog yazısı oluştur.\n\n" +

                "KURALLAR:\n" +
                "- Sadece geçerli JSON döndür.\n" +
                "- JSON dışında hiçbir açıklama yazma.\n" +
                "- Markdown kullanma (**, *, #, -, liste işaretleri vs. YASAK).\n" +
                "- İçerik tamamen düz metin olmalı.\n" +
                "- Başlık sade ve doğal olsun, kategori adı yazma.\n" +
                "- İçerik akıcı, paragraf paragraf ve okunabilir olsun.\n" +
                "- İçerikte özel karakter hatası veya bozuk escape karakteri olmasın.\n\n" +

                "GÖRSEL KURALLARI (ÇOK ÖNEMLİ):\n" +
                "- imageUrl alanına ASLA Unsplash, Pexels veya benzeri sitelerden rastgele/uydurma bir link yazma.\n" +
                "- Bunun yerine, içeriğe uygun tek kelimelik İNGİLİZCE bir anahtar kelime belirle (boşluksuz) ve şu URL yapısını kullan:\n" +
                "- https://loremflickr.com/800/600/anahtarkelime\n" +
                "- Örnek: Kategori 'Sağlıklı Beslenme' ise link 'https://loremflickr.com/800/600/nutrition' olmalıdır.\n" +
                "- Bu URL frontend'de doğrudan <img> etiketi içinde çalışacaktır.\n\n" +

                "JSON FORMATI:\n" +
                "{\"title\":\"\",\"content\":\"\",\"imageUrl\":\"\"}";
        GenerateContentResponse apiResponse = client.models.generateContent("gemini-3-flash-preview",prompt,null);
        String response=apiResponse.text();

        ObjectMapper mapper=new ObjectMapper();
        Map<String,Object> result=mapper.readValue(response,Map.class);

        String header = result.get("title").toString();
        String content = result.get("content").toString();
        String imageUrl = result.get("imageUrl").toString();
        List<String> contentList= new ArrayList<>();
        contentList.add(header);
        contentList.add(content);
        contentList.add(imageUrl);

        return contentList;
    }
}

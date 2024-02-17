package com.kalbim.vkapppairsgame.vk;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.queries.notifications.NotificationsSendMessageQuery;
import com.vk.api.sdk.queries.secure.SecureSendNotificationQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.vk.api.sdk.client.Lang.RU;
import static com.vk.api.sdk.objects.users.Fields.*;

@Service
public class VkApiClass {

    private static Logger log = LoggerFactory.getLogger(VkApiClass.class);
    @Value("${vk.app.id}")
    private String applicationId;
    @Value("${vk.client.secret}")
    private String clientSecret;
    @Value("${vk.service.access.key}")
    private String serviceAccessKey;

    private static final String NOTIFICATION_STRING = "Привет! Тебе доступна новая игра. И не забудь заглянуть в раздел \"Ещё монеты\": там есть задания с дополнительными монетами!";
    private static final String ENCODING = "UTF-8";

    public String checkForCorrectUserByKey(String url) throws Exception {
        String urlRes = "https://example.com/?" + url.split("#")[0];

        String clientSecret = this.getClientSecret();
        Map<String, String> queryParams = getQueryParams(new URL(urlRes));

        String checkString = queryParams.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith("vk_"))
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
            .collect(Collectors.joining("&"));
        String sign = getHashCode(checkString, clientSecret);

        boolean isSignEquals = sign.equals(queryParams.getOrDefault("sign", ""));
        if (isSignEquals) {
            return queryParams.get("vk_user_id");
        } else {
            throw new RuntimeException();
        }
    }

    private static Map<String, String> getQueryParams(URL url) {
        final Map<String, String> result = new LinkedHashMap<>();
        final String[] pairs = url.getQuery().split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? decode(pair.substring(0, idx)) : pair;
            String value = idx > 0 && pair.length() > idx + 1 ? decode(pair.substring(idx + 1)) : "";
            result.put(key, value);
        }

        return result;
    }

    private static String getHashCode(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(ENCODING), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes(ENCODING));
        return new String(Base64.getUrlEncoder().withoutPadding().encode(hmacData));
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return value;
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return value;
    }

    public void sendNotification(List<Integer> users) throws ClientException, ApiException {
        VkApiClient vkApiClient = getApiClient();
        NotificationsSendMessageQuery notificationsSendMessageQuery = vkApiClient.notifications()
                .sendMessage(createServiceActor(vkApiClient), NOTIFICATION_STRING, users);
        notificationsSendMessageQuery.execute();
    }

    public List<GetResponse> getUsers(List<String> entityList) throws ClientException, ApiException {
        VkApiClient vkApiClient = getApiClient();

        UsersGetQuery usersReq = vkApiClient.users().get(new ServiceActor(Integer.parseInt(getApplicationId()), getServiceAccessKey()));
        usersReq.userIds(entityList);
        usersReq.lang(RU);
        usersReq.fields(PHOTO_100, FIRST_NAME_NOM, LAST_NAME_NOM, LANGUAGE);
        return usersReq.execute();
    }

    private VkApiClient getApiClient() {
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

    private ServiceActor createServiceActor(VkApiClient vk) throws ClientException, ApiException {
        Integer appId = Integer.parseInt(getApplicationId());

        ServiceClientCredentialsFlowResponse authResponse = vk.oAuth()
                .serviceClientCredentialsFlow(appId, getClientSecret())
                .execute();

        return new ServiceActor(appId, authResponse.getAccessToken());
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getServiceAccessKey() {
        return serviceAccessKey;
    }
}

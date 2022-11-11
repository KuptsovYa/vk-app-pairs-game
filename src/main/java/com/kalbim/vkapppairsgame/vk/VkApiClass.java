package com.kalbim.vkapppairsgame.vk;

import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.queries.secure.SecureSendNotificationQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vk.api.sdk.client.Lang.RU;
import static com.vk.api.sdk.objects.users.Fields.*;

@Service
@Slf4j
public class VkApiClass {

//    @Value("${vk.app.id}")
//    private String applicationId = "51435598";
//    @Value("${vk.client.secret}")
//    private String clientSecret = "IB11omfMOf56wHKhyGAQ";
//    @Value("${vk.service.access.key}")
//    private String serviceAccessKey = "09d5529d09d5529d09d5529d290ac58ad3009d509d5529d6ae745e0e28a3a043a19ea84";

    @Value("${vk.app.id}")
    private String applicationId = "51430029";
    @Value("${vk.client.secret}")
    private String clientSecret = "wL2P2I3sG4rpIqkpn5P2";
    @Value("${vk.service.access.key}")
    private String serviceAccessKey = "53e0cc7a53e0cc7a53e0cc7ad250f00ef7553e053e0cc7a30c8967c8bd2b08c13099d0c";

    public void sendNotification(List<Integer> users) throws ClientException, ApiException {
        VkApiClient vkApiClient = getApiClient();
        SecureSendNotificationQuery secureSendNotificationQuery = vkApiClient.secure().sendNotification(createServiceActor(vkApiClient), "Вам доступна новая игра");
        secureSendNotificationQuery.userIds(users);
        secureSendNotificationQuery.unsafeParam("access_token", getServiceAccessKey());
        List<Integer> res = secureSendNotificationQuery.execute();
    }

    public List<GetResponse> getUsers(List<String> entityList) throws ClientException, ApiException {
        VkApiClient vkApiClient = getApiClient();
        UsersGetQuery usersReq = vkApiClient.users().get(createServiceActor(vkApiClient));
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

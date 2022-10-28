package com.kalbim.vkapppairsgame.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.queries.secure.SecureSendNotificationQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkApiClass {

    @Value("${vk.app.id}")
    private String applicationId = "51430029";
    @Value("${vk.client.secret}")
    private String clientSecret = "wL2P2I3sG4rpIqkpn5P2";
    @Value("${vk.service.access.key}")
    private String serviceAccessKey = "53e0cc7a53e0cc7a53e0cc7ad250f00ef7553e053e0cc7a30c8967c8bd2b08c13099d0c";

    public void sendNotification(List<Integer> users) throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vkApiClient = new VkApiClient(transportClient);
        SecureSendNotificationQuery secureSendNotificationQuery = vkApiClient.secure().sendNotification(createServiceActor(vkApiClient), "ПРИВЕТ!!11!!1!!");
        secureSendNotificationQuery.userIds(users);
        secureSendNotificationQuery.unsafeParam("access_token", getServiceAccessKey());
        List<Integer> res = secureSendNotificationQuery.execute();
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

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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VkApiClass {

    @Value("${vk.client.secret}")
    private String clientSecret;
    @Value("${vk.app.id}")
    private String applicationId;
    @Value("${vk.service.access.key}")
    private String serviceAccessKey;
    private final VkApiClient vkApiClient;

    public VkApiClass() {
        TransportClient transportClient = new HttpTransportClient();
        this.vkApiClient = new VkApiClient(transportClient);
    }

    public void sendNotification(List<Integer> users) throws ClientException, ApiException {
        SecureSendNotificationQuery secureSendNotificationQuery = getVkApiClient().secure().sendNotification(createServiceActor(), "ПРИВЕТ!!11!!1!!");
        secureSendNotificationQuery.userIds(users);
        secureSendNotificationQuery.unsafeParam("access_token", getServiceAccessKey());
        List<Integer> res = secureSendNotificationQuery.execute();
    }

    private ServiceActor createServiceActor() throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

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

    public VkApiClient getVkApiClient() {
        return vkApiClient;
    }

    public String getServiceAccessKey() {
        return serviceAccessKey;
    }
}

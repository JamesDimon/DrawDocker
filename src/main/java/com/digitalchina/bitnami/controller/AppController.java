package com.digitalchina.bitnami.controller;

import hapi.chart.ChartOuterClass;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.microbean.helm.ReleaseManager;
import org.microbean.helm.Tiller;
import org.microbean.helm.chart.URLChartLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/app")
public class AppController {

    /**
     * 新增应用
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addApp")
    public Map<String, String> addApp(@RequestBody Map<String, ArrayList<String>> params) throws InterruptedException {
        List<String> chartNameList = params.get("chartName");
        List<String> chartUrlList = params.get("chartUrl");
        int num = chartNameList.size();
        String externalIP;
        String svcPort;
        StringBuilder releaseUrl = new StringBuilder();
        StringBuilder resReleaseName = new StringBuilder();
        DefaultKubernetesClient client = k8sClient();
        for (int i = 0; i < num; i++) {
            String chartName = chartNameList.get(i);
            String chartUrl = chartUrlList.get(i);
            releaseNum++;
            String releaseName = chartName + "-test-" + releaseNum;
            try(Tiller tiller = new Tiller(client)) {
                ReleaseManager releaseManager = new ReleaseManager(tiller);
                hapi.services.tiller.Tiller.InstallReleaseRequest.Builder requestBuilder = hapi.services.tiller.Tiller.InstallReleaseRequest.newBuilder();
                requestBuilder.setNamespace(defaultNamespace);
                requestBuilder.setTimeout(300L);
                requestBuilder.setName(releaseName);
                requestBuilder.setWait(true);
                URI uri = URI.create(chartUrl);
                URL url = uri.toURL();
                URLChartLoader chartLoader = new URLChartLoader();
                ChartOuterClass.Chart.Builder chartBuilder = chartLoader.load(url);
                Future<hapi.services.tiller.Tiller.InstallReleaseResponse> releaseFuture = releaseManager.install(requestBuilder, chartBuilder);
                while (!releaseFuture.isDone()) {
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            externalIP = client.services().inNamespace(defaultNamespace).withName(releaseName).get().getStatus().getLoadBalancer().getIngress().get(0).getIp();
            svcPort = client.services().inNamespace(defaultNamespace).withName(releaseName).get().getSpec().getPorts().get(0).getPort().toString();
            releaseUrl.append(";").append(externalIP).append(":").append(svcPort);
            resReleaseName.append(";").append(releaseName);
        }
        HashMap<String, String> response = new HashMap<>();
        response.put("releaseUrl", releaseUrl.substring(1));
        response.put("releaseName", resReleaseName.substring(1));
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteApp")
    public void deleteApp(@RequestBody Map<String, ArrayList<String>> params) throws InterruptedException {
        List<String> releaseNameList = params.get("releaseName");
        DefaultKubernetesClient client = k8sClient();
        try(Tiller tiller = new Tiller(client)){
            ReleaseManager releaseManager = new ReleaseManager(tiller);
            for (String releaseName : releaseNameList) {
                hapi.services.tiller.Tiller.UninstallReleaseRequest.Builder uninstallReleaseBuilder = hapi.services.tiller.Tiller.UninstallReleaseRequest.newBuilder();
                uninstallReleaseBuilder.setName(releaseName);
                uninstallReleaseBuilder.setPurge(true);
                hapi.services.tiller.Tiller.UninstallReleaseRequest uninstallReleaseRequest = uninstallReleaseBuilder.build();
                Future<hapi.services.tiller.Tiller.UninstallReleaseResponse> uninstallReleaseFuture = releaseManager.uninstall(uninstallReleaseRequest);
                while (!uninstallReleaseFuture.isDone()) {
                    Thread.sleep(100);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int releaseNum = 0;

    @Value("${k8s_master}")
    private String masterUrl;

    @Value("${k8s_token}")
    private String token;

    @Value("${k8s_namespace}")
    private String defaultNamespace;

    private DefaultKubernetesClient k8sClient() {
        Config config = new ConfigBuilder().withOauthToken(token).withTrustCerts(true).withMasterUrl(masterUrl).build();
        return new DefaultKubernetesClient(config);
    }
}

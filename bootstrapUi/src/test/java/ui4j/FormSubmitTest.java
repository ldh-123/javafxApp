package ui4j;

import io.webfolder.ui4j.api.browser.BrowserEngine;
import io.webfolder.ui4j.api.browser.BrowserFactory;
import io.webfolder.ui4j.api.browser.Page;
import io.webfolder.ui4j.api.browser.PageConfiguration;
import io.webfolder.ui4j.api.dom.Document;
import io.webfolder.ui4j.api.interceptor.Interceptor;
import io.webfolder.ui4j.api.interceptor.Request;
import io.webfolder.ui4j.api.interceptor.Response;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ldh on 2018/3/29.
 */
public class FormSubmitTest {

    public static void main(String[] args) throws InterruptedException {
        String url = "https://abs-in.idumiao.com/user/login";
        BrowserEngine browser = BrowserFactory.getWebKit();
//        CountDownLatch latch = new CountDownLatch(1);
        Interceptor interceptor = new Interceptor() {
            @Override
            public void beforeLoad(Request request) {
            }
            @Override
            public void afterLoad(Response response) {
                System.out.println(response.getUrl());
            }
        };
        PageConfiguration configuration = new PageConfiguration(interceptor);
        Page page = browser.navigate(url, configuration);
        page.show();
        Document doc = page.getDocument();
        doc.query("#UserName").get().setValue("xiongfei.lei");
        doc.query("#UserPass").get().setValue("8uAsZ6n5rd");
        doc.query("#essToken").get().setValue("742034");
        doc.query("form").get().getForm().get().submit();
        String response = doc.getBody().getText().get();
        System.out.println(response);
        Thread.sleep(100);
        doc = page.getDocument();
        doc.query("body > div > div.panel.panel-default > table > tbody > tr:nth-child(1) > td:nth-child(5) > button:nth-child(1)").get().click();
        String response2 = doc.getBody().getText().get();
        System.out.println(response2);
        Thread.sleep(100);
        doc = page.getDocument();
        doc.query("body > div > div.panel.panel-default > div.panel-heading > div > form > button.btn.btn-danger.navbar-left").get().click();
        Thread.sleep(100);
        doc = page.getDocument();
        doc.query("body > div > ul > li:nth-child(2) > a").get().click();
        Thread.sleep(100);
        doc = page.getDocument();
        doc.query("#上海如山金融信息服务有限公司").get().click();
        Thread.sleep(100);


        doc.query("#adjust_panel > div > div:nth-child(9) > button:nth-child(1)").get().click();
        page.addDocumentListener(e->{

        });
//        latch.await();
        doc = page.getDocument();
        String text = doc.query("#adjust_40006001").get().getText().get();
        System.out.println("end:" + text);
        browser.shutdown();
    }
}

package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @BeforeEach
    public void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://185.119.57.64:9999");
    }

    @AfterAll
    public static void shouldDelInfo() {
        DataHelper.deleteOldData();
    }

    @Test
    public void shouldLoginIfValidCredentials() {
        var loginPage = new LoginPage();

        loginPage
                .validLogin(DataHelper.getLoginInfo())
                .validVerify(DataHelper.getAuthCode());
    }

    @Test
    public void shouldBlockIfInvalidPassword() {
        var loginPage = new LoginPage();

        loginPage.tooManyAttemptsAssert(DataHelper.getLoginInfo());
    }
}
